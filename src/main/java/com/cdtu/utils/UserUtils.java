package com.cdtu.utils;

import com.cdtu.entity.User;
import com.sun.istack.internal.Nullable;
import org.rapidoid.http.Req;
import org.rapidoid.jpa.JPA;

import java.io.Serializable;
import java.util.Map;

public class UserUtils {

    @Nullable
    public static User currentUser(Req req) {
        Map<String, Serializable> token = req.token();
        User user = null;
        if (token != null && token.size() > 0) {
            user = JPA.jpql("SELECT u FROM User u WHERE u.login = ?1", token.get("_user").toString()).getSingleResult();
        }
        return user;
    }

}
