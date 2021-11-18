package kstrong3.familymap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {

    private Listener listener;

    public interface Listener {
        void notifyDone();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    public void registerListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText ServerHostField = view.findViewById(R.id.ServerHostField);
        EditText ServerPortField = view.findViewById(R.id.ServerPortField);
        EditText UserNameField = view.findViewById(R.id.UserNameField);
        EditText PasswordField = view.findViewById(R.id.PasswordField);
        EditText FirstNameField = view.findViewById(R.id.FirstNameField);
        EditText LastNameField = view.findViewById(R.id.LastNameField);
        EditText EmailField = view.findViewById(R.id.EmailField);

        Button signInButton = view.findViewById(R.id.button5);
        signInButton.invalidate();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logic goes here I think
            }
        });

        Button registerButton = view.findViewById(R.id.button6);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logic goes here I think
            }
        });



        ServerHostField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServerHostField.toString().equals(""))
                {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }
}