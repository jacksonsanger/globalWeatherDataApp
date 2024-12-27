package globalWeatherDataUI;

public abstract class DataObserver {
	protected TemperatureData temperatureData;
	
	public abstract void update(String updateType);
	
	public abstract void updateVisualizationStrategy(VisualizationStrategy strategy);
}
