package history.spring.data.neo4j.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import history.spring.data.neo4j.domain.*;
import history.spring.data.neo4j.services.HistoryService;
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

	@RequestMapping("/save/{query}")
	public Map<String, Object> save(@PathVariable("query") String query) {
		Map<String, Object> result = new HashMap<>(1);
		try {
			if (!query.contains("+")) {
				throw new Exception();
			}
			String[] words = query.split("\\+");
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < words.length; i += 2) {
				map.put(words[i], words[i+1]);
			}
			if (map.containsKey("人物")) {
				String name = map.get("人物");
				String gender = "Unknown";
				Person person = new Person(name);
				historyService.savePerson(name, gender);
				System.out.println("*************save person done**************");
				if (map.containsKey("出生年份")) {
					int year = Integer.parseInt(map.get("出生年份"));
					BornIn bornIn = new BornIn(person, new Date(year));
					System.out.println("BornIn.data.year=" + bornIn.date.year);
					historyService.savePersonBornIn(bornIn);
					System.out.println("*************save BornIn done**************");
				}
				if (map.containsKey("出生城市")) {
					City city = new City(map.get("出生城市"));
					historyService.savePersonFromCity(new From(person, city));
					System.out.println("*************save FromCity done**************");
					if (map.containsKey("出生国家")) {
						Country country = new Country(map.get("出生国家"));
						historyService.saveCityInCountry(new InCountry(city, country));
						System.out.println("*************save InCountry done**************");
						if (map.containsKey("出生大洲")) {
							Continent continent = new Continent(map.get("出生大洲"));
							historyService.saveCountryInContinent(new InContinent(country, continent));
							System.out.println("*************save InContinent done**************");
						}
					}
				}
				if (map.containsKey("职业为")) {
					Occupation occupation = new Occupation(map.get("职业为"));
					historyService.savePersonWorkAs(new WorkAs(person, occupation));
					System.out.println("*************save occupation done**************");
				}
			} else if (map.containsKey("事件")) {
				Event event = new Event(map.get("事件"));
				if (map.containsKey("发生年份")) {
					int year = Integer.parseInt(map.get("发生年份"));
					HappenIn happenIn = new HappenIn(event, new Date(year));
					historyService.saveEventHappenIn(happenIn);
					System.out.println("*************save happen year done**************");
				}
				if (map.containsKey("参与者有")) {
					Person participant = new Person(map.get("参与者有"));
					DoneBy doneBy = new DoneBy(event, participant);
					historyService.saveEventDoneBy(doneBy);
					System.out.println("*************save DoneBy done**************");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", e.getMessage());
			System.out.println("*******************Add data*************************");
			System.out.println(e.getStackTrace());
			return result;
		}
		System.out.println("*******************Add data*************************");
		System.out.println("Done!");
		result.put("msg", "Done");
		return result;
	}

	@RequestMapping("/search/{query}")
	public List<Map<String, Object>> search(@PathVariable("query") String query) {
		Map<String, Object> result = new HashMap<>(1);
		try {
			if (!query.contains("+")) {
				return findByNameLike(query);
			}
			String[] words = query.split("\\+");
			List<String> parts = new ArrayList<String>(words.length);
			for (String word : words) {
				parts.add(word);
			}


			if (parts.contains("同时代")) {

			} else if (parts.contains("之前")) {

			} else if (parts.contains("之后")) {

			}
		}catch (Exception e) {
			e.printStackTrace();
			result.put("msg", e.getMessage());
			System.out.println("*******************Search End With Error*************************");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			return list;
		}
		return null;
	}

	private List<Map<String, Object>> findByNameLike(String query) {
		Map<String, Object> eventResult = historyService.findEventByNameLike(query);
		Map<String, Object> personResult = historyService.findPersonByNameLike(query);
		List<Map<String, Object>> result = new ArrayList<>();
		result.add(eventResult);
		result.add(personResult);
		return result;
	}
}
