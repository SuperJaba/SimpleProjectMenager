package pl.skg.simpleprojectmenager.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simpleprojectmenager.newstep.StepActivity;

import static pl.skg.simpleprojectmenager.utils.ListenerUtils.myTextChangesListener;

/**
 * Created by Karamba on 2017-06-03
 */

public class NewStepFormatAdapter extends AppCompatActivity {


    @BindView(R.id.stepName_editText)
    EditText stepNameEditText;
    @BindView(R.id.label_stepName_editText)
    TextInputLayout labelStepNameEditText;
    @BindView(R.id.stepForm_stepID_editText)
    EditText stepFormStepIDEditText;
    @BindView(R.id.label_stepForm_stepID_editText)
    TextInputLayout labelStepFormStepIDEditText;
    @BindView(R.id.stepForm_button)
    Button stepFormButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //    DatabaseReference myRefStep = database.getReference("step");
    DatabaseReference myRefStep = database.getReference("SingletonStepList/stepList");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_form_activity);
        ButterKnife.bind(this);


        stepFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Step step = newStep();
                myRefStep.child(stepNameEditText.getText().toString()).setValue(step);

                stepNameEditText.setText("");
                stepFormStepIDEditText.setText("");
                stepFormStepIDEditText.clearFocus();

                startActivity(new Intent(NewStepFormatAdapter.this, StepActivity.class));

            }
        });
    }

    private Step newStep() {
        Step step = new Step();
        step.setStepName(stepNameEditText.getText().toString());
        step.setStatus(0);
        step.setSectionId(Integer.parseInt(String.valueOf(stepFormStepIDEditText.getText().toString())));
        return step;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void InitListeners() {
        myTextChangesListener(stepNameEditText, labelStepNameEditText);
//        myTextChangesListener(email,emailLabel);
//        myTextChangesListener(password, passwordLabel);
    }
}
