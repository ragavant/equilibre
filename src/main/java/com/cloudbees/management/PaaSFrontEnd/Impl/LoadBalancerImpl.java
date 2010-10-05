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
