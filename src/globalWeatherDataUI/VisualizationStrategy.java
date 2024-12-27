package globalWeatherDataUI;

import java.awt.Color;

import edu.du.dudraw.Draw;

public interface VisualizationStrategy {
	
	//constants shared among the classes
    public static final double DATA_WINDOW_BORDER = 50.0;
	public final static double 	EXTREMA_PCT = 0.1;
    public final static double		MENU_STARTING_X = 40.0;
	public final static double 	MENU_STARTING_Y = 90.0;
	public final static double 	MENU_ITEM_SPACING = 5.0;
	public final static String[] 	MONTH_NAMES = { "", // 1-based
			"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    public static final double TEMPERATURE_MIN_C = -10.0;
    public static final double TEMPERATURE_MAX_C = 30.0;
	public final static double	TEMPERATURE_RANGE = TEMPERATURE_MAX_C - TEMPERATURE_MIN_C;
    public static final int WINDOW_WIDTH = 1320;
    public static final int WINDOW_HEIGHT = 720;
    
    //method that will differ between classes
    public void visualizeData(TemperatureData temperatureData, Draw m_window);
    
    //implement default getDataColor method that both strategies share
    public default Color getDataColor(Double value, boolean doGrayscale) {
    	if(null == value) {
    		return null;
    	}
    	double pct = (value - TEMPERATURE_MIN_C) / TEMPERATURE_RANGE;
    	Logger.trace("converted %f raw value to %f %%", value, pct);
    
    	if (pct > 1.0) {
            pct = 1.0;
        }
        else if (pct < 0.0) {
            pct = 0.0;
        }
        int r, g, b;
        // Replace the color scheme with my own
        if (!doGrayscale) {
        	r = (int)(255.0 * pct);
        	g = 0;
        	b = (int)(255.0 * (1.0-pct));
        	
        } else {
        	// Grayscale for the middle extema
        	r = g = b = (int)(255.0 * pct);
        }
        
        Logger.trace("converting %f to [%d, %d, %d]", value, r, g, b);

		return new Color(r, g, b);
	}
    
}
