package com.firstfactory.api.util;

import com.firstfactory.api.exception.DocumentHandlerException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor
public class DefaultConfigProperties implements ConfigProperties {

    private static final class PropertiesHolder {

        private static final PropertiesHolder INSTANCE = new PropertiesHolder();
        private final Properties properties;

        private PropertiesHolder() {
            this.properties = new Properties();
            try {
                this.properties.load(DefaultConfigProperties.class.getResourceAsStream("/config.properties"));
            } catch (IOException | RuntimeException e) {
                throw new DocumentHandlerException("Could not load properties as specified", e);
            }
        }
    }

    @Override
    public Properties getProperties() {
        return PropertiesHolder.INSTANCE.properties;
    }

    @Override
    public String getProperty(String name) {
        return this.getProperties().getProperty(name);
    }
}
