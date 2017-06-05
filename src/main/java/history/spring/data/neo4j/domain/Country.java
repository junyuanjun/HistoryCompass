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
public class Country {
    @GraphId
    private Long id;

    public String name;

    @Relationship(type = "In_Country", direction = Relationship.INCOMING)
    public InCountry inCountry = new InCountry();

    @Relationship(type = "In_Continent")
    public Continent continent = new Continent();

    public Country(String name) {
        this.name = name;
    }

    public Country() {
    }

    public String getName() {
        return name;
    }

    public InCountry getInCountry() {
        return inCountry;
    }

    public Continent getContinent() {
        return continent;
    }
}
