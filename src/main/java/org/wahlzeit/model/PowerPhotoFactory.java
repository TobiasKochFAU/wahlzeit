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

import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.utils.DesignPattern;
import org.wahlzeit.utils.asserts.ObjectAssert;

import java.util.logging.Logger;

/**
 * A Factory for creating PowerPhotos and related objects.
 */
@DesignPattern(
        name = "Abstract Factory",
        participants = {"ConcreteFactory"}
)
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
    @Override
    public PowerPhoto createPhoto() {
        return new PowerPhoto();
    }

    /**
     * Creates a new power photo with the specified id.
     * If the id is null the photo will get an unused id by itself.
     */
    @Override
    public PowerPhoto createPhoto(PhotoId id) {
        PowerPhoto photo;
        try {
            ObjectAssert.assertNotNull(id, "PhotoId is null!");
            photo = new PowerPhoto(id);
        }
        catch(IllegalArgumentException exc) {
            photo = this.createPhoto();
        }
        return photo;
    }
}
