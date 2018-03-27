package ldb.solr.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import javax.persistence.*;
import java.io.IOException;

public class SolrUpdate extends SolrServer {

    @PostPersist
    @PostUpdate
    public void onCreateUpdate( Object object){
        if (object instanceof SolrIndexed) {
            SolrIndexed indexed = (SolrIndexed) object;
            SolrClient client = getSolrClient();
            try {
                client.addBean(indexed.getSolrDto());
                client.commit();
            } catch (IOException | SolrServerException e) {
                e.printStackTrace();
            }
        }
    }

    @PostRemove
    public void onDelete( Object object){
        if (object instanceof SolrIndexed) {
            SolrIndexed indexed = (SolrIndexed) object;
            SolrClient client = getSolrClient();
            try {
                client.deleteById(indexed.getSolrDto().getId());
                client.commit();
            } catch (IOException | SolrServerException e) {
                e.printStackTrace();
            }
        }
    }

}
