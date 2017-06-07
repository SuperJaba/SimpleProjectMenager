package pl.skg.simplyprojectmenager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pl.skg.simplyprojectmenager.model.Proces;
import pl.skg.simplyprojectmenager.model.ProcesAdapter;
import pl.skg.simplyprojectmenager.model.Step;
import pl.skg.simplyprojectmenager.model.StepAdapter;

public class ProcesListFragment extends Fragment {

    @BindView(R.id.list_proceses)
    protected ListView listViewProceses;

    private ProcesAdapter procesAdapter;
    private StepAdapter stepAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("proces");

    public static final String tag = "my fragment tag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_proces, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

        procesAdapter = new ProcesAdapter(getActivity(), new ArrayList<Proces>());
        stepAdapter = new StepAdapter(getActivity(), new ArrayList<Step>());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TAG", dataSnapshot.getChildrenCount() + "");
                List<Proces> list = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Proces value = data.getValue(Proces.class);
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
        });

        listViewProceses.setAdapter(procesAdapter);
        listViewProceses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                View root = LayoutInflater.from(getActivity()).inflate(R.layout.proces_row_with_step_list, null, false);
                final TextView alertProcesRowAmount = (TextView) root.findViewById(R.id.procesRow_amount_textView);
                final TextView alertProcesName = (TextView) root.findViewById(R.id.procesRow_procesName_textView);
                final TextView alertProcesDescription = (TextView) root.findViewById(R.id.procesRow_description_textView);
                ListView alertStepsListView = (ListView) root.findViewById(R.id.list_steps);
                alertStepsListView.setAdapter(stepAdapter);



                final Proces item = procesAdapter.getItem(position);
                stepAdapter.clear();
                stepAdapter.addAll(item.getSteps());
                stepAdapter.notifyDataSetChanged();
                if (item != null) {
                    alertProcesName.setText(item.getProcesName());
                    alertProcesDescription.setText(item.getDescription());
                    alertProcesRowAmount.setText(String.valueOf(item.getAmount()));

                }
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.szczegoly))
                        .setView(root)
                        .setNegativeButton(getResources().getString(R.string.zamknij), null)
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

        return view;
    }
}


