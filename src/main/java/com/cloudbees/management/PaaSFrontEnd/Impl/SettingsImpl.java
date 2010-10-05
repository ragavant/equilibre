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
