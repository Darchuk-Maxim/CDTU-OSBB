package com.cdtu;

import com.cdtu.entity.User;
import org.rapidoid.jpa.JPA;
import org.rapidoid.setup.App;
import org.rapidoid.setup.My;
import org.rapidoid.setup.On;
import org.rapidoid.u.U;

/**
 * Created by Максим on 16.06.2017.
 */
public class Application {

    public static void main(String[] args) {
        App.bootstrap(args).jpa().auth();

        My.loginProvider((req, username, password) -> {
                    User user = JPA.jpql("SELECT u FROM User u WHERE u.login = ?1", username).getSingleResult();

                    return user != null && user.password.equals(password);
                }
        );
        My.rolesProvider((req, username) -> {
            User user = JPA.jpql("SELECT u FROM User u WHERE u.login = ?1", username).getSingleResult();
            return U.set(user.profileRole.name());
        });

//        On.page("/").mvc;
//        On.page("/osbb").roles("ADMIN").view("osbbcreate").mvc("");
    }

}
