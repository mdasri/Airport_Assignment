package LogicOfProgram;

import com.google.common.collect.*;
import java.io.*;
import java.util.*;

public class Flights {

    public String apIDfrom = "";
    public String oneStop = "";
    public String twoStop = "";
    public String threeStop = "";
    public String fourStop = "";

    Multimap<String, String> myMultimap, AirporttoAirlinesMultimap;
    Multimap<String, String> newMap;

    public static void main(String[] args) throws Exception {

        Flights f = new Flights();
        f.listOfFlights();

        f.stopover("AK", "KUL", "SIN");
//        f.stopover("SQ", "NRT", "SIN");
    }

    // Method which returns the list of routes for that airlines
    public Multimap<String, String> listOfFlights() throws Exception {

        myMultimap = ArrayListMultimap.create();
        AirporttoAirlinesMultimap = ArrayListMultimap.create();

        BufferedReader in = new BufferedReader(new FileReader("flights.dat"));
        String line = "";

        int o = 0;
        while ((line = in.readLine()) != null) {
            String airlineID = line.substring(0, line.indexOf(' '));
            apIDfrom = line.substring(line.indexOf(' ') + 1);

            myMultimap.put(airlineID, apIDfrom);
            AirporttoAirlinesMultimap.put(apIDfrom, airlineID);
        }

        in.close();
        return myMultimap;
    }

    // Method which returns the airport to airlines map. (It returns the result of what airlines is operating at that airport)
    public Multimap<String, String> listOfAirports() throws Exception {
        return AirporttoAirlinesMultimap;
    }

    // Method which returns the result of what airlines is operating at that airport
    public HashMap<String, ArrayList<String>> airportToAirlines(String from) throws Exception {
        Airports ap = new Airports();
        Map<String, String> airportMap = ap.listOfAirport();

        String airportname = "";
        String[] names = from.split("\\r?\\n");
        String k = "";
        HashMap<String, ArrayList<String>> airports = new HashMap<String, ArrayList<String>>();

        for (int a = 0; a < names.length; a++) {
            ArrayList<String> list = new ArrayList<String>();

            for (Map.Entry<String, String> entry2 : AirporttoAirlinesMultimap.entries()) {
                String key = entry2.getKey();
                String[] keyparts = key.split("\\s+");
                String values = entry2.getValue();

                airportname = (String) airportMap.get(names[a]);

                if (keyparts[0].equals(names[a])) {

                    if (!list.contains(values)) {
                        list.add(values);
                    }
                }
            }
            airports.put(airportname, list);
        }
        return airports;
    }

    // Method which returns the airlines which are operating at the airport when search by airport ID
    public String airportToAirlinesBYID(String from) {
        String k = "";
        for (Map.Entry<String, String> entry2 : AirporttoAirlinesMultimap.entries()) {
            String key = entry2.getKey();
            String values = entry2.getValue();
            if (key.contains(from)) {
                if (!k.contains(values)) {
                    k += values + " ";
                }
            }
        }
        return k;
    }

    // Method which returns both the direct flight and the service which requires one transfer
    public String toAndFro(String frm, String t) throws Exception {
        String from = frm;
        String to = t;
        int i = 0;
        int x = 0;
        int h = 0;
        String result = "";
        List arr1 = new ArrayList<String>();
        List arr2 = new ArrayList<String>();
        List lstOfAirlines1 = new ArrayList<String>();
        List lstOfAirlines2 = new ArrayList<String>();

        for (Map.Entry<String, String> entry : myMultimap.entries()) {
            String values = entry.getValue();
            String key = entry.getKey();
            String[] parts = values.split("\\s+");
            if (!values.equals(from + " " + to)) {
                if (parts[0].equals(from)) {
                    arr1.add(values);
                    lstOfAirlines1.add(key);
                }

                if (parts[1].equals(to)) {
                    arr2.add(values);
                    lstOfAirlines2.add(key);
                }
            }

            if (values.equals(from + " " + to)) {
                if (!result.contains(key)) {
                    result += key + "\n";
                }
            }

        }

        for (i = 0; i < arr1.size(); i++) {
            String first = (String) arr1.get(i);
            String firstAL = (String) lstOfAirlines1.get(i);

            for (x = 0; x < arr2.size(); x++) {
                String second = (String) arr2.get(x);
                String secAL = (String) lstOfAirlines2.get(x);

                String[] parts1 = first.split("\\s+");
                String[] parts2 = second.split("\\s+");

                if (parts1[1].equals(parts2[0])) {
                    result += firstAL + " " + secAL + " " + first + " " + second + "\n";
                }
            }
        }
        return result;
    }
    
