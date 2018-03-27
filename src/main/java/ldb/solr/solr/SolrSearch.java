package ldb.solr.solr;

import ldb.solr.jpa.EntityManagerUtility;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SolrSearch extends SolrServer {
    private EntityManager entityManager;
    private final Function<String,SolrDto.SolrId>mapToSolrId = SolrDto.SolrId::new;
    private final Function<SolrDto.SolrId, SolrIndexed>mapToSolrIndexed = solrId-> entityManager.find(solrId.getEntityClass(), solrId.getId());

    private String buildQueryString( String value){
        String[] tokens = value.split("\\s");
        StringBuffer result = new StringBuffer();
        for(String token : tokens){
            result.append(String.format("name:%s ", token));
        }

        return result.toString();
    }


    public List<SolrIndexed> search(String searchString) throws IOException, SolrServerException {

        final String[]fields = {"id"};
        SolrQuery query = new SolrQuery();
        query.setQuery(buildQueryString(searchString));
        query.setFields(fields);
        query.setSort("score", SolrQuery.ORDER.desc);

        SolrDocumentList documents = getSolrClient().query(query).getResults();

        entityManager = EntityManagerUtility.getEntityManager();
        entityManager.getTransaction().begin();
        List<SolrIndexed> resultsList = documents.stream().
                map(doc->(String)doc.get("id")).
                map(mapToSolrId).
                map(mapToSolrIndexed).
                collect(Collectors.toList());
        entityManager.close();
        return resultsList;
    }
}
