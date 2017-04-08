package com.firstfactory.api.util;

import java.util.Properties;

public interface ConfigProperties {

    Properties getProperties();

    String getProperty(String name);
}
