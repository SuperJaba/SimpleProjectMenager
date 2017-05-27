package pl.skg.simplyprojectmenager;

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
import pl.skg.simplyprojectmenager.admin.AdminStartActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


//        DatabaseReference myRef = database.getReference("user");
//
//        String user_key1 = String
//                .valueOf("aaa")
//                .replace("@", "(at)")
//                .replace(".", "(dot)");
//        myRef
//                .child(user_key1)
//                .setValue(
//                        new User(2, "greg", "aaa", "aaa", true)
//                );


    }

    @OnClick(R.id.sign)
    public void onViewClicked() {
        final String loginData = login.getText().toString();
        final String passwordData = password.getText().toString();


        String loginKey = "user/" + loginData.replace("@", "(at)").replace(".", "(dot)");
        DatabaseReference myRefUser = database.getReference(loginKey);

//        myRefUser.addValueEventListener(new ValueEventListener() {
        myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final User value = dataSnapshot.getValue(User.class);

//                playerName.addTextChangedListener(new TextWatcher()
//                {
//                    public void afterTextChanged(Editable edt){
//                        if( playerName.getText().length()>0)
//                        {
//                            playerName.setError(null);
//                        }
//                    }

                initTextChangesListeners();

                if (loginData.isEmpty()) {
                    loginLabel.setError("pole wymagane");
                } else
                if(value == null) {
                    loginLabel.setError("nie ma takiego usera");
                } else
                if (loginData.equals(value.getEmail())) {
                    loginLabel.setError("");
                    if (passwordData.isEmpty()) {
                        passwordLabel.setError("pole wymagane");
                    } else if (passwordData.equals(value.getPassword())) {
                        passwordLabel.setError("");
                        if (value.getIsAdmin()) {
                            startActivity(new Intent(LoginActivity.this, AdminStartActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, UserStartActivity.class));
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


//        startActivity(new Intent(LoginActivity.this, NewUserActivity_POLIGON.class));
    }

    private void initTextChangesListeners() {

        myTextChangesListener(login, loginLabel);
        myTextChangesListener(password, passwordLabel);

//        login.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) { }
//            @Override
//            public void afterTextChanged(Editable edt) { if (login.getText().length() > 0) { loginLabel.setError(null); } }
//        });
//        password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) { if (password.getText().length() > 0) { passwordLabel.setError(null); } }
//        });
    }


}