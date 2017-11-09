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

import org.wahlzeit.services.LogBuilder;

import java.util.logging.Logger;

/**
 * A Factory for creating PowerPhotos and related objects.
 */
public class PowerPhotoFactory extends PhotoFactory {

    private static final Logger log = Logger.getLogger(PowerPhotoFactory.class.getName());

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    private static PowerPhotoFactory instance = null;

    /**
     * Constructor, does nothing.
     */
    protected PowerPhotoFactory() {
        // do nothing
    }

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    public static void initialize() {
        getInstance(); // drops result due to getInstance() side-effects
    }

    /**
     * Public singleton access method.
     */
    public static synchronized PowerPhotoFactory getInstance() {
        if (instance == null) {
            log.config(LogBuilder.createSystemMessage().addAction("setting generic PowerPhotoFactory").toString());
            setInstance(new PowerPhotoFactory());
        }

        return instance;
    }

    /**
     * Method to set the singleton instance of PowerPhotoFactory.
     */
    protected static synchronized void setInstance(PowerPhotoFactory powerPhotoFactory) {
        if (instance != null) {
            throw new IllegalStateException("attempt to initalize PowerPhotoFactory twice");
        }

        instance = powerPhotoFactory;
    }

    /**
     * @methodtype factory
     */
    public PowerPhoto createPowerPhoto(String name, String type, int yearStartUp, double capacitySinceStart,
                                       int yearCapacity) {
        return new PowerPhoto(name, type, yearStartUp, capacitySinceStart, yearCapacity);
    }

    /**
     * Creates a new power photo with the specified id
     */
    public PowerPhoto createPowerPhoto(PhotoId id, String name, String type, int yearStartUp, double capacitySinceStart,
                                       int yearCapacity) {
        return new PowerPhoto(id, name, type, yearStartUp, capacitySinceStart, yearCapacity);
    }
}
