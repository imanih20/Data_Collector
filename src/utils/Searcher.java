package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Searcher {
    public static String search(String word) throws Exception {
        String wiki = "ویکی پدیا";
        String uri = "https://www.google.com/search?q="+wiki+word+"&ie=UTF-8";
        Document document = Jsoup.connect(uri).get();
        Elements elements = document.getElementsByClass("yuRUbf");
        return elements.first().getElementsByTag("a").attr("href");
    }
}
