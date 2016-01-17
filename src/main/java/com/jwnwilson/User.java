package com.jwnwilson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Author: Noel Wilson
 *
 * User Class for holding user data, currently used to hold username, Wall object
 * and List of Users that the user is following.
 */
public class User {

    public static final Logger LOGGER = Logger.getLogger( User.class.getName() );
    private String username;
    private Wall userWall;
    private List<User> follows = new ArrayList<User>();

    /**
     * User Constructor we require a username to create a user
     *
     * @param in_username String username to create User object with
     */
    public User(String in_username){
        username = in_username;
        // For each new user create a new wall
        userWall = new Wall(this);
        LOGGER.info("Created new user: " + in_username);
    }

    /**
     * Return List<User> list of users this user is following
     *
     * @return Return List<User> list of users this user is following
     */
    public List<User> getFollows(){
        return follows;
    }

    /**
     * Follow command to add another user to this users follows list
     *
     * @param user User object to add to this users follows list
     */
    public void follow(User user){
        follows.add(user);
        LOGGER.info("User: " + getUsername() + " now following: " + user.getUsername());
    }

    /**
     * Return Users username
     *
     * @return String users username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Return Users Wall Object
     *
     * @return Wall object for this user
     */
    public Wall getWall(){
        return userWall;
    }
}
