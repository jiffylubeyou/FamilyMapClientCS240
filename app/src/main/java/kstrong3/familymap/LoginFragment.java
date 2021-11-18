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
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        RadioButton radioMale = view.findViewById(R.id.radioButton4);
        RadioButton radioFemale = view.findViewById(R.id.radioButton5);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);

        Button signInButton = view.findViewById(R.id.button5);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logic goes here I think
            }
        });

        Button registerButton = view.findViewById(R.id.button6);
        registerButton.setEnabled(false);
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
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals(""))
                {
                    signInButton.setEnabled(false);
                }
                else
                {
                    signInButton.setEnabled(true);
                }
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals("")
                || FirstNameField.getText().toString().equals("") || LastNameField.getText().toString().equals("")
                || EmailField.getText().toString().equals("") || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ServerPortField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals(""))
                {
                    signInButton.setEnabled(false);
                }
                else
                {
                    signInButton.setEnabled(true);
                }
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals("")
                        || FirstNameField.getText().toString().equals("") || LastNameField.getText().toString().equals("")
                        || EmailField.getText().toString().equals("") || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        UserNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals(""))
                {
                    signInButton.setEnabled(false);
                }
                else
                {
                    signInButton.setEnabled(true);
                }
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals("")
                        || FirstNameField.getText().toString().equals("") || LastNameField.getText().toString().equals("")
                        || EmailField.getText().toString().equals("") || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        PasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals(""))
                {
                    signInButton.setEnabled(false);
                }
                else
                {
                    signInButton.setEnabled(true);
                }
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals("")
                        || FirstNameField.getText().toString().equals("") || LastNameField.getText().toString().equals("")
                        || EmailField.getText().toString().equals("") || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        FirstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals(""))
                {
                    signInButton.setEnabled(false);
                }
                else
                {
                    signInButton.setEnabled(true);
                }
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals("")
                        || FirstNameField.getText().toString().equals("") || LastNameField.getText().toString().equals("")
                        || EmailField.getText().toString().equals("") || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        LastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals(""))
                {
                    signInButton.setEnabled(false);
                }
                else
                {
                    signInButton.setEnabled(true);
                }
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals("")
                        || FirstNameField.getText().toString().equals("") || LastNameField.getText().toString().equals("")
                        || EmailField.getText().toString().equals("") || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        EmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals(""))
                {
                    signInButton.setEnabled(false);
                }
                else
                {
                    signInButton.setEnabled(true);
                }
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals("")
                        || FirstNameField.getText().toString().equals("") || LastNameField.getText().toString().equals("")
                        || EmailField.getText().toString().equals("") || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals(""))
                {
                    signInButton.setEnabled(false);
                }
                else
                {
                    signInButton.setEnabled(true);
                }
                if (ServerHostField.getText().toString().equals("") || ServerPortField.getText().toString().equals("")
                        || UserNameField.getText().toString().equals("") || PasswordField.getText().toString().equals("")
                        || FirstNameField.getText().toString().equals("") || LastNameField.getText().toString().equals("")
                        || EmailField.getText().toString().equals("") || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
                {
                    registerButton.setEnabled(false);
                }
                else
                {
                    registerButton.setEnabled(true);
                }
            }
        });

        return view;
    }


}