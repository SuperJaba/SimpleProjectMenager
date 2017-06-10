package pl.skg.simplyprojectmenager.gg_admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.model.User;

import static pl.skg.simplyprojectmenager.utils.ListenerUtils.myTextChangesListener;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_user_list)
    Toolbar toolbarUserList;
    @BindView(R.id.user_list_recycler_view)
    RecyclerView userListRecyclerView;
//    @Nullable
//    @BindView(R.id.user_name_textview)
//    EditText user_name_textview;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.gg_user_list)
    RelativeLayout ggUserList;

    @BindView(R.id.toolbar_user_create)
    Toolbar toolbarUserCreate;
    @BindView(R.id.image_add_user)
    ImageView imageAddUser;
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
    @BindView(R.id.gg_user_create)
    LinearLayout ggUserCreate;

    @BindView(R.id.toolbar_user_update)
    Toolbar toolbarUserUpdate;
    @BindView(R.id.image_edit_user)
    ImageView imageEditUser;
    @BindView(R.id.userNameUpdate)
    EditText userNameUpdate;
    @BindView(R.id.nameLabelUpdate)
    TextInputLayout nameLabelUpdate;

//    @BindView(R.id.emailUpdate)
//    EditText emailUpdate;
//    @BindView(R.id.emailLabelUpdate)
//    TextInputLayout emailLabelUpdate;

    @BindView(R.id.passwordUpdate)
    EditText passwordUpdate;
    @BindView(R.id.passwordLabelUpdate)
    TextInputLayout passwordLabelUpdate;
    @BindView(R.id.isAdminUpdate)
    CheckBox isAdminUpdate;
    @BindView(R.id.Update)
    Button Update;
    @BindView(R.id.gg_user_update)
    LinearLayout ggUserUpdate;



    private List<User> usersList = new ArrayList<>();
    private UserAdapter adapter;
    //    private RecyclerView recyclerView;
    private AlertDialog.Builder alertDialog;
    //    private EditText user_name_textview;
    private int edit_position;
    private View view;
    //    private boolean add = false;
    private Paint p = new Paint();
    List<User> list = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("user");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        ButterKnife.bind(this);
        initToolbar();
        initUserList();


    }


    private void initUserList() {
        //        ggUserListGone();
        ggUserListVisible();
//        ggUserCreateGone();
//        ggUserCreateVisible();
//        ggUserUpdateGone();
//        ggUserUpdateVisible();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> list = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User value = data.getValue(User.class);


                    value.setEmail(data.getKey());
//                    value.setEmail(data.getKey());
//                    myRef.child(String.valueOf(value)).orderByChild(value.getUserName());

                    list.add(value);
//                    Collections.sort(list, String.valueOf(list.get());
                }
                adapter.clear();
                adapter.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        userListRecyclerView = (RecyclerView) findViewById(R.id.user_list_recycler_view);
        userListRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        userListRecyclerView.setLayoutManager(layoutManager);
        usersList = list;
        adapter = new UserAdapter(usersList);
        userListRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        initSwipe();
    }

    @OnClick({R.id.fab, R.id.signUp, R.id.Update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab:
                ggUserListGone();
                ggUserCreateVisible();
                ggUserUpdateGone();
                break;
            case R.id.signUp:
                final String userNameData = userName.getText().toString();
                final String emailData = email.getText().toString();
                final String passwordData = password.getText().toString();
                final Boolean[] isAdminData = {false};

                String referencePath = "user/" + emailData.replace("@", "(at)").replace(".", "(dot)");
                DatabaseReference myRefUser = database.getReference(referencePath);

                myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        initListeners();


                        User value = dataSnapshot.getValue(User.class);

                        if (userNameData.isEmpty()) {
                            nameLabel.setError("pole wymagane");
                        } else if (emailData.isEmpty()) {
                            emailLabel.setError("pole wymagane");
                        } else if (value != null) {
                            emailLabel.setError("taki mail juz jest w bazie");
                        } else if (passwordData.isEmpty()) {
                            passwordLabel.setError("pole wymagane");
                            passwordLabel.setHintAnimationEnabled(true);
                        } else {
                            if (isAdmin.isChecked()) {
                                isAdminData[0] = true;
                            }

                            userName.setText("");
                            userName.clearFocus();
                            nameLabel.setError(null);
                            email.setText("");
                            email.clearFocus();
                            emailLabel.setError(null);
                            password.setText("");
                            password.clearFocus();
                            passwordLabel.setError(null);
                            isAdmin.setChecked(false);

//                            DatabaseReference myRef = database.getReference("user");

                            String user_key = String
                                    .valueOf(emailData)
                                    .replace("@", "(at)")
                                    .replace(".", "(dot)");
                            myRef
                                    .child(user_key)
                                    .setValue(
                                            new User(2, userNameData, emailData, passwordData, isAdminData[0])
                                            //User(int id, String userName, String email, String password, Boolean isAdmin) {
                                    );

                            ggUserListVisible();
                            ggUserCreateGone();
                            ggUserUpdateGone();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                break;
//            case R.id.Update:
//                initListenersUpdate();
//                final User item = adapter.getUserList().get(edit_position);
//
//                final String emailDataUpdate = item.getEmail();
//                final View root = LayoutInflater.from(UserActivity.this).inflate(R.layout.activity_users, null, false);
//                userNameUpdate.setText(item.getUserName());
//                passwordUpdate.setText(item.getPassword());
//                isAdminUpdate.setChecked(item.getIsAdmin());
//
//                ggUserListGone();
//                ggUserCreateGone();
//                ggUserUpdateVisible();
//
//
//
//                String referencePathUpdate = "user/" + item.getEmail().replace("@", "(at)").replace(".", "(dot)");
//                DatabaseReference myRefUserUpdate = database.getReference(referencePathUpdate);
//                myRefUserUpdate.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        final String userNameDataUpdate = userNameUpdate.getText().toString();
//
//                        final String passwordDataUpdate = passwordUpdate.getText().toString();
//                        final Boolean[] isAdminDataUpdate = {false};
//
//                        if (userNameDataUpdate.isEmpty()) {
//                            nameLabel.setError("pole wymagane");
//                        } else if (passwordDataUpdate.isEmpty()) {
//                            passwordLabel.setError("pole wymagane");
//                        } else {
//
//                            Update.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    final String userNameDataUpdate = userNameUpdate.getText().toString();
//                                    final String passwordDataUpdate = passwordUpdate.getText().toString();
//
//                                    final Boolean[] isAdminData = {false};
//
//                                    String referencePathUpdate = "user/" + emailDataUpdate.replace("@", "(at)").replace(".", "(dot)");
//                                    DatabaseReference myRefUserUpdate = database.getReference(referencePathUpdate);
//
//                                    item.setUserName(userNameDataUpdate);
//                                    item.setPassword(passwordDataUpdate);
//                                    item.setIsAdmin(isAdminUpdate.isChecked());
//                                    myRefUserUpdate.setValue(item);
//
//                                    startActivity(new Intent(UserActivity.this, UserActivity.class));
//                                    finish();
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//
////                ggUserListVisible();
////                ggUserCreateGone();
////                ggUserUpdateGone();
//                break;
        }
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
                    User item = adapter.getUserList().get(position);
                    String kkk = item.getEmail().replace("@", "(at)").replace(".", "(dot)");
                    myRef.child(kkk).removeValue();
                    adapter.removeItem(position);
                } else {
                    initListenersUpdate();

                    final User item = adapter.getUserList().get(position);
                    final String node = String.valueOf(item.getEmail());
//                    final String node = String.valueOf(item.getEmail().toString());
                    final String referencePath = "user/" + node.replace("@", "(at)").replace(".", "(dot)");

//                    final String emailDataUpdate = item.getEmail();
                    final View root = LayoutInflater.from(UserActivity.this).inflate(R.layout.activity_users, null, false);

                    userNameUpdate.setText(String.valueOf(item.getUserName()));
//                    emailUpdate.setText(String.valueOf(item.getEmail()));
                    passwordUpdate.setText(String.valueOf(item.getPassword()));
                    isAdminUpdate.setChecked(item.getIsAdmin());

                    ggUserListGone();
                    ggUserCreateGone();
                    ggUserUpdateVisible();



//                    final String referencePath = "user/" + emailUpdate.getText().toString().replace("@", "(at)").replace(".", "(dot)");
//                    final String referencePath = "user/" + item.getEmail().replace("@", "(at)").replace(".", "(dot)");
                    DatabaseReference myRefUser = database.getReference(referencePath);

                    Update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String userNameDataUpdate = userNameUpdate.getText().toString();
//                            final String emailDataUpdate = emailUpdate.getText().toString();
                            final String passwordDataUpdate = passwordUpdate.getText().toString();

                            final Boolean[] isAdminData = {false};

                            if (userNameDataUpdate.isEmpty()) {
                                nameLabel.setError("pole wymagane");
                            } else if (passwordDataUpdate.isEmpty()) {
                                passwordLabel.setError("pole wymagane");
                            } else {

//                                        String referencePathUpdate = "user/" + item.getEmail().replace("@", "(at)").replace(".", "(dot)");
//                                        String referencePathUpdate = "user/" + emailDataUpdate.replace("@", "(at)").replace(".", "(dot)");
                                DatabaseReference myRefUserUpdate = database.getReference(referencePath);

                                item.setUserName(userNameUpdate.getText().toString());
//                                item.setUserName(userNameDataUpdate);
//                                item.setEmail(emailDataUpdate);
                                item.setPassword(passwordDataUpdate);
                                item.setIsAdmin(isAdminUpdate.isChecked());
                                myRefUserUpdate.setValue(item);

                                startActivity(new Intent(UserActivity.this, UserActivity.class));
                                finish();
                            }
                        }
                    });



                    myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                    myRefUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            final String userNameDataUpdate = userNameUpdate.getText().toString();
