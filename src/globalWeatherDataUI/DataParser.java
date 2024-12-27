package globalWeatherDataUI;
import java.util.Scanner;

public class DataParser {
	
	
	
    /**
     * Utility function to pull a year integer out of a date string.  Supports M/D/Y and Y-M-D formats only.
     * 
     * @param dateString
     * @return
     */
    private Integer parseYear(String dateString) {
    	Integer ret = null;
    	if(dateString.indexOf("/") != -1) {
    		// Assuming something like 1/20/1823
    		String[] parts = dateString.split("/");
    		if(parts.length == 3) {
	    		ret = Integer.parseInt(parts[2]);
    		}
    	}
    	else if(dateString.indexOf("-") != -1) {
    		// Assuming something like 1823-01-20
    		String[] parts = dateString.split("-");
    		if(parts.length == 3) {
    			ret = Integer.parseInt(parts[0]);
    		}
    	}
    	else {
    		throw new RuntimeException(String.format("Unexpected date delimiter: '%s'", dateString));
    	}
    	if(ret == null) {
    		Logger.trace("Unable to parse year from date: '%s'", dateString);
    	}
    	return ret;
    }
    
    private Integer parseMonth(String dateString) {
    	Integer ret = null;
    	if(dateString.indexOf("/") != -1) {
    		// Assuming something like 1/20/1823
    		String[] parts = dateString.split("/");
    		if(parts.length == 3) {
	    		ret = Integer.parseInt(parts[0]);
    		}
    	}
    	else if(dateString.indexOf("-") != -1) {
    		// Assuming something like 1823-01-20
    		String[] parts = dateString.split("-");
    		if(parts.length == 3) {
    			ret = Integer.parseInt(parts[1]);
    		}
    	}
    	else {
    		throw new RuntimeException(String.format("Unexpected date delimiter: '%s'", dateString));
    	}
    	if(ret == null || ret.intValue() < 1 || ret.intValue() > 12) {
    		Logger.trace("Unable to parse month from date: '%s'", dateString);
    		return null;
    	}
    	return ret;
	}
    
    public TemperatureRecord getRecordFromLine(String line) {
        // Assuming a CSV format: date,temperature,temperatureUncertainty,state,country
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");

            String date = rowScanner.next();
            int year = parseYear(date);
            int month = parseMonth(date);

            // Skip this record if there's no temperature value
            if (!rowScanner.hasNextDouble()) {
                //Logger.error("Skipping line due to missing temperature value: " + line);
                return null;
            }
            double temperature = rowScanner.nextDouble();

            // Skip the temperature uncertainty value
            rowScanner.next(); // We assume that the uncertainty value is always followed by the temperature

            // Proceed to state and country, which are expected to be non-numeric values
            if (!rowScanner.hasNext()) {
                //Logger.error("Skipping line due to missing state value: " + line);
                return null;
            }
            String state = rowScanner.next();

            if (!rowScanner.hasNext()) {
                //Logger.error("Skipping line due to missing country value: " + line);
                return null;
            }
            String country = rowScanner.next();

            return new TemperatureRecord(year, month, temperature, state, country);
        } catch (Exception e) {
            //Logger.error("Failed to parse line: " + line + "; Error: " + e.getMessage());
            return null;
        }
    }
}
