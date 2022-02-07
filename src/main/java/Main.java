import org.json.JSONArray;
import org.json.JSONObject;
import song.SongLibrary;
import web.pageElements.Period;
import web.WebManager;
import web.pageElements.SortOrder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


public class Main {

    public static void main(String[] args) throws IOException {


        JSONArray o = new JSONArray(Files.readString(Paths.get("setting.json")));

        SongLibrary lib = new SongLibrary((String) o.get(0));

//        if (Paths.get((String) o.get(0)).toString().equals(SongLibrary.path()))
//            lib.addSongsFromDirExceptRoot((String) o.get(0));

        for (Object path : (JSONArray) o.get(1)) {
            if (Paths.get((String) path).toString().equals(SongLibrary.path()))
            lib.addSongsFromDirExceptRoot((String) path);
        }

        Iterator<Object> it = o.iterator();
        it.next();
        it.next();

        WebManager b = new WebManager();

        JSONObject categories = new JSONObject(Files.readString(Paths.get("cat.json")));

        JSONObject genres = categories.getJSONObject("genre");


        while (it.hasNext()) {
            JSONObject obj = (JSONObject) it.next();
            b.fetchSongs(genres.getString(String.valueOf(obj.getInt("genre"))),Period.num(obj.getInt("period")),
                    SortOrder.num(obj.getInt("sort_order")),obj.getBoolean("ranked"),obj.getString("username"),obj.getInt("max"));
        }


        b.downloadAndAddToLibrary(lib);

        lib.createFiles();


    }


}
