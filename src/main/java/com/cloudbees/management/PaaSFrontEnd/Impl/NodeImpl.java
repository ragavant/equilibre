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
