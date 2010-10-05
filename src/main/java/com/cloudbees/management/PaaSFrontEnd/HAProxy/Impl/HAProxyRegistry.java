package com.cloudbees.management.PaaSFrontEnd.HAProxy.Impl;

import com.cloudbees.management.PaaSFrontEnd.*;

public class HAProxyRegistry {
    public static String TYPE = "HAProxy";

    static {
        LoadBalancerFactory.register("HAProxy", LoadBalancer.class, HAProxyImpl.class);
        LoadBalancerFactory.register("HAProxy", Proxy.class, ProxyHAProxyImpl.class);
        LoadBalancerFactory.register("HAProxy", Node.class, ServerHAProxyImpl.class);
        LoadBalancerFactory.register("HAProxy", Settings.class, SettingsHAProxyImpl.class);
    }
}
