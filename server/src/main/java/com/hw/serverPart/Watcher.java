package com.hw.serverPart;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Watcher {
    private static final Logger logger = Logger.getLogger(Watcher.class.getName());

    public static void main(String[] args) throws Exception{
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(new FileInputStream("server/src/main/resources/logging.properties"));

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = currentDateTime.format(formatter);

        FileAlterationObserver observer = new FileAlterationObserver(
                "/Users/niksipeykin/Documents/IT/java/javaHome/LevelTwo_NetworkStorage/nettyNetworkStorage");

        FileAlterationMonitor monitor = new FileAlterationMonitor(1000);
        FileAlterationListener listener = new FileAlterationListener() {
            @Override
            public void onStart(FileAlterationObserver observer) {

            }

            @Override
            public void onDirectoryCreate(File directory) {

            }

            @Override
            public void onDirectoryChange(File directory) {

            }

            @Override
            public void onDirectoryDelete(File directory) {

            }

            @Override
            public void onFileCreate(File file) {
                logger.log(Level.INFO, "Created Filename: " + file.getName() + " path:" + file.getAbsolutePath() + " date:" + dateTime);
                System.out.println("Created Filename: " + file.getName() + " path:" + file.getAbsolutePath());
            }

            @Override
            public void onFileChange(File file) {
                logger.log(Level.INFO, "Change Filename: " + file.getName() + " path:" + file.getAbsolutePath() + " date:" + dateTime);
                System.out.println("Change Filename: " + file.getName() + " path:" + file.getAbsolutePath());
            }

            @Override
            public void onFileDelete(File file) {
                logger.log(Level.INFO, "Delete Filename: " + file.getName() + " path:" + file.getAbsolutePath() + " date:" + dateTime);
                System.out.println("Delete Filename: " + file.getName() + " path:" + file.getAbsolutePath());
            }

            @Override
            public void onStop(FileAlterationObserver observer) {

            }
        };

        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
    }
}
