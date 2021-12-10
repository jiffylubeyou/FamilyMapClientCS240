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

import kstrong3.familymap.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //this code doesn't work
//
//        View view = getLayoutInflater().inflate(R.layout.activity_settings, ,  false);
//
//        Switch lifeStoryLines = view.findViewById(R.id.switch1);
//        Switch famjlyTreeLines = view.findViewById(R.id.switch2);
//        Switch spouseLines = view.findViewById(R.id.switch3);
//        Switch fathersSide = view.findViewById(R.id.switch4);
//        Switch mothersSide = view.findViewById(R.id.switch5);
//        Switch maleEvents = view.findViewById(R.id.switch6);
//        Switch femaleEvents = view.findViewById(R.id.switch7);
    }
}