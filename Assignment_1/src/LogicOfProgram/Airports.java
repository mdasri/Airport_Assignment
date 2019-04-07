package LogicOfProgram;

import java.io.*;
import java.util.*;

public class Airports {

    public String apID = "";
    public String apName = "";
    HashMap<String, String> map = new HashMap<String, String>();
    
    public HashMap<String, String> listOfAirport() throws Exception{
        
        BufferedReader in = new BufferedReader(new FileReader("airports.dat"));
        String line = "";
        String sortedAirport = "";
        
        while ((line = in.readLine()) != null) {
            String airportID = line.substring(0,line.indexOf(' '));
            apName = line.substring(line.indexOf(' ')+1);
            map.put(airportID, apName);
            apID += airportID + " ";
        }
        in.close();
        return map; 
    }
    
    public HashMap<String, String> sorted(Map<String, String> map) {
        List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, String> sortedMap = new LinkedHashMap<String, String>();
        for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    
    public String returnAirportID(String name){
        String newname = name.toLowerCase();
        String airportID = "";
         for (Map.Entry<String, String> entry: map.entrySet()){
             String key = entry.getKey();
             String values = entry.getValue();
             String value = values.toLowerCase();
             if (value.contains(newname)){
                 airportID += key + "\n";
             }
         }
         return airportID;
    }

}
