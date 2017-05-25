package pl.skg.simplyprojectmenager.dragList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.model.Step;


public class StepAdapter extends ArrayAdapter<Step> {


    private final DragActivity.MyTouchListener myTouchListener;
    @BindView(R.id.id_button_step)
    protected Button stepButton;

    public StepAdapter(Context context, List<Step> stepList, DragActivity.MyTouchListener myTouchListener) {
        super(context, 0, stepList);
        this.myTouchListener = myTouchListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Step model = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_row, parent, false);
            convertView.setLongClickable(true);
        }
        ButterKnife.bind(this, convertView);
        stepButton.setText(model.getStepName());
        convertView.setOnTouchListener(myTouchListener);
        return convertView;
    }
}