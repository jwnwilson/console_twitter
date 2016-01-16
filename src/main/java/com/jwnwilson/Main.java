/*
 * Entry Point for Console Twitter Exercise
 *
 * @Author: Noel Wilson
 * @Date: 14/01/2016
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

public class Main {

    public static boolean flagSet(List<String> flags, String flag){
        for( String setFlat : flags){
            if( setFlat.equals(flag))
                return true;
        }
        return false;
    }

    public static boolean optSet(Map<String, String> opts, String opt){
        for( String setOpt : opts.keySet()){
            if( setOpt.equals(opt))
                return true;
        }
        return false;
    }

    /**
     * Not used for this exercise
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

    public static void main(String[] args) {
        List<String> argsList = new ArrayList<String>();
        Map<String, String> optsList = new HashMap<String, String>();
        List<String> flagList = new ArrayList<String >();
        String initFile = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
                case '-':
                    if (args[i].length() < 2)
                        throw new IllegalArgumentException("Not a valid argument: "+args[i]);
                    // if no arg persume flag
                    if (args.length-1 > i && args[i + 1].startsWith("-"))
                        flagList.add(args[i].replace("-", ""));
                    else {
                        // add opt
                        optsList.put(args[i].replace("-", ""), args[i + 1]);
                        i++;
                    }
                    break;
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
        if( optSet(optsList, "init_data")){
            // get init file
            initFile = optsList.get("init_data");
        }

        Client.LOGGER.setLevel(level);
        Message.LOGGER.setLevel(level);
        User.LOGGER.setLevel(level);
        Wall.LOGGER.setLevel(level);
        JSONReader.LOGGER.setLevel(level);

        ConsoleTwitter consoleTwitter = new ConsoleTwitter();
        Client client = new Client(consoleTwitter);

        // Load initial data passed as file path
        if(initFile != null){
            consoleTwitter.loadInitData(initFile);
        }

        client.prompt();
    }
}
