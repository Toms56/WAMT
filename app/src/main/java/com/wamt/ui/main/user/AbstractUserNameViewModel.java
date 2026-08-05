package com.wamt.ui.main.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public abstract class AbstractUserNameViewModel extends ViewModel{
    private static final int MIN_USERNAME_LENGTH = 2;

    protected final MutableLiveData<String> username = new MutableLiveData<>("");
    protected final LiveData<Boolean> isUsernameValid;

    protected AbstractUserNameViewModel() {
        isUsernameValid = Transformations.map(username, text -> text != null &&
                text.trim().length() >= MIN_USERNAME_LENGTH);
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public LiveData<Boolean> getIsUsernameValid() {
        return isUsernameValid;
    }
}
