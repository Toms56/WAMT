package com.wamt.ui.main.user;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wamt.data.model.User;
import com.wamt.data.repository.UserPreferencesRepository;
import com.wamt.data.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final UserPreferencesRepository userPreferencesRepository;

    // Liste des utilisateurs, mise à jour automatiquement par Room
    // dès qu'une donnée change en base (LiveData observée en continu)
    private final LiveData<List<User>> allUsers;
    private final LiveData<Integer> userCount;

    private final MutableLiveData<User> selectedUser = new MutableLiveData<>();


    @Inject
    public UserViewModel(UserRepository repository, UserPreferencesRepository userPreferencesRepository){
        this.userRepository = repository;
        this.userCount = repository.getUserCount();
        this.userPreferencesRepository = userPreferencesRepository;
        this.allUsers = userRepository.getAllUsers();
    }

    //LiveData expose la liste en lecture seule, pour que le fragment ne puisse qu'observer, pas modifier.
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUserById(long id) {
        return userRepository.getUserById(id);
    }

    public void upsertUser(User user) {
        userRepository.upsertUser(user);
    }

    public LiveData<Integer> getUserCount() {
        return userCount;
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public void setDefaultUser(long userId) {
        userPreferencesRepository.setDefaultUser(userId);
        Log.d("UserViewModel", "Default user set to: " + userId);
    }

    public MutableLiveData<User> getSelectedUser() {
        return selectedUser;
    }

    public void selectUser(User user){
        selectedUser.setValue(user);
    }
}
