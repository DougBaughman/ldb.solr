package ldb.solr.entity;

import ldb.solr.solr.SolrDto;
import ldb.solr.solr.SolrIndexed;
import ldb.solr.solr.SolrUpdate;

import javax.persistence.*;

@Entity
@EntityListeners(SolrUpdate.class)
@Table(name = "people")
public class Person implements SolrIndexed {
    private Long id;
    private String name;

    @Id
    @SequenceGenerator(name="people_id_seq", sequenceName = "people_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "people_id_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    @Transient
    @Override
    public SolrDto getSolrDto() {
        SolrDto dto = new SolrDto();
        dto.setId(this.getClass(), id);
        dto.setName(name);

        return dto;
    }
}
