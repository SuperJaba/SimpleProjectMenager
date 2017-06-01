package pl.skg.simplyprojectmenager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.admin.AdminStartActivity;
import pl.skg.simplyprojectmenager.model.Proces;
import pl.skg.simplyprojectmenager.model.Step;
import pl.skg.simplyprojectmenager.model.User;
import pl.skg.simplyprojectmenager.user.UserStartActivity;

import static pl.skg.simplyprojectmenager.utils.MyListeners.myTextChangesListener;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign)
    Button sign;
    @BindView(R.id.emailTextView)
    TextView emailTextView;
    @BindView(R.id.passwordTextView)
    TextView passwordTextView;
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

//        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


//        buildAdminAccount();

        DatabaseReference myRef = database.getReference("gg_process");
        List<Step> steps_list = new ArrayList<>();
        steps_list.add(new Step("name", 1, true, false));
        steps_list.add(new Step("name1", 1, true, false));
        steps_list.add(new Step("name2", 1, true, false));
        String user_key1 = String
                .valueOf("aaa")
                .replace("@", "(at)")
                .replace(".", "(dot)");
        myRef
                .child(user_key1)
                .setValue(
                        new Proces("proces_name", 1, "description", 10, steps_list)
//                        Proces(String process_name, long proces_id, String description, int amount, List<Step> steps)
//                        Step(String step_name, int section_id, boolean started, boolean finished)
                );

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

//      myRefUser.addValueEventListener(new ValueEventListener() {
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

    private void initLoginValidation(User value, String loginData, String passwordData){
        if (loginData.isEmpty()) {
            loginLabel.setError("pole wymagane");
        } else if (loginData.equals("aaa") && value == null) {
            loginLabel.setError("nie było takiego usera ale już jest");
            buildAdminAccount();
//            Snackbar.make(coordinatorLayout, "user added", Snackbar.LENGTH_LONG).show();
//            Toast.makeText(this, "user dodany", Toast.LENGTH_LONG).show();
        } else if (value == null) {
            loginLabel.setError("nie ma takiego usera");
        } else if (loginData.equals(value.getEmail())) {
            loginLabel.setError("");
            if (passwordData.isEmpty()) {
                passwordLabel.setError("pole wymagane");
            } else if (passwordData.equals(value.getPassword())) {
                passwordLabel.setError("");
                if (value.getIsAdmin()) {
                    startActivity(new Intent(LoginActivity.this, AdminStartActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(LoginActivity.this, UserStartActivity.class));
                    finish();
                }
            } else {
                passwordLabel.setError("Hasło niepoprawne");
            }
        } else {
            loginLabel.setError("nie ma takiego usera");
        }
    }

    private void initTextChangesListeners() {

        myTextChangesListener(login, loginLabel);
        myTextChangesListener(password, passwordLabel);

    }



}