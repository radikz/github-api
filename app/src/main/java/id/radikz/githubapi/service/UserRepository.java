package id.radikz.githubapi.service;

import android.support.annotation.NonNull;
import android.util.Log;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import id.radikz.githubapi.model.RepoList;
import id.radikz.githubapi.model.RepoList2;
import id.radikz.githubapi.model.UserList;
import id.radikz.githubapi.util.TLSSocketFactory;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static id.radikz.githubapi.DetailFragment.USER_REPO;
import static id.radikz.githubapi.UserFragment.userSearch;


public class UserRepository {

    private static final String BASE_URL = "https://api.github.com/";
    private static UserRepository repository;

    private RestApiService api;

    List<RepoList> repos = new ArrayList<RepoList>();



    private UserRepository(RestApiService api) {
        this.api = api;
    }

    public static UserRepository getInstance() {
        OkHttpClient client=new OkHttpClient();
        try {
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(new TLSSocketFactory())
                    .build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new UserRepository(retrofit.create(RestApiService.class));
        }

        return repository;
    }

    public void getUser(final UserCallback callback) {


        api.getUserList(userSearch)
                .enqueue(new Callback<UserList>() {
                    @Override
                    public void onResponse(@NonNull Call<UserList> call, @NonNull Response<UserList> response) {
                        if (response.isSuccessful()) {
                            UserList userResponse = response.body();
                            if (userResponse != null && userResponse.getItems() != null) {
                                callback.onSuccess(userResponse.getItems());
                            } else {
                                callback.onError();
                                Log.d("Error", "userResponse = null && userResponse.getItems() = null");
                            }
                        } else {
                            callback.onError();
                            Log.d("Error", "response.failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserList> call, Throwable t) {
                        callback.onError();
                        Log.d("Error", t.getMessage());
                    }
                });
    }

    public void getRepo(final RepoCallback callback1) {
        api.getRepoList(USER_REPO)
                .enqueue(new Callback<List<RepoList>>() {
                    @Override
                    public void onResponse(Call<List<RepoList>> call, Response<List<RepoList>> response) {
                        repos = response.body();
                        callback1.onSuccess(repos);
                        Log.d("Error", "sukses");
                    }

                    @Override
                    public void onFailure(Call<List<RepoList>> call, Throwable t) {
                        callback1.onError(t.getMessage());
                        Log.d("Error", t.getMessage());
                    }


                });
    }
}
