package globalWeatherDataUI;
 
public class Main {
	
    public static void main(String[] args) {
        TemperatureData tD = new TemperatureData("data/GlobalLandTemperaturesByState.csv");
        
        //demonstration of observer design pattern with two UIs. When a user selection is changed on one, it is changed on both
        DataViewerAppUI gui = new DataViewerAppUI(tD);
        DataViewerAppUI gui2 = new DataViewerAppUI(tD);
    }
}

