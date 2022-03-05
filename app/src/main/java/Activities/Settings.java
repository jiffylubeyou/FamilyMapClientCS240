package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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
        Switch famjlyTreeLines = findViewById(R.id.switch2);
        Switch spouseLines = findViewById(R.id.switch3);
        Switch fathersSide = findViewById(R.id.switch4);
        Switch mothersSide = findViewById(R.id.switch5);
        Switch maleEvents = findViewById(R.id.switch6);
        Switch femaleEvents = findViewById(R.id.switch7);


        lifeStoryLines.setOnCheckedChangeListener((c, isOn)->lifeStoryLinesHelper(isOn));
    }
    private void lifeStoryLinesHelper(boolean isOn)
    {
        //add a datacache boolean that will be checked when the mapfragment draws the story lines
    }
}