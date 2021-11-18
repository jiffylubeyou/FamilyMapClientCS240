package kstrong3.familymap;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import model.Event;
import model.Person;

public class DataCache {

    private static DataCache instance = new DataCache();

    //List<Person> people;
    //List<Event> events;
    //The String keys should be the personIDs and eventIDs
    TreeMap<String, Person> people = new TreeMap<String, Person>();
    TreeMap<String, Event> Events = new TreeMap<String, Event>();
    TreeMap<String, List<Event>> personEvents = new TreeMap<String, List<Event>>();
    //This string is a personID
    TreeSet<String> paternalAncestors = new TreeSet<String>();
    TreeSet<String> maternalAncestors = new TreeSet<String>();

    public static DataCache getInstance()
    {
        return instance;
    }

    private DataCache() {}

//    public Person getPersonByID(String personID)
//    {
//
//    }

//    public Event getEventbyID(String eventID)
//    {
//
//    }

//    public List<Event> getPersonEvents(String personID)
//    {
//
//    }
}
