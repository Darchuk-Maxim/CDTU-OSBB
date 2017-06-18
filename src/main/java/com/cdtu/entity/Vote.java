package com.cdtu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Vote {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public Question question;

    @OneToOne
    public User user;

    public boolean vote;

}
