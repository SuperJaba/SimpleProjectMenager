package pl.skg.simplyprojectmenager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.model.User;
import pl.skg.simplyprojectmenager.userSwipe.SwipeActivity;

import static pl.skg.simplyprojectmenager.utils.MyListeners.myTextChangesListener;

public class NewUserActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.nameLabel)
    TextInputLayout nameLabel;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.emailLabel)
    TextInputLayout emailLabel;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.passwordLabel)
    TextInputLayout passwordLabel;
    @BindView(R.id.isAdmin)
    CheckBox isAdmin;
    @BindView(R.id.signUp)
    Button signUp;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ButterKnife.bind(this);


    }

    @OnClick(R.id.signUp)
    public void onViewClicked() {


        final String userNameData = userName.getText().toString();
        final String emailData = email.getText().toString();
        final String passwordData = password.getText().toString();
        final Boolean[] isAdminData = {false};

        String referencePath = "user/" + emailData.replace("@", "(at)").replace(".", "(dot)");
        DatabaseReference myRefUser = database.getReference(referencePath);

        myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                initListeners();


                User value = dataSnapshot.getValue(User.class);

                if (userNameData.isEmpty()) {
                    nameLabel.setError(getResources().getString(R.string.pole_wymagane));
                } else if (emailData.isEmpty()) {
                    emailLabel.setError(getResources().getString(R.string.pole_wymagane));
                } else if (value != null) {
                    emailLabel.setError(getResources().getString(R.string.juz_jest));
                } else if (passwordData.isEmpty()) {
                    passwordLabel.setError(getResources().getString(R.string.pole_wymagane));
                    passwordLabel.setHintAnimationEnabled(true);
                } else {
                    if (isAdmin.isChecked()) {
                        isAdminData[0] = true;
                    }

                    userName.setText("");
                    userName.clearFocus();
                    nameLabel.setError(null);
                    email.setText("");
                    email.clearFocus();
                    emailLabel.setError(null);
                    password.setText("");
                    password.clearFocus();
                    passwordLabel.setError(null);
                    isAdmin.setChecked(false);

                    DatabaseReference myRef = database.getReference("user");

                    String user_key = String
                            .valueOf(emailData)
                            .replace("@", "(at)")
                            .replace(".", "(dot)");
                    myRef
                            .child(user_key)
                            .setValue(
                                    new User(2, userNameData, emailData, passwordData, isAdminData[0])
                                    //User(int id, String userName, String email, String password, Boolean isAdmin) {
                            );

//                    Toast.makeText(NewUserActivity.this, "user dodany", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(NewUserActivity.this, SwipeActivity.class));
                    finish();

                }


//                else {
//                    if (isAdmin.isChecked()) {
////                        isAdminData = true;
//                    }
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initListeners() {
        myTextChangesListener(userName, nameLabel);
        myTextChangesListener(email,emailLabel);
        myTextChangesListener(password, passwordLabel);
    }
}
