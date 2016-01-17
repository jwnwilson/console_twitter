package com.jwnwilson;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Author: Noel Wilson
 *
 * Wall Class used to hold all user messages.
 */
public class Wall {
    public static final Logger LOGGER = Logger.getLogger( Wall.class.getName() );
    private List<Message> messages = new ArrayList<Message>();
    private User owner;

    /**
     * Wall constructor, has to be created for a User object
     *
     * @param user User object to create wall for
     */
    public Wall(User user){
        owner = user;
        LOGGER.info("Created new wall for user: " + user.getUsername());
    }

    /**
     * Wall constructor, has to be created for a User object, and a Message list to
     * intialise messages for user.
     *
     * @param user User object to create wall for
     * @param msgList List<Message> list of messages to add to wall
     */
    public Wall(User user, List<String> msgList){
        owner = user;

        for(String msg : msgList){
            addMessage(msg);
        }
        LOGGER.info("Created new wall for user: " + user);
    }

    /**
     * Add new message to wall
     *
     * @param message Message object to add to wall
     */
    public void addMessage(Message message){
        messages.add(message);
        LOGGER.info("Added message: " + message.getMessage()  + " to users wall: " + owner.getUsername());
    }

    /**
     * Add new message to wall
     *
     * @param messageStr String to create a message with and add to wall
     */
    public void addMessage(String messageStr){
        DateTime now = new DateTime();
        Message message = new Message(messageStr, now, owner);
        messages.add(message);
        LOGGER.info("Added message: " + messageStr + " to users wall: " + owner.getUsername());
    }

    /**
     * Get Wall messages
     *
     * @return List<Message> Wall messages
     */
    public List<Message> getMessages(){
        return messages;
    }

    /**
     * Get owner of wall
     *
     * @return User object of owner of wall
     */
    public User getOwner(){

        return owner;
    }
}