//
//                            final String passwordDataUpdate = passwordUpdate.getText().toString();
//                            final Boolean[] isAdminDataUpdate = {false};

                            Update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final String userNameDataUpdate = userNameUpdate.getText().toString();
//                                    final String emailDataUpdate = emailUpdate.getText().toString();
                                    final String passwordDataUpdate = passwordUpdate.getText().toString();

                                    final Boolean[] isAdminData = {false};

                                    if (userNameDataUpdate.isEmpty()) {
                                        nameLabel.setError("pole wymagane");
                                    } else if (passwordDataUpdate.isEmpty()) {
                                        passwordLabel.setError("pole wymagane");
                                    } else {

//                                        String referencePathUpdate = "user/" + item.getEmail().replace("@", "(at)").replace(".", "(dot)");
//                                        String referencePathUpdate = "user/" + emailDataUpdate.replace("@", "(at)").replace(".", "(dot)");
                                        DatabaseReference myRefUserUpdate = database.getReference(referencePath);

                                        item.setUserName(userNameDataUpdate);
//                                        item.setEmail(emailDataUpdate);
                                        item.setPassword(passwordDataUpdate);
                                        item.setIsAdmin(isAdminUpdate.isChecked());
                                        myRefUserUpdate.setValue(item);

                                        startActivity(new Intent(UserActivity.this, UserActivity.class));
                                        finish();
                                    }
                                }
                            });
