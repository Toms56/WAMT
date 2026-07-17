package com.wamt.ui.main.user.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class CreateUserViewModel extends ViewModel {

    private static final int MIN_USERNAME_LENGTH = 2;

    // Pseudo saisi en cours de frappe, initialisé à "" (pas null) pour que
    // isUsernameValid soit évalué dès le départ (false, car < MIN_USERNAME_LENGTH)
    private final MutableLiveData<String> username = new MutableLiveData<>("");
    private final LiveData<Boolean> isUsernameValid;

    public CreateUserViewModel() {
        isUsernameValid = Transformations.map(
                username, text-> text!= null && text.trim().length() >= MIN_USERNAME_LENGTH
        );
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public LiveData<Boolean> getIsUsernameValid() {
        return isUsernameValid;
    }
}
