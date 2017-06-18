package com.cdtu.entity;

import com.cdtu.role.ProfileRole;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue
    public Long id;

    @NotNull
    public String login;

    @NotNull
    public String password;

    public ProfileRole profileRole;

}
