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

		public String name;

		public String gender;

//	@Relationship(type = "ACTED_IN")
//	private List<Movie> movies = new ArrayList<>();

		@Relationship(type = "Done_By", direction = Relationship.INCOMING)
		private List<DoneBy> doneByList = new LinkedList<>();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DoneBy> getDoneByList() {
		return doneByList;
	}

	public void setDoneByList(List<DoneBy> doneByList) {
		this.doneByList = doneByList;
	}

	public City getCity() {
		return city;
	}

	public Industry getIndustry() {
		return industry;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	//	public List<Movie> getMovies() {
//		return movies;
//	}
}
