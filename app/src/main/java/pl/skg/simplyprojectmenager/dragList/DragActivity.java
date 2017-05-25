package pl.skg.simplyprojectmenager.dragList;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import pl.skg.simplyprojectmenager.model.Step;


public class DragActivity extends Activity {
    /** Called when the activity is first created. */


    private StepAdapter stepAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("test");

    @BindView(R.id.drag_fild_down)
    protected LinearLayout dragFildDown;

    @BindView(R.id.drag_fild_up)
    protected LinearLayout dragFildUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        ButterKnife.bind(this);
dragFildDown.setOnDragListener(new MyDragListener());
dragFildUp.setOnDragListener(new MyDragListener());


        String key = myRef.push().getKey();
        myRef.child(key).setValue(new Step("aaa","www",true,true));

        String key2 = myRef.push().getKey();
        myRef.child(key2).setValue(new Step("bbb","eee",true,true));

        String key22 = myRef.push().getKey();
        myRef.child(key22).setValue(new Step("ccc","rrr",true,true));

        String key3 = myRef.push().getKey();
        myRef.child(key3).setValue(new Step("ddd","ttt",true,true));


        stepAdapter= new StepAdapter(this, new ArrayList<Step>(), new MyTouchListener());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TAG", dataSnapshot.getChildrenCount() + "");
                List<Step> list = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Step value = data.getValue(Step.class);
                    value.setSectionId(data.getKey());
                    list.add(value);
                    View convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.step_row, null, false);
                    convertView.setOnTouchListener(new MyTouchListener());
                    ((LinearLayout)findViewById(R.id.drag_fild_up)).addView(convertView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}