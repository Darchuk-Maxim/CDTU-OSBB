package com.cdtu.entity;

import org.hibernate.validator.constraints.Length;

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

    @Length(max = 1000)
    public String question;

    public String detail;

    public boolean isAnonymous;

}
