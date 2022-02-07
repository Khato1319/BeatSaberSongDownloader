package song;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class MapFile {
    private final JSONObject json;

    MapFile(File file) throws IOException {
        this.json = new JSONObject(Files.readString(file.toPath()));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MapFile))
            return false;

        return ((MapFile)obj).json.toString().equals(json.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(json.toString());
    }
}
