package com.wamt.di;

import android.content.Context;

import androidx.room.Room;

import com.wamt.data.WamtDatabase;
import com.wamt.data.dao.IUserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public WamtDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), WamtDatabase.class,  "WamptDatabse").build();
    }

    @Provides
    public IUserDao provideUserDao(WamtDatabase database) {
        return database.userDao();
    }
}
