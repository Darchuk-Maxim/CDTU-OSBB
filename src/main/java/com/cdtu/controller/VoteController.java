package com.cdtu.controller;

import com.cdtu.entity.Question;
import com.cdtu.entity.User;
import com.cdtu.entity.Vote;
import com.cdtu.utils.UserUtils;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.Param;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;
import org.rapidoid.jpa.JPA;
import org.rapidoid.security.annotation.LoggedIn;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VoteController {

    @LoggedIn
    @GET("/vote/{qustionID}/{decision}")
    public void vote(@Param("qustionID") long qustionID, @Param("decision") long decision, Req req, Resp resp) {
        List<Question> all = JPA.jpql("SELECT u FROM Question u WHERE u.id = ?1", qustionID).all();
//
//
        Question question = all.get(0);
//
        User user = UserUtils.currentUser(req);
//
        List<Vote> votes = JPA.jpql("SELECT v FROM Vote v where v.user.id = ?1 AND v.question.id = ?2", user.id, qustionID).all();
//        List<Vote> votes = new ArrayList<>();
//
//
        if (votes.size() == 0) {
            createVote(user, question, decision == 1);
        } else {
            updateVote(votes.get(0), decision == 1);
        }

        resp.redirect("/osbb");
    }

    private void updateVote(Vote vote, boolean decision) {
        vote.vote = decision;
        JPA.update(vote);
    }

    private void createVote(User user, Question question, boolean decision) {
        Vote vote = new Vote();
            vote.question = question;
            vote.user = user;
            vote.vote = decision;

            JPA.save(vote);
    }

}
