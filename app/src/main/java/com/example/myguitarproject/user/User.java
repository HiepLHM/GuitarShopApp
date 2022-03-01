package com.example.myguitarproject.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "User")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int IdUser;
    private String Username;
    private String Password;
    private String Email;
    private String Avatar;
    private String Phone_number;
    private String RoleAccount;

    public User() {
    }

    public User(int idUser, String username, String password, String email, String avatar, String phone_number, String roleAccount) {
        IdUser = idUser;
        Username = username;
        Password = password;
        Email = email;
        Avatar = avatar;
        Phone_number = phone_number;
        RoleAccount = roleAccount;
    }

    public String getRoleAccount() {
        return RoleAccount;
    }

    public void setRoleAccount(String roleAccount) {
        RoleAccount = roleAccount;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }
}
