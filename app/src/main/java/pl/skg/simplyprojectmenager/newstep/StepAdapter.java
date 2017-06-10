package pl.skg.simplyprojectmenager.newstep;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.skg.simplyprojectmenager.R;
import pl.skg.simplyprojectmenager.model.Step;

/**
 * Created by k.czechowski83@gmail.com on 2017-06-03.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private List<Step> stepNameList;

    public List<Step> getStepNameList() {
        return stepNameList;
    }

    public StepAdapter(List<Step> stepNameList) {
        this.stepNameList = stepNameList;
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_row_layout, viewGroup, false);
        return new StepAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder viewHolder, int i) {

        viewHolder.stepName.setText(stepNameList.get(i).getStepName().toString());
    }

    @Override
    public int getItemCount() {
        return stepNameList.size();
    }

    public void addItem(Step country) {
        stepNameList.add(country);
        notifyItemInserted(stepNameList.size());
    }

    public void clear() {
        int size = stepNameList.size();
        stepNameList.clear();
        notifyItemRangeRemoved(0, (size - 1));
    }

    public void addAll(List<Step> stringList) {
        int size = stringList.size();
        stepNameList.addAll(stringList);
        notifyItemRangeInserted(0, (size - 1));
    }

    public void removeItem(int position) {
        stepNameList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, stepNameList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepName;

        public ViewHolder(View view) {
            super(view);
            stepName = (TextView) view.findViewById(R.id.step_name);

        }
    }
}