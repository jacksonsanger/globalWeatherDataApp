package globalWeatherDataUI;

public class TemperatureRecord {
	private int year;
	private int month;
	private double temperature;
	private String state;
	private String country;
	
    public TemperatureRecord(int year, int month, double temperature, String state, String country) {
        this.year = year;
        this.month = month;
        this.temperature = temperature;
        this.state = state;
        this.country = country;
    }
    
    public int getYear() {
    	return this.year;
    }
    
    public int getMonth() {
    	return this.month;
    }
    
    public double getTemperature() {
    	return this.temperature;
    }
    
    public String getState() {
    	return this.state;
    }
    
    public String getCountry() {
    	return this.country;
    }
}

