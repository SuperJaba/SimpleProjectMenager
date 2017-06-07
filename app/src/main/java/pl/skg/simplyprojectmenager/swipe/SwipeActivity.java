package pl.skg.simplyprojectmenager.swipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
import pl.skg.simplyprojectmenager.admin.NewUserActivity;
import pl.skg.simplyprojectmenager.model.User;

public class SwipeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.card_recycler_view)
    RecyclerView cardRecyclerView;

    private List<User> usersList = new ArrayList<>();
    private DataAdapter adapter;
    private RecyclerView recyclerView;
    private AlertDialog.Builder alertDialog;
    private EditText et_country;
    private int edit_position;
    private View view;
    private boolean add = false;
    private Paint p = new Paint();
    List<User> list = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_swipe);
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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TAG", dataSnapshot.getChildrenCount() + "");
                List<User> list = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User value = data.getValue(User.class);
                    value.setEmail(data.getKey());
                    list.add(value);
                }
                adapter.clear();
                adapter.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        initViews();
        initDialog();
    }

    private void initViews() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fab:
                        startActivity(new Intent(SwipeActivity.this, NewUserActivity.class));
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        usersList = list;
        adapter = new DataAdapter(usersList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        initSwipe();
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    User item = adapter.getCountries().get(position);
                    myRef.child(item.getEmail().replace("@", "(at)").replace(".", "(dot)")).removeValue();
                    adapter.removeItem(position);

                } else {
                    final User item = adapter.getCountries().get(position);
                    final View root = LayoutInflater.from(SwipeActivity.this).inflate(R.layout.form_update_user, null, false);
                    final EditText userNameEditText = (EditText) root.findViewById(R.id.userName);
                    userNameEditText.setText(item.getUserName());
                    final EditText passwordEditText = (EditText) root.findViewById(R.id.password);
                    passwordEditText.setText(item.getPassword());
                    final CheckBox isAdminCheckBox = (CheckBox) root.findViewById(R.id.isAdmin);
                    isAdminCheckBox.setChecked(item.getIsAdmin());
                    final String key = item.getEmail().replace("@", "(at)").replace(".", "(dot)");

                    Button signUpButton = (Button) root.findViewById(R.id.signUp);
                    signUpButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.setUserName(userNameEditText.getText().toString());
                            item.setPassword(passwordEditText.getText().toString());
                            item.setIsAdmin(isAdminCheckBox.isChecked());
                            myRef.child(key).setValue(item);
                        }
                    });
                    removeView();
                    edit_position = position;
                    alertDialog.setTitle(" ");
                    alertDialog.setView(root);
                    et_country.setText(usersList.get(position).getUserName().toString());
                    alertDialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

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
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.lb_ic_actions_right_arrow);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.lb_ic_stop);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void initDialog() {
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("TAG", dataSnapshot.getChildrenCount() + "");
                        List<User> list = new ArrayList<>();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            User value = data.getValue(User.class);
                            value.setEmail(data.getKey());
                            list.add(value);
                        }
                        adapter.clear();
                        adapter.addAll(list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        et_country = (EditText) view.findViewById(R.id.et_country);
    }
}
