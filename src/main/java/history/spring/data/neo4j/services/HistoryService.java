package history.spring.data.neo4j.services;

import java.util.*;

import history.spring.data.neo4j.domain.*;
import history.spring.data.neo4j.repositories.EventRepository;
import history.spring.data.neo4j.repositories.MovieRepository;
import history.spring.data.neo4j.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoryService {

//    @Autowired MovieRepository movieRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    EventRepository eventRepository;

    private Map<String, Object> toD3Format(Collection<Event> events) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Event> result = events.iterator();
        while (result.hasNext()) {
//            Movie movie = result.next();
//            nodes.add(map("title", movie.getTitle(), "label", "movie"));
//            int target = i;
//            i++;
//            for (Role role : movie.getRoles()) {
//                Map<String, Object> actor = map("title", role.getPerson().getName(), "label", "actor");
//                int source = nodes.indexOf(actor);
//                if (source == -1) {
//                    nodes.add(actor);
//                    source = i++;
//                }
//                rels.add(map("source", source, "target", target));
//            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, Object>  graph(int limit) {
        Collection<Event> result = eventRepository.graph(limit);
        return toD3Format(result);
    }

    /*Person Save*/
    public void savePerson(String name, String gender) {
        personRepository.savePerson(name, gender);
    }

    public void savePersonWorkAs(WorkAs workAs) {
        personRepository.savePersonWorkAs(workAs);
    }

    public void savePersonBornIn(BornIn bornIn){
        personRepository.savePersonBornIn(bornIn);
    }

    public void savePersonFromCity(From from) {
        personRepository.savePersonFromCity(from);
    }

    public void saveCityInCountry(InCountry inCountry) {
        personRepository.saveCityInCountry(inCountry);
    }

    public void saveCountryInContinent(InContinent inContinent) {
        personRepository.saveCountryInContinent(inContinent);
    }

    /*Event Save*/
    public void saveEventDoneBy(DoneBy doneBy) {
        eventRepository.saveEventDoneBy(doneBy);
    }

    public void saveEventHappenIn(HappenIn happenIn) {
        eventRepository.saveEventHappenIn(happenIn);
    }
}