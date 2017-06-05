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
public class Occupation {
    @GraphId
    private Long id;

    private String name;

    @Relationship(type = "Work_As", direction = Relationship.INCOMING)
    private List<WorkAs> workAses = new LinkedList<>();

    public Occupation(String name) {
        this.name = name;
    }

    public Occupation() {
    }

    public String getName() {
        return name;
    }

    public List<WorkAs> getWorkAses() {
        return workAses;
    }
}
