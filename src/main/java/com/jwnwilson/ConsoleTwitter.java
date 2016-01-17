package com.jwnwilson;

import java.util.*;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 *
 * Main container for Classes and logic for this exercise
 */
public class ConsoleTwitter {
    public static final Logger LOGGER = Logger.getLogger( ConsoleTwitter.class.getName() );
    private List<User> users = new ArrayList<User>();
    private List<Wall> walls =  new ArrayList<Wall>();
    private Client client;
    private JSONObject jsonObj;

    /**
     * ConsoleTwitter constructor
     */
    public ConsoleTwitter(){
    }

    /**
     * Load initial data into ConsoleTwitter from json file, check test_data.json for example
     * Tested with relative path only.
     *
     * @param initFile: String path to initial data file
     */
    public void loadInitData(String initFile){
        LOGGER.info("Loading init file: " + initFile);
        jsonObj = JSONReader.parse(initFile);

        JSONObject userObjs = jsonObj.getJSONObject("users");
        Iterator<?> users = userObjs.keys();

        // Iterate through json objects and build User, Wall and message data
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

    /**
     * Set client in which to send output to.
     *
     * @param inClient Client object to receive output
     */
    public void setClient(Client inClient){
        client = inClient;
    }

    /**
     * Create a User with username provided
     *
     *  @param userStr: String username to create a new user with.
     */
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
     * Get a User if they exist by username or create and return a new one
     *
     * @param userStr String username to either return or create
     * @return
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

    /**
     * Post command will post a message to a users wall
     *
     * @param user User object that holds the wall that the message will be posted to
     * @param messageStr String message to post to the wall of the user passed in
     */
    public void post(User user, String messageStr ){
        Wall userWall = user.getWall();
        userWall.addMessage(messageStr);
        LOGGER.info("Posting message: " + messageStr + " to Wall " + userWall.toString());
    }

    /**
     * Will return a list of users messages
     *
     * @param user: User object to retrieve messages from
     * @return List<Messages> List of the User objects saved messages
     */
    public List<Message> read(User user){
        Wall userWall = user.getWall();
        List<Message> messages = userWall.getMessages();

        return messages;
    }

    /**
     * Follow command will add users wall to list of messages to output on wall command call
     *
     * @param user User object of user who will be following
     * @param followUser User object of user who will be followed
     */
    public void follow(User user, User followUser){
        user.follow(followUser);
    }

    /**
     * Return list of users
     *
     * @return List<User> List of users currently in ConsoleTwitter
     */
    public List<User> getUsers(){
        return users;
    }

    /**
     * Return users identified by username
     *
     * @return User users currently in ConsoleTwitter
     */
    public User getUser(String username){
        for(User user: users){
            if( user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Wall command will show current users messages then the messages of all users they are following
     * Will return messages in newest -> oldest order
     *
     * @param user User to output wall return for
     * @return List<Message> List of messages in order of newest to oldest
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
