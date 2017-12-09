/*
 * Copyright (c) 2017 by Tobias Koch
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import org.wahlzeit.utils.asserts.ObjectAssert;

/**
 * Class representing a location.
 * @author Tobias Koch
 */
public class Location {
    /**
     * Coordinate
     */
    protected Coordinate coordinate = null;

    /**
     * Constructor
     * @param coord    Coordinate
     */
    public Location(Coordinate coord) {
        ObjectAssert.assertNotNull(coord, "Coordinate is null!");
        this.coordinate = coord;
    }
}
