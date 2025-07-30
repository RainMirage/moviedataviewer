import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dialog {
    // this will erase the menu and show the basic page for visualing all the data
    public static void open(JFrame window, ArrayList<Movie> movies) {
        window.getContentPane().removeAll();

        Font font = new Font(null);
        font = GUI.setFontSpecs("./resources/fonts/Title_Font.ttf", 30f);
        

        // set up a jpanel for the buttons and a jpanel for the data
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 200));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        JPanel dataPanel = new JPanel(new BorderLayout());
        dataPanel.setPreferredSize(new Dimension(600, 200));

        buttonPanel.setOpaque(false);
        dataPanel.setOpaque(false);
        buttonPanel.setBackground(new Color(0, 255, 0));
        dataPanel.setBackground(new Color(0,0,255));

        // create a bunch of buttons
        JButton countriesButton = new JButton("Country");
        JButton backButton = new JButton("Back");
        JButton publishYearButton = new JButton("Published Year");
        JButton languageButton = new JButton("Language");
        JButton watchedButton = new JButton("Year Watched");

        countriesButton = GUI.setButtonStyle(countriesButton, font);
        backButton = GUI.setButtonStyle(backButton, font);
        publishYearButton = GUI.setButtonStyle(publishYearButton, font);
        languageButton = GUI.setButtonStyle(languageButton, font);
        watchedButton = GUI.setButtonStyle(watchedButton, font);

        // create the easy charts when the page loads in so that the lag is spread out lol
        Chart.createYearWatchedChart(window, dataPanel, movies);
        Chart.createYearCreatedChart(window,dataPanel, movies);


        // add the buttons to buttonPanel
        //buttonPanel.add(countriesButton);
        buttonPanel.add(publishYearButton);
        //buttonPanel.add(languageButton);
        buttonPanel.add(watchedButton);
        buttonPanel.add(backButton);

        //add action listeners
        countriesButton.addActionListener(e -> buttonPressed(e, window, dataPanel, movies));
        backButton.addActionListener(e -> buttonPressed(e, window, dataPanel, movies));
        publishYearButton.addActionListener(e -> buttonPressed(e, window, dataPanel, movies));
        languageButton.addActionListener(e -> buttonPressed(e, window, dataPanel, movies));
        watchedButton.addActionListener(e -> buttonPressed(e, window, dataPanel, movies));

        // set up a big jpanel for everything
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setOpaque(true);
        mainPanel.setBackground(new Color(0, 0, 0));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        mainPanel.add(dataPanel);
        window.add(mainPanel);

        window.repaint();
        window.revalidate();
    }

    // listener for every button on the page LOL
    private static void buttonPressed(ActionEvent e, JFrame window, JPanel dataPanel, ArrayList<Movie> movies) {
        String text = ((JButton)e.getSource()).getText();
        if(text == "Back"){
            window.getContentPane().removeAll();
            GUI.launchMenu(window);
        }
        else if(text == "Country"){
            showChart("Country", dataPanel);
            window.revalidate();
            window.repaint();
        }
        else if(text =="Published Year"){
            showChart("Published Year", dataPanel);
            window.revalidate();
            window.repaint();
        }
        else if(text =="Language"){
            showChart("Language", dataPanel);
            window.revalidate();
            window.repaint();
        }
        else if(text =="Year Watched"){
            showChart("Year Watched", dataPanel);
            window.revalidate();
            window.repaint();
            
        }
        else{
            System.out.println("Something has gone wrong.");
        }
    }

    private static void showChart(String chartType, JPanel panel) {
        if(chartType =="Year Watched"){
            panel.removeAll(); // get rid of any previous charts
            ImageIcon yearsWatchedChart = new ImageIcon("./resources/charts/yearswatched.jpeg");
            panel.add(new JLabel(yearsWatchedChart));
        }
        else if(chartType =="Published Year"){
            panel.removeAll(); // get rid of any previous charts
            ImageIcon publishDateChart = new ImageIcon("./resources/charts/publishdatechart.jpeg");
            panel.add(new JLabel(publishDateChart));
        }
        else if(chartType =="Language"){
            // put a 'please wait' notice up cause idk how long this bit will take lol
            panel.removeAll(); // get rid of any previous charts
            JLabel pleaseWait = new JLabel("PLEASE WAIT, LOADING CHART");
            Font titleFont = new Font(null);
            String titleFontString = "./resources/fonts/Title_Font.ttf";
            titleFont = GUI.setFontSpecs(titleFontString, 50f);
            pleaseWait.setFont(titleFont);
            pleaseWait.setForeground(Color.WHITE);
            panel.add(pleaseWait);


        }
        else if(chartType =="Country"){
            // put a 'please wait' notice up cause idk how long this bit will take lol
            panel.removeAll(); // get rid of any previous charts
            JLabel pleaseWait = new JLabel("PLEASE WAIT, LOADING CHART");
            Font titleFont = new Font(null);
            String titleFontString = "./resources/fonts/Title_Font.ttf";
            titleFont = GUI.setFontSpecs(titleFontString, 50f);
            pleaseWait.setFont(titleFont);
            pleaseWait.setForeground(Color.WHITE);
            panel.add(pleaseWait);

            Scraper.run("Country"); // run scraper of country information
        }
        else{
            System.out.println("Something has gone wrong.");
        }
    }
}
