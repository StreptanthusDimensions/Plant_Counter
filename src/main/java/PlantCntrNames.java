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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class PlantCntrNames {
    private String directory = 	IJ.getDirectory("home");
    private String name = ".PlantCounterNames.txt";
    private File nameFile = new File(directory, name);
    private int totalDefaultCats = 8; /* number of default categories to create if no file*/
    private ArrayList<String> cntrNames = new ArrayList<String>();
    
    /* Create new instance of PlantCntrNames */
    public PlantCntrNames() {
    }
    
    public void fill() { //Fill from file or create default names
        if (nameFile.exists()) { // Read names from file
            try {
                String line = null;
                BufferedReader br = new BufferedReader(new FileReader(nameFile));
                while((line = br.readLine()) != null) {
                    if (line.charAt(0) != "#".charAt(0)) {
                        cntrNames.add(line);                        
                    }
                }
                br.close();
            } catch (IOException e) {
                IJ.showMessage("exception", e.getMessage());
            }
            
        } else {
            try {nameFile.createNewFile();} /*only creates new file if path empty*/
            catch (IOException e) {
                IJ.showMessage("exception", e.getMessage());
            }
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(nameFile));
                bw.write("# Custom Category File for Plant Counter");
                bw.newLine();
                bw.write("# One category per line");
                bw.newLine();
                for (int i = 1; i <= totalDefaultCats; i++) {
                    bw.write("Type" + Integer.toString(i));
                    bw.newLine();
                    cntrNames.add("Type" + Integer.toString(i));
                }
                bw.close();
            } catch (IOException e) {
                IJ.showMessage("exception", e.getMessage());
            }
        }

    }
    
    public ArrayList<String> getCntrNames() {
        return cntrNames;
    }
    
    public int getSize() {
        return cntrNames.size();
    }
    
    public String get(int id) {
        return cntrNames.get(id);
    }
    
    public void add(String name) {
        cntrNames.add(name);
    }
    
    public void add(int index, String name) {
        if (index < cntrNames.size()) {
             cntrNames.add(index, name) ;
        } else {
            cntrNames.add(name);
        }
    }
    
    public boolean contains(String name) {
        return cntrNames.contains(name);
    }
    
    public int indexOf(String name) {
        return cntrNames.indexOf(name);
    }
    
    public void clear() { // empty it out
        cntrNames.clear();
    }
}

