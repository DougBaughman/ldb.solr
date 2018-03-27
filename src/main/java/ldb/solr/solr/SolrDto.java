package ldb.solr.solr;


import org.apache.solr.client.solrj.beans.Field;

public class SolrDto {
    public static class SolrId{
        private final Class<? extends SolrIndexed> entityClass;
        private final Long id;

        public SolrId(Class<? extends SolrIndexed> clazz, Long id) {
            this.entityClass = clazz;
            this.id = id;
        }

        public SolrId( String solrId){
            int dotPos = solrId.lastIndexOf('.');
            if( dotPos == -1){
                throw new RuntimeException("Invalid SolrId:  " + solrId);
            }
            try {
                this.entityClass = (Class<? extends SolrIndexed>) Class.forName(solrId.substring(0, dotPos));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("SolrId has invalid class" + solrId);
            }
            this.id = Long.valueOf(solrId.substring(dotPos +1));
        }

        public Class<? extends SolrIndexed> getEntityClass() {
            return entityClass;
        }

        public Long getId() {
            return id;
        }

        @Override
        public String toString() {
            return String.format("%s.%d", entityClass.getCanonicalName(), id);
        }
    }
    @Field
    private String id;
    @Field
    private String name;

    public String getId() {
        return id;
    }

    public void setId(Class<? extends SolrIndexed>clazz, Long id){
        this.id = new SolrId(clazz, id).toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
