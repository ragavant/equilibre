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
