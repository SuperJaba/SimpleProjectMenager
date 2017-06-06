package pl.skg.simplyprojectmenager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import pl.skg.simplyprojectmenager.model.Step;
import pl.skg.simplyprojectmenager.stepSwipeActivity.StepSwipeActivity;
import pl.skg.simplyprojectmenager.swipe.DataAdapter;

public class ProcessActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_step)
    FloatingActionButton addStep;
    @BindView(R.id.card_recycler_view)
    RecyclerView cardRecyclerView;

    private List<Step> stepsList = new ArrayList<>();
    private ProcesAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    private Step step;
    MySingelton mySingelton=MySingelton.getInstance();

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


        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
            step = (Step) bundle.get("step");
            stepsList.add(step);
            Toast.makeText(ProcessActivity.this, step.toString(), Toast.LENGTH_LONG).show();
        }

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.i("TAG", dataSnapshot.getChildrenCount() + "");
//                List<User> list = new ArrayList<>();
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    User value = data.getValue(User.class);
//                    value.setEmail(data.getKey());
//                    list.add(value);
//                }
//                adapter.clear();
//                adapter.addAll(list);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

        initView();
    }


private void initView(){
    addStep.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//                        removeView();
//                        add = true;
//                        alertDialog.setTitle("Add Country");
//                        et_country.setText("");
//                        alertDialog.show();
//                        break;

                    startActivity(new Intent(ProcessActivity.this, StepSwipeActivity.class));

        }
    });

    cardRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
    cardRecyclerView.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    cardRecyclerView.setLayoutManager(layoutManager);
    List<Step> list=MySingelton.getInstance().getSteps();
    adapter = new ProcesAdapter(list);

//    adapter.addAll(stepsList);
    cardRecyclerView.setAdapter( adapter);

}

}
