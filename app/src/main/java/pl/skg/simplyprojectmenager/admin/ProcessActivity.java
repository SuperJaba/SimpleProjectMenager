package pl.skg.simplyprojectmenager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.App;
import pl.skg.simplyprojectmenager.MySingelton;
import pl.skg.simplyprojectmenager.model.Proces;
import pl.skg.simplyprojectmenager.model.Step;
import pl.skg.simplyprojectmenager.stepSwipeActivity.StepSwipeActivity;
import pl.skg.simplyprojectmenager.swipe.DataAdapter;

public class ProcessActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_step)
    Button addStep;
    @BindView(R.id.card_recycler_view)
    RecyclerView cardRecyclerView;
    @BindView(R.id.proces_name_edit_text)
    EditText procesNameEditText;
    @BindView(R.id.proces_name_Label)
    TextInputLayout loginLabel;
    @BindView(R.id.proces_id_edit_text)
    EditText procesIdEditText;
    @BindView(R.id.proces_id_Label)
    TextInputLayout procesIdLabel;

    @BindView(R.id.proces_description_edit_text)
    EditText procesDescriptionEditText;
    @BindView(R.id.proces_description_Label)
    TextInputLayout procesDescriptionLabel;

    @BindView(R.id.proces_amount_edit_text)
    EditText procesAmountEditText;
    @BindView(R.id.proces_amount_Label)
    TextInputLayout procesAmountLabel;

    @BindView(R.id.proces_save_button)
    Button procesSaveButton;

    private List<Step> stepsList = new ArrayList<>();
    private ProcesAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("proces");
    private Step step;
    MySingelton mySingelton = MySingelton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_process);
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
        procesNameEditText.setText(MySingelton.getInstance().getProcesName());
        procesIdEditText.setText(MySingelton.getInstance().getProcesId());
        procesDescriptionEditText.setText(MySingelton.getInstance().getDescription());
        procesAmountEditText.setText(String.valueOf(MySingelton.getInstance().getAmount()));


        initView();
    }


    private void initView() {
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String procesNameFromField = procesNameEditText.getText().toString();
                String procesIdFromField = procesIdEditText.getText().toString();
                String procesDescriptionFromField = procesDescriptionEditText.getText().toString();
                String procesAmountFromField = procesAmountEditText.getText().toString();

                MySingelton.getInstance().setProcesName(procesNameFromField);
                MySingelton.getInstance().setProcesId(procesIdFromField);
                MySingelton.getInstance().setDescription(procesDescriptionFromField);
                MySingelton.getInstance().setAmount((int) Integer.valueOf(procesAmountFromField));
                startActivity(new Intent(ProcessActivity.this, StepSwipeActivity.class));
            }
        });

        procesSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = procesNameEditText.getText().toString();
                int amount = Integer.valueOf(procesAmountEditText.getText().toString());
                String id = procesIdEditText.getText().toString();
                String description = procesDescriptionEditText.getText().toString();
                List<Step> stepList = MySingelton.getInstance().getListSteps();
                Proces proces = new Proces(name, id, description, amount, stepList);
                myRef.child(myRef.getKey()).setValue(proces);
            }
        });
        cardRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        cardRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cardRecyclerView.setLayoutManager(layoutManager);
        List<Step> list = MySingelton.getInstance().getListSteps();
        adapter = new ProcesAdapter(list);
        cardRecyclerView.setAdapter(adapter);
    }

}
