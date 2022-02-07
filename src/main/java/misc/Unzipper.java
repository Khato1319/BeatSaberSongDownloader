package misc;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.removeExtension;


public class Unzipper {

    /***
     * unzips file from source path into target path
     * @param src
     * @param target
     */
    public static void unzip(String src, String target){

        try {
            ZipFile zipFile = new ZipFile(src);
            zipFile.extractAll(target);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    /***
     * returns the name of the file without its ".zip" extension
     * @param file
     * @return
     */
    public static String zipName(File file) {
        if (!isZip(file))
            throw new RuntimeException("El archivo no es un zip");
        return removeExtension(file.getName());
    }

    public static boolean isZip(File file) {
        return getExtension(file.getName()).equals("zip");
    }



}
