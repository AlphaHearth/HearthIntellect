package com.hearthintellect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for the `core` module
 */
@Configuration
@Import(SpringMongoConfig.class)
public class SpringCoreConfig {}
