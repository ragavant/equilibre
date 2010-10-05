package com.cloudbees.management.PaaSFrontEnd.HAProxy.Impl;

import com.cloudbees.management.PaaSFrontEnd.Impl.NodeImpl;

import java.util.Iterator;


public class ServerHAProxyImpl extends NodeImpl {

    public ServerHAProxyImpl(String type, String name, String address) {
        super(type, "server", name, address);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(tag);
        sb.append(TAB).append(getName()).append(SP).append(getAddress());
        for (Iterator it = getOptions().iterator(); it.hasNext();) {
            sb.append(SP).append(it.next());
            sb.append(NL);
        }
        return sb.toString();
    }
}
