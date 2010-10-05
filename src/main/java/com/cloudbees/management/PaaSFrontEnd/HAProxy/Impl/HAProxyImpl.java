package com.cloudbees.management.PaaSFrontEnd.HAProxy.Impl;

import com.cloudbees.management.PaaSFrontEnd.Impl.LoadBalancerImpl;
import com.cloudbees.management.PaaSFrontEnd.Proxy;
import com.cloudbees.management.PaaSFrontEnd.Settings;


public class HAProxyImpl extends LoadBalancerImpl {

    public HAProxyImpl(String type, String description) {
        super(type, description);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("# " + getDescription());
        sb.append(NL);
        for (Settings settings : getSettings()) {
            sb.append(settings).append(NL);
            sb.append(NL);
        }
        for (Proxy listen : getProxies()) {
            sb.append(listen).append(NL);
            sb.append(NL);
        }
        return sb.toString();
    }
}
