/*
 * Entry Point for Console Twitter Exercise
 *
 * @Author: Noel Wilson
 * @Date: 16/01/2016
 *
 * Main entry point for Console Twitter Designed to work on a terminal
 */

package com.jwnwilson;

import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

/**
 * @Author: Noel Wilson
 *
 * Entry point for application will parse args then run client to handle input / output and
 * ConsoleTwitter object to handle logic for the application.
 *
 * Valid args:
 * -v --verbose: Turn on verbose mode to see logs and details
 * -i --init_data: File path of initial data to load into application on startup.
 */
public class Main {
    /**
     * Check if option has been set in args
     *
     * @param opts Map of parsed args
     * @param opt Arg to check for
     * @return
     */
    public static boolean optSet(Map<String, List<String>> opts, String opt){
        for( String setOpt : opts.keySet()){
            if( setOpt.equals(opt))
                return true;
        }
        return false;
    }

    /**
     * Not used for this exercise, used to add additional logging handlers if needed.
     *
     * @param level Logging Level to set new logging handler to
     */
    public static void configureAdditionalLogger(Level level){
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new SimpleFormatter());
        ch.setLevel(level);

        Client.LOGGER.addHandler(ch);
        Message.LOGGER.addHandler(ch);
        User.LOGGER.addHandler(ch);
        Wall.LOGGER.addHandler(ch);
        JSONReader.LOGGER.addHandler(ch);
    }

    /**
     * Main entry point of application
     *
     * @param args String array args
     */
    public static void main(String[] args) {
        String initFile = null;
        List<String> options = null;
        final Map<String, List<String>> params = new HashMap<String, List<String>>();
        // Parse args
        for (int i = 0; i < args.length; i++) {
            final String a = args[i];
            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    System.err.println("Error at argument " + a);
                    return;
                }
                options = new ArrayList<String>();
                params.put(a.substring(1), options);
            }
            else if (options != null) {
                options.add(a);
            }
            else {
                System.err.println("Illegal parameter usage");
                return;
            }
        }

        Level level;
        // Handle verbose arg
        if( optSet(params, "verbose") || optSet(params, "v")){
            // Set logging level
            level = Level.ALL;
        }
        else {
            // Set logging level
            level = Level.WARNING;
        }

        // handle input file
        if( optSet(params, "init_data")){
            // get init file
            initFile = params.get("init_data").get(0);
        }
        else if(optSet(params, "i")){
            // get init file
            initFile = params.get("i").get(0);
        }

        Client.LOGGER.setLevel(level);
        Message.LOGGER.setLevel(level);
        User.LOGGER.setLevel(level);
        Wall.LOGGER.setLevel(level);
        JSONReader.LOGGER.setLevel(level);

        // Initialise Client and ConsoleTwitter
        ConsoleTwitter consoleTwitter = new ConsoleTwitter();
        Client client = new Client(consoleTwitter);

        // Load initial data passed as file path
        if(initFile != null){
            consoleTwitter.loadInitData(initFile);
        }

        // Start input loop
        client.prompt();
    }
}
