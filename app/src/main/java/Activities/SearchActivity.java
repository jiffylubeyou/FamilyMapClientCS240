package Activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kstrong3.familymap.DataCache;
import kstrong3.familymap.MainActivity;
import kstrong3.familymap.R;
import model.Event;
import model.Person;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for the recycler view stuff
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        SearchAdapter adapter = new SearchAdapter(DataCache.peopleArray, DataCache.eventsArray);
        recyclerView.setAdapter(adapter);
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
        private final List<Event> events;
        private final List<Person> people;
        public static final int EVENT_ITEM_VIEW_TYPE = 0;
        public static final int PERSON_ITEM_VIEW_TYPE = 1;

        SearchAdapter(model.Person[] people, model.Event[] events) {
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
        private Event event;
        private Person person;

        SearchViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if(viewType == EVENT_ITEM_VIEW_TYPE) {
                name = itemView.findViewById(R.id.event_itemTitle);
                location = itemView.findViewById(R.id.event_item);
            } else {
                name = itemView.findViewById(R.id.PersonTitle);
                location = itemView.findViewById(R.id.person_item);
            }
        }

        public void bind(Event event) {
            this.event = event;
            name.setText(event.getEventType());
            location.setText(event.getCountry());
        }

        public void bind(Person person) {
            this.person = person;
            name.setText(person.getFirstName());
            location.setText(person.getLastName());
        }

        @Override
        public void onClick(View view) {
            if(viewType == EVENT_ITEM_VIEW_TYPE) {
                // This is were we could pass the event to a event detail activity

//                Toast.makeText(MainActivity.this, String.format("Enjoy skiing %s!",
//                        skiResort.getName()), Toast.LENGTH_SHORT).show();
            } else {
                // This is were we could pass the person to a person detail activity

//                Toast.makeText(MainActivity.this, String.format("Enjoy hiking %s. It's %s.",
//                        hikingTrail.getName(), hikingTrail.getDifficulty().toLowerCase()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}