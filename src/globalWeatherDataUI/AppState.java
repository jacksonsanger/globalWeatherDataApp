package globalWeatherDataUI;

public interface AppState {
	public AppState mainMenu();
	public AppState plot();
	public boolean isMainMenu();
}
