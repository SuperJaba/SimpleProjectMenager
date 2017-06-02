package pl.skg.simplyprojectmenager.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.skg.simpleprojectmenager.R;

/**
 * Created by Karamba on 2017-06-01
 */

public class ProcesAdapter extends ArrayAdapter<Proces> {


    @BindView(R.id.procesRow_procesName_textView)
     TextView procesRowProcesNameTextView;
    @BindView(R.id.procesRow_amount_textView)
     TextView procesRowAmountTextView;
    @BindView(R.id.procesRow_description_textView)
     TextView procesRowDescriptionTextView;

    public ProcesAdapter(Context context, List<Proces> procesList) {
        super(context, 0, procesList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Proces proces = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.proces_row, parent, false);
            convertView.setLongClickable(true);
        }
        ButterKnife.bind(this, convertView);

        if (proces != null) {
            procesRowProcesNameTextView.setText(proces.getProcesName());
            procesRowAmountTextView.setText(String.valueOf(proces.getAmount()));
            procesRowDescriptionTextView.setText(proces.getDescription());
        }



        return convertView;
    }
}
