package com.jwnwilson;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.Arrays;

/**
 * Created by noel on 14/01/16.
 */
public class Client {

    public static final Logger LOGGER = Logger.getLogger( Client.class.getName() );
    private ConsoleTwitter source;
    private List<String> validCommands = Arrays.asList("wall", "->", "follows");

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
     *
     *
     * @param input will be a command and a value
     */
    private void processInput(String input) {

        List<String> args = Arrays.asList(input.split(" "));
        if(args.size() == 0){
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
}
