package misc;


import java.io.File;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.removeExtension;

public class Utils {

    /***
     * Returns an unused file name for file "source" to be outputted in "dir" directory with name "name" if possible (title of song), and if not available,
     * with a "(n)" appended at the end where n is a number
     * @param source the file which has to be renamed. If null then the method only seacrhes for an available name to be used inside dir
     * @param name the name the file can possibly have. If not available then "(n)" is appended to the end where n is a number
     * @param dir the target directory for the file source
     * @return a string with a valid name for the file source
     */
    public static String getUnusedFileName(File source, String name, String dir) {
        File fileDir = new File(dir);
        String extWDot = getExtension(name).equals("zip") ? ".zip" : "";
        String initialSongName = extWDot.equals(".zip") ? removeExtension(stripName(name)) : stripName(name);
        String aux = initialSongName;
        String newPath = fileDir.getAbsolutePath() + "\\" + initialSongName + extWDot;
        int num=0;
        while (new File(newPath).exists() && (source == null || !source.getAbsolutePath().equals(newPath))) {
            initialSongName = aux;
            initialSongName += "(" + num + ")";
            newPath = dir + "\\" + initialSongName + extWDot;
            num++;
        }
        return initialSongName + extWDot;
    }

    /***
     * Removes all invalid characters from the name and removes the ".zip" extension if the file is a zip
     * @param name the initial name of the file
     * @return a new name with all invalid characters stripped and its .zip extension removed if it has one
     */
    public static String stripName(String name) {
        String extWDot = getExtension(name).equals("zip") ? ".zip" : "";
        String initialSongName = extWDot.equals(".zip") ? removeExtension(name).replaceAll("[<>:\"/\\|?*]", "_") : name.replaceAll("[<>:\"/\\|?*]", "_");
        return initialSongName + extWDot;
    }
}


