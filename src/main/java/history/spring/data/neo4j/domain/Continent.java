package history.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


/**
 * @author Jun Yuan
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class Continent {
    @GraphId
    private Long id;

    public String name;

    @Relationship(type = "In_Continent", direction = Relationship.INCOMING)
    public InContinent inContinent = new InContinent();

    public Continent(String name) {
        this.name = name;
    }

    public Continent() {
    }

    public String getName() {
        return name;
    }

    public InContinent getInContinent() {
        return inContinent;
    }
}
