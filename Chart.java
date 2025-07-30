import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Chart {

    public static void createCountriesAndLanguageChart(JFrame window, JPanel dataPanel, ArrayList<Movie> movies, String chartType) {

        File f = new File("./resources/charts/language.jpeg");
        File g = new File("./resources/charts/countries.jpeg");
        if (f.exists() && !f.isDirectory()) { // if the files both exist, no need to remake them
            // do nothing it's fine
        }
        else if(g.exists() && !g.isDirectory()) {
            // do nothing it's fine
        }
        else{
            ArrayList<String> countryAndLanguage = new ArrayList<String>();
           for(int movie = 0; movie < movies.size(); movie++){
            countryAndLanguage.add(Scraper.run(movies.get(movie).url)); // for every movie, get its country and language
        }
        }
    }

    public static void createYearWatchedChart(JFrame window, JPanel dataPanel, ArrayList<Movie> movies){
        try{
        // years is all the different years a movie was watched in
        ArrayList<Integer> years = new ArrayList<>();
        // yearScore is the number of movies that were watched in each year
        ArrayList<Integer> yearScore = new ArrayList<>();
        // only add unique values to years because each value is going to be a key in the pie chart
        for(int movie = 0; movie < movies.size(); movie++){
            int dateWatched = movies.get(movie).dateWatched;
            if(!years.contains(dateWatched)){
                years.add(dateWatched);
            }
        }
        // add the same number of year watched listings into yearscore. this should have been a 2 dimensional array i think
        for (int numberOfYearListings = 0; numberOfYearListings < years.size(); numberOfYearListings++){
            yearScore.add(0);
        }

        // go through every movie
        for(int movie = 0; movie < movies.size(); movie++){ // is it ok to use movie twice? lol
            // go through every watch year in years array
            for(int yearListing = 0; yearListing < years.size(); yearListing++){
                int movieWatchDate = movies.get(movie).dateWatched;
                if(movieWatchDate == years.get(yearListing)){ // if the movie was watched in the year date, add a point to the yearScore
                    int value = yearScore.get(yearListing); // get value
                    value = value + 1; // increment value
                    yearScore.set(yearListing, value); // replace value

                }
            }
        }
        // create bar chart here lol
        final String yearWatched = "Year Watched";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int data = 0; data < years.size(); data++){
            // add a new dataset for every year a movie was watched in
            if(years.get(data) == 0){
                // do nothing if this is true
            }
            else{
                dataset.addValue(yearScore.get(data), years.get(data), yearWatched);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
            "MOVIES WATCHED PER YEAR",
            "Year", "Movies Watched",
            dataset, PlotOrientation.VERTICAL,
            true, true, false);
        int barChartWidth = 500;
        int barChartHeight = 500;
        File yearsWatchedBarChart = new File("./resources/charts/yearswatched.jpeg");
        ChartUtilities.saveChartAsJPEG(yearsWatchedBarChart, barChart, barChartWidth, barChartHeight);

    } // end of try
    catch(Exception e){
        System.out.println(e);
    }
    }

    // this creates the JPEG chart of when watched movies were published
    public static void createYearCreatedChart(JFrame window, JPanel dataPanel, ArrayList<Movie> movies) {
        // add a line chart here lol
        try {

            // this is going to be a fucking disgusting piece of code im so sorry
            int[] decadeWatchNumbers = new int[13]; // each int represents how many movies were made in that decade.
            // will not work into 2030 im sure thats fine
            for (int movieNum = 0; movieNum < movies.size(); movieNum++) { // go through every movie and see when it was made
                int decadePublished = movies.get(movieNum).publishDate; // then add a point to the corresponding point in
                                                                     // decadeWatchNumbers
                decadePublished = (decadePublished / 10); // remove the year
                //System.out.println(decadePublished + " " + movies.get(movieNum).name);

                switch (decadePublished) {
                    case 190:
                        decadeWatchNumbers[0]++;
                        break;
                    case 191:
                        decadeWatchNumbers[1]++;
                        break;
                    case 192:
                        decadeWatchNumbers[2]++;
                        break;
                    case 193:
                        decadeWatchNumbers[3]++;
                        break;
                    case 194:
                        decadeWatchNumbers[4]++;
                        break;
                    case 195:
                        decadeWatchNumbers[5]++;
                        break;
                    case 196:
                        decadeWatchNumbers[6]++;
                        break;
                    case 197:
                        decadeWatchNumbers[7]++;
                        break;
                    case 198:
                        decadeWatchNumbers[8]++;
                        break;
                    case 199:
                        decadeWatchNumbers[9]++;
                        break;
                    case 200:
                        decadeWatchNumbers[10]++;
                        break;
                    case 201:
                        decadeWatchNumbers[11]++;
                        break;
                    case 202:
                        decadeWatchNumbers[12]++;
                        break;
                    default:
                        System.out.println("something is wrong with the createyearcreatedchart function.");
                }   

            }

            // create the bar chart
            DefaultCategoryDataset lineChartDataset = new DefaultCategoryDataset();
            lineChartDataset.addValue(decadeWatchNumbers[0], "Movies Watched from this decade", "1900");
            lineChartDataset.addValue(decadeWatchNumbers[1], "Movies Watched from this decade", "1910");
            lineChartDataset.addValue(decadeWatchNumbers[2], "Movies Watched from this decade", "1920");
            lineChartDataset.addValue(decadeWatchNumbers[3], "Movies Watched from this decade", "1930");
            lineChartDataset.addValue(decadeWatchNumbers[4], "Movies Watched from this decade", "1940");
            lineChartDataset.addValue(decadeWatchNumbers[5], "Movies Watched from this decade", "1950");
            lineChartDataset.addValue(decadeWatchNumbers[6], "Movies Watched from this decade", "1960");
            lineChartDataset.addValue(decadeWatchNumbers[7], "Movies Watched from this decade", "1970");
            lineChartDataset.addValue(decadeWatchNumbers[8], "Movies Watched from this decade", "1980");
            lineChartDataset.addValue(decadeWatchNumbers[9], "Movies Watched from this decade", "1990");
            lineChartDataset.addValue(decadeWatchNumbers[10], "Movies Watched from this decade", "2000");
            lineChartDataset.addValue(decadeWatchNumbers[11], "Movies Watched from this decade", "2010");
            lineChartDataset.addValue(decadeWatchNumbers[12], "Movies Watched from this decade", "2020");
            

            JFreeChart lineChartObject = ChartFactory.createBarChart("Movies watched from each Decade", "Years", "Movies watched", 
            lineChartDataset, PlotOrientation.VERTICAL, true, true, false);
            int lineChartWidth = 600;
            int lineChartHeight = 500;
            File lineChart = new File("./resources/charts/publishdatechart.jpeg");
            ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, lineChartWidth, lineChartHeight);
        } catch (Exception e){
            System.out.println(e + " RAHHH");
        }
    }

}