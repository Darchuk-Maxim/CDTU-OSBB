package com.cdtu.controller;

import com.cdtu.entity.*;
import com.cdtu.role.OSBBRole;
import com.cdtu.utils.UserUtils;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.annotation.Page;
import org.rapidoid.gui.GUI;
import org.rapidoid.html.customtag.ColspanTag;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;
import org.rapidoid.jpa.JPA;
import org.rapidoid.security.annotation.LoggedIn;
import org.rapidoid.security.annotation.Roles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller("/osbb")
public class OSBBController {

    @LoggedIn
    @Page("/")
    public Object osbbGet(Req request) {
        if (!isUserHaveSubscription(UserUtils.currentUser(request))) {
            return GUI.render("osbbcreate.html");
        } else {
            User user = UserUtils.currentUser(request);

            Subscription subscription = (Subscription) JPA.jpql("SELECT sub FROM Subscription sub WHERE sub.user.id = ?1", user.id).all().get(0);
            Osbb osbb = subscription.osbb;

            List<Question> all = JPA.jpql("SELECT u FROM Question u WHERE u.osbb.id = ?1", (long) osbb.id).all();
            return showQuestionAndVoteButton(all);
        }
    }

    private Object showQuestionAndVoteButton(List<Question> all) {
        List<ColspanTag> tags = new ArrayList<>();

        all.forEach(el -> {
                    List<Vote> votes = JPA.jpql("SELECT v FROM Vote v WHERE v.question.id = ?1", el.id).all();
                    Map<Boolean, List<Vote>> collect = votes.stream().collect(Collectors.groupingBy(el1 -> el1.vote));

                    tags.add(
                            GUI.col12(
                                    GUI.div(GUI.col12(GUI.h4(GUI.a(el.question).href("/question/detail/" + el.id))),
                                            GUI.col4(GUI.col2(GUI.btn("За").info().go("/vote/" + el.id + "/1")),
                                                     GUI.col2(GUI.h4(collect.getOrDefault(true, new ArrayList<>()).size())),
                                                     GUI.col3(GUI.btn("Против").warning().go("/vote/" + el.id + "/0")),
                                                     GUI.col2(GUI.h4(collect.getOrDefault(false, new ArrayList<>()).size())))
                                    )
                            )
                    );
                }
        );

        return GUI.col12(
                GUI.col12(GUI.btn("Создать вопрос").success().go("/question")),
                GUI.col12(GUI.titleBox("Список вопросов")),
                GUI.col12(tags.toArray(new ColspanTag[tags.size()])));
    }

//    @LoggedIn
//    @POST("/qustion")
//    public void qustionSave(Question question, Req req, Resp resp) {
//        User user = UserUtils.currentUser(req);
//        Osbb osbb = getOsbb(user);
//        question.osbb = osbb;
//
//        JPA.save(question);
//        resp.redirect("/osbb");
//    }



    @Roles("ADMIN")
    @POST("/save")
    public void create(Osbb osbb, Req request, Resp resp) {
        User user = UserUtils.currentUser(request);
        osbb.admin = user;
        Subscription subscription = new Subscription();
        subscription.roleOnOsb = OSBBRole.ADMIN;
        Osbb save = JPA.save(osbb);
        subscription.osbb = save;
        subscription.user = user;
        subscription.isActivated = true;
        JPA.save(subscription);
        resp.redirect("/osbb");
    }

    @Roles("ADMIN")
    @GET("/subscribe/{osbbID}")
    public void subscribe(long osbbID, Req request, Resp resp) {
        User user = UserUtils.currentUser(request);
        Subscription subscription = new Subscription();

        Osbb osbb = JPA.jpql("SELECT u FROM Osbb u WHERE u.id = ?1", osbbID).getSingleResult();

        subscription.user = user;
        subscription.isActivated = false;
        subscription.osbb = osbb;
        subscription.roleOnOsb = OSBBRole.CUSTOMER;

        JPA.save(subscription);

        resp.redirect("/");
    }

    private boolean isUserHaveSubscription(User user) {
        List<Subscription> sub = new ArrayList();
        if (user != null) {
            sub = JPA.jpql("SELECT sub FROM Subscription sub WHERE sub.user.id = ?1", user.id).all();
        }
        ;
        return sub.size() > 0;
    }

}
