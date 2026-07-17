package com.wamt.ui.main.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.wamt.data.model.User;
import com.wamt.data.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;

    // Liste des utilisateurs, mise à jour automatiquement par Room
    // dès qu'une donnée change en base (LiveData observée en continu)
    private final LiveData<List<User>> allUsers;


    @Inject
    public UserViewModel(UserRepository repository){
        this.userRepository = repository;
        this.allUsers = userRepository.getAllUsers();
    }

    //LiveData expose la liste en lecture seule, pour que le fragment ne puisse qu'observer, pas modifier.
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUserById(long id) {
        return userRepository.getUserById(id);
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

}
