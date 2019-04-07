package LogicOfProgram;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Airlines {

    public String alID = "";
    public String alName = "";

    public HashMap<String, String> listOfAirlines() throws Exception {

        HashMap<String, String> map = new HashMap<String, String>();
        BufferedReader in = new BufferedReader(new FileReader("airlines.dat"));
        String line = "";
        String sortedAirline = "";

        while ((line = in.readLine()) != null) {
            String parts[] = line.split("\\s+");
            map.put(parts[0], parts[1]);
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

    public String returnAirlineID(String s) throws Exception {
        Map<String, String> map = listOfAirlines();
        String airlineID = "";
        
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String lowerCase = s.toLowerCase();
            String value = entry.getValue().toLowerCase();
            String key = entry.getKey();
            
            if(value.contains(lowerCase)){
                airlineID = key;
            }
        }

        return airlineID;
    }

}
