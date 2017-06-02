package history.spring.data.neo4j.repositories;

import history.spring.data.neo4j.domain.*;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * @author junyuan
 */
@RepositoryRestResource(collectionResourceRel = "history", path = "history")
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
    @Query("MATCH (e:Event)-[r:DoneBy]->(p:Person) RETURN e,r,p LIMIT {limit}")
    Collection<Event> graph(@Param("limit") int limit);

    @Query("MERGE (a:Event { name: doneBy.event.name }\n" +
        "MERGE (b:Person { name: doneBy.person.name })\n" +
        "MERGE (a)-[:Done_By]->(b)")
    void saveEventDoneBy(@Param("doneBy")DoneBy doneBy);

    @Query("MERGE (a:City { name: inCountry.city.name }\n" +
            "MERGE (b:Country { name: inCountry.country.name })\n" +
            "MERGE (a)-[:In_Country]->(b)")
    void saveCityInCountry(@Param("inCountry")InCountry inCountry);

    @Query("MERGE (a:Country { name: inContinent.country.name }\n" +
            "MERGE (b:Continent { name: inContinent.continent.name })\n" +
            "MERGE (a)-[:In_Continent]->(b)")
    void saveCountryInContinent(@Param("inContinent")InContinent inContinent);

    @Query("MERGE (a:Event { name: happenIn.event.name }\n" +
            "MERGE (b:Date { name: happenIn.date.name })\n" +
            "MERGE (a)-[:Happen_In]->(b)")
    void saveEventHappenIn(@Param("happenIn")HappenIn happenIn);
}