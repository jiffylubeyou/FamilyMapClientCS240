package kstrong3.familymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.mainActivity);

        if (fragment == null)
        {
            fragment = createLoginFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.mainActivity, fragment)
                    .commit();
        }
        else
        {
            if (fragment instanceof LoginFragment)
            {
                ((LoginFragment) fragment).registerListener(this);
            }
        }
    }

    private Fragment createLoginFragment()
    {
        LoginFragment fragment = new LoginFragment();
        fragment.registerListener(this);
        return fragment;
    }

    @Override
    public void notifyDone() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        //Fragment fragment = new SecondFragment(); this should be the map fragment

//        fragmentManager.beginTransaction()
//                .replace(R.id.fragmentFrameLayout, fragment)
//                .commit();
    }
}