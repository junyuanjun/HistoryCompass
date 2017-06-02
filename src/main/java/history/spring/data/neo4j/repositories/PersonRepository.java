package history.spring.data.neo4j.repositories;

import history.spring.data.neo4j.domain.*;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author junyuan
 */
//@Repository
@RepositoryRestResource(collectionResourceRel = "history", path = "history")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
//    @Query("MATCH (e:Event)-[r:DoneBy]->(p:Person) RETURN e,r,p LIMIT {limit}")
//    Collection<Person> graph(@Param("limit") int limit);

    @Query("MERGE (a:Person { name: name, gender: gender }")
    void savePerson(@Param("name")String name, @Param("gender")String gender);

    @Query("MERGE (a:Person { name: workAs.person.name }\n" +
            "MERGE (b:Occupation { name: workAs.occupation.name })\n" +
            "MERGE (a)-[:Work_As]->(b)")
    void savePersonWorkAs(@Param("workAs")WorkAs workAs);

    @Query("MERGE (a:Person { name: bornIn.person.name }\n" +
            "MERGE (b:Date { year: bornIn.date.year })\n" +
            "MERGE (a)-[:Born_In]->(b)")
    void savePersonBornIn(@Param("bornIn")BornIn bornIn);

    @Query("MERGE (a:Person { name: from.person.name }\n" +
            "MERGE (b:City { name: from.city.name })\n" +
            "MERGE (a)-[:From]->(b)")
    void savePersonFromCity(@Param("from")From from);

    @Query("MERGE (a:City { name: inCountry.city.name }\n" +
            "MERGE (b:Country { name: inCountry.country.name })\n" +
            "MERGE (a)-[:In_Country]->(b)")
    void saveCityInCountry(@Param("inCountry")InCountry inCountry);

    @Query("MERGE (a:Country { name: inContinent.country.name }\n" +
            "MERGE (b:Continent { name: inContinent.continent.name })\n" +
            "MERGE (a)-[:In_Continent]->(b)")
    void saveCountryInContinent(@Param("inContinent")InContinent inContinent);
}
