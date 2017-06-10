package pl.skg.simpleprojectmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simpleprojectmanager.admin.AdminStartActivity;
import pl.skg.simpleprojectmanager.model.User;
import pl.skg.simpleprojectmanager.user.UserStartActivity;

import static pl.skg.simpleprojectmanager.utils.ListenerUtils.myTextChangesListener;

//import pl.skg.simplyprojectmenager.stepsSingelton.StepListSingleton;
//import pl.skg.simplyprojectmenager.stepsSingelton.SingletonStepList;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign)
    Button sign;
//    @BindView(R.id.emailTextView)
//    TextView emailTextView;
//    @BindView(R.id.passwordTextView)
//    TextView passwordTextView;
    @BindView(R.id.loginLabel)
    TextInputLayout loginLabel;
    @BindView(R.id.passwordLabel)
    TextInputLayout passwordLabel;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @BindView(R.id.backdoor)
    TextView backdoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        Intent intent= getIntent();
//        Bundle bundle=intent.getExtras();
//        if(bundle!=null) {
//            Step step = (Step) bundle.get("step");
//
//
//            Toast.makeText(LoginActivity.this, step.toString(), Toast.LENGTH_LONG).show();
//        }
//        SingletonStepList singletonStepList=SingletonStepList.getInstance();
//        List<Step> list=singletonStepList.getStepList();
//        Step step=list.get(0);
//        Toast.makeText(LoginActivity.this,step.toString() , Toast.LENGTH_LONG).show();
//        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//        buildAdminAccount();


//        DatabaseReference myRef = database.getReference("gg_process");
//        List<Step> steps_list = new ArrayList<>();
//        steps_list.add(new Step("name", 1, true, false));
//        steps_list.add(new Step("name1", 1, true, false));
//        steps_list.add(new Step("name2", 1, true, false));
//        String user_key1 = String
//                .valueOf("aaa")
//                .replace("@", "(at)")
//                .replace(".", "(dot)");
//        myRef
//                .child(user_key1)
//                .setValue(
//                        new Process("proces_name", 1, "description", 10, steps_list)
////                        Process(String process_name, long proces_id, String description, int amount, List<Step> steps)
////                        Step(String step_name, int section_id, boolean started, boolean finished)
//                );

    }

    private void buildAdminAccount() {
        DatabaseReference myRef = database.getReference("user");

        String user_key1 = String
                .valueOf("aaa")
                .replace("@", "(at)")
                .replace(".", "(dot)");
        myRef
                .child(user_key1)
                .setValue(
                        new User(2, "greg", "aaa", "aaa", true)
                );
    }

    @OnClick(R.id.sign)
    public void onViewClicked() {
        final String loginData = login.getText().toString();
        final String passwordData = password.getText().toString();

        String loginKey = "user/" + loginData.replace("@", "(at)").replace(".", "(dot)");
        DatabaseReference myRefUser = database.getReference(loginKey);

        myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final User value = dataSnapshot.getValue(User.class);

                initTextChangesListeners();
                initLoginValidation(value, loginData, passwordData);

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


//        startActivity(new Intent(LoginActivity.this, NewUserActivity_POLIGON.class));
    }

    private void initLoginValidation(User value, String loginData, String passwordData) {
        if (loginData.isEmpty()) {
            loginLabel.setError(getResources().getString(R.string.pole_wymagane));
        } else if (loginData.equals("aaa") && value == null) {
            loginLabel.setError(getResources().getString(R.string.niema_usera));
            buildAdminAccount();
//            Snackbar.make(coordinatorLayout, "user added", Snackbar.LENGTH_LONG).show();
//            Toast.makeText(this, "user dodany", Toast.LENGTH_LONG).show();
        } else if (value == null) {
            loginLabel.setError(getResources().getString(R.string.nie_znaleziono));
        } else if (loginData.equals(value.getEmail())) {
            loginLabel.setError("");
            if (passwordData.isEmpty()) {
                passwordLabel.setError(getResources().getString(R.string.pole_wymagane));
            } else if (passwordData.equals(value.getPassword())) {
                passwordLabel.setError("");
                if (value.getIsAdmin()) {

                    //podlaczenie stepform na szybko
                    startActivity(new Intent(LoginActivity.this, AdminStartActivity.class));
//                    startActivity(new Intent(LoginActivity.this, NewStepFormatAdapter.class));

                    finish();
                } else {
                    startActivity(new Intent(LoginActivity.this, UserStartActivity.class));
                    finish();
                }
            } else {
                passwordLabel.setError(getResources().getString(R.string.zle_haslo));
            }
        } else {
            loginLabel.setError(getResources().getString(R.string.nie_znaleziono));
        }
    }

    private void initTextChangesListeners() {

        myTextChangesListener(login, loginLabel);
        myTextChangesListener(password, passwordLabel);

    }

}