package kstrong3.familymap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Tasks.GetDataTask;
import Tasks.LoginTask;
import Tasks.RegisterTask;
import kstrong3.familymap.DataCache;
import kstrong3.familymap.R;
import model.Person;
import requestresponse.LoginRequest;
import requestresponse.LoginResult;
import requestresponse.PersonRequest;
import requestresponse.RegisterRequest;

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
                try {
                    LoginRequest request = new LoginRequest();
                    request.setDataFromRegister(UserNameField.getText().toString(),
                            PasswordField.getText().toString());

                    Handler uiThreadMessageHandler = new Handler(Looper.getMainLooper())
                    {
                        @Override
                        public void handleMessage(Message msg) {
                            Bundle bundle = msg.getData();
                            if (bundle.getString(DataCache.AUTH_TOKEN_KEY) != null)
                            {
                                //do the handler and task for the GetDataTask as well
                                Handler uiThreadMessageHandler2 = new Handler(Looper.getMainLooper())
                                {
                                    @Override
                                    public void handleMessage(Message msg2) {
                                        Bundle bundle2 = msg2.getData();
                                        TreeMap<String, Person> person = DataCache.getInstance().people;
                                        Toast.makeText(getActivity(),
                                                DataCache.getInstance().people.get(DataCache.personID).getFirstName()
                                                 + " " +
                                                DataCache.getInstance().people.get(DataCache.personID).getLastName(),
                                                Toast.LENGTH_SHORT).show();
                                        
                                        //switch to map fragment here
                                        if(listener != null) {
                                            listener.notifyDone();
                                        }
                                    }
                                };
                                GetDataTask getDataTask = new GetDataTask(uiThreadMessageHandler2,
                                        DataCache.getUsername(), ServerHostField.getText().toString(),
                                        ServerPortField.getText().toString());
                                ExecutorService executor2 = Executors.newSingleThreadExecutor();
                                executor2.submit(getDataTask);
                            } else {
                                Toast.makeText(getActivity(),bundle.getString(DataCache.MESSAGE_KEY), Toast.LENGTH_SHORT).show();
                            }

                        }
                    };

                    LoginTask logintask = new LoginTask(uiThreadMessageHandler, request,
                            ServerHostField.getText().toString(), ServerPortField.getText().toString());
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(logintask);
                }
                catch (Exception e)
                {
                    //print stuff
                }
            }
        });

        Button registerButton = view.findViewById(R.id.button6);
        registerButton.setEnabled(false);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String gender;
                    if (radioMale.isChecked())
                    {
                        gender = "m";
                    }
                    else
                    {
                        gender = "f";
                    }
                    RegisterRequest request = new RegisterRequest();
                    request.username = UserNameField.getText().toString();
                    request.password = PasswordField.getText().toString();
                    request.email = EmailField.getText().toString();
                    request.firstName = FirstNameField.getText().toString();
                    request.lastName = LastNameField.getText().toString();
                    request.gender = gender;

                    Handler uiThreadMessageHandler = new Handler(Looper.getMainLooper())
                    {
                        @Override
                        public void handleMessage(Message msg) {

                            Bundle bundle = msg.getData();
                            if (bundle.getString(DataCache.AUTH_TOKEN_KEY) != null)
                            {
                                //do the handler and task for the GetDataTask as well
                                Handler uiThreadMessageHandler2 = new Handler(Looper.getMainLooper())
                                {
                                    @Override
                                    public void handleMessage(Message msg2) {
                                        Bundle bundle2 = msg2.getData();
                                        Toast.makeText(getActivity(),
                                                DataCache.getInstance().people.get(DataCache.personID).getFirstName()
                                                        + " " +
                                                        DataCache.getInstance().people.get(DataCache.personID).getLastName(),
                                                Toast.LENGTH_SHORT).show();
                                                //switch to map fragment here
                                    }
                                };
                                GetDataTask getDataTask = new GetDataTask(uiThreadMessageHandler2,
                                        DataCache.getUsername(), ServerHostField.getText().toString(),
                                        ServerPortField.getText().toString());
                                ExecutorService executor2 = Executors.newSingleThreadExecutor();
                                executor2.submit(getDataTask);
                            } else {
                                Toast.makeText(getActivity(),bundle.getString(DataCache.MESSAGE_KEY), Toast.LENGTH_SHORT).show();
                            }

                        }
                    };

                    RegisterTask registerTask = new RegisterTask(uiThreadMessageHandler, request,
                            ServerHostField.getText().toString(), ServerPortField.getText().toString());
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(registerTask);
                }
                catch (Exception e)
                {
                    //print stuff
                }
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