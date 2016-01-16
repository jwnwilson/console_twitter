package com.jwnwilson;

import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.logging.Logger;
import java.io.BufferedReader;


/**
 * @author Noel Wilson
 */

public class JSONReader {
    public static final Logger LOGGER = Logger.getLogger( ConsoleTwitter.class.getName() );

    public JSONReader(){

    }

    private static boolean checkFileExists(String path){

        File f = new File(path);
        if(f.exists() && !f.isDirectory()) {
            return true;
        }
        else{
            String errorMsg = "JSON file not found: " + path;
            LOGGER.severe(errorMsg);
            throw new ExceptionInInitializerError(errorMsg);
        }
    }

    public static JSONObject parse(String filePath) {

        JSONObject jsonObject = null;
        try {
            if( checkFileExists(filePath) ) {
                String jsonData = "";
                BufferedReader br = null;
                try {
                    String line;
                    br = new BufferedReader(new FileReader(filePath));
                    while ((line = br.readLine()) != null) {
                        jsonData += line + "\n";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null)
                            br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                //JSONTokener tokener = new JSONTokener(jsonData);
                jsonObject = new JSONObject(jsonData);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonObject;

    }
}