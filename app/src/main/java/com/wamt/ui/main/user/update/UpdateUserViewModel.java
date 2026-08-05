package com.wamt.ui.main.user.update;

import com.wamt.ui.main.user.AbstractUserNameViewModel;

public class UpdateUserViewModel extends AbstractUserNameViewModel{
    // Ne pas réécraser la saisie de l'utilisateur si la LiveData
    // du User réémet après le premier chargement
    private boolean initialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public void markInitialized() {
        initialized = true;
    }
}