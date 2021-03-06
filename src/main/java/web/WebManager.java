package web;

import misc.Utils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import song.SongFile;
import song.SongLibrary;
import song.SongParameters;
import web.pageElements.Period;
import web.pageElements.SortOrder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebManager {

    private static final String downloadUrlFormat = "https://api.beatsaver.com/download/key/%s";
    private final Map<String,String> songsById = new HashMap<>();


    private String getId(String songUrl) {
        String[] splitted = songUrl.split("/");
        return splitted[splitted.length-1];
    }

    public static Set<String> getPeriods() throws IOException {
        Document doc = Jsoup.connect("https://bsaber.com/songs/top").get();
        Elements periods = Objects.requireNonNull(doc.body().getElementsByClass("songs-filter").first()).children().get(1).children();
        return periods.stream().map(e -> e.attr("href").split("=")[1]).collect(Collectors.toSet());
    }

    public static Set<String> getCategories() throws IOException {
        Document doc = Jsoup.connect("https://bsaber.com/songs/").get();
        Elements categories = Objects.requireNonNull(doc.body().getElementsByClass("js-song-genre").first()).children();
        return categories.stream().map(e -> e.attr("value")).collect(Collectors.toSet());
    }

    public void fetchSongs(SongParameters parameters, int qty) throws IOException {
        BSaberWebPage page = new BSaberWebPage(parameters);
        System.out.printf("Fetching %s (max = %s)\n", parameters.setting(), qty);
        int fetched = 0;
        Document doc = Jsoup.connect(page.setPage(0).toString()).get();
        System.out.printf("Connecting to %s\n",page);
        Elements elements = doc.body().getElementsByAttributeValue("rel","bookmark");
        Element lastPageElement = doc.body().getElementsByAttributeValue("class", "page-numbers").last();


        int lastPage = lastPageElement == null ? 0 : Integer.parseInt(lastPageElement.text().replaceAll(",", ""));

        System.out.println("--------------------------------------------------------------------");
        for (int i=0 ; i<=lastPage && (qty == 0 || fetched < qty) ; i++) {
            if (i != 0) {
                doc = Jsoup.connect(page.setPage(i).toString()).get();
                elements = doc.body().getElementsByAttributeValue("rel","bookmark");
            }
            for (Element element : elements) {
                String id = getId(element.attr("href"));
                String title = element.attr("title").replaceAll("[^a-zA-Z0-9-]", "_");

                fetched++;

                System.out.println(fetched + ": " + "added " + title + " (id = " + id + ") to queue");
                songsById.put(id,title);


                if (fetched == qty)
                    break;

            }
        }
        System.out.println("--------------------------------------------------------------------");
    }


    public void downloadAndAddToLibrary(SongLibrary lib) {

        for (Map.Entry<String,String> e : songsById.entrySet()) {

                String fileName = Utils.getUnusedFileName(null,e.getValue()+ ".zip", SongFile.getTargetDir());
                File downloadFile = new File(SongFile.getTargetDir() + "\\" + fileName);

                System.out.println("Downloading " +  "'" + downloadFile.getName() + "'");
                try {
                    FileUtils.copyURLToFile(new URL(String.format(downloadUrlFormat,e.getKey())),
                            downloadFile,5000,20000);

                System.out.println("Extracting " +  "'" + downloadFile.getName() + "'");

                SongFile s = new SongFile(downloadFile);

                if (lib.contains(s)) {
                    System.out.println("Song " + "'" + downloadFile.getName() + "'" + "already exists in library");
                    FileUtils.deleteDirectory(s.getFile());
                }

                else lib.add(s);


                } catch(IOException ex) {
                    System.err.println("Failed to download " + "'" + e.getValue() + "'");
                }

        }
    }


}
