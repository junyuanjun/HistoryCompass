package history.spring.data.neo4j.domain;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author junyuan
 */

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class Person {

		@GraphId
		private Long id;

		private String name;

		public Gender gender;

//	@Relationship(type = "ACTED_IN")
//	private List<Movie> movies = new ArrayList<>();

		@Relationship(type = "Participate_In")
		private List<Event> events = new LinkedList<>();

		@Relationship(type = "From_City")
		private City city = new City();

//		@Relationship(type = "From_Country")
//		private Country country = new Country();
//
//		@Relationship(type = "From_Continent")
//		private Continent continent = new Continent();

	@Relationship(type = "Work_With")
	private Industry industry = new Industry();

	@Relationship(type = "Work_As")
	private Occupation occupation = new Occupation();

	@Relationship(type = "Born_In")
	private Date date = new Date();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Event> getEvents() {
		return events;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	//	public List<Movie> getMovies() {
//		return movies;
//	}
}
