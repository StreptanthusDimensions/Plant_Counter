# Plant Counter

The plant counter is an imageJ plugin.  It enables counting of plant organs and records the bounding box around each structure.  This may be useful for developing machine learning classifiers for plant structure.

This plugin is based on the [Cell Counter](https://github.com/fiji/Cell_Counter) plugin by Kurt De Vos and Curtis Rueden.

### Installation

Latest version is 0.1.3b

[Download](https://github.com/StreptanthusDimensions/Plant_Counter/raw/master/target/Plant_Counter-0.1.3b.jar) the file `Plant_Counter-x.y.jar` from the `target` directory and place it in the plugins folder of Fiji.  Restart Fiji.

(On a mac you can find the plugins folder by right-clicking (Or ctrl-clicking) on the Fiji application in the Finder and choosing `Show Package Contents`)

### Updating from a previous version

Delete any old versions that you have in the Fiji plugins folder.  Then follow the installation instructions above.

### Usage

1. Open an image
2. Start the plugin.  From the menu bar: `Plugins > Analyze > Plant Counter > Plant Counter`
3. Click `Initialize`
4. Select the category
5. Make sure that the rectangle tool is selected from the Fiji toolbar
6. Use the mouse to draw a rectangle around the structure of interest
7. Double-click within your rectangle to record it
8. Back to step 5 for other structures in the same category
9. Back to step 4 for the next category
10. When you are done you can:
    1. Press the "Measure" button to create a spreadsheet of the info.  Cut and paste into an excel spreadsheet.
    2. Press the "Record" button to tally the number of observations of each category.
    2. Press the "Save Markers" to save your results to an xml file.
11. To delete marker:
    1. Choose the category that contains markers you want to delete.
    2. Check `Delete Mode`.
    3. Double click on the image.  The marker closest to where you double click will be deleted.

### Custom Category Labels

* After you open the plugin for the first time, a file `.PlantCounterNames.txt` will be created in your home directory (note: this will be a hidden file on Mac and Linux).
* Edit this file to create custom category labels.  One label per line.
* Restart the plug-in and voila!
* Custom names are saved in the xml

### Known bugs

* When loading and xml marker file, if the file has more categories than the current number of categories present in the plugin, the final categories will not get loaded.
* The workaround is to make sure that the `.PlantCounterNames.txt` file has at least as many categories as are in your xml file.
    
### Problems?

Please create an [issue](https://github.com/StreptanthusDimensions/Plant_Counter/issues) in this github repository.  

### Release notes

* v0.13b: Fix writexml bug
* v0.1.3a: custom category names
* v0.1.2: image dimension (in pixels) is now included in measurements table and xml file
* V0.1.1a: Attempt to fix writexml error that some users are having.   
* V0.1.1: 
    - Fix column heading alignment in measurement table.
    - Provide calibrated measurements as well as pixel measurements in measurement table. 
    * V0.1: Initial release

### Build instructions

If you want to build this yourself:

Clone the repo to your computer.

#### Mac

Make sure that you have java8 and maven installed

    brew cask install java8
    brew install maven

Then use maven to build using your favorite IDE, or, go to the project directory in terminal and type `mvn intall` to build the jar.  You will still need to copy the jar to fiji by hand.
