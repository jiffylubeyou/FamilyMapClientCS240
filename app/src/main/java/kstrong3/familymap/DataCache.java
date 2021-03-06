package kstrong3.familymap;

import com.google.android.gms.maps.model.Polyline;

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
    public static String userFirstName;
    public static Person[] peopleArray;
    public static Event[] eventsArray;
    public static TreeMap<String, Person> people = new TreeMap<String, Person>();
    public static TreeMap<String, Event> events = new TreeMap<String, Event>();
    public static TreeMap<String, List<Event>> personEvents = new TreeMap<String, List<Event>>();
    public static TreeSet<String> paternalAncestors = new TreeSet<String>();
    public static TreeSet<String> maternalAncestors = new TreeSet<String>();
    public static boolean spouseLinesEnabled = true;
    public static boolean familyTreeLinesEnabled = true;
    public static boolean lifeStoryLinesEnabled = true;
    public static boolean fatherSideEnabled = true;
    public static boolean motherSideEnabled = true;
    public static boolean maleEventsEnabled = true;
    public static boolean femaleEventsEnabled = true;
    public static boolean logout = false;
    public static Polyline spouseLines;
    public static List<Polyline> generationLinesList = new ArrayList<>();
    public static List<Polyline> lifeStoryLines = new ArrayList<>();
    public static Integer generationNum;
    public static Person tempPerson;
    public static Event tempEvent;
//    This string is a personID


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

    public void setUserFirstName(String userFirstName) { DataCache.userFirstName = userFirstName; }

    public String getUserFirstName() { return userFirstName; }

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

        if (people.get(personID).getFatherID() != null)
        {
            populateFatherSide(people.get(personID).getFatherID());
        }
        if (people.get(personID).getMotherID() != null)
        {
            populateMotherSide(people.get(personID).getMotherID());
        }
    }

    public static void clear()
    {
        //build the clear here
        username = null;
        personID = null;
        userFirstName = null;
        peopleArray = null;
        eventsArray = null;
        people = new TreeMap<String, Person>();
        events = new TreeMap<String, Event>();
        personEvents = new TreeMap<String, List<Event>>();
        paternalAncestors = new TreeSet<String>();
        maternalAncestors = new TreeSet<String>();
        spouseLinesEnabled = true;
        familyTreeLinesEnabled = true;
        lifeStoryLinesEnabled = true;
        fatherSideEnabled = true;
        motherSideEnabled = true;
        maleEventsEnabled = true;
        femaleEventsEnabled = true;
        logout = false;
        spouseLines = null;
        generationLinesList = new ArrayList<>();
        lifeStoryLines = new ArrayList<>();
        generationNum = null;
        tempPerson = null;
        tempEvent = null;
    }

    private void populateFatherSide(String fatherID)
    {
        paternalAncestors.add(fatherID);
        if ((people.get(fatherID).getFatherID() == null) || (people.get(fatherID).getMotherID() == null))
        {
            return;
        }

        populateFatherSide(people.get(fatherID).getFatherID());
        populateFatherSide(people.get(fatherID).getMotherID());
    }

    private void populateMotherSide(String motherID)
    {
        maternalAncestors.add(motherID);
        if ((people.get(motherID).getFatherID() == null) || (people.get(motherID).getMotherID() == null))
        {
            return;
        }

        populateMotherSide(people.get(motherID).getFatherID());
        populateMotherSide(people.get(motherID).getMotherID());
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
