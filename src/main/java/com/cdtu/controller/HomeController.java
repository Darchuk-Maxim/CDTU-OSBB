package com.cdtu.controller;

import com.cdtu.entity.Osbb;
import com.cdtu.entity.Subscription;
import com.cdtu.entity.User;
import com.cdtu.utils.UserUtils;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.gui.GUI;
import org.rapidoid.html.customtag.ColspanTag;
import org.rapidoid.http.Req;
import org.rapidoid.jpa.JPA;
import org.rapidoid.security.annotation.LoggedIn;

import java.util.ArrayList;
import java.util.List;

@Controller("/")
public class HomeController {

    @LoggedIn
    @Page("/")
    public Object home(Req req) {
        List<Osbb> all = JPA.of(Osbb.class).all();
        List<ColspanTag> tags = new ArrayList<>();

        User user = UserUtils.currentUser(req);

        List<Subscription> sub = new ArrayList();
        if (user != null) {
            sub = JPA.jpql("SELECT sub FROM Subscription sub WHERE sub.user.id = ?1", user.id).all();
        }

        if (sub.size() > 0) {
            Subscription subscription = sub.get(0);
            Osbb osbb = subscription.osbb;
            List<Subscription> all1 = JPA.jpql("SELECT s FROM Subscription s WHERE s.osbb.id =?1", osbb.id).all();
            all1.forEach(el -> tags.add(GUI.col12(GUI.h5(el.user.login))));
            return GUI.col12(
                    GUI.h2(osbb.city + ", " + osbb.street + ", " + osbb.number),
                    GUI.h3("Пользователи ОСББ"),
                    GUI.col12(tags.toArray(new ColspanTag[tags.size()]))
                    );
        } else {
            all.forEach(el -> {
//                tags.add(GUI.col4(GUI.show(el).buttons(
//                        GUI.btn("Subscribe").go("osbb/subscribe/" + el.id)
//                )));

                tags.add(GUI.col12(GUI.col12(
                        GUI.h4(el.city + ", " + el.street + ", " + el.number)),
                        GUI.col4(GUI.btn("Subscribe").go("osbb/subscribe/" + el.id)))
                        );
            });
        }


        return GUI.col12(tags.toArray(new ColspanTag[tags.size()]));
    }

}
