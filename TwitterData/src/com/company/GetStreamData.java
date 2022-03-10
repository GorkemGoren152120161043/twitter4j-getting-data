package com.company;

import com.opencsv.CSVWriter;
import twitter4j.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetStreamData {

    public static void main(String[] args) throws TwitterException, IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("StreamDataCSV.csv"),
                ';',
                '"', '\\'
                , CSVWriter.DEFAULT_LINE_END);


        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {

                User user = status.getUser();

                // gets Username
                String username = status.getUser().getScreenName();
                System.out.println(username);
                String profileLocation = user.getLocation();
                System.out.println(profileLocation);
                long tweetId = status.getId();
                String Id = String.valueOf(tweetId);
                System.out.println(tweetId);
                String content = status.getText();
                System.out.println(content + "\n");
                String dateTime = status.getCreatedAt().toGMTString();

                String[] row = new String[]{username, profileLocation, dateTime, Id, content};

                writer.writeNext(row);


            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {

            }
        };

        FilterQuery fq = new FilterQuery();

        String keywords[] = {"COVID"};

        fq.track(keywords);
        fq.language("en");

        twitterStream.addListener(listener);
        twitterStream.filter(fq);

    }
}
