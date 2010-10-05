package com.cloudbees.management.PaaSFrontEnd.HAProxy.Impl;

import com.cloudbees.management.PaaSFrontEnd.Impl.ProxyImpl;
import com.cloudbees.management.PaaSFrontEnd.Node;

public class ProxyHAProxyImpl extends ProxyImpl {

    public ProxyHAProxyImpl(String type, String name, String address) {
        super(type, "listen", name, address);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(tag);
        sb.append(TAB).append(getName()).append(SP).append(getAddress()).append(NL);
        sb.append(getSettings());
        for (Node node : getNodes()) {
            sb.append(TAB).append(node);
        }
        return sb.toString();
    }

}
