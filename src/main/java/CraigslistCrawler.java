import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class CraigslistCrawler {
    public static void main(String[] args) throws Exception{
        CraigslistCrawler.Crawler("https://sfbay.craigslist.org/d/apts-housing-for-rent/search/apa");
    }

    static void Crawler(String url) throws Exception {
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        //headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "en-US,en;q=0.8");
        Document doc = Jsoup.connect(url).headers(headers).get();
//        System.out.println(doc);
        String prefix = "#sortable-results > ul > li:nth-child(";
        for (int i = 1; i <= 120; i++) {
            Element titleElem = doc.select( prefix+ i + ") > p > a").first();
            String title = titleElem == null ? "null" : titleElem.text();

            Element priceElem = doc.select(prefix + i  + ") > p > span.result-meta > span.result-price").first();
            String rentPrice = priceElem == null ? "null" : priceElem.text();

            Element urlElem = doc.select(prefix + i + ") > p > a").first();
            String detailurl = urlElem == null ? "null" : urlElem.attr("href");

            Element hoodElem = doc.select(prefix + i +") > p > span.result-meta > span.result-hood").first();
            String hood = hoodElem == null ? "null" : hoodElem.text().replaceAll("[()]", "");

            System.out.println("No." + i);
            System.out.println("    Title : " + title);
            System.out.println("    Rent price : " + rentPrice);
            System.out.println("    Detail url : " + detailurl);
            System.out.println("    Hood : " + hood);
        }
    }
}
