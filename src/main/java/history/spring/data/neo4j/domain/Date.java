package history.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedList;
import java.util.List;

/**
 * @author junyuan
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class Date {
    @GraphId
    private Long id;

    public int year;

    @Relationship(type = "Born_In", direction = Relationship.INCOMING)
    private List<Person> persons = new LinkedList<>();

    @Relationship(type = "Happen_In", direction = Relationship.INCOMING)
    private List<Event> start_events = new LinkedList<>();

    @Relationship(type = "Last_To", direction = Relationship.INCOMING)
    private List<Event> end_events = new LinkedList<>();

    public Date(int year) {
        this.year = year;
    }

    public Date() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Event> getStart_events() {
        return start_events;
    }

    public void setStart_events(List<Event> start_events) {
        this.start_events = start_events;
    }

    public List<Event> getEnd_events() {
        return end_events;
    }

    public void setEnd_events(List<Event> end_events) {
        this.end_events = end_events;
    }
}
