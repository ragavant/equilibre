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

package com.cloudbees.management.PaaSFrontEnd.Impl;

import com.cloudbees.management.PaaSFrontEnd.Node;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 */
public abstract class NodeImpl extends BaseImpl implements Node {
    private String name;
    private String address;
    private Set<String> options;

    public NodeImpl(String type, String tag, String name, String address) {
        super(type, tag);
        options = new LinkedHashSet<String>();
        this.name = name;
        this.address = address;
    }

    /**
     * @return the name of the server
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address information  ip:port
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return  Return the set of options
     */
    public Set<String> getOptions() {
        return options;
    }

    public void addOption(String value) {
        options.add(value);
    }

}
