package com.company;

import com.opencsv.CSVWriter;
import twitter4j.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetTwitterTrends {

    public static void main(String[] args) throws TwitterException, IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("TrendCSV.csv"),
                ';',
                '"', '\\'
                , CSVWriter.DEFAULT_LINE_END);

        List<String[]> therows = new ArrayList<>();

        try {
            int woeid = args.length > 0 ? Integer.parseInt(args[0]) : 1;
            Twitter twitter = new TwitterFactory().getInstance();
            ArrayList<Object> trendsTitle = new ArrayList<Object>();
            Trends trends = twitter.getPlaceTrends(23424969); // For Turkey woid=23424969

            System.out.println("Showing trends for " + trends.getLocation().getName());

            for (Trend trend : trends.getTrends()) {
                trendsTitle.add(trend);
                String trendName =trend.getName();
                String trendLocation = trends.getLocation().getName();
                String trendVolume = String.valueOf(trend.getTweetVolume());

                String[] row = new String[]{trendLocation, trendName,trendVolume};
                therows.add(row);

                System.out.println(String.format("%s (tweet_volume: %d)", trend.getName(), trend.getTweetVolume()));
            }

            System.out.println("done.");

            writer.writeAll(therows);
            writer.close();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

}


