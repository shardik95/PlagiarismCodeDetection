package edu.northeastern.cs5500.moss;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import it.zielke.moji.MossException;
import org.apache.commons.io.FileUtils;
import it.zielke.moji.SocketClient;

public class MossApi {

    private static final Logger logger = Logger.getLogger(MossApi.class.getName());


    public String getMossURL() throws MossException {

        Collection<File> files = FileUtils.listFiles(new File("Assignment/"), new String[]{"py"}, true);
        SocketClient socketClient = new SocketClient();


        //set your Moss user ID
        socketClient.setUserID("363865293");


        //set the programming language of all student source codes
        socketClient.setLanguage("python");


        //initialize connection and send parameters.
        try {
            socketClient.run();
        } catch (UnknownHostException e) {
            logger.info("UnknowsHostException.");
        } catch (IOException e) {
            logger.info("IOException");
        }


        //upload all source files of students
        for (File f : files) {
            try {
                socketClient.uploadFile(f);
            } catch (IOException e) {
                logger.info("Input output exception.");
            }
        }


        //finished uploading, tell server to check files
        try {
            socketClient.sendQuery();
        } catch (IOException e) {
            logger.info("IOException");
        }

        //get URL with Moss results and do something with it
        logger.info(socketClient.getResultURL().toString());
        return socketClient.getResultURL().toString();

    }
}
