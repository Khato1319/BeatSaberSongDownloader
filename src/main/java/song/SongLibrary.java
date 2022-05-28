package song;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class SongLibrary {
    private Set<SongFile> songs = new HashSet<>();
    private static String path;

    public SongLibrary() {
        SongLibrary.path = "./songs";
        addSongsFromDir(path);
    }

    public static String path() {
        return path;
    }

    public void add(SongFile file) {
        songs.add(file);
    }

    public boolean contains(SongFile file) {
        return songs.contains(file);
    }

    private void addSongsFromDir(String dir) {
        File fileDir = new File(dir);
        File[] existingFiles = fileDir.listFiles();
        if (existingFiles != null)
            for (File file : existingFiles) {
                try {
                    SongFile toAdd = new SongFile(file);

                    if (songs.contains(toAdd)) {
                        System.out.println("La lista ya contiene la cancion " + "'" + toAdd.getTitle() + "'");
                        if (file.getParent().equals(SongFile.getTargetDir())) {
                            System.out.println("Borrando " + file.getAbsolutePath());
                            FileUtils.deleteDirectory(file);
                        }

                    }

                    else {
                        System.out.println("'" + toAdd.getTitle() + "' en " + toAdd.getFile().getAbsolutePath() + " agregado a la lista");
                        songs.add(toAdd);
                    }


                } catch(IOException e) {
                    file.delete();
                    System.out.println("El archivo " + file.getAbsolutePath() + " es corrupto y se ha borrado");
                }

            }

    }

//    public void createFiles() throws IOException {
//        for (SongFile song : songs) {
//            song.createFile();
//        }
//    }
}
