/*
 * #%L
 * Cell Counter plugin for ImageJ.
 * %%
 * Copyright (C) 2007 - 2015 Kurt De Vos and Board of Regents of the
 * University of Wisconsin-Madison.
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

/**
 * TODO
 *
 * @author Kurt De Vos
 */
public class CellCntrMarker {
    private int x;
    private int y;
    private int z;
    
    /** Creates a new instance of Marker */
    public CellCntrMarker() {
    }
    
    public CellCntrMarker(int x, int y, int z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

}
