package com.jwnwilson;

import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import org.json.JSONObject;
import java.util.logging.Logger;
import java.io.BufferedReader;

/**
 * @author Noel Wilson
 * @Date: 16/01/2016
 *
 * Simple JSON parser using org.json libraries
 */
public class JSONReader {
    public static final Logger LOGGER = Logger.getLogger( ConsoleTwitter.class.getName() );

    /**
     * JSONReader constructor
     */
    public JSONReader(){
    }

    /**
     * Check that the json file exists and throw an exception if not
     *
     * @param path json file path
     */
    private static void checkFileExists(String path){
        File f = new File(path);
        if(f.exists() == false || f.isDirectory()) {
            String errorMsg = "JSON file not found: " + path;
            LOGGER.severe(errorMsg);
            throw new ExceptionInInitializerError(errorMsg);
        }
    }

    /**
     * Parse a JSON file and return a JSONObject
     *
     * @param filePath String path to json file to read
     * @return JSONObject parsed file object
     */
    public static JSONObject parse(String filePath) {
        JSONObject jsonObject = null;
        try {
            // Throws exception if not found
            checkFileExists(filePath);

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
            jsonObject = new JSONObject(jsonData);
        } catch (Exception e) {
            LOGGER.severe("Unable to read JSON file: " + filePath);
            LOGGER.severe(e.getStackTrace().toString());
        }

        return jsonObject;
    }
}