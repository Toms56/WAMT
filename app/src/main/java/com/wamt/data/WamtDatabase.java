package com.wamt.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.wamt.converters.DateConverter;
import com.wamt.data.dao.IUserDao;
import com.wamt.data.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = true) //Premiere version de la bdd
@TypeConverters({DateConverter.class})
public abstract class WamtDatabase extends RoomDatabase {

    private static volatile WamtDatabase INSTANCE; //Volatile : ecriture de chaque I partagée entre tous les threads, et non dans le cache local de chaque thread
    public abstract IUserDao userDao();

    public static WamtDatabase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (WamtDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WamtDatabase.class,  "WamptDatabse").build();
                }
            }
        }
        return INSTANCE;
    }

}
