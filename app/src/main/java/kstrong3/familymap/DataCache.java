package kstrong3.familymap;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import model.Event;
import model.Person;

public class DataCache {

    private static DataCache instance = new DataCache();

    private String authToken;
    public static String AUTH_TOKEN_KEY = "asdf";
    public static String MESSAGE_KEY = "alksfslkj";

    public static String username;
    public static String personID;
    public static Person[] peopleArray;
    public static Event[] eventsArray;
    TreeMap<String, Person> people = new TreeMap<String, Person>();
    TreeMap<String, Event> events = new TreeMap<String, Event>();
    TreeMap<String, List<Event>> personEvents = new TreeMap<String, List<Event>>();
//    //This string is a personID
//    TreeSet<String> paternalAncestors = new TreeSet<String>();
//    TreeSet<String> maternalAncestors = new TreeSet<String>();

    public static DataCache getInstance()
    {
        return instance;
    }

    private DataCache() {}

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public String getAuthToken()
    {
        return this.authToken;
    }

    public void setUsername(String username) {
        DataCache.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public void setPersonID(String personID)
    {
        DataCache.personID = personID;
    }

    public static String getPersonID() {
        return personID;
    }

    public void setPeopleArray(Person[] people)
    {
        DataCache.peopleArray = people;
    }

    public static Person[] getPeopleArray() {
        return peopleArray;
    }

    public void setEventsArray(Event[] events)
    {
        DataCache.eventsArray = events;
    }

    public static Event[] getEventsArray() {
        return eventsArray;
    }

    public void populateMaps()
    {
        for (int i = 0; i < peopleArray.length; ++i)
        {
            people.put(peopleArray[i].getPersonID(), peopleArray[i]);
        }

        for (int i = 0; i < eventsArray.length; ++i)
        {
            events.put(eventsArray[i].getEventID(), eventsArray[i]);
        }

        for (int i = 0; i < peopleArray.length; ++i)
        {
            ArrayList<Event> eventsList = new ArrayList<Event>();
            for (int j = 0; j < eventsArray.length; ++j)
            {
                if (peopleArray[i].getPersonID().equals(eventsArray[j].getPersonID()))
                {
                    eventsList.add(eventsArray[j]);
                }
            }
            personEvents.put(peopleArray[i].getPersonID(), eventsList);
        }
    }

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
