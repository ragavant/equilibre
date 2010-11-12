/*
 * Copyright 2010, CloudBees Inc.
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.cloudbees.management.PaaSFrontEnd;

/**
 * Base interface for load balancer components.
 */
public interface Base {
    /**
     * This method returns the component tag. A tag is a display label used to define the
     * component configuration section
     * @return  The tag as a String
     */
    String getTag();

    /**
     * This method sets the component tag. See getTag()
     * @param tag  The tag label
     */
    void setTag(String tag);

    /**
     * This method returns the implementation type
     * @return  The implementation type as a String
     */
    String getType();
}
