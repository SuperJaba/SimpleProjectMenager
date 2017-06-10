package pl.skg.simplyprojectmenager.admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import pl.skg.simplyprojectmenager.model.*;
import pl.skg.simplyprojectmenager.model.ProcesAdapter;
import pl.skg.simplyprojectmenager.model.Process;

public class ProcesListActivity extends AppCompatActivity {
    @BindView(R.id.list_proceses)
    protected ListView listViewProceses;

    private ProcesAdapter procesAdapter;
    private StepAdapter stepAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("proces");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_proces);
        ButterKnife.bind(this);

        procesAdapter = new pl.skg.simplyprojectmenager.model.ProcesAdapter(this, new ArrayList<Process>());
        stepAdapter = new StepAdapter(this, new ArrayList<Step>());
        myRef.addValueEventListener(getValueEventListener());

        listViewProceses.setAdapter(procesAdapter);
        listViewProceses.setOnItemLongClickListener(getListener());
    }

    @NonNull
    private AdapterView.OnItemLongClickListener getListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                View root = LayoutInflater.from(ProcesListActivity.this).inflate(R.layout.proces_row_with_step_list, null, false);
                final TextView alertProcesRowAmount = (TextView) root.findViewById(R.id.procesRow_amount_textView);
                final TextView alertProcesName = (TextView) root.findViewById(R.id.procesRow_procesName_textView);
                final TextView alertProcesDescription = (TextView) root.findViewById(R.id.procesRow_description_textView);
                ListView alertStepsListView = (ListView) root.findViewById(R.id.list_steps);
                alertStepsListView.setAdapter(stepAdapter);


                final Process item = procesAdapter.getItem(position);
                stepAdapter.clear();
                stepAdapter.addAll(item.getSteps());
                stepAdapter.notifyDataSetChanged();
                if (item != null) {
                    alertProcesName.setText(item.getProcesName());
                    alertProcesDescription.setText(item.getDescription());
                    alertProcesRowAmount.setText(String.valueOf(item.getAmount()));
                }
                new AlertDialog.Builder(ProcesListActivity.this)
                        .setTitle(getResources().getString(R.string.szczegoly))
                        .setView(root)
                        .setNegativeButton(getResources().getString(R.string.zamknij), null)
                        .show();
                return true;
            }
        };
    }

    @NonNull
    private ValueEventListener getValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Process> list = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Process value = data.getValue(Process.class);
                    value.setProcesId(data.getKey());
                    list.add(value);
                }
                procesAdapter.clear();
                procesAdapter.addAll(list);
                procesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
