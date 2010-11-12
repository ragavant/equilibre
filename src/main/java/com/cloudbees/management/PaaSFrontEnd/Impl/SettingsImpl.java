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

import com.cloudbees.management.PaaSFrontEnd.Settings;

import java.util.*;

/**
 */
public abstract class SettingsImpl extends BaseImpl implements Settings {
    private List<String> options;

    public SettingsImpl(String type, String tag) {
        super(type, tag);
        options = new ArrayList<String>();
    }

    public void addOption(String value) {
        options.add(value);
    }

    public List<String> getOptions() {
        return options;
    }

}
