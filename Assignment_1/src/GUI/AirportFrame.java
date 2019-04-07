package GUI;

import LogicOfProgram.Airports;
import LogicOfProgram.Airlines;
import LogicOfProgram.Flights;
import com.google.common.collect.Multimap;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AirportFrame extends JFrame implements ItemListener, ActionListener {

    public JLabel title;
    public JPanel titlePanel, listPanel, resultsPanel1, cards, comboBoxPane, midPanel, searchPanel,
            theBigPanel, thirdPanel, output;
    public JButton searchByAirport, searchByAirline, boardNDest, searchByStopovers;
    public JTextField airlineSearch, airportSearch, fromSearch, toSearch;
    public JTextArea availAirlines, alResults, availAirports, apResults;
    public JScrollPane airlineScroll, airportScroll, destScroll, airportResultScroll, airlineResultScroll;
    public String airportNames, airlineNames, destinations;
    public JComboBox chooseAirline;
    public HashMap<String, String> hashOfAirports, hashOfAirlines, unsortHashOfAirports, unsortHashOfAirlines;
    public Multimap<String, String> routesMap, AirporttoAirlinesMultimap;

    Airports ap = new Airports();
    Airlines al = new Airlines();
    Flights f = new Flights();

    public AirportFrame() throws Exception {

        routesMap = f.listOfFlights();
        AirporttoAirlinesMultimap = f.listOfAirports();

        //The combobox for the selection of screen
        comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = {"Search by Airline", "Search by Airport"};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        //The awesome title!
        title = new JLabel("!~ YOUR NON-STOP FLIGHTS INFORMATION ~!", SwingConstants.CENTER);
        title.setForeground(Color.BLUE);
        title.setFont(new Font("Helvetica", Font.BOLD, 22));

        //The declaration of buttons used
        searchByAirline = new JButton("Search by Airline");
        searchByAirport = new JButton("Search by Airport");
        boardNDest = new JButton("Search by Direct/Transfer");
        searchByStopovers = new JButton("Search by Stopvers");

        //JTextField declarations
        airlineSearch = new JTextField(20);
        airportSearch = new JTextField(20);
        fromSearch = new JTextField(10);
        toSearch = new JTextField(10);

        //JTextArea declarations
        availAirlines = new JTextArea(30, 30);
        availAirlines.setEditable(false);
        unsortHashOfAirlines = al.listOfAirlines();
        hashOfAirlines = al.sorted(unsortHashOfAirlines);
        airlineNames = "";
        for (Map.Entry<String, String> entry : hashOfAirlines.entrySet()) {
            airlineNames += entry.getKey() + "\t" + entry.getValue() + "\n";
        }
        airlineScroll = addScrollPane(availAirlines, airlineNames);

        alResults = new JTextArea(30, 30);
        alResults.setEditable(false);
        airlineResultScroll = addScrollPane(alResults, "");

        availAirports = new JTextArea(30, 30);
        availAirports.setEditable(false);
        unsortHashOfAirports = ap.listOfAirport();
        hashOfAirports = ap.sorted(unsortHashOfAirports);
        airportNames = "Airport ID\t  Airport Name\n\n";
        for (Map.Entry<String, String> entry : hashOfAirports.entrySet()) {
            airportNames += entry.getKey() + "\t" + entry.getValue() + "\n";
        }
        airportScroll = addScrollPane(availAirports, airportNames);

        apResults = new JTextArea(15, 15);
        apResults.setEditable(false);
        airportResultScroll = addScrollPane(apResults, "");

        // Creation of the combobox for the user to select the airline
        ArrayList<String> list1 = new ArrayList();
        for (Map.Entry<String, String> map1 : hashOfAirlines.entrySet()) {
            list1.add(map1.getValue());
        }
        String[] comboBoxAirline = new String[list1.size()];
        comboBoxAirline = list1.toArray(comboBoxAirline);
        chooseAirline = new JComboBox(comboBoxAirline);

        //Add action listeners to the buttons used in program
        searchByAirport.addActionListener(this);
        searchByAirline.addActionListener(this);
        boardNDest.addActionListener(this);
        searchByStopovers.addActionListener(this);

        addComponents();
    }

    public void addComponents() {
        setLayout(new BorderLayout());

        titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.add(title);
        titlePanel.add(comboBoxPane);

        // WORKING PANELS! (AKA SEARCH USING JTEXTFIELD)
        // Creation of the panels to be added to the cardLayout
        JPanel airportPanel = new JPanel();
        JLabel label = new JLabel("Enter Airport Name/ID:\t");
        airportPanel = visual("Available Airports", "Your Results", airportSearch, searchByAirport, airportResultScroll, airportScroll, label);

        JPanel airlinePanel = new JPanel();
        JLabel label1 = new JLabel("Enter Airline ID:\t");
        airlinePanel = visual("Available Airlines", "Route the airlines took", airlineSearch, searchByAirline, airlineResultScroll, airlineScroll, label1);

        //Create the panel that contains the "cards" AKA the different screen that you see
        cards = new JPanel(new CardLayout());
        cards.add(airlinePanel, "Search by Airline");
        cards.add(airportPanel, "Search by Airport");

        // Adding all the components to the jframe
        add(titlePanel, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);
    }

    // Method to add scrollpane to the jtextarea used in program
    public JScrollPane addScrollPane(JTextArea panel, String text) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setText(text);

        return scroll;
    }

    // Method used for the creation of the panels
    public JPanel visual(String leftBox, String rightBox, JTextField search, JButton btn, JScrollPane results, JScrollPane sp, JLabel jl) {
        listPanel = new JPanel(new GridLayout(1, 1));
        listPanel.setBorder(BorderFactory.createTitledBorder(leftBox));
        listPanel.add(sp);

        resultsPanel1 = new JPanel(new GridLayout(1, 1));
        resultsPanel1.setBorder(BorderFactory.createTitledBorder(rightBox));
        resultsPanel1.add(results);

        //Panel holding the list of airplanes panel and the results
        midPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        midPanel.add(listPanel);
        midPanel.add(resultsPanel1);

        //Panel holding the search bar and button at the bottom of the panel
        JPanel extraSearchPanel1 = new JPanel();
        extraSearchPanel1.setBorder(BorderFactory.createTitledBorder("Search By Airport"));
        JPanel extraSearchPanel2 = new JPanel();
        extraSearchPanel2.setBorder(BorderFactory.createTitledBorder("Search By Destination"));

        if (leftBox == "Available Airports") {
            searchPanel = new JPanel(new GridLayout(2, 1));
            JLabel frm = new JLabel("From: ", SwingConstants.RIGHT);
            JLabel to = new JLabel("To: ", SwingConstants.RIGHT);

            extraSearchPanel1.add(jl);
            extraSearchPanel1.add(search);
            extraSearchPanel1.add(btn);

            extraSearchPanel2.add(chooseAirline);
            extraSearchPanel2.add(frm);
            extraSearchPanel2.add(fromSearch);
            extraSearchPanel2.add(to);
            extraSearchPanel2.add(toSearch);

            JPanel emptyPanel = new JPanel();
            JPanel searchButtonPanel = new JPanel();
            searchButtonPanel.add(boardNDest);
            searchButtonPanel.add(searchByStopovers);

            searchPanel.add(extraSearchPanel1);
            searchPanel.add(extraSearchPanel2);
            searchPanel.add(emptyPanel);
            searchPanel.add(searchButtonPanel);
        } else {

            searchPanel = new JPanel();
            searchPanel.setBorder(BorderFactory.createTitledBorder("Search By Airlines"));
            searchPanel.add(jl);
            searchPanel.add(search);
            searchPanel.add(btn);
        }

        theBigPanel = new JPanel();
        theBigPanel.setLayout(new BoxLayout(theBigPanel, BoxLayout.Y_AXIS));
        theBigPanel.add(midPanel);
        theBigPanel.add(searchPanel);

        return theBigPanel;

    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchByAirline) {

            String query = airlineSearch.getText().toUpperCase();
            ArrayList<String> list1 = new ArrayList<String>();

            alResults.setText("");

            // If the user enter the 2-char string like the airline id, if the map contains the id, proceed.
            // else will be shown invalid airline id
            if (query.length() == 2) {
                if (routesMap.containsKey(query)) {
                    //Retrieve the list of values that correspond to the key of the airline
                    Collection<String> list = routesMap.get(query);
                    ArrayList al = new ArrayList(list);
                    alResults.append(hashOfAirlines.get(query) + " airlines has " + al.size() + " trips...\n");

                    // Looping through the arraylist of destinations and adding the values to
                    // another arraylist to be sorted
                    for (int i = 0; i < al.size(); i++) {
                        String f = (String) al.get(i);
                        String[] parts = f.split("\\s+");
                        String from = parts[0];
                        String to = parts[1];

                        String source = hashOfAirports.get(from);
                        String dests = hashOfAirports.get(to);
                        String full = source + "\t" + dests;

                        list1.add(full);
                    }

                    // sorting is taking place then after which, the program will output the
                    // the sorted result to the user in ascending base on the source
                    Collections.sort(list1);
                    for (int i = 0; i < list1.size(); i++) {
                        String flights = (String) list1.get(i);
                        String[] parts = flights.split("\\t");
                        String from = parts[0];
                        String to = parts[1];

                        alResults.append("\nFrom " + from + " \nto " + to + "\n");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid airline ID eg. AK, EK, etc");
                }
                airlineSearch.setText("");
            }
        }

        if (e.getSource() == searchByAirport) {

            apResults.setText("");
            ArrayList<String> noAirlines = new ArrayList<String>();
            String search = airportSearch.getText();
            String searchUpperCase = search.toUpperCase();
            ArrayList<String> sortedAirportKeys = new ArrayList<String>();

            //Retrieve the String of airport id which have the above substring
            String airportID = ap.returnAirportID(search);
            String[] airportIDParts = airportID.split("\\s+");

            //Retrieve the airport full name and the airlines operating at the airport as a hashmap
            HashMap<String, ArrayList<String>> airlineIDs = new HashMap<String, ArrayList<String>>();
            try {
                airlineIDs = f.airportToAirlines(airportID);
            } catch (Exception ex) {
                System.out.println(ex);
            }

            //Add the keys of the above hashmap (airlineIDs) into an arraylist for sorting in ascending order
            for (Map.Entry<String, ArrayList<String>> entry3 : airlineIDs.entrySet()) {
                sortedAirportKeys.add(entry3.getKey());
            }
            Collections.sort(sortedAirportKeys);

            // iterate through both the sortedairportkeys and the airlineIDs hasmap to be printed out
            for (int x = 0; x < sortedAirportKeys.size(); x++) {
                String key = sortedAirportKeys.get(x);
                ArrayList<String> airlineID = airlineIDs.get(key);
                Collections.sort(airlineID);

                if (!airlineID.isEmpty()) {
                    apResults.append("\nThe airlines operating at " + key + " is/are: \n");
                    for (int i = 0; i < airlineID.size(); i++) {
                        apResults.append((i + 1) + ". " + hashOfAirlines.get(airlineID.get(i)) + " airlines \n");
                    }
                } else {
                    noAirlines.add(key);
                }
            }

            //Sorting of the arrayliist of the airports which have no airlines operating there and printing
            //out the list of airports
            Collections.sort(noAirlines);
            if (!airportID.isEmpty()) {
                apResults.append("\nThere is no airlines operating in the following airports:\n");
                for (int a = 0; a < noAirlines.size(); a++) {
                    apResults.append((a + 1) + ". " + noAirlines.get(a) + "\n");
                }
            } // This 'else' statement is to show the user that the string they enter is not inside the database
              // Since program search using the airlineID
            else {
                apResults.append("\nThere is no airlines operating in the following airports:\n" + search + "\n");
            }

            //This section is to cater to search by airport ID in case the user key in substring like sin, syd
            //which are substring of both singapore and sydney and the airport iD 
            //If the array: airportIDParts do not contain the "searched" query, then in will print out the result
            
            if (!Arrays.asList(airportIDParts).contains(searchUpperCase)) {
                if (hashOfAirports.containsKey(searchUpperCase)) {
                    String alID = f.airportToAirlinesBYID(searchUpperCase);
                    String[] airlineID = alID.split("\\s+");

                    if (!alID.isEmpty()) {
                        apResults.append("\nThe airlines operating at " + hashOfAirports.get(searchUpperCase) + " is/are: \n");
                        for (int i = 0; i < airlineID.length; i++) {
                            apResults.append((i + 1) + ". " + hashOfAirlines.get(airlineID[i]) + " airlines \n");
                        }
                    } else {
                        apResults.append("\nThere is no airlines operating in" + hashOfAirports.get(searchUpperCase) + "\n");
                    }
                }
            } // The else statement is to show the user that his query base on the airport id has been shown in the list above
            else {
                apResults.append("\nResult for airport " + hashOfAirports.get(searchUpperCase) + " can be found above\n");
            }

            airportSearch.setText("");
        }

        if (e.getSource() == boardNDest) {

            apResults.setText("");
            String from = fromSearch.getText().toUpperCase();
            String to = toSearch.getText().toUpperCase();
            String result = " ";

            // Retrieve the results of the transfer from the "from" to the "to" as specified by the user
            try {
                result = f.toAndFro(from, to);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            // Split the transfer + direct results by different line
            String[] query = result.split("\\r?\\n");
            int a = 0;
            int b = 0;

            // Check first if the query specified by the user is inside the list of airports. If yes, then check if result (AKA, routes)
            // is empty or not. If empty, inform the user that there's no flight from source to destination.
            // If not empty, display both the direct flight and direct if needed
            
            if (hashOfAirports.containsKey(from) && hashOfAirports.containsKey(to)) {
                if (!result.isEmpty()) {
                    apResults.append("\nInformation for flight from " + hashOfAirports.get(from) + " to " + hashOfAirports.get(to) + "\n");
                    for (int i = 0; i < query.length; i++) {
                        if (query[i].length() == 2) {
                            apResults.append("Direct flight " + (a + 1) + ": " + hashOfAirlines.get(query[i]) + "\n");
                            a++;
                        } else {
                            String[] output = query[i].split("\\s+");

                            if (!output[0].equals(output[1])) {
                                apResults.append("\nTransfer Info No. " + (b + 1) + ".  " + hashOfAirports.get(output[2]) + " --> " + hashOfAirports.get(output[3]) + " --> " + hashOfAirports.get(output[output.length - 1]) + "\n");
                                apResults.append("Airlines :  " + hashOfAirlines.get(output[0]) + " airlines CHANGE TO " + hashOfAirlines.get(output[1]) + " airlines at" + hashOfAirports.get(output[3]) + "\n");
                                b++;
                            }
                        }
                    }
                } else {
                    apResults.append("\nThere is no flight to go from" + hashOfAirports.get(from) + " to " + hashOfAirports.get(to) + "  \n");
                }
            } else {
                apResults.append("You have entered either one or two invalid airport ID\n");
            }

        }

        if (e.getSource() == searchByStopovers) {

            apResults.setText("");
            String dropdownAirlineId = "";
            String from = fromSearch.getText().toUpperCase();
            String to = toSearch.getText().toUpperCase();

            //Retrieve the airline ID base on the selected dropdown menu selection (Use only for stopovers)
            for (Map.Entry<String, String> al : hashOfAirlines.entrySet()) {
                String key = al.getKey();
                String value = al.getValue();

                if (chooseAirline.getSelectedItem().equals(value)) {
                    dropdownAirlineId = key;
                }
            }

            try {
                f.stopover(dropdownAirlineId, from, to);
            } catch (Exception ex) {
                System.out.println(ex);
            }

            String oneStop = f.returnOneStop();
            String twoStop = f.returnTwoStop();
            String threeStop = f.returnThreeStop();
            String fourStop = f.returnFourStop();

            if (hashOfAirports.containsKey(from) && hashOfAirports.containsKey(to)) {
                apResults.append("Displaying Information for stopovers from "+hashOfAirports.get(from)+
                        " to "+hashOfAirports.get(to)+"\nAirline: " + chooseAirline.getSelectedItem() + " airlines\n\n");

                String[] oneStopParts = oneStop.split("\\r?\\n");
                String[] twoStopParts = twoStop.split("\\r?\\n");
                String[] threeStopParts = threeStop.split("\\r?\\n");
                String[] fourStopParts = fourStop.split("\\r?\\n");

                if (!oneStop.isEmpty()) {
                    apResults.append("Displaying Information for 1 stopover \n\n");
                    for (int x = 0; x < oneStopParts.length; x++) {
                        String[] smallerOneStopParts = oneStopParts[x].split("\\s+");
                        for (int y = 0; y < smallerOneStopParts.length; y++) {
                            if(y != smallerOneStopParts.length-1){
                                if(!(smallerOneStopParts[y].equals(smallerOneStopParts[y+1]))){
                                    apResults.append(hashOfAirports.get(smallerOneStopParts[y])+" --> ");
                                }
                            } else{
                                apResults.append(hashOfAirports.get(smallerOneStopParts[y])+"\n");
                            }
                        }
                    }

                }

                if (!twoStop.isEmpty()) {
                    apResults.append("\nDisplaying Information for 2 stopovers \n\n");
                    for (int x = 0; x < twoStopParts.length; x++) {
                        String[] smallerTwoStopParts = twoStopParts[x].split("\\s+");
                        for (int y = 0; y < smallerTwoStopParts.length; y++) {
                            if(y != smallerTwoStopParts.length-1){
                                if(!(smallerTwoStopParts[y].equals(smallerTwoStopParts[y+1]))){
                                    apResults.append(hashOfAirports.get(smallerTwoStopParts[y])+" --> ");
                                }
                            } else{
                                apResults.append(hashOfAirports.get(smallerTwoStopParts[y])+"\n");
                            }
                        }
                    }

                }

                if (!threeStop.isEmpty()) {
                    apResults.append("\nDisplaying Information for 3 stopovers \n\n");
                    for (int x = 0; x < threeStopParts.length; x++) {
                        String[] smallerThreeStopParts = threeStopParts[x].split("\\s+");
                        for (int y = 0; y < smallerThreeStopParts.length; y++) {
                            if(y != smallerThreeStopParts.length-1){
                                if(!(smallerThreeStopParts[y].equals(smallerThreeStopParts[y+1]))){
                                    apResults.append(hashOfAirports.get(smallerThreeStopParts[y])+" --> ");
                                }
                            } else{
                                apResults.append(hashOfAirports.get(smallerThreeStopParts[y])+"\n");
                            }
                        }
                    }

                }

                if (!fourStop.isEmpty()) {
                    apResults.append("\nDisplaying Information for 4 stopovers \n\n");
                    for (int z = 0; z < fourStopParts.length; z++) {
                        String[] smallerFourStopParts = fourStopParts[z].split("\\s+");
                        for (int c = 0; c < smallerFourStopParts.length; c++) {
                            if(c != smallerFourStopParts.length-1){
                                if(!(smallerFourStopParts[c].equals(smallerFourStopParts[c+1]))){
                                    apResults.append(hashOfAirports.get(smallerFourStopParts[c])+" --> ");
                                }
                            } else{
                                apResults.append(hashOfAirports.get(smallerFourStopParts[c])+"\n");
                            }
                        }
                    }

                }

                if(oneStop.isEmpty() && twoStop.isEmpty() && threeStop.isEmpty() && fourStop.isEmpty()){
                    apResults.append("There is no possible stopovers from " + hashOfAirports.get(from.toUpperCase())+
                            " and "+ hashOfAirports.get(to.toUpperCase())+" using the flight "+ chooseAirline.getSelectedItem()+" airlines\n");
                }
            } else{
                apResults.append("You have entered either one or two invalid airport ID\n");
            }
        }

    }
}
