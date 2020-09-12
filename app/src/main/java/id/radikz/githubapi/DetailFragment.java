package id.radikz.githubapi;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import id.radikz.githubapi.adapter.RepoAdapter;
import id.radikz.githubapi.model.RepoList;
import id.radikz.githubapi.model.User;
import id.radikz.githubapi.service.RepoCallback;
import id.radikz.githubapi.service.RestApiService;
import id.radikz.githubapi.service.UserCallback;
import id.radikz.githubapi.service.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.radikz.githubapi.UserFragment.userSearch;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    public static String KEY_ACTIVITY = "msg_activity";
    public static String USER_REPO;


    private RestApiService api;
    private TextView loginText, repoText;
    private User user;
    private RepoAdapter adapter;
    private ArrayList<RepoList> repoList;
    private ImageView avatar;
//    List<RepoList> list = new ArrayList<RepoList>();
    private ListView listView;
    ProgressDialog loading;

    private UserRepository userRepository;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRepository = UserRepository.getInstance();

        listView = view.findViewById(R.id.listView);

        avatar = view.findViewById(R.id.user_avatar_repo);
        loginText = view.findViewById(R.id.user_name_repo);


//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);

//        loginText = view.findViewById(R.id.detail_login);
//        repoText = view.findViewById(R.id.de);

        Bundle bundle = this.getArguments();

        loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);


        if (bundle != null){
            user = bundle.getParcelable(KEY_ACTIVITY); // Key
            USER_REPO = user.getLogin();
        }

        Glide.with(getActivity()).load(user.getAvatarUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(avatar) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                avatar.setImageDrawable(circularBitmapDrawable);
            }
        });

        loginText.setText(user.getLogin());

        userRepository.getRepo(new RepoCallback() {
            @Override
            public void onSuccess(List<RepoList> repo) {
                adapter = new RepoAdapter(getActivity(), R.layout.list_repo, repo);
                listView.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });

    }

}
