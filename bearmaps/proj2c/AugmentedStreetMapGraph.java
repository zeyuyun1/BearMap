package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.MyTrieSet;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private WeirdPointSet searcher;
    private HashMap<Point, Long> reader;
    private MyTrieSet cleanNames = new MyTrieSet();
    private HashSet<String> cleanNamesContainer = new HashSet<>();
    private HashMap<String, HashSet<Node>> allNodes = new HashMap<>();
    private boolean emptystring = false;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>(5);
        reader = new HashMap<>();
        for (Node item : nodes) {
            if (neighbors(item.id()).size() != 0) {
                Point a = new Point(item.lon(), item.lat());
                points.add(a);
                reader.put(a, item.id());
            }
            String givenName = item.name();
            if (givenName != null) {
                String cleanName = cleanString(givenName);
                if (!cleanNames.contains(cleanName)) {
                    cleanNames.add(cleanName);
                    HashSet<Node> thisName = new HashSet<>();
                    allNodes.put(cleanName, thisName);
                }
                allNodes.get(cleanName).add(item);
            }
        }
        searcher = new WeirdPointSet(points);
    }

    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point desiredPoint = searcher.nearest(lon, lat);
        return reader.get(desiredPoint);
    }

    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     *
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        LinkedList<String> samePrefix = new LinkedList<>();
        for (String name : cleanNames.keysWithPrefix(cleanString(prefix))) {
            for (Node item : allNodes.get(name)) {
                samePrefix.add(item.name());
            }
        }
        System.out.println(samePrefix);
        return samePrefix;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     *
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        ArrayList<Map<String, Object>> allLocation = new ArrayList<>();
        String cleanLocationName = cleanString(locationName);
        for (Node item : allNodes.get(cleanLocationName)) {
            HashMap<String, Object> thisNode = new HashMap<>();
            thisNode.put("name", item.name());
            thisNode.put("lat", item.lat());
            thisNode.put("lon", item.lon());
            thisNode.put("id", item.id());
            allLocation.add(thisNode);
        }
        System.out.println(allLocation);
        return allLocation;
    }

}
