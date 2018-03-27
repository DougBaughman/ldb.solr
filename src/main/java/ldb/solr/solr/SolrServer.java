package ldb.solr.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrServer {
    protected SolrClient getSolrClient(){
        return new HttpSolrClient("http://localhost:8983/solr/ldb");
    }

}
