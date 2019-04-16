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

// Created on November 22, 2005, 5:58 PM

import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Roi;
import ij.gui.Toolbar;
import ij.measure.Calibration;
import ij.process.ImageProcessor;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ListIterator;
import java.util.Vector;

/**
 * TODO
 *
 * @author Kurt De Vos
 */
public class PlantCntrImageCanvas extends ImageCanvas {

	private Vector<PlantCntrMarkerVector> typeVector;
	private PlantCntrMarkerVector currentMarkerVector;
	private PlantCntrNames cntrNames;
	private final PlantCounter cc;
	private final ImagePlus img;
	private boolean delmode = false;
	private boolean showNumbers = true;
	private boolean showAll = false;
	private final Font font = new Font("SansSerif", Font.PLAIN, 10);

	/** Creates a new instance of PlantCntrImageCanvas */
	public PlantCntrImageCanvas(final ImagePlus img,
		final Vector<PlantCntrMarkerVector> typeVector, final PlantCounter cc,
		final Vector<Roi> displayList,
		final PlantCntrNames cntrNames)
	{
		super(img);
		this.img = img;
		this.typeVector = typeVector;
		this.cc = cc;
		this.cntrNames = cntrNames;
		if (displayList != null) this.setDisplayList(displayList);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		super.mousePressed(e);
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (currentMarkerVector == null) {
				IJ.error("Select a counter type first!");
				return;
			}
			
			Rectangle r = img.getRoi().getBounds();
			if (!delmode) {
				final PlantCntrMarker m = new PlantCntrMarker(r, img.getCurrentSlice());
				currentMarkerVector.addMarker(m);
			}
			else {
				final int x = (int) Math.round(r.getX() + r.getWidth()/2);
				final int y = (int) Math.round(r.getY() + r.getHeight()/2);
				final PlantCntrMarker m =
					currentMarkerVector.getMarkerFromPosition(new Point(x, y), img
						.getCurrentSlice());
				currentMarkerVector.remove(m);
			}
			repaint();
			cc.populateTxtFields();
		}
		else {
			super.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		super.mouseMoved(e);
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		super.mouseExited(e);
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		super.mouseEntered(e);
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		super.mouseDragged(e);
		}

	@Override
	public void mouseClicked(final MouseEvent e) {
		super.mouseClicked(e);
	}

	private Rectangle srcRect = new Rectangle(0, 0, 0, 0);

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		srcRect = getSrcRect();
		double xM = 0;
		double yM = 0;

