package song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import misc.Unzipper;
import misc.Utils;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.removeExtension;

public class SongFile{
    public static String getTargetDir() {
        return targetDir;
    }

    private static String targetDir = "./songs";
    private static final Set<String> infoKeyValues = new HashSet<>(Arrays.asList("info","Info", "isInfo"));
    private File file;
    private File infoFile;
    private final Set<MapFile> maps = new HashSet<>();
    private final String title;


    public String getTitle() {
        return title;
    }



    public File getFile() {
        return file;
    }


    public SongFile(File file) throws IOException {
        if (Unzipper.isZip(file)) {
            this.file = new File(file.getParent() + "\\" + Utils.getUnusedFileName(file, Unzipper.zipName(file), targetDir));
            Unzipper.unzip(file.getAbsolutePath(), this.file.getAbsolutePath());

            file.delete();
        }

        else this.file = file;

        File[] files = this.file.listFiles();
        if (files == null)
            throw new IOException("El directorio no tiene los archivos necesarios");

        for (File subf : files) {
            String name = removeExtension(subf.getName());
            if (getExtension(subf.getName()).equals("dat")) {
                if (infoKeyValues.contains(name))
                    infoFile = subf;
                else if (getExtension(subf.getName()).equals("dat"))
                    maps.add(new MapFile(subf));
            }
        }

        if (infoFile == null) {
            throw new IOException("El directorio de la canción " + file.getAbsolutePath() + " no posee los archivos correctos");
        }

        JSONObject o = new JSONObject(Files.readString(infoFile.toPath()));
        title = o.getString("_songName");

    }

//    public void createFile() throws IOException {
//
//        File possibleTargetFile = new File(targetDir + "\\" + Utils.getUnusedFileName(file,title,targetDir));
//
//        if (file.getParent().equals(targetDir) && !file.getName().equals(Utils.stripName(title)) && !file.getName().equals(possibleTargetFile.getName())) {
//            // si el archivo se encuentra en el mismo directorio y tiene un nombre incorrecto
//            System.out.println("Renaming " + file.getAbsolutePath() + " to " + possibleTargetFile.getAbsolutePath());
//            file.renameTo(possibleTargetFile);
//        }
//
//        else if (!file.getParent().equals(targetDir)){
//            System.out.println("Copying " + file.getAbsolutePath() + " to " + possibleTargetFile.getAbsolutePath());
//            FileUtils.copyDirectory(file, possibleTargetFile);
//        }
//
//        // el archivo ya se encuentra en el directorio y tiene el nombre correcto. No hago nada.
//
//    }

    public boolean equals(Object o) {

        if (!(o instanceof SongFile))
            return false;

        SongFile aux = (SongFile) o;


        return aux.maps.equals(maps);

    }


    @Override
    public int hashCode() {
        return Objects.hash(maps);
    }

    public String toString() {
        return "'" + title + "'";
    }
}
