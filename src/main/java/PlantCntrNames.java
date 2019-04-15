/*
 * #%L
 * Plant Counter plugin for ImageJ.
 * %%
 * Copyright (C) 2019 Julin Maloof and Regents of the
 * University of California.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

/*
This class is created to load category names from a file
*/

import ij.IJ;
import ij.IJ.ExceptionHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class PlantCntrNames {
    private String directory = 	IJ.getDirectory("home");
    private String name = ".PlantCounterNames.csv";
    private File nameFile = new File(directory, name);
    
    /* Create new instance of PlantCntrNames */
    public PlantCntrNames() {
        IJ.showMessage("file path", nameFile.getAbsolutePath());
        try {nameFile.createNewFile();} /*only creates new file if path empty*/
        catch (IOException e) {
            IJ.showMessage("exception", e.getMessage());
        }
    }
}



/*
public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "/Users/mkyong/csv/country.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
*/