		final Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1f));
		g2.setFont(font);

		final ListIterator<PlantCntrMarkerVector> it = typeVector.listIterator();
		while (it.hasNext()) {
			final PlantCntrMarkerVector mv = it.next();
			final int typeID = mv.getType();
			g2.setColor(mv.getColor());
			final ListIterator<PlantCntrMarker> mit = mv.listIterator();
			while (mit.hasNext()) {
				final PlantCntrMarker m = mit.next();
				final boolean sameSlice = m.getZ() == img.getCurrentSlice();
				if (sameSlice || showAll) {
					xM = ((m.getX() - srcRect.x) * magnification);
					yM = ((m.getY() - srcRect.y) * magnification);
					if (sameSlice) g2.fillOval((int) xM - 2, (int) yM - 2, 4, 4);
					else g2.drawOval((int) xM - 2, (int) yM - 2, 4, 4);
					if (showNumbers) g2.drawString(Integer.toString(typeID),
						(int) xM + 3, (int) yM - 3);
				}
			}
		}
	}

	public void removeLastMarker() {
		currentMarkerVector.removeLastMarker();
		repaint();
		cc.populateTxtFields();
	}

	public ImagePlus imageWithMarkers() {
		final Image image = this.createImage(img.getWidth(), img.getHeight());
		final Graphics gr = image.getGraphics();

		double xM = 0;
		double yM = 0;

		try {
			if (imageUpdated) {
				imageUpdated = false;
				img.updateImage();
			}
			final Image image2 = img.getImage();
			gr.drawImage(image2, 0, 0, img.getWidth(), img.getHeight(), null);
		}
		catch (final OutOfMemoryError e) {
			IJ.outOfMemory("Paint " + e.getMessage());
		}

		final Graphics2D g2r = (Graphics2D) gr;
		g2r.setStroke(new BasicStroke(1f));

		final ListIterator<PlantCntrMarkerVector> it = typeVector.listIterator();
		while (it.hasNext()) {
			final PlantCntrMarkerVector mv = it.next();
			final int typeID = mv.getType();
			g2r.setColor(mv.getColor());
			final ListIterator<PlantCntrMarker> mit = mv.listIterator();
			while (mit.hasNext()) {
				final PlantCntrMarker m = mit.next();
				if (m.getZ() == img.getCurrentSlice()) {
					xM = m.getX();
					yM = m.getY();
					g2r.fillOval((int) xM - 2, (int) yM - 2, 4, 4);
					if (showNumbers) g2r.drawString(Integer.toString(typeID),
						(int) xM + 3, (int) yM - 3);
				}
			}
		}

		@SuppressWarnings("unchecked")
		final Vector<Roi> displayList = getDisplayList();
		if (displayList != null && displayList.size() == 1) {
			final Roi roi = displayList.elementAt(0);
			if (roi.getType() == Roi.COMPOSITE) roi.draw(gr);
		}

		return new ImagePlus("Markers_" + img.getTitle(), image);
	}

	public void measure() { 
		Calibration cal = img.getCalibration();
		String unit = cal.getUnit();
		String columnHeadings = new String();
		String resultsRow = new String();
		
		int imgW = img.getWidth();
		int imgH = img.getHeight();
		
		if (unit.equals("pixel"))  columnHeadings = String.format("File\tImageW(px)\tImageH(px)\tType\tSlice\tcenterX(px)\tcenterY(px)\tboxX(px)\tboxY(px)\tboxW(px)\tboxH(px)");
		else columnHeadings = String.format("File\tImageW(px)\tImageH(px)\tType\tSlice\tcenterX(px)\tcenterY(px)\tboxX(px)\tboxY(px)\tboxW(px)\tboxH(px)\tcenterX(%s)\tcenterY(%s)\tboxX(%s)\tboxY(%s)\tboxW(%s)\tboxH(%s)", unit, unit, unit, unit, unit, unit);
		
		IJ.setColumnHeadings(columnHeadings);
		
		final String filename = img.getTitle().substring(17);
				
		for (int i = 1; i <= img.getStackSize(); i++) {
			img.setSlice(i);
			final ImageProcessor ip = img.getProcessor();
			
			final ListIterator<PlantCntrMarkerVector> it = typeVector.listIterator();
			while (it.hasNext()) {
				final PlantCntrMarkerVector mv = it.next();
				final String typeID = cntrNames.get(mv.getType());
				final ListIterator<PlantCntrMarker> mit = mv.listIterator();
				while (mit.hasNext()) {
					final PlantCntrMarker m = mit.next();
					if (m.getZ() == i) {
						final int zM = m.getZ();
						
						final int xMcenter = m.getX();
						final int yMcenter = m.getY();
						final int xMbox = m.getBoxX();
						final int yMbox = m.getBoxY();
						final int wMbox = m.getBoxW();
						final int hMbox = m.getBoxH();
					
						final double xMcenterCal = xMcenter * cal.pixelWidth;
						final double yMcenterCal = yMcenter * cal.pixelHeight;
						final double xMboxCal = xMbox * cal.pixelWidth;
						final double yMboxCal = yMbox * cal.pixelWidth;
						final double wMboxCal = wMbox * cal.pixelWidth;
						final double hMboxCal = hMbox * cal.pixelWidth;
						
						if (unit.equals("pixel")) resultsRow = String.format("%s\t%d\t%d\t%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d", filename, imgW, imgH, typeID, zM, xMcenter, yMcenter, xMbox, yMbox, wMbox, hMbox);
						else resultsRow = String.format("%s\t%d\t%d\t%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f", filename, imgW, imgH, typeID, zM, xMcenter, yMcenter, xMbox, yMbox, wMbox, hMbox, xMcenterCal, yMcenterCal, xMboxCal, yMboxCal, wMboxCal, hMboxCal);
						IJ.write(resultsRow);
						
					}
				}
			}
		}
	}

	public Vector<PlantCntrMarkerVector> getTypeVector() {
		return typeVector;
	}

	public void setTypeVector(final Vector<PlantCntrMarkerVector> typeVector) {
		this.typeVector = typeVector;
	}

	public PlantCntrMarkerVector getCurrentMarkerVector() {
		return currentMarkerVector;
	}

	public void setCurrentMarkerVector(
		final PlantCntrMarkerVector currentMarkerVector)
	{
		this.currentMarkerVector = currentMarkerVector;
	}

	public boolean isDelmode() {
		return delmode;
	}

	public void setDelmode(final boolean delmode) {
		this.delmode = delmode;
	}

	public boolean isShowNumbers() {
		return showNumbers;
	}

	public void setShowNumbers(final boolean showNumbers) {
		this.showNumbers = showNumbers;
	}

	public void setShowAll(final boolean showAll) {
		this.showAll = showAll;
	}

}
