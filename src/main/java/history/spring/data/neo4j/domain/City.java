package history.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jun Yuan
 */

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class City {
    @GraphId
    private Long id;

    public String name;

    @Relationship(type = "From", direction = Relationship.INCOMING)
    public List<From> froms = new LinkedList<>();

    @Relationship(type = "In_Country")
    public Country country = new Country();

    @Relationship(type = "In_City", direction = Relationship.INCOMING)
    private List<HappenIn> happenIns = new LinkedList<>();

    public City(String name) {
        this.name = name;
    }

    public City() {
    }

    public String getName() {
        return name;
    }

    public List<From> getFroms() {
        return froms;
    }

    public Country getCountry() {
        return country;
    }
}
