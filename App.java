import java.util.ArrayList;

import javax.swing.JFrame;

public class App {

    static JFrame window;
    
    public static void main(String[] args) {
        
        window = new JFrame("Your Movie Data Viewer");
        // basic configurations like frame size and location
        GUI.configWindow(window);
        
        // launches the main menu
        GUI.launchMenu(window);
    }

    // this happens once the movies objects have all been processed and filled
    static void showMainDialog(ArrayList<Movie> movies){
        Dialog.open(window, movies);

    }

    // this happens if the user selects 'how to get CSV on the main menu'
    public static void showHowToPage() {
        window = Tutorial.open(window);
    }
    
}