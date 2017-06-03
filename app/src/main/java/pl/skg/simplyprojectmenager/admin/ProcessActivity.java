package pl.skg.simplyprojectmenager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.stepSwipeActivity.StepSwipeActivity;
import pl.skg.simplyprojectmenager.swipe.SwipeActivity;

public class ProcessActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_step)
    FloatingActionButton addStep;
    @BindView(R.id.card_recycler_view)
    RecyclerView cardRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_process);
        ButterKnife.bind(this);

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
}

}
