package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;

/**
 *
 * @author PAVLE
 */
public class JsonExporter {

    private JsonExporter() {
    }

    public static void exportToJson(Object data, String filePath) throws Exception {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try ( FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        }
    }
}
