package pl.skg.simplyprojectmenager.admin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.model.Step;

public class ProcessListAdapter extends RecyclerView.Adapter<ProcessListAdapter.ViewHolder> {

    private List<Step> stepList;

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public ProcessListAdapter(List<Step> stepList) {
        this.stepList = stepList;
    }

    @Override
    public ProcessListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.userRow.setText(stepList.get(i).getStepName());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public void clear() {
        int size = stepList.size();
        stepList.clear();
        notifyItemRangeRemoved(0, (size - 1));
    }

    public void addAll(List<Step> stepsList) {
        int size = stepsList.size();
        stepList.addAll(stepsList);
        notifyItemRangeInserted(0, (size - 1));
    }

    public void removeItem(int position) {
        stepList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, stepList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userRow;

        public ViewHolder(View view) {
            super(view);

            userRow = (TextView) view.findViewById(R.id.user_row);
        }
    }
}
