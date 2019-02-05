package com.pluralsight.resourceProp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        try {
            Properties defaultProps = new Properties();
            //can load the document from the main class and set them into the defaultProps instance
            try (InputStream inputStream = Main.class.getResourceAsStream("MyDefaultValues.XML")) {
                defaultProps.loadFromXML(inputStream);
            }
            //creating userProps instance and passing in the defaultProps that I loaded
            Properties userProps = new Properties(defaultProps);
            loadUserProps(userProps);

            String welcomeMessage = userProps.getProperty("welcomeMessage");
            String farewellMessage = userProps.getProperty("farewellMessage");

            System.out.println(welcomeMessage);
            System.out.println(farewellMessage);

            //show that the property changes will persist outside of the application run
            //Will save the new values to the file
            if(userProps.getProperty("isFirstRun").equalsIgnoreCase("Y")){
                userProps.setProperty("welcomeMessage", "Welcome back");
                userProps.setProperty("farewellMessage", "Things will be familiar now");
                userProps.setProperty("isfirstRun", "N");
                saveUserProps(userProps);
            }

        } catch (IOException e) {
            System.out.println("Exception <" + e.getClass().getSimpleName() + "> " + e.getMessage());
        }
    }
    //read in my xml file and set to userProps
    private static Properties loadUserProps(Properties userProps) throws IOException{
        Path userFile = Paths.get("UserValues.xml");
        if(Files.exists(userFile)){
            try(InputStream inputStream = Files.newInputStream(userFile)){
                userProps.loadFromXML(inputStream);
            }
        }
        return userProps;
    }
    private static void saveUserProps(Properties userProps) throws IOException{
        try(OutputStream outputStream = Files.newOutputStream(Paths.get("UserValues.xml"))){
            userProps.storeToXML(outputStream, null);
        }

    }
}

