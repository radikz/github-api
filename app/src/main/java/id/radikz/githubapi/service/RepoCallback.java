package id.radikz.githubapi.service;

import java.util.List;

import id.radikz.githubapi.model.RepoList;

public interface RepoCallback {

    void onSuccess(List<RepoList> repo);

    void onError(String error);

}