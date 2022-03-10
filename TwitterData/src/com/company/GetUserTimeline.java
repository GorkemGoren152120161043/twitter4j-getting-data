package com.company;

import com.opencsv.CSVWriter;
import twitter4j.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetUserTimeline {

    public static void main(String[] args) throws IOException {
        // gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();

        CSVWriter writer = new CSVWriter(new FileWriter("DataCSV.csv"),
                ';',
                '"', '\\'
                , CSVWriter.DEFAULT_LINE_END);

        List<String[]> therows = new ArrayList<>();
        String[] header = new String[]{"Userid", "Username", "Tweet", "Date"};
        therows.add(header);

        try {
            List<Status> statuses;
            String user;
            if (args.length == 1) {
                user = args[0];
                statuses = twitter.getUserTimeline(user);
            } else {
                user = twitter.verifyCredentials().getScreenName();
                Paging paging = new Paging(1, 20);

                statuses = twitter.getUserTimeline("Reuters", paging);
            }
            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {

                String Userid = String.valueOf(status.getUser().getId());
                String Username = status.getUser().getName();
                String Tweet = status.getText();
                String Date = status.getCreatedAt().toLocaleString();

                String[] row = new String[]{Userid, Username, Tweet, Date};
                therows.add(row);

                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
            writer.writeAll(therows);
            writer.close();


        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }

    }
