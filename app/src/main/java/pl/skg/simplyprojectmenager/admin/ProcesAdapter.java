package pl.skg.simplyprojectmenager.admin;

/**
 * Created by RENT on 2017-05-27
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.model.Step;


public class ProcesAdapter extends RecyclerView.Adapter<ProcesAdapter.ViewHolder> {

    private List<Step> countries;

    public List<Step> getCountries() {
        return countries;
    }

    public void setCountries(List<Step> countries) {
        this.countries = countries;
    }

    public ProcesAdapter(List<Step> countries) {
        this.countries = countries;
    }

    @Override
    public ProcesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvCountry.setText(countries.get(i).getStepName());
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

//    public void addItem(User country) {
//        countries.add(country);
//        notifyItemInserted(countries.size());
//    }

    public void clear() {
        int size = countries.size();
        countries.clear();
        notifyItemRangeRemoved(0, (size - 1));
    }

    public void addAll(List<Step> stepsList) {
        int size = stepsList.size();
        countries.addAll(stepsList);
        notifyItemRangeInserted(0, (size - 1));
    }

    public void removeItem(int position) {
        countries.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, countries.size());
    }

//    public void removeItem(int position) {
//        stepNameList.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, stepNameList.size());
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountry;

        public ViewHolder(View view) {
            super(view);

            tvCountry = (TextView) view.findViewById(R.id.user_row);
        }
    }
}
