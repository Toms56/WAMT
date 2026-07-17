package com.wamt.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

import com.wamt.data.entity.UserEntity;

@Dao
public interface IUserDao {

    @Query("""
SELECT * FROM user WHERE id = :id
""")
    LiveData<UserEntity> getUserById(long id);

    //Conteneur de données observables, il notifie les observateurs lors des changements dans les données qu'il contient
    @Query("""
SELECT * FROM user
""")
    LiveData<List<UserEntity>> getAllUsers();

    @Upsert
    void insertUser(UserEntity user);

    @Delete
    void deleteUser(UserEntity user);
}
