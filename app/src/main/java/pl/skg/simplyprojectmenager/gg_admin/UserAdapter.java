package pl.skg.simplyprojectmenager.gg_admin;

/**
 * Created by Grzegorz Goździak on 2017-05-27.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.skg.simpleprojectmenager.R;
import pl.skg.simplyprojectmenager.model.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.user_name_textview.setText(userList.get(i).getUserName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addItem(User user) {
        userList.add(user);
        notifyItemInserted(userList.size());
    }

    public void clear() {
        int size = userList.size();
        userList.clear();
        notifyItemRangeRemoved(0, (size - 1));
    }

    public void addAll(List<User> userList) {
        int size = userList.size();
        this.userList.addAll(userList);
        notifyItemRangeInserted(0, (size - 1));
    }

    public void removeItem(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_name_textview;

        public ViewHolder(View view) {
            super(view);

            user_name_textview = (TextView) view.findViewById(R.id.user_row);
        }
    }
}
