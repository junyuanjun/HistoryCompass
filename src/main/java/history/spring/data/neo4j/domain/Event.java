package history.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author junyuan
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class Event {
    @GraphId
    private Long id;

    public String name;

    @Relationship(type = "Participate_In", direction = Relationship.INCOMING)
    private List<Person> persons = new LinkedList<>();

    @Relationship(type = "In_City")
    public City city = new City();

    @Relationship(type = "Happen_In")
    public Date happen_date = new Date();

    @Relationship(type = "Last_To")
    public Date end_date = new Date();

    public Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getHappen_date() {
        return happen_date;
    }

    public void setHappen_date(Date happen_date) {
        this.happen_date = happen_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
