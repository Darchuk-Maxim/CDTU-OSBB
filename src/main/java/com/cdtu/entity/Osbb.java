package com.cdtu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Osbb {

    @Id
    @GeneratedValue
    public Long id;

    @NotNull
    public String city;

    @NotNull
    public String street;

    @NotNull
    public String number;

    @OneToOne
    public User admin;

}
