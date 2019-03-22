# Plant Counter

The plant counter is an imageJ plugin.  It enables counting of plant organs and records the bounding box around each structure.  This may be useful for developing machine learning classifiers for plant structure.

This plugin is based on the [Cell Counter](https://github.com/fiji/Cell_Counter) plugin by Kurt De Vos and Curtis Reudin.

### Installation

[Download](https://github.com/StreptanthusDimensions/Plant_Counter/raw/master/target/Plant_Counter-0.1.jar) the file `Plant_Counter-x.y.jar` from the `target` directory and place it in the plugins folder of Fiji.  Restart Fiji.

### Usage

1. Open an image
2. Start the plugin.  From the plugins menu: `Analyze > Plant Counter > Plant Counter`
3. Click `Initialize`
4. Select the category
5. Make sure that the rectangle tools is selected from the Fiji toolbar
6. Use the mouse to draw a rectangle around the structure of interest
7. Double-click within your rectangle to record it.
8. Back to step 5 for other structures in the same category.
9. Back to step 4 for the next category.
10. When you are done you can:
    1. Press the "Measure" button to create a spreadsheet of the info.  Cut and paste into an excel spreadsheet.
    2. Press the "Record" button to tally the number of observations of each category.
    2. Press the "Save Markers" to save your results to an xml file.
    
### Build instructions

Clone the repo to your copmuter.

#### Mac

Make sure that you have java8 and maven instalelled

    brew cask install java8
    brew install maven

Then use maven to build using your favorite IDE, or, from the project directory type `maven intall` to build the jar.  You will still need to copy it to fiji by hand.
