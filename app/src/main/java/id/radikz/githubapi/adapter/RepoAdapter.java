package id.radikz.githubapi.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import id.radikz.githubapi.R;
import id.radikz.githubapi.model.RepoList;

public class RepoAdapter extends ArrayAdapter<RepoList> {

    private Context context;
    private List<RepoList> repos;

    public RepoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<RepoList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.repos = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_repo, parent, false);

//        TextView txtUserId = rowView.findViewById(R.id.txtUserId);
        TextView txtUsername = rowView.findViewById(R.id.textView_repo);
        TextView txtDesc = rowView.findViewById(R.id.textView_desc);

//        txtUserId.setText(String.format("#ID: %d", repos.get(pos).getId()));
        txtUsername.setText(String.format("%s", repos.get(pos).getName()));
        txtDesc.setText(String.format("%s", repos.get(pos).getDescription()));

        return rowView;
    }
}