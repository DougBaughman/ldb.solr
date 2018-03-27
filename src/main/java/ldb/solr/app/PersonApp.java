package ldb.solr.app;

import ldb.solr.crud.PersonCrud;
import ldb.solr.entity.Person;
import ldb.solr.solr.SolrIndexed;
import ldb.solr.solr.SolrSearch;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

public class PersonApp {
    private static String genPersonName(){
        long seq = Math.round(Math.random() * 10000.0);
        return "George " + seq + " Washington";
    }
    public static void main(String[]args) throws IOException, SolrServerException {
        Person person = new Person();
        person.setName(genPersonName());
        person = new PersonCrud().create(person);
        System.out.println("Created Person:  " + person);

        List<SolrIndexed>searchedPeople = new SolrSearch().search(person.getName());
        searchedPeople.forEach(p->System.out.println("Search Result:  " + p));

        System.exit(0);
    }
}
