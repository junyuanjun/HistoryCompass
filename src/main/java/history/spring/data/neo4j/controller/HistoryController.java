package history.spring.data.neo4j.controller;

import java.util.HashMap;
import java.util.Map;

import history.spring.data.neo4j.domain.*;
import history.spring.data.neo4j.repositories.EventRepository;
import history.spring.data.neo4j.repositories.PersonRepository;
import history.spring.data.neo4j.services.HistoryService;
import org.neo4j.driver.internal.value.IntegerValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jun Yuan
 */
@RestController("/")
public class HistoryController {

//	final MovieService movieService;
	final HistoryService historyService;

	@Autowired
	public HistoryController(HistoryService historyService) {
		this.historyService = historyService;
	}

	@RequestMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return historyService.graph(limit == null ? 100 : limit);
	}

	@RequestMapping("/save_person/{personName}/{personGender}")
	public boolean savePerson(@PathVariable("personName") String personName,
							  @PathVariable("personGender") String personGender) {
		try {
			historyService.savePerson(personName, personGender);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@RequestMapping("/save/{query}")
	public boolean save(@PathVariable("query") String query) {
		try {
			if (!query.contains("+")) {
				throw new Exception();
			}
			String[] words = query.split("&");
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < words.length; i += 2) {
				map.put(words[i], words[i+1]);
			}
			if (map.containsKey("人物")) {
				String name = map.get("人物");
				String gender = "Unknown";
				Person person = new Person(name);
				historyService.savePerson(name, gender);
				if (map.containsKey("出生年份")) {
					int year = Integer.parseInt(map.get("出生年份"));
					BornIn bornIn = new BornIn(person, new Date(year));
					historyService.savePersonBornIn(bornIn);
				}
				if (map.containsKey("出生城市")) {
					City city = new City(map.get("出生城市"));
					historyService.savePersonFromCity(new From(person, city));
					if (map.containsKey("出生国家")) {
						Country country = new Country(map.get("出生国家"));
						historyService.saveCityInCountry(new InCountry(city, country));
						if (map.containsKey("出生大洲")) {
							Continent continent = new Continent(map.get("出生大洲"));
							historyService.saveCountryInContinent(new InContinent(country, continent));
						}
					}
				}
				if (map.containsKey("职业为")) {
					Occupation occupation = new Occupation(map.get("职业为"));
					historyService.savePersonWorkAs(new WorkAs(person, occupation));
				}
			} else if (map.containsKey("事件")) {
				Event event = new Event(map.get("事件"));
				if (map.containsKey("发生年份")) {
					int year = Integer.parseInt(map.get("发生年份"));
					HappenIn happenIn = new HappenIn(event, new Date(year));
					historyService.saveEventHappenIn(happenIn);
				}
				if (map.containsKey("参与者有")) {
					Person participant = new Person(map.get("参与者有"));
					DoneBy doneBy = new DoneBy(event, participant);
					historyService.saveEventDoneBy(doneBy);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
