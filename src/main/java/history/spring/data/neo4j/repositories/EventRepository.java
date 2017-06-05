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
    /*Save Operation*/
    @Query("MERGE (a:Event { name: {eventName} })\n" +
        "MERGE (b:Person { name: {personName} })\n" +
        "MERGE (a)-[:Done_By]->(b)")
    void saveEventDoneBy(@Param("eventName")String eventName, @Param("personName")String personName);

    @Query("MERGE (a:Event { name: {eventName} }\n" +
            "MERGE (b:Date { year: {year} })\n" +
            "MERGE (a)-[:Happen_In]->(b)")
    void saveEventHappenIn(@Param("eventName")String eventName, @Param("year")int year);

    /*Search Operation*/
    @Query("MATCH (e:Event{name: {name}})-[r]->(a) RETURN e,r,a")
    Collection<Event> findByName(@Param("name")String name);

    @Query("MATCH (e:Event)-[r]->(a)\n" +
            "WHERE e.name CONTAINS {name}\n" +
            "RETURN e,r,a")
    Collection<Event> findByNameLike(@Param("name")String name);

    @Query("{query}")
    Collection<Event> findByQuery(@Param("query")String query);
}