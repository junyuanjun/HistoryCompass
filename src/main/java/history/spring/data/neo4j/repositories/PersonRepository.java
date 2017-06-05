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
    /*Add Operation*/
    @Query("MERGE (a:Person { name: {personName}, gender: {personGender} })")
    void savePerson(@Param("personName")String personName, @Param("personGender")String personGender);

    @Query("MERGE (a:Person { name: {personName} })\n" +
            "MERGE (b:Occupation { name: {occupation} })\n" +
            "MERGE (a)-[:Work_As]->(b)")
    void savePersonWorkAs(@Param("personName")String personName, @Param("occupation")String occupation);

    @Query("MERGE (a:Person { name: {personName} })\n" +
            "MERGE (b:Date { year: {year} })\n" +
            "MERGE (a)-[:Born_In]->(b)")
    void savePersonBornIn(@Param("personName")String personName, @Param("year")int year);

    @Query("MERGE (a:Person { name: {personName} })\n" +
            "MERGE (b:City { name: {cityName} })\n" +
            "MERGE (a)-[:From]->(b)")
    void savePersonFromCity(@Param("personName")String personName, @Param("cityName")String cityName);

    @Query("MERGE (a:City { name: {cityName} })\n" +
            "MERGE (b:Country { name: {countryName} })\n" +
            "MERGE (a)-[:In_Country]->(b)")
    void saveCityInCountry(@Param("cityName")String cityName, @Param("countryName")String countryName);

    @Query("MERGE (a:Country { name: {countryName} })\n" +
            "MERGE (b:Continent { name: {continentName} })\n" +
            "MERGE (a)-[:In_Continent]->(b)")
    void saveCountryInContinent(@Param("countryName")String countryName, @Param("continentName")String continentName);

    /*Search Operation*/
    @Query("MATCH (e:Event)-[r:Done_By]->(p:Person) RETURN e,r,p LIMIT {limit}")
    Collection<Person> graph(@Param("limit") int limit);

    /*Search Operation*/
    @Query("MATCH (p:Person{name: {name}})-[r]->(a) RETURN p,r,a")
    Collection<Person> findByName(@Param("name")String name);

    @Query("MATCH (p:Person)-[r]->(a)\n" +
            "WHERE p.name CONTAINS {name}\n" +
            "RETURN p,r,a")
    Collection<Person> findByNameLike(@Param("name")String name);

    @Query("{query}")
    Collection<Person> findByQuery(@Param("query")String query);
}
