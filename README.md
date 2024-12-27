# DataViewer Application

The **DataViewer Application** is a modular and extensible tool for visualizing global temperature data by state and country. It implements advanced software design principles, including the Strategy and State patterns, to enhance maintainability and scalability. The application provides an intuitive graphical interface for exploring temperature trends, supporting multiple visualization modes and interactive user selections.

## Key Features
- **Dynamic Data Visualization**: Displays temperature data by year and month in either raw or extrema-based modes.
- **Interactive User Interface**: Users can filter data by country, state, and date range, and switch between visualization strategies.
- **Modular Design**: Implements the Strategy and State patterns for flexibility in extending visualization modes and application states.
- **Observer Pattern**: Ensures synchronized updates between the data model and the visual interface.
- **Robust Parsing**: Reads and validates temperature records from CSV files, handling various formats gracefully.

## Architecture Overview - **SEE UMLDiagram.drawio.png for clear understanding!!**
The application is built around the principles of object-oriented programming and is divided into distinct components:

### Classes and Responsibilities
- **Main**: Entry point of the application. Initializes the `DataViewerApp` with a specified data file.
- **DataViewerApp**: Manages the overall application lifecycle, delegating responsibilities to appropriate components.
- **TemperatureData**: Centralized data model. Stores parsed records and notifies observers of changes.
- **TemperatureRecord**: Represents a single temperature record, encapsulating fields like year, month, temperature, state, and country.
- **DataParser**: Responsible for reading and parsing CSV data into `TemperatureRecord` objects.
- **VisualizationStrategy Interface**: Abstract interface for implementing different visualization modes.
  - **RawStrategy**: Visualizes raw temperature data.
  - **ExtremaStrategy**: Highlights temperature extremes within 10% of min/max values.
- **AppState Interface**: Abstract interface for implementing application states.
  - **MainState**: Represents the main menu state, where users configure filters and settings.
  - **PlotState**: Represents the data visualization state, where filtered data is displayed.
- **DataObserver**: Interface for observing changes in the `TemperatureData` model.
- **Logger**: Utility for managing debugging, informational, and error messages.

## Technical Skills
- **Programming Languages**: Java
- **Design Patterns**: Strategy, State, Observer
- **Libraries**: DuDraw for graphical interface, Java Collections Framework for data structures
- **File Handling**: CSV parsing with robust error handling
- **Object-Oriented Design**: Modular and extensible architecture with clear separation of concerns

## How to Run
1. Ensure the application is set up in your Java IDE or development environment.
2. Compile and run the `Main` class to launch the application.

## Future Improvements
- **Additional Visualization Modes**: Integrate more strategies for enhanced data analysis.
- **Dynamic Data Sources**: Allow users to upload custom datasets.
- **Performance Optimization**: Improve rendering efficiency for large datasets.
