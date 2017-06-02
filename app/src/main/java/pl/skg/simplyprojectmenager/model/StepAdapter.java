package pl.skg.simplyprojectmenager.model;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.skg.simpleprojectmenager.R;

/**
 * Created by Karamba on 2017-06-02.
 */

public class StepAdapter extends ArrayAdapter<Step> {

    @BindView(R.id.row_stepName_textView)
    TextView rowStepNameTextView;
    @BindView(R.id.row_stepId_textView)
    TextView rowStepIdTextView;
    @BindView(R.id.row_stepStatus_textView)
    TextView rowStepStatusTextView;
    @BindView(R.id.row_stepIcon_imageView)
    ImageView rowStepIconImageView;
    @BindView(R.id.row_separator)
    EditText rowSeparator;

    public StepAdapter(Context context, List<Step> stepList) {
        super(context, 0, stepList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Step step = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_row, parent, false);
            convertView.setLongClickable(true);
        }
        ButterKnife.bind(this, convertView);

        if (step != null) {
            rowStepNameTextView.setText(step.getStepName());
            rowStepIdTextView.setText(step.getSectionId());
            switch (step.getStatus()){
                case 0:
                    rowStepStatusTextView.setText("Awaiting");
                    rowStepIconImageView.setImageResource(R.drawable.step_row_awating_icon);
                case 1:
                    rowStepStatusTextView.setText("Work in progres");
                    rowStepIconImageView.setImageResource(R.drawable.ic_menu_manage);
                case 2:
                    rowStepStatusTextView.setText("Done");
                    rowStepIconImageView.setImageResource(R.drawable.step_row_ok);

            }
        }

        return convertView;
    }

}
