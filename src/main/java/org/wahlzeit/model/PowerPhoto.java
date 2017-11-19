/*
 * Copyright (c) 2006-2009 by Tobias Koch
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

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class PowerPhoto extends Photo {

    /**
     * attributes to describe a power plant
     * this could be an extra class with specific subclasses
     * maybe I will do this later
     */
    protected String name = "";
    protected String type = "";
    protected int yearStartUp = 0;
    protected double capacitySinceStart = 0.0d; // in GWh, capacity gained till year yearCapacity
    protected int yearCapacity = 0; // the year until the capacity was measured

    public PowerPhoto() {
        super();
    }

    public PowerPhoto(PhotoId myId) {
        super(myId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYearStartUp() {
        return yearStartUp;
    }

    public void setYearStartUp(int yearStartUp) {
        this.yearStartUp = yearStartUp;
    }

    public double getCapacitySinceStart() {
        return capacitySinceStart;
    }

    /**
     * Capacity has to be set together with related year.
     * @param capacitySinceStart    capacity measured since start until yearCapacity
     * @param yearCapacity      year of measured capacity
     */
    public void setCapacitySinceStart(double capacitySinceStart, int yearCapacity) {
        this.setYearCapacity(yearCapacity);
        this.setCapacitySinceStart(capacitySinceStart);
    }

    /**
     * private as the capacity should not be set without the related year.
     * @param capacitySinceStart    capacity measured since start until yearCapacity
     */
    private void setCapacitySinceStart(double capacitySinceStart) {
        this.capacitySinceStart = capacitySinceStart;
    }

    public int getYearCapacity() {
        return yearCapacity;
    }

    /**
     * private as the year should not be set without the related capacity.
     * Throws exception if yearCapacity is smaller than this.yearStartUp.
     * @param yearCapacity      year of measured capacity
     */
    private void setYearCapacity(int yearCapacity) {
        if (yearCapacity < this.yearStartUp) {
            throw new IllegalArgumentException("year in which the capacity was measured " +
                    "can not be smaller than the plant start up year");
        }
        this.yearCapacity = yearCapacity;
    }
}
