/*
 * #%L
 * Plant Counter plugin for ImageJ.
 * %%
 * Copyright (C) 2007 - 2015 Kurt De Vos and Board of Regents of the
 * University of Wisconsin-Madison.
 * Modified from Cell Counter by Julin Maloof
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

// Created on 23 November 2004, 22:56

import ij.IJ;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ListIterator;
import java.util.Vector;

/**
 * TODO
 *
 * @author Kurt De Vos
 */
public class WritePCXML {

	private OutputStream XMLFileOut;
	private OutputStream XMLBuffOut;
	private OutputStreamWriter out;

	/**
	 * Creates a new instance of ODWritePCXMLODD
	 */
	public WritePCXML(final String XMLFilepath) {
		try {
			XMLFileOut = new FileOutputStream(XMLFilepath); // add FilePath
			XMLBuffOut = new BufferedOutputStream(XMLFileOut);
			out = new OutputStreamWriter(XMLBuffOut, "UTF-8");
		}
		catch (final FileNotFoundException e) {
			System.out.println("File Not Found " + e.getMessage());
		}
		catch (final UnsupportedEncodingException e) {
			System.out.println("This VM does not support the UTF-8 character set. " +
				e.getMessage());
		}
	}

	public boolean writePCXML(final String imgFilename, final int imgW, final int imgH,
		final Vector<PlantCntrMarkerVector> typeVector, final int currentType, final PlantCntrNames cntrNames)
	{
		try {
			out.write("<?xml version=\"1.0\" ");
			out.write("encoding=\"UTF-8\"?>\r\n");
			out.write("<PlantCounter_Marker_File>\r\n");

			// write the image properties
			out.write(" <Image_Properties>\r\n");
			out.write("     <Image_Filename>" + imgFilename + "</Image_Filename>\r\n");
			out.write("     <Image_Width>" + Integer.toString(imgW) + "</Image_Width>\r\n");
			out.write("     <Image_Height>" + Integer.toString(imgH) + "</Image_Height>\r\n");
			out.write(" </Image_Properties>\r\n");

			// write the marker data
			out.write(" <Marker_Data>\r\n");
			out.write("     <Current_Type>" + currentType + "</Current_Type>\r\n");
			final ListIterator<PlantCntrMarkerVector> it = typeVector.listIterator();
			while (it.hasNext()) {
				final PlantCntrMarkerVector markerVector = it.next();
				final int type = markerVector.getType()-1;
				IJ.log("marker type " + Integer.toString(type));
				final String cntrName = cntrNames.get(type);
				out.write("     <Marker_Type>\r\n");
				out.write("         <Type>" + type + "</Type>\r\n");
				out.write("         <Name>" + cntrName + "</Name>\r\n");
				final ListIterator<PlantCntrMarker> lit = markerVector.listIterator();
				while (lit.hasNext()) {
					final PlantCntrMarker marker = lit.next();
					final int z = marker.getZ();
					final int xcenter = marker.getX();
					final int ycenter = marker.getY();
					final int xbox = marker.getBoxX();
					final int ybox = marker.getBoxY();
					final int wbox = marker.getBoxW();
					final int hbox = marker.getBoxH();
					out.write("         <Marker>\r\n");
					out.write("             <MarkerXCenter>" + xcenter + "</MarkerXCenter>\r\n");
					out.write("             <MarkerYCenter>" + ycenter + "</MarkerYCenter>\r\n");
					out.write("             <MarkerXBox>" + xbox + "</MarkerXBox>\r\n");
					out.write("             <MarkerYBox>" + ybox + "</MarkerYBox>\r\n");
					out.write("             <MarkerWBox>" + wbox + "</MarkerWBox>\r\n");
					out.write("             <MarkerHBox>" + hbox + "</MarkerHBox>\r\n");
					out.write("             <MarkerZ>" + z + "</MarkerZ>\r\n");
					out.write("         </Marker>\r\n");
				}
				out.write("     </Marker_Type>\r\n");
			}

			out.write(" </Marker_Data>\r\n");
			out.write("</PlantCounter_Marker_File>\r\n");

			out.flush(); // Don't forget to flush!
			out.close();
			return true;
		}
		catch (final IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}