//                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    final User item = adapter.getUserList().get(position);
//                    final View root = LayoutInflater.from(UserActivity.this).inflate(R.layout.form_update_user, null, false);
//                    final EditText userNameEditText = (EditText) root.findViewById(R.id.userName);
//                    userNameEditText.setText(item.getUserName());
//                    final EditText passwordEditText = (EditText) root.findViewById(R.id.password);
//                    passwordEditText.setText(item.getPassword());
//                    final CheckBox isAdminCheckBox = (CheckBox) root.findViewById(R.id.isAdmin);
//                    isAdminCheckBox.setChecked(item.getIsAdmin());
//                    final String key = item.getEmail().replace("@", "(at)").replace(".", "(dot)");
//
//                    Button signUpButton = (Button) root.findViewById(R.id.signUp);
//                    signUpButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            item.setUserName(userNameEditText.getText().toString());
//                            item.setPassword(passwordEditText.getText().toString());
//                            item.setIsAdmin(isAdminCheckBox.isChecked());
//                            myRef.child(key).setValue(item);
//                        }
//                    });
//                    removeView();
//                    edit_position = position;
//                    alertDialog.setTitle(" ");
//                    alertDialog.setView(root);
//                    user_name_textview.setText(usersList.get(position).getUserName().toString());
//                    alertDialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView
                    recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {

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
        itemTouchHelper.attachToRecyclerView(userListRecyclerView);
    }

    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void initListeners() {
        myTextChangesListener(userName, nameLabel);
        myTextChangesListener(email, emailLabel);
        myTextChangesListener(password, passwordLabel);
    }

    private void initListenersUpdate() {
        myTextChangesListener(userNameUpdate, nameLabelUpdate);
        myTextChangesListener(passwordUpdate, passwordLabelUpdate);
    }

    private void initToolbar() {
//        final Toolbar toolbarUserCreate = (Toolbar) findViewById(R.id.toolbar_user_create);
        setSupportActionBar(toolbarUserCreate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarUserCreate.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ggUserListVisible();
                ggUserCreateGone();
                ggUserUpdateGone();
                initUserList();
            }
        });
