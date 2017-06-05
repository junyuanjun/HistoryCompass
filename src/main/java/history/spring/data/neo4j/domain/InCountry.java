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
@RelationshipEntity(type = "In_Country")
public class InCountry {
    @GraphId
    private Long id;

    @StartNode
    public City city;

    @EndNode
    public Country country;

    public InCountry(City city, Country country) {
        this.city = city;
        this.country = country;
    }

    public InCountry() {
    }

    public City getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }
}
