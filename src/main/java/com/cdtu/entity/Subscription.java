package com.cdtu.entity;

import com.cdtu.role.OSBBRole;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Subscription {

    @Id
    @GeneratedValue
    public Long id;

    public OSBBRole roleOnOsb;

    @OneToOne
    public Osbb osbb;

    @OneToOne
    public User user;

    public boolean isActivated;

}
