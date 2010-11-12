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

import com.cloudbees.management.PaaSFrontEnd.LoadBalancerFactory;
import com.cloudbees.management.PaaSFrontEnd.Node;
import com.cloudbees.management.PaaSFrontEnd.Proxy;
import com.cloudbees.management.PaaSFrontEnd.Settings;

import java.util.*;

/**
 */
public abstract class ProxyImpl extends BaseImpl implements Proxy {
    private String name;
    private String address;
    private Settings settings;
    private List<Node> nodes;

    public ProxyImpl(String type, String tag, String name, String address) {
        super(type, tag);
        nodes = new ArrayList<Node>();
        this.name = name;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
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
     * @return  Return the set of settings
     */
    public Settings getSettings() {
        return settings;
    }

    public Settings createSettings() {
        Settings settings = LoadBalancerFactory.getSettings(type, null);
        setSetting(settings);
        return settings;
    }

    public void setSetting(Settings settings) {
        this.settings = settings;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Node createNode(String name, String address) {
        Node node = LoadBalancerFactory.getNode(type, name, address);
        addNode(node);
        return node;
    }

    public void addNode(Node node) {
        nodes.add(node);    
    }
}
