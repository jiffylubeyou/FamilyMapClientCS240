package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import kstrong3.familymap.DataCache;
import kstrong3.familymap.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch lifeStoryLines = findViewById(R.id.switch1);
        Switch familyTreeLines = findViewById(R.id.switch2);
        Switch spouseLines = findViewById(R.id.switch3);
        Switch fathersSide = findViewById(R.id.switch4);
        Switch mothersSide = findViewById(R.id.switch5);
        Switch maleEvents = findViewById(R.id.switch6);
        Switch femaleEvents = findViewById(R.id.switch7);

        lifeStoryLines.setOnCheckedChangeListener((c, isOn)->lifeStoryLinesHelper(isOn));
        familyTreeLines.setOnCheckedChangeListener((c, isOn)->familyTreeLinesHelper(isOn));
        spouseLines.setOnCheckedChangeListener((c, isOn)->spouseLinesHelper(isOn));
        fathersSide.setOnCheckedChangeListener((c,isOn)->fatherSideHelper(isOn));
        mothersSide.setOnCheckedChangeListener((c,isOn)->motherSideHelper(isOn));
        maleEvents.setOnCheckedChangeListener((c,isOn)->maleEventsHelper(isOn));
        femaleEvents.setOnCheckedChangeListener((c,isOn)->femaleEventsHelper(isOn));
    }

    //these set the Datacache booleans that are checked in the MapsFragment before loading the lines
    private void lifeStoryLinesHelper(boolean isOn)
    {
        DataCache.lifeStoryLinesEnabled = isOn;
    }

    private void familyTreeLinesHelper(boolean isOn)
    {
        DataCache.familyTreeLinesEnabled = isOn;
    }

    private void spouseLinesHelper(boolean isOn)
    {
        DataCache.spouseLinesEnabled = isOn;
    }
    private void fatherSideHelper(boolean isOn)
    {
        DataCache.fatherSideEnabled = isOn;
    }
    private void motherSideHelper(boolean isOn)
    {
        DataCache.motherSideEnabled = isOn;
    }
    private void maleEventsHelper(boolean isOn)
    {
        DataCache.maleEventsEnabled = isOn;
    }
    private void femaleEventsHelper(boolean isOn)
    {
        DataCache.femaleEventsEnabled = isOn;
    }
}