//        final Toolbar toolbarUserUpdate = (Toolbar) findViewById(R.id.toolbar_user_update);
        setSupportActionBar(toolbarUserUpdate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarUserUpdate.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                ggUserListVisible();
//                toolbar.setVisibility(View.GONE);
                ggUserCreateGone();
                ggUserUpdateGone();
                initUserList();
            }
        });
//        final Toolbar toolbarUserList = (Toolbar) findViewById(R.id.toolbar_user_list);
        setSupportActionBar(toolbarUserList);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarUserList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                initUserList();
            }
        });
    }

    private void ggUserListGone() {
        toolbarUserList.setVisibility(View.GONE);
        ggUserList.setVisibility(View.GONE);
        userListRecyclerView.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }

    private void ggUserListVisible() {
        toolbarUserList.setVisibility(View.VISIBLE);
        ggUserList.setVisibility(View.VISIBLE);
        userListRecyclerView.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }

    private void ggUserCreateGone() {
        toolbarUserCreate.setVisibility(View.GONE);
        ggUserCreate.setVisibility(View.GONE);
        imageAddUser.setVisibility(View.GONE);
        userName.setVisibility(View.GONE);
        nameLabel.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        emailLabel.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        passwordLabel.setVisibility(View.GONE);
        isAdmin.setVisibility(View.GONE);
        signUp.setVisibility(View.GONE);
    }

    private void ggUserCreateVisible() {
        toolbarUserCreate.setVisibility(View.VISIBLE);
        ggUserCreate.setVisibility(View.VISIBLE);
        imageAddUser.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);
        nameLabel.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        emailLabel.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        passwordLabel.setVisibility(View.VISIBLE);
        isAdmin.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.VISIBLE);
    }

    private void ggUserUpdateGone() {
        toolbarUserUpdate.setVisibility(View.GONE);
        ggUserUpdate.setVisibility(View.GONE);
        imageEditUser.setVisibility(View.GONE);
        userNameUpdate.setVisibility(View.GONE);
        nameLabelUpdate.setVisibility(View.GONE);
        passwordUpdate.setVisibility(View.GONE);
        passwordLabelUpdate.setVisibility(View.GONE);
        isAdminUpdate.setVisibility(View.GONE);
        Update.setVisibility(View.GONE);
    }

    private void ggUserUpdateVisible() {
        toolbarUserUpdate.setVisibility(View.VISIBLE);
        ggUserUpdate.setVisibility(View.VISIBLE);
        imageEditUser.setVisibility(View.VISIBLE);
        userNameUpdate.setVisibility(View.VISIBLE);
        nameLabelUpdate.setVisibility(View.VISIBLE);
        passwordUpdate.setVisibility(View.VISIBLE);
        passwordLabelUpdate.setVisibility(View.VISIBLE);
        isAdminUpdate.setVisibility(View.VISIBLE);
        Update.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "no settings yet...", Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_logout) {
            Toast.makeText(this, "no logout yet...", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
