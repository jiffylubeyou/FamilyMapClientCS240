package kstrong3.familymap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;

import Activities.PersonActivity;
import Activities.SearchActivity;
import Activities.Settings;
import model.Event;
import model.Person;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private GoogleMap map;
    private TextView textView;
    private ImageView imageView;

    private GoogleMap.OnMarkerClickListener listener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(@NonNull Marker marker) {

            removeLines();

            Event event = (Event) marker.getTag();
            Person person = DataCache.people.get(event.getPersonID());

            LatLng latLng = new LatLng(event.getLatitude(), event.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            //pass in user info here
            String string = person.getFirstName() + " " + person.getLastName() + " \n" +
                    event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " ("
                    + event.getYear() + ")";
            textView.setText(string);

            if (person.getGender().equals("m"))
            {
                Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                        colorRes(R.color.male).sizeDp(40);
                imageView.setImageDrawable(genderIcon);
            }
            else
            {
                Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                        colorRes(R.color.female).sizeDp(40);
                imageView.setImageDrawable(genderIcon);
            }

            //draw the spouse lines
            if (DataCache.spouseLinesEnabled)
            {
                drawSpouseLines(event, person);
            }

            if (DataCache.familyTreeLinesEnabled)
            {
                drawGenerationsLines(event, person);
            }

            if (DataCache.lifeStoryLinesEnabled)
            {
                drawLifeStoryLines(event, person);
            }

            return false;
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_maps, container, false);

        setHasOptionsMenu(true);


        textView = view.findViewById(R.id.mapTextView);
        imageView = view.findViewById(R.id.gender);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LinearLayout linearLayout = view.findViewById(R.id.mapLinearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(listener);
        map.setOnMapLoadedCallback(this);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        map.animateCamera(CameraUpdateFactory.newLatLng(sydney));

        //populate the map with the events
        Integer iterator = 0;
        TreeMap<String, Integer> eventTypes = new TreeMap<String, Integer>();
        for (int i = 0; i < DataCache.eventsArray.length; i++)
        {
            //if male events isn't enabled then skip to next event
            if (!DataCache.maleEventsEnabled)
            {
                if ("m".equals(DataCache.people.get(DataCache.eventsArray[i].getPersonID()).getGender()))
                {
                    continue;
                }
            }
            //if female events not enabled, skip
            if (!DataCache.femaleEventsEnabled)
            {
                if ("f".equals(DataCache.people.get(DataCache.eventsArray[i].getPersonID()).getGender()))
                {
                    continue;
                }
            }
            //checking if the paternal or maternal ancestors are enabled, if not then skip this loop
            if (!DataCache.fatherSideEnabled)
            {
                if (DataCache.paternalAncestors.contains(DataCache.eventsArray[i].getPersonID())) {
                    continue;
                }
            }
            if (!DataCache.motherSideEnabled)
            {
                if (DataCache.maternalAncestors.contains(DataCache.eventsArray[i].getPersonID())) {
                    continue;
                }
            }

            //now we are good to add the event marker to the map
            Integer colorInt;
            if (eventTypes.containsKey(DataCache.eventsArray[i].getEventType().toLowerCase()))
            {
                 colorInt = eventTypes.get(DataCache.eventsArray[i].getEventType().toLowerCase());
            }
            else
            {
                iterator = (iterator + 1) % 10;
                colorInt = iterator;
                eventTypes.put(DataCache.eventsArray[i].getEventType().toLowerCase(), colorInt);
            }

            float googleColor = BitmapDescriptorFactory.HUE_RED;
            if (colorInt.equals(0))
            {
                googleColor = BitmapDescriptorFactory.HUE_RED;
            }
            if (colorInt.equals(1))
            {
                googleColor = BitmapDescriptorFactory.HUE_GREEN;
            }
            if (colorInt.equals(2))
            {
                googleColor = BitmapDescriptorFactory.HUE_BLUE;
            }
            if (colorInt.equals(3))
            {
                googleColor = BitmapDescriptorFactory.HUE_ORANGE;
            }
            if (colorInt.equals(4))
            {
                googleColor = BitmapDescriptorFactory.HUE_AZURE;
            }
            if (colorInt.equals(5))
            {
                googleColor = BitmapDescriptorFactory.HUE_CYAN;
            }
            if (colorInt.equals(6))
            {
                googleColor = BitmapDescriptorFactory.HUE_MAGENTA;
            }
            if (colorInt.equals(7))
            {
                googleColor = BitmapDescriptorFactory.HUE_ROSE;
            }
            if (colorInt.equals(8))
            {
                googleColor = BitmapDescriptorFactory.HUE_VIOLET;
            }
            if (colorInt.equals(9))
            {
                googleColor = BitmapDescriptorFactory.HUE_YELLOW;
            }


            LatLng temp = new LatLng(DataCache.eventsArray[i].getLatitude(), DataCache.eventsArray[i].getLongitude());
            Marker marker = map.addMarker((new MarkerOptions().position(temp).title(
                    DataCache.eventsArray[i].getCity() + ", " + DataCache.eventsArray[i].getCountry()
            )).icon(BitmapDescriptorFactory.defaultMarker(googleColor)));

            marker.setTag(DataCache.eventsArray[i]);
        }

    }

    @Override
    public void onMapLoaded() {
        // You probably don't need this callback. It occurs after onMapReady and I have seen
        // cases where you get an error when adding markers or otherwise interacting with the map in
        // onMapReady(...) because the map isn't really all the way ready. If you see that, just
        // move all code where you interact with the map (everything after
        // map.setOnMapLoadedCallback(...) above) to here.
    }

    private void drawSpouseLines(Event event, Person person)
    {
        if (person.getSpouseID() != null)
        {
            String earliestEventID = getEarliestEvent(person.getSpouseID());

            LatLng startPoint = new LatLng(event.getLatitude(), event.getLongitude());
            LatLng endPoint = new LatLng(DataCache.events.get(earliestEventID).getLatitude(),
                    DataCache.events.get(earliestEventID).getLongitude());
            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(endPoint)
                    .color(getResources().getColor(R.color.red))
                    .width(10);
            DataCache.spouseLines = map.addPolyline(options);
        }
    }

    private void drawGenerationsLines(Event event, Person person)
    {
        DataCache.generationNum = 10;
        drawGenerationsLinesRecusor(event, person);
    }

    private void drawGenerationsLinesRecusor(Event event, Person person)
    {
        if (person.getFatherID() != null && person.getMotherID() != null)
        {
            String earliestFatherEventID = getEarliestEvent(person.getFatherID());
            String earliestMotherEventID = getEarliestEvent(person.getMotherID());

            LatLng startPoint = new LatLng(event.getLatitude(), event.getLongitude());
            LatLng fatherPoint = new LatLng(DataCache.events.get(earliestFatherEventID).getLatitude(),
                    DataCache.events.get(earliestFatherEventID).getLongitude());
            LatLng motherPoint = new LatLng(DataCache.events.get(earliestMotherEventID).getLatitude(),
                    DataCache.events.get(earliestMotherEventID).getLongitude());
            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(fatherPoint)
                    .color(getResources().getColor(R.color.green))
                    .width(DataCache.generationNum);
            Polyline line1 = map.addPolyline(options);
            DataCache.generationLinesList.add(line1);
            PolylineOptions options2 = new PolylineOptions()
                    .add(startPoint)
                    .add(motherPoint)
                    .color(getResources().getColor(R.color.green))
                    .width(DataCache.generationNum);
            DataCache.generationLinesList.add(map.addPolyline(options2));
            DataCache.generationNum = DataCache.generationNum - 2;
            drawGenerationsLinesRecusor(DataCache.events.get(earliestFatherEventID), DataCache.people.get(person.getFatherID()));
            drawGenerationsLinesRecusor(DataCache.events.get(earliestMotherEventID), DataCache.people.get(person.getMotherID()));
            DataCache.generationNum = DataCache.generationNum + 2;
        }
    }

    private void drawLifeStoryLines(Event event, Person person)
    {
        String earliestEventID = getEarliestEvent(person.getPersonID());
        List<Integer> ints = new ArrayList<>();
        TreeMap<Integer, Event> eventMap = new TreeMap<Integer, Event>();
        for (Event e : DataCache.personEvents.get(person.getPersonID()))
        {
               ints.add(e.getYear());
               eventMap.put(e.getYear(), e);
        }

        Collections.sort(ints);

        for (int i = 0; i < ints.size() - 1; ++i)
        {
            Event e = DataCache.events.get(earliestEventID);
            LatLng startPoint = new LatLng(eventMap.get(ints.get(i)).getLatitude(),
                    eventMap.get(ints.get(i)).getLongitude());
            LatLng endPoint = new LatLng(eventMap.get(ints.get(i + 1)).getLatitude(),
                    eventMap.get(ints.get(i + 1)).getLongitude());
            PolylineOptions options = new PolylineOptions()
                    .add(startPoint)
                    .add(endPoint)
                    .color(getResources().getColor(R.color.blue))
                    .width(10);
            Polyline line = map.addPolyline(options);
            DataCache.lifeStoryLines.add(line);
        }
    }

    private String getEarliestEvent(String personID)
    {
        String earliestEventID = DataCache.personEvents.get(personID).get(0).getEventID();
        for (Event e : DataCache.personEvents.get(personID))
        {
            if (e.getYear() < DataCache.events.get(earliestEventID).getYear())
            {
                earliestEventID = e.getEventID();
            }
        }
        return earliestEventID;
    }

    private void removeLines()
    {
        if (DataCache.spouseLines != null)
        {
            DataCache.spouseLines.remove();
        }

        for (Polyline P : DataCache.generationLinesList)
        {
            P.remove();
        }

        for (Polyline P : DataCache.lifeStoryLines)
        {
            P.remove();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem fileMenuItem = menu.findItem(R.id.fileMenuItem);
        fileMenuItem.setIcon(new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
                .colorRes(R.color.white)
                .actionBarSize());

        MenuItem personMenuItem = menu.findItem(R.id.personMenuItem);
        personMenuItem.setIcon(new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear)
                .colorRes(R.color.white)
                .actionBarSize());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch(menu.getItemId()) {
            case R.id.fileMenuItem:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.personMenuItem:
                Intent intent1 = new Intent(getActivity(), Settings.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map == null)
        {
            return;
        }
        map.clear();
        map.setOnMarkerClickListener(listener);
        map.setOnMapLoadedCallback(this);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        map.animateCamera(CameraUpdateFactory.newLatLng(sydney));

        //populate the map with the events
        Integer iterator = 0;
        TreeMap<String, Integer> eventTypes = new TreeMap<String, Integer>();
        for (int i = 0; i < DataCache.eventsArray.length; i++)
        {
            //if male events isn't enabled then skip to next event
            if (!DataCache.maleEventsEnabled)
            {
                if ("m".equals(DataCache.people.get(DataCache.eventsArray[i].getPersonID()).getGender()))
                {
                    continue;
                }
            }
            //if female events not enabled, skip
            if (!DataCache.femaleEventsEnabled)
            {
                if ("f".equals(DataCache.people.get(DataCache.eventsArray[i].getPersonID()).getGender()))
                {
                    continue;
                }
            }
            //checking if the paternal or maternal ancestors are enabled, if not then skip this loop
            if (!DataCache.fatherSideEnabled)
            {
                if (DataCache.paternalAncestors.contains(DataCache.eventsArray[i].getPersonID())) {
                    continue;
                }
            }
            if (!DataCache.motherSideEnabled)
            {
                if (DataCache.maternalAncestors.contains(DataCache.eventsArray[i].getPersonID())) {
                    continue;
                }
            }

            //now we are good to add the event marker to the map
            Integer colorInt;
            if (eventTypes.containsKey(DataCache.eventsArray[i].getEventType().toLowerCase()))
            {
                colorInt = eventTypes.get(DataCache.eventsArray[i].getEventType().toLowerCase());
            }
            else
            {
                iterator = (iterator + 1) % 10;
                colorInt = iterator;
                eventTypes.put(DataCache.eventsArray[i].getEventType().toLowerCase(), colorInt);
            }

            float googleColor = BitmapDescriptorFactory.HUE_RED;
            if (colorInt.equals(0))
            {
                googleColor = BitmapDescriptorFactory.HUE_RED;
            }
            if (colorInt.equals(1))
            {
                googleColor = BitmapDescriptorFactory.HUE_GREEN;
            }
            if (colorInt.equals(2))
            {
                googleColor = BitmapDescriptorFactory.HUE_BLUE;
            }
            if (colorInt.equals(3))
            {
                googleColor = BitmapDescriptorFactory.HUE_ORANGE;
            }
            if (colorInt.equals(4))
            {
                googleColor = BitmapDescriptorFactory.HUE_AZURE;
            }
            if (colorInt.equals(5))
            {
                googleColor = BitmapDescriptorFactory.HUE_CYAN;
            }
            if (colorInt.equals(6))
            {
                googleColor = BitmapDescriptorFactory.HUE_MAGENTA;
            }
            if (colorInt.equals(7))
            {
                googleColor = BitmapDescriptorFactory.HUE_ROSE;
            }
            if (colorInt.equals(8))
            {
                googleColor = BitmapDescriptorFactory.HUE_VIOLET;
            }
            if (colorInt.equals(9))
            {
                googleColor = BitmapDescriptorFactory.HUE_YELLOW;
            }


            LatLng temp = new LatLng(DataCache.eventsArray[i].getLatitude(), DataCache.eventsArray[i].getLongitude());
            Marker marker = map.addMarker((new MarkerOptions().position(temp).title(
                    DataCache.eventsArray[i].getCity() + ", " + DataCache.eventsArray[i].getCountry()
            )).icon(BitmapDescriptorFactory.defaultMarker(googleColor)));

            marker.setTag(DataCache.eventsArray[i]);
        }
    }
}
