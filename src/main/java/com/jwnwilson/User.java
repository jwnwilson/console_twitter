package com.jwnwilson;

import java.util.List;

/**
 * Created by noel on 14/01/16.
 */
public class User {
    private String username;
    private Wall userWall;
    private List<User> follows;

    public User(String in_username){
        username = in_username;
        // For each new user create a new wall
        userWall = new Wall();
    }

    public void follow(User user){
        follows.add(user);
    }

    public String getUsername(){
        return username;
    }

    public Wall getWall(){
        return userWall;
    }
}
