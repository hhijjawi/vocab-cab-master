package com.vocab.vocab.Settings;

/**
 * Created by Hisham on 11/30/2015.
 */
public class SettingsSingleton {
    private static SettingsSingleton ourInstance = new SettingsSingleton();

    public static SettingsSingleton getInstance() {
        return ourInstance;
    }

    private SettingsSingleton() {
    }
}
