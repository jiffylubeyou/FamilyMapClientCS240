package Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;

import kstrong3.familymap.DataCache;
import kstrong3.familymap.EventMapFragment;
import kstrong3.familymap.LoginFragment;
import kstrong3.familymap.MainActivity;
import kstrong3.familymap.MapsFragment;
import kstrong3.familymap.R;
import model.Event;
import model.Person;


public class PersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);

        //this grabs the info for the event that was clicked on
        Person person = DataCache.tempPerson;

        //this line of code leaves expandableListView null, no idea why
        ExpandableListView expandableListView = findViewById(R.id.expandableListView);

        expandableListView.setAdapter(new ExpandableListAdapter(DataCache.personEvents.get(person.getPersonID()),
                person));

        //this sets the text fields before the expandable list views
        TextView firstName = findViewById(R.id.firstName);
        TextView lastName = findViewById(R.id.lastName);
        TextView gender = findViewById(R.id.gender);


        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if (person.getGender().equals("m"))
        {
            gender.setText("Male");
        }
        else if (person.getGender().equals("f"))
        {
            gender.setText("Female");
        }
        else
        {
            gender.setText("No Gender???????????");
        }

    }

    private void switchToEventMapFragment(Event event)
    {
        DataCache.tempEvent = event;
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = new EventMapFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.person_activity, fragment)
                .commit();
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int EVENT_GROUP_POSITION = 0;
        private static final int PERSON_GROUP_POSITION = 1;

        private final List<Event> events;
        private final List<Person> people;
        private final model.Person person;

        ExpandableListAdapter(List<Event> events, model.Person people) {
            this.person = people;
            this.events = events;
            this.people = new ArrayList<>();
            if (people.getFatherID() != null)
            {
                this.people.add(DataCache.people.get(people.getFatherID()));
            }
            if (people.getMotherID() != null)
            {
                this.people.add(DataCache.people.get(people.getMotherID()));
            }
            if (people.getSpouseID() != null)
            {
                this.people.add(DataCache.people.get(people.getSpouseID()));
            }
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return events.size();
                case PERSON_GROUP_POSITION:
                    return people.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return getString(R.string.eventsTitle);
                case PERSON_GROUP_POSITION:
                    return getString(R.string.personTitle);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return events.get(childPosition);
                case PERSON_GROUP_POSITION:
                    return people.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    titleView.setText(R.string.eventsTitle);
                    break;
                case PERSON_GROUP_POSITION:
                    titleView.setText(R.string.personTitle);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch(groupPosition) {
                case EVENT_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.event_item, parent, false);
                    initializeSkiResortView(itemView, childPosition);
                    break;
                case PERSON_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.person_item, parent, false);
                    initializeHikingTrailView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return itemView;
        }

        private void initializeSkiResortView(View skiResortItemView, final int childPosition) {
            TextView resortNameView = skiResortItemView.findViewById(R.id.event_itemTitle);
            resortNameView.setText(events.get(childPosition).getEventType());

            TextView resortLocationView = skiResortItemView.findViewById(R.id.event_item);
            resortLocationView.setText(events.get(childPosition).getCountry()
            + ", " + events.get(childPosition).getCity() + " " + events.get(childPosition).getYear()
            + " " + person.getFirstName() + " " + person.getLastName());

            skiResortItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //the new google map has to go here, don't make a new map fragment
                    switchToEventMapFragment(events.get(childPosition));
                }
            });
        }



        private void initializeHikingTrailView(View hikingTrailItemView, final int childPosition) {
            TextView trailNameView = hikingTrailItemView.findViewById(R.id.PersonTitle);
            trailNameView.setText(people.get(childPosition).getFirstName() + " " +  people.get(childPosition).getLastName());

            TextView trailLocationView = hikingTrailItemView.findViewById(R.id.person_item);
            if (people.get(childPosition).getPersonID().equals(person.getFatherID()))
            {
                trailLocationView.setText("Father");
            }
            if (people.get(childPosition).getPersonID().equals(person.getMotherID()))
            {
                trailLocationView.setText("Mother");
            }
            if (people.get(childPosition).getPersonID().equals(person.getSpouseID()))
            {
                trailLocationView.setText("Spouse");
            }

            //set the image icon for the person
            ImageView imageView = hikingTrailItemView.findViewById(R.id.gender);

            if (people.get(childPosition).getGender().equals("m"))
            {
                Drawable genderIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_male).
                        colorRes(R.color.male).sizeDp(40);
                imageView.setImageDrawable(genderIcon);
            }
            else
            {
                Drawable genderIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_female).
                        colorRes(R.color.female).sizeDp(40);
                imageView.setImageDrawable(genderIcon);
            }

            hikingTrailItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataCache.tempPerson = people.get(childPosition);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
