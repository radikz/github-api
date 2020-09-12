package id.radikz.githubapi;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.radikz.githubapi.adapter.UserAdapter;
import id.radikz.githubapi.model.User;
import id.radikz.githubapi.service.UserCallback;
import id.radikz.githubapi.service.UserRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private static final String STATE_RESULT = "state_result";

    ListView listView;
    EditText editUser;
    private UserAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;

    //    private CoordinatorLayout coordinatorLayout;
    private UserRepository userRepository;
    ProgressDialog loading;
    public static String userSearch;

    private Parcelable mLayoutManagerState;


    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable(STATE_RESULT, mLayoutManagerState);
    }

    @Override
    public void onPause() {
        super.onPause();

        mLayoutManagerState = recyclerView.getLayoutManager().onSaveInstanceState();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRepository = UserRepository.getInstance();
//        listView = findViewById(R.id.listView);
        listView = view.findViewById(R.id.list_item);
        editUser = view.findViewById(R.id.edit_user);

        recyclerView =  view.findViewById(R.id.recycler_user_list);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        editUser.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    gatherData();
                    handled = true;
                }
                return handled;
            }
        });

        if (savedInstanceState != null) {
            mLayoutManagerState = savedInstanceState.getParcelable(STATE_RESULT);
            recyclerView.getLayoutManager().onRestoreInstanceState(mLayoutManagerState);
        }
        else{
            gatherData();
        }




    }

    private void gatherData(){
        if (!isNetworkAvailable()) {
            Log.d("connection", "gak onok internet");
        }
        else{
            Log.d("connection", "sip internet");



            if (isEmpty(editUser)){

                userSearch = "a";
            }
            else{
                userSearch = editUser.getText().toString().trim();

//                Toast.makeText(getActivity(), arg.getFilter(), Toast.LENGTH_SHORT).show();
            }
            loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);

            userRepository.getUser(new UserCallback() {



                @Override
                public void onSuccess(List<User> user) {
                    Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_SHORT).show();
                    adapter = new UserAdapter(getActivity(), user);
                    recyclerView.setAdapter(adapter);
                    loading.dismiss();
                    Log.d("connection", "sip Adapter");


                }

                @Override
                public void onError() {
                    Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Log.d("connection", "Please check your internet connection");

                }
            });
        }
    }

    private boolean isEmpty(EditText etText)
    {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
