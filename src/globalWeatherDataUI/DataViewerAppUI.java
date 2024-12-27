
package globalWeatherDataUI;

import java.awt.Color;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

public class DataViewerAppUI extends DataObserver implements DrawListener {
	
	//Instance variables
    private TemperatureData temperatureData;
    private Draw m_window;
    
    //design pattern variables
    private VisualizationStrategy visualizationStrategy = new RawStrategy(); //default value is raw
    private AppState appState = new MainState(this);
    
    // Constants
    private static final double DATA_WINDOW_BORDER = 50.0;
	private final static double 	EXTREMA_PCT = 0.1;
    private final static double		MENU_STARTING_X = 40.0;
	private final static double 	MENU_STARTING_Y = 90.0;
	private final static double 	MENU_ITEM_SPACING = 5.0;
	private final static String[] 	MONTH_NAMES = { "", // 1-based
			"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    private static final double TEMPERATURE_MIN_C = -10.0;
    private static final double TEMPERATURE_MAX_C = 30.0;
	private final static double	TEMPERATURE_RANGE = TEMPERATURE_MAX_C - TEMPERATURE_MIN_C;
    private static final int WINDOW_WIDTH = 1320;
    private static final int WINDOW_HEIGHT = 720;


    

        

        
    public DataViewerAppUI(TemperatureData temperatureData) {
        this.temperatureData = temperatureData;
        m_window = new Draw("DataViewer Application");
        m_window.setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        m_window.addListener(this);
        m_window.enableDoubleBuffering(); // Too slow otherwise -- need to use .show() later
        //add self to the observer list when created
        this.temperatureData.attatchObserver(this);
        
        //draw main menu
        appState.mainMenu();
    }

    public void drawData() {
    	visualizationStrategy.visualizeData(temperatureData, m_window);
    }
    
    public void drawMainMenu() {
    	m_window.clear(Color.WHITE);

    	String[] menuItems = {
    			"Type the menu number to select that option:",
    			"",
    			String.format("C     Set country: [%s]", temperatureData.getSelectedCountry()),
    			String.format("T     Set state: [%s]", temperatureData.getSelectedState()),
    			String.format("S     Set start year [%d]", temperatureData.getSelectedStartYear()),
    			String.format("E     Set end year [%d]", temperatureData.getSelectedEndYear()),
    			String.format("V     Set visualization [%s]", temperatureData.getSelectedVisualization()),
    			String.format("P     Plot data"),
    			String.format("Q     Quit"),
    	};
    	
    	// enable drawing by "percentage" with the menu drawing
        m_window.setXscale(0, 100);
		m_window.setYscale(0, 100);
		
		// draw the menu
    	m_window.setPenColor(Color.BLACK);
		
		drawMenuItems(menuItems);
		
		m_window.show();
    }
   
	private void drawMenuItems(String[] menuItems) {
		double yCoord = MENU_STARTING_Y;
		
		for(int i=0; i<menuItems.length; i++) {
			m_window.textLeft(MENU_STARTING_X, yCoord, menuItems[i]);
			yCoord -= MENU_ITEM_SPACING;
		}
	}

	public void keyPressed(int key) {
        switch (key) {
        	case 'M':
        		appState.mainMenu();
        		break;
        
            case 'P':
            	appState.plot();
                break;
            case 'C':
            	if (appState.isMainMenu()) {
	                // Change country
	                changeCountry();
	                temperatureData.setDefaultSelections();
	                temperatureData.setSelectedVisualization("Raw");
	                //update
	                temperatureData.updatePlotData("UserChange");
            	}
                break;
            case 'T':
            	if (appState.isMainMenu()) {

	                // Change state and update data
	                changeState();
	                temperatureData.updatePlotData("UserChange");
            	}
                break;
            case 'S':
            	if (appState.isMainMenu()) {
	                // Change start year
	                changeStartYear();
	                temperatureData.updatePlotData("UserChange");
            	}
                break;
            case 'E':
            	if (appState.isMainMenu()) {
	                // Change end year
	                changeEndYear();
	                temperatureData.updatePlotData("UserChange");
            	}
                break;
            case 'V':
            	if (appState.isMainMenu()) {
	                // Change visualization
	                changeVisualization();
	                temperatureData.updatePlotData("UserChange");
            	}
                break;
            case 'Q':
                System.exit(0);
                break;
        }
    }

    private void changeCountry() {
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose a Country", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                temperatureData.getDataCountries().toArray(), temperatureData.getSelectedCountry());
        if (selectedValue != null) {
            temperatureData.setSelectedCountry((String) selectedValue);
        }
    }

    private void changeState() {
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose a State", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                temperatureData.getDataStates().toArray(), temperatureData.getSelectedState());
        if (selectedValue != null) {
            temperatureData.setSelectedState((String) selectedValue);
        }
    }

    private void changeStartYear() {
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose the Start Year", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                temperatureData.getDataYears().toArray(), temperatureData.getSelectedStartYear());
        if (selectedValue != null) {
            temperatureData.setSelectedStartYear((Integer) selectedValue);
        }
    }

    private void changeEndYear() {
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose the End Year", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                temperatureData.getDataYears().toArray(), temperatureData.getSelectedEndYear());
        if (selectedValue != null) {
            temperatureData.setSelectedEndYear((Integer) selectedValue);
        }
    }

    private void changeVisualization() {
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose the Visualization Mode", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"Raw", "Extrema"}, temperatureData.getSelectedVisualization());
        if (selectedValue != null) {
            temperatureData.setSelectedVisualization((String) selectedValue);
        }
    }
	@Override
	public void keyReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(char arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//update method for the observer design pattern
	public void update(String updateType) {
		if(updateType == "UserChange") {
			appState.mainMenu();
		}
			
	}
	
	//setter for changing the visualization strategy
	public void updateVisualizationStrategy(VisualizationStrategy strategy) {
	    this.visualizationStrategy = strategy;
	}



	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}