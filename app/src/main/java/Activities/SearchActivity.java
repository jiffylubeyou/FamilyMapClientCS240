package Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kstrong3.familymap.DataCache;
import kstrong3.familymap.EventMapFragment;
import kstrong3.familymap.MainActivity;
import kstrong3.familymap.R;
import model.Event;
import model.Person;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        //for the recycler view stuff
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        SearchAdapter adapter = new SearchAdapter(DataCache.peopleArray, DataCache.eventsArray);
        recyclerView.setAdapter(adapter);

        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.action_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

    }

    private void switchToEventMapFragment(Event event)
    {
        DataCache.tempEvent = event;
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = new EventMapFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.search_activity, fragment)
                .commit();
    }


    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>  implements Filterable {
        private final List<Event> events;
        private final List<Person> people;
        private final List<Event> eventsFull;
        private final List<Person> peopleFull;
        public static final int EVENT_ITEM_VIEW_TYPE = 0;
        public static final int PERSON_ITEM_VIEW_TYPE = 1;

        SearchAdapter(model.Person[] people, model.Event[] events) {
            this.events = new ArrayList<>();
            this.people = new ArrayList<>();
            this.eventsFull = new ArrayList<>();
            this.peopleFull = new ArrayList<>();

            for (int i = 0; i < people.length; ++i) {
                this.people.add(people[i]);
            }
            for (int i = 0; i < events.length; ++i) {
                this.events.add(events[i]);
            }
            this.eventsFull.addAll(this.events);
            this.peopleFull.addAll(this.people);
            this.events.clear();
            this.people.clear();

        }

        @Override
        public Filter getFilter() {
            return filter;
        }

        private Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Event> filteredEvents = new ArrayList<>();
                List<Person> filteredPeople = new ArrayList<>();
                if (constraint == null || constraint.length() == 0)
                {
                    events.clear();
                    people.clear();
                    filteredEvents = events;
                    filteredPeople = people;
                }
                else
                {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Event event : eventsFull)
                    {
                        if (event.getCity().toLowerCase().contains(filterPattern) ||
                                event.getCountry().toLowerCase().contains(filterPattern) ||
                                event.getEventType().toLowerCase().contains(filterPattern) ||
                                Integer.toString(event.getYear()).contains(filterPattern))
                        {
                            filteredEvents.add(event);
                        }
                    }
                    for (Person person : peopleFull)
                    {
                        if (person.getFirstName().toLowerCase().contains(filterPattern) ||
                        person.getLastName().toLowerCase().contains(filterPattern))
                        {
                            filteredPeople.add(person);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                List[] myListArray = {filteredEvents, filteredPeople};
                results.values = myListArray;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                events.clear();
                people.clear();

                List[] myListArray = (List[]) results.values;
                events.addAll(myListArray[0]);
                people.addAll(myListArray[1]);
                notifyDataSetChanged();
            }
        };

        @Override
        public int getItemViewType(int position) {
            return position < events.size() ? EVENT_ITEM_VIEW_TYPE : PERSON_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if (viewType == EVENT_ITEM_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.event_item, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.person_item, parent, false);
            }
            return new SearchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            if (position < events.size()) {
                holder.bind(events.get(position));
            } else {
                holder.bind(people.get(position - events.size()));
            }
        }

        @Override
        public int getItemCount() {
            return people.size() + events.size();
        }
    }

    private class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public static final int EVENT_ITEM_VIEW_TYPE = 0;
        public static final int PERSON_ITEM_VIEW_TYPE = 1;
        private final TextView name;
        private final TextView location;

        private final int viewType;
        private View tempView;
        private Event event;
        private Person person;

        SearchViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            this.tempView = view;

            itemView.setOnClickListener(this);

            if(viewType == EVENT_ITEM_VIEW_TYPE)
            {
                name = itemView.findViewById(R.id.event_itemTitle);
                location = itemView.findViewById(R.id.event_item);
            }
            else
            {
                name = itemView.findViewById(R.id.PersonTitle);
                location = itemView.findViewById(R.id.person_item);
            }
        }

        public void bind(Event event) {
            this.event = event;
            name.setText(event.getEventType() + " " +
                    event.getCity() + ", " + event.getCountry() + " " +
                    Integer.toString(event.getYear()));
            Person person = DataCache.people.get(event.getPersonID());
            location.setText(person.getFirstName() + " " + person.getLastName());
        }

        public void bind(Person person) {
            this.person = person;
            name.setText(person.getFirstName() + " " + person.getLastName());
            location.setText("");
            ImageView imageView = tempView.findViewById(R.id.gender);

            if (person.getGender().equals("m")) {
                Drawable genderIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_male).
                        colorRes(R.color.male).sizeDp(40);
                imageView.setImageDrawable(genderIcon);
            } else {
                Drawable genderIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_female).
                        colorRes(R.color.female).sizeDp(40);
                imageView.setImageDrawable(genderIcon);
            }
        }

        @Override
        public void onClick(View view) {
            if(viewType == EVENT_ITEM_VIEW_TYPE) {
                // This is were we pass the event to the event map fragment
                switchToEventMapFragment(event);

            } else {
                // This is were we pass the person and start the person activity
                DataCache.tempPerson = person;
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                finish();
                startActivity(intent);
            }
        }
    }
}