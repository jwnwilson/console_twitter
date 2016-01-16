package com.jwnwilson;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by noel on 14/01/16.
 */
public class Wall {
    public static final Logger LOGGER = Logger.getLogger( Wall.class.getName() );
    private List<Message> messages = new ArrayList<Message>();
    private User owner;

    public Wall(User user){

        owner = user;
        LOGGER.info("Created new wall for user: " + user.getUsername());
    }
    public Wall(User user, List<String> msgList){

        owner = user;

        for(String msg : msgList){
            addMessage(msg);
        }
        LOGGER.info("Created new wall for user: " + user);
    }

    public void addMessage(Message message){

        messages.add(message);
        LOGGER.info("Added message: " + message.getMessage()  + " to users wall: " + owner.getUsername());
    }

    public void addMessage(String messageStr){

        DateTime now = new DateTime();
        Message message = new Message(messageStr, now, owner);
        messages.add(message);
        LOGGER.info("Added message: " + messageStr + " to users wall: " + owner.getUsername());
    }

    public List<Message> getMessages(){

        return messages;
    }
}
