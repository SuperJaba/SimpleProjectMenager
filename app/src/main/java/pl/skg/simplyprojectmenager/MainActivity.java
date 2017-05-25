package pl.skg.simplyprojectmenager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import pl.skg.simplyprojectmenager.model.User;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


//        DatabaseReference myRef = database.getReference("user");
//
//        String user_key1 = String.valueOf("tt@tt.tt").replace("@", "(at)").replace(".", "(dot)");
//        myRef
//                .child(user_key1)
//                .setValue(
//                        new User(2, "greg", "tt@tt.tt", "password", false)
//                );


    }

    @OnClick(R.id.sign)
    public void onViewClicked() {
        final String loginData = login.getText().toString();
        final String passwordData = password.getText().toString();


        DatabaseReference myRefUser = database.getReference("user/" + loginData.replace("@", "(at)").replace(".", "(dot)"));

        myRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User value = dataSnapshot.getValue(User.class);

                if (loginData.isEmpty()) {
                    loginLabel.setError("pole wymagane");
                } else if(value == null) {
                    loginLabel.setError("nie ma takiego usera");
                } else if (loginData.equals(value.getEmail())) {
                    loginLabel.setError("");
                    if (passwordData.isEmpty()) {
                        passwordLabel.setError("pole wymagane");
                    } else if (passwordData.equals(value.getPassword())) {
                        passwordLabel.setError("");
                        if (value.getIsAdmin()) {
                            startActivity(new Intent(MainActivity.this, AdminActivity.class));
                        } else {
                            startActivity(new Intent(MainActivity.this, UserActivity.class));
                        }
                    } else {
                        passwordLabel.setError("Hasło niepoprawne");
                    }
                } else {
                    loginLabel.setError("nie ma takiego usera");
                }


//                Log.d(TAG, "Value is: " + value.getEmail() + " " + value.getPassword());
//                emailTextView.setText(value.getEmail());
//                passwordTextView.setText(value.getPassword());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


//        startActivity(new Intent(MainActivity.this, AdminActivity.class));
    }
}
