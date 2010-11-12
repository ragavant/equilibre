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

import java.util.Set;

/**
 * This class represents a proxy node definition
 */
public interface Node extends Base {
    /**
     * This method returns the name of the proxy
     * @return the name of the proxy
     */
    String getName();

    /**
     * This method returns the address of the proxy
     * @return the address of the proxy
     */
    String getAddress();

    /**
     * This method returns the Set of proxies
     * @return the option Set of the proxy
     */
    Set<String> getOptions();

    /**
     * Add option definition to the proxy
     * @param value  The definition of the option
     */
    void addOption(String value);
}
