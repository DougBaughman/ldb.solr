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
        PersonCrud crud = new PersonCrud();
        SolrSearch search = new SolrSearch();

        // Create a new person
        Person person = new Person();
        person.setName(genPersonName());
        person = crud.create(person);
        System.out.println("Created Person:  " + person);

        // Search for people using the new person's name as the search text
        // Expect all people to be returned (since all have names like 'George * Washington' but the new person will be first
        List<SolrIndexed>searchedPeople = search.search(person.getName());
        searchedPeople.forEach(p->System.out.println("Search Result:  " + p));

        // Update person with a new name
        person.setName(genPersonName());
        person = crud.update(person.getId(), person);
        System.out.println( "Updated person:  " + person);

        // Now search for people using the updated person's name as the search text
        // Again, the updated person will be first in the list
        searchedPeople = search.search(person.getName());
        searchedPeople.forEach(p->System.out.println("Search Result:  " + p));

        // Now delete the person and then search people
        // The deleted person should not be in the search results.
        crud.delete(person.getId());
        searchedPeople = search.search(person.getName());
        searchedPeople.forEach(p->System.out.println("Search Result:  " + p));

        System.exit(0);
    }
}
