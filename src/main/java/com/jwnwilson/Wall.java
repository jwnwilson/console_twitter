package com.jwnwilson;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by noel on 14/01/16.
 */
public class Wall {
    private List<Message> messages;

    public Wall(){

    }

    public void addMessage(String messageStr){
        DateTime now = new DateTime();
        Message message = new Message(messageStr, now);
        messages.add(message);
    }

    public List<Message> getMessages(){
        return messages;
    }
}
