import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI {
    public static JFrame launchMenu(JFrame window) {

        
        JPanel menu = new JPanel();
        menu.setSize(window.getWidth(), window.getHeight());
        menu.setBackground(Color.BLACK);
        window.add(menu);
        JLabel title = new JLabel("Your Movie Data Viewer");

        // create a title font and set font
        Font titleFont = new Font(null);
        String titleFontString = "./resources/fonts/Title_Font.ttf";
        titleFont = setFontSpecs(titleFontString, 50f);
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);

        // create a font for buttons and set font
        Font buttonFont = new Font(null);
        String buttonFontString = "./resources/fonts/Title_Font.ttf";
        buttonFont = setFontSpecs(buttonFontString, 50f);

        // create an import button and set style
        JButton importButton = new JButton("Import CSV");
        importButton = setButtonStyle(importButton, buttonFont);
        importButton.addActionListener(e -> importButtonPressed(e));

        // create a 'how to' button and set style
        JButton howButton = new JButton("How to get CSV");
        howButton = setButtonStyle(howButton, buttonFont);
        howButton.addActionListener(e -> howButtonPressed(e));


        menu.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.insets = new Insets(3,3,3,3);
        constraints.gridy = 0;
        menu.add(title, constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridy = 2;
        menu.add(importButton, constraints);
        constraints.gridy = 3;
        menu.add(howButton, constraints);
        window.repaint();
        window.revalidate();
        
        return window;
    }

    private static void howButtonPressed(ActionEvent e) {
        App.showHowToPage();
    }

    // this runs when the import csv button is pressed
    public static void importButtonPressed(ActionEvent e) {
        // handle menu button action
        //System.out.println("Oh fuck yeah");
        // Create a file chooser
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
        fc.setFileFilter(filter);
        // In response to a button click:
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            // this is where app opens file i guess
            //String fileName = file.getName();
            //System.out.println(file.getName());
            String placeToSaveFile = "./moviedata.csv";
            try {
                Files.copy(file.toPath(), new File(placeToSaveFile).toPath(), StandardCopyOption.REPLACE_EXISTING);
                processCSV();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else{
            //System.out.println("");
        }
    }

    // once the CSV is copied, make new movie objects for every line in the csv
    private static void processCSV() {
        final String COMMA_DELIMITER = ",";
        List<List<String>> records = new ArrayList<>();
        ArrayList<Movie> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("moviedata.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int movieNumber = 0; movieNumber < records.size(); movieNumber++){
            // split records and get the name date and url
            String name;
            String dateWatched;
            String url;
            String publishDate;
            int dateWatchedInt;
            int dateCreatedInt = 0;
            String regex = ",";
            String movieunsplitString = records.get(movieNumber).toString();
            movieunsplitString.replaceAll("\\[", "").replaceAll("\\]","");
            String[] movieSplitStrings = movieunsplitString.split(regex);
            String yearOnly = movieSplitStrings[0]; // this gives you a date like this [2025-05-07
            //System.out.println(yearOnly);
            yearOnly.replaceAll("[^\\d.]", ""); //remove open bracket so now its like 2025-05-07
            yearOnly = yearOnly.substring(yearOnly.length() - 4); // isolate the year
            dateWatched = yearOnly;
            
            //System.out.println(dateWatched);
            

            name = movieSplitStrings[1];
            publishDate = movieSplitStrings[2];
            try{
                publishDate = publishDate.replace(" ", "");
                dateCreatedInt = Integer.valueOf(publishDate);
            }
            catch(Exception e){
                System.out.println(e);
            }

            url = movieSplitStrings[3];
            // add a new movie object the movies arraylist
            movies.add(new Movie());
            // set the name of each movie in movies to the correct data
            movies.get(movieNumber).name = name;
            try{
            if(dateWatched != ""){
                dateWatchedInt = Integer.valueOf(dateWatched);
            }
            else{
                dateWatchedInt = 0;
            }
            //System.out.println(dateWatchedInt);
            movies.get(movieNumber).dateWatched = dateWatchedInt;
            }
            catch(Exception e){
                System.out.println(e + "FUUUCK");
            }
            movies.get(movieNumber).url = url;
            movies.get(movieNumber).publishDate = dateCreatedInt;

            //movies.get(movieNumber).printInfo();
           
        }
        App.showMainDialog(movies);
    }

    static JFrame configWindow(JFrame window) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(900, 600);
        window.setLocationRelativeTo(null);
        ImageIcon favicon = new ImageIcon("./resources/images/icon.png");
        window.setIconImage(favicon.getImage());
        window.repaint();
        window.revalidate();
        window.setVisible(true);
        return window;

    }

    static Font setFontSpecs(String fontFileSource, Float size) {
        Font font = new Font(null);
        try {
            //System.out.println(fileSource);
            font = Font.createFont(Font.TRUETYPE_FONT, new File(fontFileSource)).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontFileSource)));
        } catch (IOException | FontFormatException e) {
            System.out.println (e.toString());
        }
        return font;
    }

    static JButton setButtonStyle(JButton button, Font font) {

        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        button.setFont(font);
        return button;
    }
}