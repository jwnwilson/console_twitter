package com.jwnwilson;

import java.util.*;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Created by noel on 14/01/16.
 *
 * Main container for Classes and logic for this exercise
 */
public class ConsoleTwitter {

    public static final Logger LOGGER = Logger.getLogger( ConsoleTwitter.class.getName() );
    private List<User> users = new ArrayList<User>();
    private List<Wall> walls =  new ArrayList<Wall>();
    private Client client;
    private JSONObject jsonObj;

    public ConsoleTwitter(){

    }

    public void loadInitData(String configFile){

        LOGGER.info("Loading init file: " + configFile);
        jsonObj = JSONReader.parse(configFile);

        JSONObject userObjs = jsonObj.getJSONObject("users");
        Iterator<?> users = userObjs.keys();

        while( users.hasNext() ) {
            String userStr = (String)users.next();
            User user = getOrCreateUser(userStr);
            JSONObject jsonUserObj = userObjs.getJSONObject(userStr);
            JSONArray messages = jsonUserObj.getJSONArray("messages");
            JSONArray follows = jsonUserObj.getJSONArray("follows");
            for( int j=0;j< messages.length();j++){
                JSONArray messageData = messages.getJSONArray(j);
                String messageStr = messageData.getString(0);
                String dateStr = messageData.getString(1);
                DateTime messageDate = new DateTime(dateStr);
                Message message = new Message(messageStr, messageDate, user);
                user.getWall().addMessage(message);
            }
            for( int j=0;j< follows.length();j++){
                String follow = follows.getString(j);
                User followUser = getOrCreateUser(follow);
                follow(user, followUser);
            }
        }

        LOGGER.info("Successfully loaded init data.");

    }

    public void setClient(Client inClient){

        client = inClient;
    }

    public User createUser(String userStr){
        User user = new User(userStr);
        // add user wall to walls
        walls.add(user.getWall());
        // add user to twitter system
        users.add(user);
        LOGGER.info("Created new user: " + userStr);
        return user;
    }

    /**
     * Get or create and get a User from the List of Users
     */
    public User getOrCreateUser(String userStr){

        User user = getUser(userStr);
        if(user == null){
            client.output("User not found creating new user: " + userStr);
            user = createUser(userStr);
            return user;
        }
        return user;
    }

    User getUser(String userStr){

        for(User user : users){
            if(user.getUsername().equals(userStr)){
                return user;
            }
        }
        return null;
    }

    /**
     * Post command will post a message to a users wall
     */
    public void post(User user, String messageStr ){

        Wall userWall = user.getWall();
        userWall.addMessage(messageStr);
        LOGGER.info("Posting message: " + messageStr + " to Wall " + userWall.toString());
    }

    /**
     * Will return a list of users messages
     *
     * @param user: User object to retrieve messages for
     * @return List<Messages> the User objects saved messages
     */
    public List<Message> read(User user){

        Wall userWall = user.getWall();
        List<Message> messages = userWall.getMessages();

        return messages;
    }

    /**
     * Follow command will add users wall to list of messages to print on wall command
     */
    public void follow(User user, User followUser){

        user.follow(followUser);
    }

    /**
     * Wall command will show current users messages then the messages of all users they are following
     */
    public List<Message> wall(User user){

        // Get user messages
        List<Message> userMsg = read(user);
        // Temporary List
        List<Message> wallMsgs = new ArrayList<Message>();
        wallMsgs.addAll(userMsg);
        // Show followed messages
        List<User> followedUsers = user.getFollows();
        for(User followedUser : followedUsers){
            List<Message> followWallMsgs = read(followedUser);
            wallMsgs.addAll(followWallMsgs);
        }
        // sort by date
        Collections.sort(wallMsgs, new MessageComparator());

        return wallMsgs;
    }
}
