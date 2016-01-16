package com.jwnwilson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by noel on 14/01/16.
 */
public class User {

    public static final Logger LOGGER = Logger.getLogger( User.class.getName() );
    private String username;
    private Wall userWall;
    private List<User> follows = new ArrayList<User>();

    public User(String in_username){

        username = in_username;
        // For each new user create a new wall
        userWall = new Wall(this);
        LOGGER.info("Created new user: " + in_username);
    }

    public List<User> getFollows(){

        return follows;
    }

    public void follow(User user){

        follows.add(user);
        LOGGER.info("User: " + getUsername() + " now following: " + user.getUsername());
    }

    public String getUsername(){

        return username;
    }

    public Wall getWall(){

        return userWall;
    }
}
