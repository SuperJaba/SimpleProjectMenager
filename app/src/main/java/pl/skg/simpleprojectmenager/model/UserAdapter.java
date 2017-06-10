package pl.skg.simpleprojectmenager.model;

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

public class UserAdapter extends ArrayAdapter<User> {

    @BindView(R.id.name)
    protected TextView name;


    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final User model = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_row, parent, false);
            convertView.setLongClickable(true);
        }
        ButterKnife.bind(this, convertView);
        name.setText(model.getEmail());

        return convertView;
    }
}