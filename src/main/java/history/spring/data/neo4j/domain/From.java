package history.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @author junyuan
 */

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@RelationshipEntity(type = "From")
public class From {
    @GraphId
    private Long id;

    @StartNode
    public Person person;

    @EndNode
    public City city;

    public From(Person person, City city) {
        this.person = person;
        this.city = city;
    }

    public Person getPerson() {
        return person;
    }

    public City getCity() {
        return city;
    }
}
