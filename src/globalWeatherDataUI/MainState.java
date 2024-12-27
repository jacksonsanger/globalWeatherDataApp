package globalWeatherDataUI;


public class MainState implements AppState{
	
	private DataViewerAppUI dataViewerUI;
	
	public MainState(DataViewerAppUI dataViewerUI) {
		this.dataViewerUI = dataViewerUI;
	}

	@Override
	public AppState mainMenu() {
		dataViewerUI.drawMainMenu();
		return this;
	}

	@Override
	public AppState plot() {
		dataViewerUI.drawData();
		return new PlotState(dataViewerUI);
	}

	@Override
	public boolean isMainMenu() {
		// TODO Auto-generated method stub
		return true;
	}

}
