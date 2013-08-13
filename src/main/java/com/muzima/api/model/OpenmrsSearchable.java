/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.muzima.api.model;

import com.muzima.search.api.model.object.Searchable;

/**
 * Every OpenMRS object will have uri associated with it. This is not being used right now.
 */
public abstract class OpenmrsSearchable implements Searchable {

    private String uri;

    private String uuid;

    /**
     * Get the openmrs object's uri.
     *
     * @return the uri;
     */
    public String getUri() {
        return uri;
    }

    /**
     * Set the openmrs object's uri.
     *
     * @param uri the uri.
     */
    public void setUri(final String uri) {
        this.uri = uri;
    }

    /**
     * Get the uuid for the object.
     *
     * @return the uuid.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Set the uuid for the object.
     *
     * @param uuid the uuid to set.
     */
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}
