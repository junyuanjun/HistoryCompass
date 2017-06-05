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
    private List<BornIn> bornIn = new LinkedList<>();

    @Relationship(type = "Happen_In", direction = Relationship.INCOMING)
    private List<HappenIn> start_events = new LinkedList<>();

    public Date(int year) {
        this.year = year;
    }

    public Date() {
    }

    public int getYear() {
        return year;
    }
}
