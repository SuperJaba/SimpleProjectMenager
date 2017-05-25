package pl.skg.simplyprojectmenager;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.model.User;

public class AdminActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.signUp)
    public void onViewClicked() {

        String userNameData = userName.getText().toString();
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        Boolean isAdminData = false;
        if(userNameData.isEmpty()){
            nameLabel.setError("pole wymagane");
        } else {
            if (isAdmin.isChecked()) {
                isAdminData = true;
            }
        }

        DatabaseReference myRef = database.getReference("user");

        String user_key = String.valueOf(emailData).replace("@", "(at)").replace(".", "(dot)");
        myRef
                .child(user_key)
                .setValue(
                        new User(2, userNameData, emailData, passwordData, isAdminData)
                        //User(int id, String userName, String email, String password, Boolean isAdmin) {
                );
    }
}
