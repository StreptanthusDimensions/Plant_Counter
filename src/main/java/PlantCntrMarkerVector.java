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

// Created on December 13, 2005, 8:40 AM

import ij.IJ;

import java.awt.Color;
import java.awt.Point;
import java.util.ListIterator;
import java.util.Vector;

import org.scijava.Context;
import org.scijava.options.OptionsService;

/**
 * TODO
 *
 * @author Kurt De Vos
 */
public class PlantCntrMarkerVector extends Vector<PlantCntrMarker> {

	private int type;

	/** Creates a new instance of MarkerVector */
	public PlantCntrMarkerVector(final int type) {
		super();
		this.type = type;
	}

	public void addMarker(final PlantCntrMarker marker) {
		add(marker);
	}

	public PlantCntrMarker getMarker(final int n) {
		return get(n);
	}

	public int getVectorIndex(final PlantCntrMarker marker) {
		return indexOf(marker);
	}

	public void removeMarker(final int n) {
		remove(n);
	}

	public void removeLastMarker() {
		super.removeElementAt(size() - 1);
	}

	public PlantCntrMarker getMarkerFromPosition(final Point p,
		final int sliceIndex)
	{
		final Vector<PlantCntrMarker> v = new Vector<PlantCntrMarker>();
		final ListIterator<PlantCntrMarker> it = this.listIterator();
		while (it.hasNext()) {
			final PlantCntrMarker m = it.next();
			if (m.getZ() == sliceIndex) {
				v.add(m);
			}
		}
		PlantCntrMarker currentsmallest = v.get(0);
		for (int i = 1; i < v.size(); i++) {
			final PlantCntrMarker m2 = v.get(i);
			final Point p1 =
				new Point(currentsmallest.getX(), currentsmallest.getY());
			final Point p2 = new Point(m2.getX(), m2.getY());
			final boolean closer =
				Math.abs(p1.distance(p)) > Math.abs(p2.distance(p));
			if (closer) {
				currentsmallest = m2;
			}
		}

		return currentsmallest;
	}

	public int getType() {
		return type;
	}

	public void setType(final int type) {
		this.type = type;
	}

	public Color getColor() {
		final Context c = (Context) IJ.runPlugIn("org.scijava.Context", "");
		final OptionsService optionsService = c.service(OptionsService.class);
		final PlantCounterOptions options =
			optionsService.getOptions(PlantCounterOptions.class);
		return options.getColor(type);
	}

}
