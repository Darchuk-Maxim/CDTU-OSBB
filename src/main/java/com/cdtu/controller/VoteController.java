package com.cdtu.controller;

import com.cdtu.entity.Question;
import com.cdtu.entity.Vote;
import com.cdtu.utils.UserUtils;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;
import org.rapidoid.jpa.JPA;

import java.util.List;

@Controller
public class VoteController {

    @GET("/vote/{qustionID}/{decision}")
    public void vote(long qustionID, long decision, Req req, Resp resp) {
        List<Question> all = JPA.jpql("SELECT u FROM Question u WHERE u.id = ?1", qustionID).all();

        Question question = all.get(0);

        Vote vote = new Vote();

        vote.question = question;
        vote.user = UserUtils.currentUser(req);
        vote.vote = decision == 1;

        JPA.save(vote);

        resp.redirect("/osbb");
    }

}
