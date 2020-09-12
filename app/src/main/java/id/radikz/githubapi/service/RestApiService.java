package id.radikz.githubapi.service;

import java.util.List;

import id.radikz.githubapi.model.RepoList;
import id.radikz.githubapi.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RestApiService {

    @GET("search/users")
//    @GET("movie/popular")
    Call<UserList> getUserList(@Query("q") String filter);

    @GET("users/{login}/repos")
    Call<List<RepoList>> getRepoList(@Path("login") String login);

}
