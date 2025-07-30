//import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
    static String run(String url) {
        String countryAndLanguage = new String();

        try {
            Document document = Jsoup.connect("https://letterboxd.com/film/paddington-in-peru").get();
            Elements scriptElements = document.getElementsByTag("script:containsData(Country)");

            for (Element element : scriptElements) {
                for (DataNode node : element.dataNodes()) {
                    System.out.println(node.getWholeData());
                }
                System.out.println("-------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countryAndLanguage;
    }
}
