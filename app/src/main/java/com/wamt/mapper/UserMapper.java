package com.wamt.mapper;

import com.wamt.data.entity.UserEntity;
import com.wamt.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    private UserMapper() {}

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(entity.getId(), entity.getPseudo());
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity(user.getPseudo());
        entity.setId(user.getId());
        entity.setPseudo(user.getPseudo());
        return entity;
    }

    public static List<User> toDomainList(List<UserEntity> entities) {
        List<User> result = new ArrayList<>();
        for (UserEntity e : entities) {
            result.add(toDomain(e));
        }
        return result;
    }
}
