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

import com.google.appengine.api.images.Image;

import java.util.logging.Logger;

public class PowerPhotoManager extends PhotoManager {

    protected static final PowerPhotoManager instance = new PowerPhotoManager();

    private static final Logger log = Logger.getLogger(PowerPhotoManager.class.getName());

    public PowerPhotoManager() {
        super();
    }

    /**
     *
     */
    @Override
    public PowerPhoto getPhotoFromId(PhotoId id) {
        return (PowerPhoto) super.getPhotoFromId(id);
    }

    /**
     *
     */
    @Override
    public PowerPhoto getVisiblePhoto(PhotoFilter filter) {
        return (PowerPhoto) super.getVisiblePhoto(filter);
    }

    /**
     *
     */
    @Override
    public PowerPhoto createPhoto(String filename, Image uploadedImage) throws Exception {
        return (PowerPhoto) super.createPhoto(filename, uploadedImage);
    }
}
