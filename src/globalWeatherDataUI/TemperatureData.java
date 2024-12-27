package globalWeatherDataUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TemperatureData {
    private String dataFile;
    
    private SortedSet<String> dataCountries;
    
    //observer list
    private List<DataObserver> dataObservers;
    // Country -> State -> Year -> List of TemperatureRecords
    private Map<String, TreeMap<String, TreeMap<Integer, List<TemperatureRecord>>>> organizedData;
    //For extrema visualization
	private TreeMap<Integer,Double> plotMonthlyMaxValue = null;
	private TreeMap<Integer,Double> plotMonthlyMinValue = null;
    // Plot data is a map where the key is the Month, and the value is a sorted map where the key is the year.
    private Map<Integer,SortedMap<Integer,Double>> plotData;
   
    //US will be the default for selectedCountry
    private String selectedCountry = "United States";
    private String selectedState;
    private Integer selectedStartYear;
    private Integer selectedEndYear;
    private String selectedVisualization;

    public TemperatureData(String dataFile) {
        this.dataFile = dataFile;
        this.dataObservers = new ArrayList<DataObserver>();
        this.dataCountries = new TreeSet<String>();
        this.organizedData = new HashMap<>();
        this.plotData = new HashMap<>();
        loadData();
        //update the initial plotData when menu loads
        updatePlotData("");
    }

    private void loadData() {
        DataParser parser = new DataParser();
        try (Scanner scanner = new Scanner(new File(dataFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                TemperatureRecord record = parser.getRecordFromLine(line);
                if (record != null) {
                    organizedData.computeIfAbsent(record.getCountry(), k -> new TreeMap<>())
                                 .computeIfAbsent(record.getState(), k -> new TreeMap<>())
                                 .computeIfAbsent(record.getYear(), k -> new ArrayList<>())
                                 .add(record);
                    dataCountries.add(record.getCountry());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        
        //populate the main menu items with correct selections after we load the data
        setDefaultSelections();
    }

    public void setDefaultSelections() {
        selectedState = organizedData.get(selectedCountry).firstKey();
        selectedStartYear = organizedData.get(selectedCountry).get(selectedState).firstKey();
        selectedEndYear = organizedData.get(selectedCountry).get(selectedState).lastKey();
        selectedVisualization = "Raw";
    }



    
    public void updatePlotData(String updateType) {
        plotData.clear(); // Clear any existing data in plotData
        plotMonthlyMaxValue = new TreeMap<Integer, Double>();
        plotMonthlyMinValue = new TreeMap<Integer, Double>();

     // Initialize min and max temperature maps with correct extreme values
        for (int i = 1; i <= 12; i++) {
            plotMonthlyMinValue.put(i, Double.MAX_VALUE); 
            plotMonthlyMaxValue.put(i, Double.MIN_VALUE); 
        }


        // Access the data for the selected country and state
        TreeMap<String, TreeMap<Integer, List<TemperatureRecord>>> stateData = organizedData.get(selectedCountry);
        if (stateData != null) {
            TreeMap<Integer, List<TemperatureRecord>> yearData = stateData.get(selectedState);
            if (yearData != null) {
                // Iterate through each year within the user-selected range
                for (Map.Entry<Integer, List<TemperatureRecord>> entry : yearData.entrySet()) {
                    Integer year = entry.getKey();
                    if (year >= selectedStartYear && year <= selectedEndYear) {
                        List<TemperatureRecord> records = entry.getValue();
                        // Iterate through each record to process monthly data
                        for (TemperatureRecord record : records) {
                            int month = record.getMonth();
                            double temperature = record.getTemperature();

                            // Update monthly minimum and maximum temperatures
                            plotMonthlyMinValue.put(month, Math.min(plotMonthlyMinValue.get(month), temperature));
                            plotMonthlyMaxValue.put(month, Math.max(plotMonthlyMaxValue.get(month), temperature));

                            // Update plotData with the temperature for this record
                            plotData.computeIfAbsent(month, k -> new TreeMap<>()).put(year, temperature);
                        }
                    }
                }
            }
        }
        //update the observers after a change to the data
        updateObservers(updateType);
    }
    
    
    //observer pattern methods
    public void attatchObserver(DataObserver dO) {
    	dataObservers.add(dO);
    }
    
    public void detatchObserver(DataObserver dO) {
    	dataObservers.remove(dO);
    }
    
    public void updateObservers(String updateType) {
    	for (DataObserver observer : dataObservers) {
    		observer.update(updateType);
    	}
    }

    // Getters for accessing the organized and filtered data
    public Map<String, TreeMap<String, TreeMap<Integer, List<TemperatureRecord>>>> getOrganizedData() {
    	return organizedData; 
    }
    
    
    //Getters for the plot related maps
    public Map<Integer, SortedMap<Integer, Double>> getPlotData() { 
    	return plotData; 
    }
    public TreeMap<Integer, Double> getPlotMonthlyMaxValue(){
    	return this.plotMonthlyMaxValue;
    }
    public TreeMap<Integer, Double> getPlotMonthlyMinValue(){
    	return this.plotMonthlyMinValue;
    }
    
    
    //getter for the dataCountries set
    public SortedSet<String> getDataCountries(){
    	return dataCountries;
    }
    
    //getters for the sets that contain relevant information according to the selected country
    public SortedSet<String> getDataStates(){
    	//return set representation of the keyset of the second layer of the data structure
    	return new TreeSet<>(organizedData.get(selectedCountry).keySet());
    }
    
    public SortedSet<Integer> getDataYears(){
    	return new TreeSet<Integer>(organizedData.get(selectedCountry).get(selectedState).keySet());
    }
    
    
    // Setters for updating user selections.
    public void setSelectedCountry(String selectedCountry) { this.selectedCountry = selectedCountry; }
    public void setSelectedState(String selectedState) { this.selectedState = selectedState; }
    public void setSelectedStartYear(Integer selectedStartYear) { this.selectedStartYear = selectedStartYear; }
    public void setSelectedEndYear(Integer selectedEndYear) { this.selectedEndYear = selectedEndYear; }
    public void setSelectedVisualization(String selectedVisualization) {
        this.selectedVisualization = selectedVisualization;
        notifyVisualizationChange();
        updateObservers("UserChange");
    }

    private void notifyVisualizationChange() {
        VisualizationStrategy newStrategy = selectedVisualization.equals("Raw") ? new RawStrategy() : new ExtremaStrategy();
        for (DataObserver observer : dataObservers) {
            observer.updateVisualizationStrategy(newStrategy);
        }
    }

    
    // Getters for retrieving current user selections.
    public String getSelectedCountry() { return this.selectedCountry; }
    public String getSelectedState() { return this.selectedState; }
    public int getSelectedStartYear() { return this.selectedStartYear; }
    public int getSelectedEndYear() { return this.selectedEndYear; }
    public String getSelectedVisualization() { return this.selectedVisualization; }
}