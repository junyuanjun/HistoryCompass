package history.spring.data.neo4j.services;

import java.util.*;

import history.spring.data.neo4j.domain.*;
import history.spring.data.neo4j.repositories.EventRepository;
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

    private Map<String, Object> toD3Format(Collection<Person> persons) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Person> result = persons.iterator();
        while (result.hasNext()) {
            Person person = result.next();
            nodes.add(map("name", person.getName(), "label", "person"));
            int target = i++;
            for (DoneBy doneBy : person.getDoneByList()) {
                Map<String, Object> event = map("name", doneBy.getEvent().getName(), "label", "event");
                int source = nodes.indexOf(event);
                if (source == -1) {
                    nodes.add(event);
                    source = i++;
                }
                rels.add(map("source", source, "target", target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> getPersonResult(Collection<Person> persons) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Person> result = persons.iterator();
        while (result.hasNext()) {
            Person person = result.next();
            nodes.add(map("name", person.getName(), "label", "person"));
            int target = i++;

            nodes.add(map("name", person.getDate().getYear(), "label", "date"));
            int source = i++;
            rels.add(map("source", target, "target", source));

            nodes.add(map("name", person.getOccupation().getName(), "label", "occupation"));
            source = i++;
            rels.add(map("source", target, "target", source));

            nodes.add(map("name", person.getCity().getName(), "label", "city"));
            source = i++;
            rels.add(map("source", target, "target", source));

            for (DoneBy doneBy : person.getDoneByList()) {
                Map<String, Object> event = map("name", doneBy.getEvent().getName(), "label", "event");
                source = nodes.indexOf(event);
                if (source == -1) {
                    nodes.add(event);
                    source = i++;
                }
                rels.add(map("source", source, "target", target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> getEventResult(Collection<Event> events) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Event> result = events.iterator();
        while (result.hasNext()) {
            Event event = result.next();
            nodes.add(map("name", event.getName(), "label", "event"));
            int source = i++;

            nodes.add(map("name", event.getCity(), "label", "city"));
            int target = i++;
            rels.add(map("source", source, "target", target));

            nodes.add(map("name", event.getHappen_date().getYear(), "label", "date"));
            target = i++;
            rels.add(map("source", source, "target", target));

            for (Person person : event.getPersons()) {
                Map<String, Object> personMap = map("name", person.getName(), "label", "event");
                target = nodes.indexOf(personMap);
                if (target == -1) {
                    nodes.add(personMap);
                    target = i++;
                }
                rels.add(map("source", source, "target", target));
            }
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
        Collection<Person> result = personRepository.graph(limit);
//        return toD3Format(result);
        Map<String, Object> resultInD3 = toD3Format(result);
        System.out.println("*************get graph result, with length = "+ resultInD3.size() + "*****************");
        return resultInD3;
    }

    /*Person Save*/
    public void savePerson(String name, String gender) {
        personRepository.savePerson(name, gender);
    }

    public void savePersonWorkAs(WorkAs workAs) {
        personRepository.savePersonWorkAs(workAs.getPerson().getName(), workAs.getOccupation().getName());
    }

    public void savePersonBornIn(BornIn bornIn){
        personRepository.savePersonBornIn(bornIn.getPerson().getName(), bornIn.getDate().getYear());
    }

    public void savePersonFromCity(From from) {
        personRepository.savePersonFromCity(from.getPerson().getName(), from.getCity().getName());
    }

    public void saveCityInCountry(InCountry inCountry) {
        personRepository.saveCityInCountry(inCountry.getCity().getName(), inCountry.getCountry().getName());
    }

    public void saveCountryInContinent(InContinent inContinent) {
        personRepository.saveCountryInContinent(inContinent.getCountry().getName(), inContinent.getContinent().getName());
    }

    /*Event Save*/
    public void saveEventDoneBy(DoneBy doneBy) {
        eventRepository.saveEventDoneBy(doneBy.getEvent().getName(), doneBy.getPerson().getName());
    }

    public void saveEventHappenIn(HappenIn happenIn) {
        eventRepository.saveEventHappenIn(happenIn.getEvent().getName(), happenIn.getDate().getYear());
    }

    /*Search Event*/
    public Map<String, Object> findEventByNameLike(String name) {
        Collection<Event> result = eventRepository.findByNameLike(name);
        Map<String, Object> resultInD3 = getEventResult(result);
        System.out.println("*************get findEventByNameLike result, with length = "
                + resultInD3.size() + "*****************");
        return resultInD3;
    }

    /*Search Person*/
    public Map<String, Object> findPersonByNameLike(String name) {
        Collection<Person> result = personRepository.findByNameLike(name);
//        return toD3Format(result);
        Map<String, Object> resultInD3 = getPersonResult(result);
        System.out.println("*************get findPersonByNameLike result, with length = "
                + resultInD3.size() + "*****************");
        return resultInD3;
    }

    /*Search Date*/
}