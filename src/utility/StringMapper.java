package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringMapper {
    public static List<Map<String, String>> toMapArray(String data) {
        String[] line = data.split("\n-\n");
        List<Map<String, String>> maps = new ArrayList<>();
        for (String aLine : line) {
            Map<String, String> map = new HashMap<>();
            String[] splitString = aLine.split("&");
            for (String aSplitString : splitString) {
                String name = aSplitString.split("=")[0];
                String value;
                if (aSplitString.split("=").length == 1)
                    value = "";
                else
                    value = aSplitString.split("=")[1];
                map.put(name, value);
            }
            maps.add(map);
        }
        return maps;
    }
}
