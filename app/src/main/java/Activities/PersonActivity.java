package Activities;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kstrong3.familymap.DataCache;
import kstrong3.familymap.MainActivity;
import kstrong3.familymap.R;
import model.Event;
import model.Person;


public class PersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this line of code leaves expandableListView null, no idea why
        ExpandableListView expandableListView = findViewById(R.id.expandableListView);

        expandableListView.setAdapter(new ExpandableListAdapter(DataCache.peopleArray, DataCache.eventsArray));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int EVENT_GROUP_POSITION = 0;
        private static final int PERSON_GROUP_POSITION = 1;

        private final List<Event> events;
        private final List<Person> people;

        ExpandableListAdapter(model.Person[] people, model.Event[] events) {
            this.events = new ArrayList<>();
            this.people = new ArrayList<>();
            for (int i = 0; i < people.length; ++i) {
                this.people.add(people[i]);
            }
            for (int i = 0; i < events.length; ++i) {
                this.events.add(events[i]);
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
            resortLocationView.setText(events.get(childPosition).getCountry());

            skiResortItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MainActivity.this, getString(R.string.eventsTitle, events.get(childPosition).getEventType()), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void initializeHikingTrailView(View hikingTrailItemView, final int childPosition) {
            TextView trailNameView = hikingTrailItemView.findViewById(R.id.PersonTitle);
            trailNameView.setText(people.get(childPosition).getFirstName());

            TextView trailLocationView = hikingTrailItemView.findViewById(R.id.person_item);
            trailLocationView.setText(people.get(childPosition).getLastName());

            hikingTrailItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MainActivity.this, getString(R.string.hikingTrailToastText, hikingTrails.get(childPosition).getName()), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
