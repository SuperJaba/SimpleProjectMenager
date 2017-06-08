package pl.skg.simplyprojectmenager.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.MySingelton;
import pl.skg.simplyprojectmenager.model.Proces;
import pl.skg.simplyprojectmenager.model.Step;
import pl.skg.simplyprojectmenager.stepSwipe.StepSwipeActivity;

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
    private Paint p = new Paint();
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
        initSwipe();
    }

    private void initSwipe() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT) { //| ItemTouchHelper.RIGHT

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView
                    .ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    MySingelton.getInstance().removeItem(position);
                    List<Step> list = MySingelton.getInstance().getListSteps();
                    adapter = new ProcesAdapter(list);
                    cardRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView
                    .ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.lb_ic_actions_right_arrow);

                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);

                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_forever_white_48dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 3 * width, (float) itemView.getTop() + (width / 2), (float) itemView.getRight() - width, (float) itemView.getBottom() - (width / 2));

                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(cardRecyclerView);
    }

}
