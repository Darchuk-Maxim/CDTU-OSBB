package com.cdtu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Question {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public Osbb osbb;

    public String question;

}
