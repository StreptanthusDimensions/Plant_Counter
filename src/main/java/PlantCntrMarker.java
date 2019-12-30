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

// Created on December 13, 2005, 8:41 AM

import java.awt.Rectangle;

/**
 * TODO
 *
 * @author Kurt De Vos
 */
public class PlantCntrMarker {

	private int x; // centroid
	private int y; // centroid
	private int z; // slice
	private Rectangle rect; // bounding rectangle

	/** Creates a new instance of Marker */
	public PlantCntrMarker() {}

	public PlantCntrMarker(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PlantCntrMarker(final Rectangle rect, final int z) {
		this.rect = rect;
		this.x = (int) Math.round(rect.getX() + rect.getWidth()/2);
		this.y = (int) Math.round(rect.getY() + rect.getHeight()/2);
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(final int z) {
		this.z = z;
	}
	
	public int getBoxX() {
		return (int) Math.round(rect.getX());
	}
	
	public int getBoxY() {
		return (int) Math.round(rect.getY());
	}
	
	public int getBoxW() {
		return (int) Math.round(rect.getWidth());
	}
	
	public int getBoxH() {
		return (int) Math.round(rect.getHeight());
	}
	
	public void setRect(final Rectangle rect) {
		this.rect = rect;
	}
	
	public Rectangle getRect() {
		return rect;
	}

}
