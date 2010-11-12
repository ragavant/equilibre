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
import com.cloudbees.management.PaaSFrontEnd.LoadBalancer;
import com.cloudbees.management.PaaSFrontEnd.Proxy;
import com.cloudbees.management.PaaSFrontEnd.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * User: fabian
 * Date: Aug 25, 2010
 * Time: 4:00:52 PM
 */
public abstract class LoadBalancerImpl extends BaseImpl implements LoadBalancer {
    private String description;
    private List<Settings> settings;
    private List<Proxy> proxies;

    protected LoadBalancerImpl(String type, String description) {
        super(type, null);
        this.description = description;
        settings = new ArrayList<Settings>();
        proxies = new ArrayList<Proxy>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Settings createSettings(String category) {
        Settings settings = LoadBalancerFactory.getSettings(type, category);
        this.settings.add(settings);
        return settings;
    }

    public void addSettings(Settings settings) {
        this.settings.add(settings);
    }

    public Proxy createProxy(String name, String address) {
        Proxy proxy = LoadBalancerFactory.getProxy(type, name, address);
        proxies.add(proxy);
        return proxy;
    }

    public void addProxy(Proxy proxy) {
        proxies.add(proxy);
    }

    public List<Settings> getSettings() {
        return settings;
    }

    public List<Proxy> getProxies() {
        return proxies;
    }
}
