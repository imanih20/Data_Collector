package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public record Collector(String uri) {

    public String CollectData() {
        try {
            Document doc = Jsoup.connect(uri).get();
            Element body = doc.body();
            Elements contentContainer = body.getElementsByClass("mw-parser-output");
            Elements elements = contentContainer.get(0).getElementsByTag("p");
            StringBuilder builder = new StringBuilder();
            for (Element element : elements) {
                String text = element.text();
                builder.append(text).append("\n");
            }
            return builder.toString();
        }catch (Exception e){
            return "";
        }
    }
}
