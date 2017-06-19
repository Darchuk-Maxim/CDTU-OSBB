package com.cdtu.controller;

import com.cdtu.entity.Osbb;
import com.cdtu.entity.Question;
import com.cdtu.entity.User;
import com.cdtu.entity.Vote;
import com.cdtu.utils.UserUtils;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.POST;
import org.rapidoid.annotation.Page;
import org.rapidoid.gui.GUI;
import org.rapidoid.html.customtag.ColspanTag;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;
import org.rapidoid.jpa.JPA;
import org.rapidoid.security.annotation.LoggedIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller("/question")
public class QustionController {

    @LoggedIn
    @Page("/")
    public Object showQustionCreatePage() {
        return GUI.render("questioncreate.html");
    }

    @LoggedIn
    @POST("/create")
    public void create(Question question, Req req, Resp resp) {
        User user = UserUtils.currentUser(req);
        Osbb osbb = getOsbb(user);
        question.osbb = osbb;

        JPA.save(question);
        resp.redirect("/osbb");
    }

    @LoggedIn
    @Page("/detail/{id}")
    public Object detail(long id) {
        Question question = JPA.jpql("SELECT q FROM Question q WHERE q.id = ?1", id).getSingleResult();

        List<Vote> votes = JPA.jpql("SELECT v FROM Vote v WHERE v.question.id = ?1 AND v.question.isAnonymous = 0", id).all();

        return GUI.col12(
                GUI.col12(GUI.h4(question.question)),
                GUI.br(),
                GUI.h4("Описание: "),
                GUI.col12(question.detail),
                getVotes(votes)
        );
    }

    private ColspanTag getVotes(List<Vote> votes) {
        if(votes.size() == 0) {
            return GUI.col12();
        }

        Map<Boolean, List<User>> collect =
                votes.stream().collect(Collectors.groupingBy(el -> el.vote, Collectors.mapping(el -> el.user, Collectors.toList())));

        return GUI.col12(
                GUI.col6(GUI.col12(GUI.h3("Голосовавшие за:")),
                        getUserList(collect.getOrDefault(true, new ArrayList<>()))),
                GUI.col6(GUI.col12(GUI.h3("Голосовавшие против:")),
                        getUserList(collect.getOrDefault(false, new ArrayList<>())))
        );
    }

    private List<ColspanTag> getUserList(List<User> users) {
        return users.stream().map(el -> GUI.col12(GUI.h3(el.login))).collect(Collectors.toList());
    }

    private Osbb getOsbb(User user) {
        List<Osbb> all = JPA.jpql("SELECT sub FROM Osbb sub WHERE sub.admin.id = ?1", user.id).all();
        return all.get(0);
    }

}
