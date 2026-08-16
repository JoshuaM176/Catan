package luis.josh.catan.util;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import luis.josh.catan.host.game.board.resources.Resource;

public class JSONUtil {

    @SuppressWarnings("unchecked")
    public static <T> JSONArray ArrayToJSON(T[] array) {
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < array.length; i++) {
            jsonArray.add(array[i]);
        }
        return jsonArray;
    }

    @SuppressWarnings("unchecked")
    public static <T> JSONArray MatrixToJSON(T[][] matrix) {
        JSONArray jsonArray = new JSONArray();
        for(int row = 0; row < matrix.length; row++) {
            JSONArray inner = new JSONArray();
            for(int col = 0; col < matrix[0].length; col++) {
                inner.add(matrix[row][col]);
            }
            jsonArray.add(inner);
        }
        return jsonArray;
    }

    /**
     * Takes a json array representing resources and converts it to a hash map. Json should
     * be formatted [{"resource": "RESOURCENAME", "count": 1}, ...].
     * @param resources The JSONArray of resources.
     */
    public static HashMap<Resource, Integer> resourceMapFromJSON(JSONArray resources) {
        HashMap<Resource, Integer> resourcesMap = new HashMap<>();
        for(Object each: resources) {
            JSONObject rsrc_data = (JSONObject)each;
            Resource resource = Resource.valueOf((String)rsrc_data.get("resource"));
            int count = (int)rsrc_data.get("count");
            resourcesMap.put(resource, count);
        }
        return resourcesMap;
    }
}
