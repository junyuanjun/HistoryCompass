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
@RelationshipEntity(type = "In_Continent")
public class InContinent {
    @GraphId
    private Long id;

    @StartNode
    public Country country;

    @EndNode
    public Continent continent;

    public InContinent(Country country, Continent continent) {
        this.country = country;
        this.continent = continent;
    }

    public InContinent() {
    }

    public Country getCountry() {
        return country;
    }

    public Continent getContinent() {
        return continent;
    }
}
