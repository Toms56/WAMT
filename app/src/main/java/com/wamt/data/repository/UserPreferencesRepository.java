package com.wamt.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class UserPreferencesRepository {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_DEFAULT_USER_ID = "default_user_id";
    private static final long NO_DEFAULT_USER = -1L;

    private final SharedPreferences sharedPreferences;

    @Inject
    public UserPreferencesRepository(@ApplicationContext Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setDefaultUser(long userId) {
        sharedPreferences.edit()
                .putLong(KEY_DEFAULT_USER_ID, userId)
                .apply();
    }

    public void clearDefaultUser() {
        sharedPreferences.edit()
                .remove(KEY_DEFAULT_USER_ID)
                .apply();
    }

    public long getDefaultUserId() {
        return sharedPreferences.getLong(KEY_DEFAULT_USER_ID, NO_DEFAULT_USER);
    }

    public boolean hasDefaultUser() {
        return getDefaultUserId() != NO_DEFAULT_USER;
    }
}
