package com.sparkTech.cat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Shubham A Gupta on 02-May-18.
 */
public class AppContext {

    private static Properties properties = null;
    private static AppContext instance = null;

    private AppContext(String filePath){
        if (properties == null){
            properties = new Properties();
            loadProperties(filePath);
        }
    }

    public static AppContext getInstance(String filePath) {
        if(instance == null){
           instance = new AppContext(filePath);
        }
        return instance;
    }

    public String getValue(String key){
        return properties.getProperty(key);
    }


    public Set<String> getAllValuesContaining(String string) {
        Set<String> allKeys = new TreeSet<>();
        Set<Object> objects = properties.keySet();

        for (Object obj : objects) {
            String str = obj.toString();
            if (str.startsWith(string)) {
                allKeys.add(str);
            }
        }
        return allKeys;
    }



    private static void loadProperties(String filePath) {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath));
            properties.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
