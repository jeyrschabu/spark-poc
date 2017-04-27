package com.jeyrs.spark.configuration;

import org.aeonbits.owner.Config;

@Config.Sources({"file:${CONFIG_FILE}"})
public interface ApplicationConfig extends Config {

    @Config.Key("config.mongo.database")
    String db();

    @Config.Key("config.mongo.username")
    String dbUser();

    @Config.Key("config.mongo.password")
    String dbPass();

    @Config.Key("config.mongo.host")
    String dbHost();

}
