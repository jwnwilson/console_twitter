package com.jwnwilson;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.Arrays;

/**
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 *
 * A simple terminal input which will send the strings to the ConsoleTwitter
 * Wraps output in a method to be redined by a derived class.
 */
public class Client {
    public static final Logger LOGGER = Logger.getLogger( Client.class.getName() );
    private ConsoleTwitter source;
    private List<String> validCommands = Arrays.asList("wall", "->", "follows");
    private String lastOutput;

    /**
     * Client Constructor
     */
    public Client(){
    }

    /**
     * Create a Client passing in a ConsoleTwitter object to link to to send input and receive
     * output from.
     *
     * @param source_obj
     */
    public Client(ConsoleTwitter source_obj){
        source = source_obj;
        source.setClient(this);
    }

    /**
     * Output function to display message or push it to wherever or to be re-implemented in a
     * dereived class
     *
     * @param output: String to output
     */
    public void output(String output) {
        System.out.println(output);
        lastOutput = output;
    }

    /**
     * Output message via our output method
     *
     * @param outputMessageList: messages to output
     */
    private void output_message(List<Message> outputMessageList) {
        for (Message outputMsg : outputMessageList) {
            output(outputMsg.toString());
        }
        if (outputMessageList.size() == 0){
            output("No messages");
        }
    }

    /**
     * Process input from User valid commands:
     * {username}: read message
     * {username} -> {message}: post message
     * {username} follows {username}: follow user command
     * {username} wall: wall command for user
     *
     * @param input will be a command and a value
     */
    public void processInput(String input) {
        List<String> args = Arrays.asList(input.split(" "));
        if(args.size() == 0){
            return;
        }
        if(args.get(0).equals("quit")){
            return;
        }

        String userStr = args.get(0);
        User user = source.getOrCreateUser(userStr);

        // Run read command
        if(args.size() == 1){
            output_message(source.read(user));
        }
        // Run wall command
        else if(args.size() == 2 && args.get(1).equals("wall")){
            output_message(source.wall(user));
        }
        // Run post command
        else if(args.size() > 2 && args.get(1).equals("->")){
            List<String> stringList = args.subList(2,args.size());
            source.post(user, String.join(" ", stringList));
        }
        // Run follow command
        else if(args.size() > 2 && args.get(1).equals("follows")){
            User followUser = source.getUser(args.get(2));
            if(followUser == null){
                String errorMsg = "User: " + args.get(2) + " not found.";
                LOGGER.severe(errorMsg);
                output(errorMsg + " Please check spelling and try again.");
                return;
            }
            source.follow(user, followUser);
        }
        // Invalid command show help
        else{
            String errorMsg = "Invalid command: " + args.get(1) + ", valid commands are:";
            for( int i=0;i<validCommands.size();i++){
                errorMsg += "\n" + Integer.toString(i+1) + ". " + validCommands.get(i);
            }
            output(errorMsg);
        }
    }

    /**
     * Infinite prompt loop to process commands, will exit on "quit" input
     */
    public void prompt() {
        LOGGER.info("Starting prompt.");
        System.out.println("Welcome to ConsoleTwitter, please enter command:");
        String input = "";
        while( input.equals("quit") == false){
            Scanner reader = new Scanner(System.in);
            input = reader.nextLine();

            // process input string
            LOGGER.info("Input: " + input);

            // Process input string
            processInput(input);
        }
        System.out.println("Exiting ConsoleTwitter.");
        LOGGER.info("Closing prompt.");
    }

    /**
     * Get Last output string for debugging purposes
     */
    public String getLastOutput(){
        return lastOutput;
    }
}
