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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * The load balancer factory class
 */
public class LoadBalancerFactory {
    private static Map<String, Map<Class, Class>> classes = new HashMap<String, Map<Class, Class>>();

    /**
     * This method registers load balancer implementation classes
     * @param type      The load balancer type
     * @param interfaceClass  The load balancer interface type
     * @param implClass  The implementation class
     */
    public static void register(String type, Class interfaceClass, Class implClass) {
        Map<Class,Class> impls = classes.get(type);
        if (impls == null) {
            impls = new HashMap<Class, Class>();
            classes.put(type, impls);
        }
        impls.put(interfaceClass, implClass);
    }

    private static Class getClass(String type, Class interfaceClass) {
        Map<Class,Class> impls = classes.get(type);
        if (impls != null) {
            return impls.get(interfaceClass);
        }
        return null;
    }

    /**
     * This method creates an instance of a proxy node
     * @param type The proxy node type
     * @param name The node name
     * @param address  The node address
     * @return The node instance created
     */
    public static Node getNode(String type, String name, String address) {
        Class impl = getClass(type, Node.class);
        if (impl == null)
            throw new IllegalArgumentException("type unknown");

        if (Node.class.isAssignableFrom(impl)) {
            try {
                Class[] args = {String.class, String.class, String.class};
                Constructor constr = impl.getConstructor(args);
                return (Node)constr.newInstance(type, name, address);
            } catch (Exception e) {
                throw new IllegalArgumentException("type not compatible", e);
            }
        }
        throw new IllegalArgumentException("type not compatible");
    }

    /**
     * This method creates an instance of Settings
     * @param type The settings type
     * @param category The settings category
     * @return The Settings instance created
     */
    public static Settings getSettings(String type, String category) {
        Class impl = getClass(type, Settings.class);
        if (impl == null)
            throw new IllegalArgumentException("type unknown");

        if (Settings.class.isAssignableFrom(impl)) {
            try {
                Class[] args = {String.class, String.class};
                Constructor constr = impl.getConstructor(args);
                return (Settings)constr.newInstance(type, category);
            } catch (Exception e) {
                throw new IllegalArgumentException("type not compatible", e);
            }
        }
        throw new IllegalArgumentException("type not compatible");
    }

    /**
     * This method creates an instance of a proxy
     * @param type The proxy type
     * @param name The proxy name
     * @param address The proxy address
     * @return The Proxy instance created
     */
    public static Proxy getProxy(String type, String name, String address) {
        Class impl = getClass(type, Proxy.class);
        if (impl == null)
            throw new IllegalArgumentException("type unknown");

        if (Proxy.class.isAssignableFrom(impl)) {
            try {
                Class[] args = {String.class, String.class, String.class};
                Constructor constr = impl.getConstructor(args);
                return (Proxy)constr.newInstance(type, name, address);
            } catch (Exception e) {
                throw new IllegalArgumentException("type not compatible", e);
            }
        }
        throw new IllegalArgumentException("type not compatible");
    }

    /**
     * This method creates a instance of a load balancer
     * @param type The load balancer type
     * @param description The load balancer description
     * @return the LoadBalancer instance created
     */
    public static LoadBalancer getLoadBalancer(String type, String description) {
        Class impl = getClass(type, LoadBalancer.class);
        if (impl == null)
            throw new IllegalArgumentException("type unknown");

        if (LoadBalancer.class.isAssignableFrom(impl)) {
            try {
                Class[] args = {String.class, String.class};
                Constructor constr = impl.getConstructor(args);
                return (LoadBalancer)constr.newInstance(type, description);
            } catch (Exception e) {
                throw new IllegalArgumentException("type not compatible", e);
            }
        }
        throw new IllegalArgumentException("type not compatible");
    }
}
