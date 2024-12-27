package globalWeatherDataUI;

public class PlotState implements AppState{
	
	private DataViewerAppUI dataViewerUI;
	
	public PlotState(DataViewerAppUI dataViewerUI) {
		this.dataViewerUI = dataViewerUI;
	}

	@Override
	public AppState mainMenu() {
		dataViewerUI.drawMainMenu();
		return new MainState(dataViewerUI);
	}

	@Override
	public AppState plot() {
		dataViewerUI.drawData();
		return this;
	}

	@Override
	public boolean isMainMenu() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
