package com.wamt.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.wamt.data.dao.IUserDao;
import com.wamt.data.entity.UserEntity;
import com.wamt.data.model.User;
import com.wamt.mapper.UserMapper;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class UserRepository {

    private final IUserDao userDao;

    private final ExecutorService executorService;

    @Inject
    public UserRepository(IUserDao userDao, ExecutorService executorService) {
        this.userDao = userDao;
        this.executorService = executorService;
    }

    public LiveData<User> getUserById(long id) {
        return Transformations.map(userDao.getUserById(id), UserMapper::toDomain);
    }

    public LiveData<List<User>> getAllUsers() {
        return Transformations.map(userDao.getAllUsers(), UserMapper::toDomainList);
    }

    public void insertUser(User user) {
        executorService.execute(() -> userDao.insertUser(UserMapper.toEntity(user)));
    }

    public void deleteUser(User user) {
        executorService.execute(() -> userDao.deleteUser(UserMapper.toEntity(user)));
    }
}