   //Method for the flights stopover
    public String stopover(String airline, String from, String to) throws Exception {
        //output strings
        oneStop = "";
        twoStop = "";
        threeStop = "";
        fourStop = "";

        //arraylist to store mapping
        List arr1 = new ArrayList<String>();
        List arr2 = new ArrayList<String>();
        List arr3 = new ArrayList<String>();
        List arr4 = new ArrayList<String>();
        List middlearr = new ArrayList<String>();

        for (Map.Entry<String, String> entry : myMultimap.entries()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String[] parts = value.split("\\s+");

            if (!value.equals(from + " " + to)) {
                if (parts[0].equals(from)) {
                    if (key.equals(airline)) {
                        arr1.add(value);
                    }
                }
                if (parts[1].equals(to)) {
                    if (key.equals(airline)) {
                        arr2.add(value);
                    }
                }
                if (!parts[0].equals(from) && !parts[1].equals(to)) {
                    if (key.equals(airline)) {
                        arr3.add(value);
                        arr4.add(value);
                    }
                }
            }
        }

        //mapping of flights which have no relation to "from" and "to" query
        String middleStopover1, middleStopover2, middleStop = "";
        String[] splitMiddle1, splitMiddle2;
        for (int k = 0; k < arr3.size(); k++) {
            middleStopover1 = (String) (arr3.get(k));
            splitMiddle1 = middleStopover1.split("\\s+");

            for (int x = 0; x < arr4.size(); x++) {
                middleStopover2 = (String) (arr4.get(x));
                splitMiddle2 = middleStopover2.split("\\s+");
                if (splitMiddle1[1].equals(splitMiddle2[0])) {
                    middleStop = middleStopover1 + " " + middleStopover2;
                    middlearr.add(middleStop);
                }
            }
        }
        //finding stopovers
        for (int i = 0; i < arr1.size(); i++) {
            String list1 = (String) arr1.get(i);
            String[] part1 = list1.split("\\s+");
            //for one stopovers
            for (int x = 0; x < arr2.size(); x++) {
                String list2 = (String) arr2.get(x);
                String[] part2 = list2.split("\\s+");
                if (part1[1].equals(part2[0])) {
                    oneStop += list1 + " " + list2 + "\n";
                }
                //for two stopovers
                for (int y = 0; y < arr3.size(); y++) {
                    String list3 = (String) arr3.get(y);
                    String[] part3 = list3.split("\\s+");
                    if (part1[1].equals(part3[0]) && part3[1].equals(part2[0])) {
                        twoStop += list1 + " " + list3 + " " + list2 + "\n";
                    }
                    //for three stopovers
                    for (int z = 0; z < middlearr.size(); z++) {
                        String list4 = (String) middlearr.get(z);
                        String[] part4 = list4.split("\\s+");
                        if (part1[1].equals(part4[0]) && part4[3].equals(part2[0])) {
                            if (!threeStop.contains(list1 + " " + list4 + " " + list2 + "\n")) {
                                //remove return flight back to any destination
                                if (!part1[1].equals(part4[3])) {
                                    threeStop += list1 + " " + list4 + " " + list2 + "\n";
                                }
                            }
                        }
                        //for four stopovers
                        if (part1[1].equals(part4[0]) && part4[3].equals(part3[0]) && part3[1].equals(part2[0])) {
                            if (!fourStop.contains(list1 + " " + list4 + " " + list3 + " " + list2 + "\n")) {
                                //remove return flight back to any destination
                                if (!part1[1].equals(part4[3]) && !part1[1].equals(part3[1]) && !part4[1].equals(part3[1])) {
                                    fourStop += list1 + " " + list4 + " " + list3 + " " + list2 + "\n";
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return oneStop;
    }

    public String returnOneStop() {
        return oneStop;
    }

    public String returnTwoStop() {
        return twoStop;
    }
    
    public String returnThreeStop() {
        return threeStop;
    }
    
    public String returnFourStop() {
        return fourStop;
    }
}
