package id.radikz.githubapi.service;

import java.util.List;

import id.radikz.githubapi.model.User;

public interface UserCallback {

    void onSuccess(List<User> user);

    void onError();

}
