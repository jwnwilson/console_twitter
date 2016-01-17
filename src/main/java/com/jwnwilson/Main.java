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

/** convenient "-flag opt" combination */
class Option {
    String flag, opt;
    public Option(String flag, String opt) { this.flag = flag; this.opt = opt; }
}

/**
 * @Author: Noel Wilson
 *
 * Entry point for application will parse args then run client to handle input / output and
 * ConsoleTwitter object to handle logic for the application.
 *
 * Valid args:
 * -v --verbose: Turn on verbose mode to see logs and details
 * --init_data: File path of initial data to load into application on startup.
 */
public class Main {
    /**
     * Check if a flag has been set in the args
     *
     * @param flags List of parsed flags
     * @param flag Flag to check for
     * @return
     */
    public static boolean flagSet(List<String> flags, String flag){
        for( String setFlat : flags){
            if( setFlat.equals(flag))
                return true;
        }
        return false;
    }

    /**
     * Check if option has been set in args
     *
     * @param opts Map of parsed args
     * @param opt Arg to check for
     * @return
     */
    public static boolean optSet(Map<String, String> opts, String opt){
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
        List<String> argsList = new ArrayList<String>();
        Map<String, String> optsList = new HashMap<String, String>();
        List<String> flagList = new ArrayList<String >();
        String initFile = null;

        // Parse args
        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
                case '-':
                    // if not last arg
                    if((args.length > (i+1))) {
                        // If no second arg persume flag
                        if (args[i + 1].startsWith("-")) {
                            flagList.add(args[i].replace("-", ""));
                        }
                        // Add option
                        else {
                            optsList.put(args[i].replace("-", ""), args[i + 1]);
                            i++;
                        }
                        break;
                    }
                    else{
                        flagList.add(args[i].replace("-", ""));
                        break;
                    }
                default:
                    // arg
                    argsList.add(args[i]);
                    break;
            }
        }

        Level level;
        // Handle verbose arg
        if( flagSet(flagList, "verbose") || flagSet(flagList, "v")){
            // Set logging level
            level = Level.ALL;
        }
        else {
            // Set logging level
            level = Level.WARNING;
        }

        // handle input file
        if( optSet(optsList, "init_data") || optSet(optsList, "i")){
            // get init file
            initFile = optsList.get("init_data");
            if(initFile == null){
                initFile = optsList.get("i");
            }
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
