package pl.skg.simplyprojectmenager.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.model.Proces;
import pl.skg.simplyprojectmenager.model.ProcesAdapter;
import pl.skg.simplyprojectmenager.model.Step;
import pl.skg.simplyprojectmenager.model.StepAdapter;

/**
 * Created by Karamba on 2017-06-01
 */

public class ProcesListActivity extends AppCompatActivity {
    @BindView(R.id.list_proceses)
    protected ListView listViewProceses;

    private ProcesAdapter procesAdapter;
    private StepAdapter stepAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("proces");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Proces proces2 = new Proces();
        proces2.setProcesName("130/06/17");
        proces2.setAmount(100);
        proces2.setDescription(" aby zobaczyc czy działa");
        Step step = new Step();
        step.setStepName("giecie");
        step.setSectionId(3);
        step.setStatus(0);
        step.setSectionId(1);
        Step step1 = new Step();
        step1.setStepName("giecie");
        step1.setSectionId(3);
        step1.setStatus(1);
        step1.setSectionId(4);
        ArrayList<Step> arrayListSteps = new ArrayList<>();
        arrayListSteps.add(0, step);
        arrayListSteps.add(1, step1);
        proces2.setSteps(arrayListSteps);
        myRef.child("key2").setValue(proces2);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_proces);
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

        procesAdapter = new ProcesAdapter(this, new ArrayList<Proces>());
        stepAdapter = new StepAdapter(this, new ArrayList<Step>());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TAG", dataSnapshot.getChildrenCount() + "");
                List<Proces> list = new ArrayList<>();
                List<Step> stepList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Proces value = data.getValue(Proces.class);
                    Step stepValues = data.getValue(Step.class);
                    value.setProcesId(data.getKey());
                    list.add(value);
                    stepList.add(stepValues);
                }

                procesAdapter.clear();
                procesAdapter.addAll(list);
                procesAdapter.notifyDataSetChanged();

                stepAdapter.clear();
                stepAdapter.addAll(stepList);
                stepAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//View view = getLayoutInflater().inflate(R.layout.proces_row_with_step_list, listViewProceses);
        View view = LayoutInflater.from(this).inflate(R.layout.proces_row_with_step_list, null, false);
        final TextView alertProcesRowAmount = (TextView) view.findViewById(R.id.procesRow_amount_textView);
        final TextView alertProcesName = (TextView) view.findViewById(R.id.procesRow_procesName_textView);
        final TextView alertProcesDescription = (TextView) view.findViewById(R.id.procesRow_description_textView);
        ListView alertStepsListView = (ListView) view.findViewById(R.id.list_steps);
        alertStepsListView.setAdapter(stepAdapter);

        listViewProceses.setAdapter(procesAdapter);
        listViewProceses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Proces item = procesAdapter.getItem(position);
                if (item != null) {
                    alertProcesName.setText(item.getProcesName());
                    alertProcesDescription.setText(item.getDescription());
                    alertProcesRowAmount.setText(String.valueOf(item.getAmount()));

                }
                new AlertDialog.Builder(ProcesListActivity.this)
                            .setTitle("Szczegóły zlecenia")
                            .setView(view)
                            .setNegativeButton("Zamknij", null)
//                        .setNegativeButton("Zamknij", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
                            .show();

                return true;
            }
        });
        toolbar.removeView(view);
    }
}
