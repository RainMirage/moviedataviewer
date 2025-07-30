import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.BorderLayout;
import java.awt.Color;

public class Tutorial {

    // this opens the page that tells the user how to get their CSV from letterboxd
    // and also tells them they need to delete the column names row
    // and find and replace all commas with nothing
    public static JFrame open(JFrame window) {
        window.getContentPane().removeAll();
        JLabel tutorialText = new JLabel();
        

        try {
            BufferedReader br = new BufferedReader(new FileReader("./resources/guide.txt"));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString();
                tutorialText.setText(everything);
                
            } finally {
                br.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        Font font = new Font(null);

        font = GUI.setFontSpecs("./resources/fonts/Title_Font.ttf", 30f);
        tutorialText.setForeground(Color.WHITE);
        JPanel tutorialPanel = new JPanel(new BorderLayout());
        tutorialPanel.setPreferredSize(new Dimension(window.getWidth()-50, window.getHeight()-100));

        tutorialText.setFont(font);
        tutorialText.setHorizontalAlignment(JLabel.CENTER);

        tutorialPanel.setOpaque(false);
        tutorialPanel.setBackground(new Color(0,0,255));
        //tutorialPanel.setLayout(new BorderLayout());
        tutorialPanel.add(tutorialText);

        JButton backButton = new JButton("Back");
        backButton = GUI.setButtonStyle(backButton, font);
        
        backButton.addActionListener(e -> buttonPressed(e, window));
        

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
        mainPanel.setOpaque(true);
        mainPanel.setBackground(new Color(0, 0, 0));
        mainPanel.add(backButton);
        mainPanel.add(tutorialPanel);
        
        
        window.add(mainPanel);
        window.revalidate();
        window.repaint();
        return window;
    }

    private static void buttonPressed(ActionEvent e, JFrame window) {
        window.getContentPane().removeAll();
        GUI.launchMenu(window);
    }
    
}
