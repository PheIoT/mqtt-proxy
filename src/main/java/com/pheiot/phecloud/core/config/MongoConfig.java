/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
//@EnableMongoRepositories(basePackages = "com.pheiot.phecloud.core.dao")
@PropertySource("classpath:application.properties")
public class MongoConfig extends AbstractMongoConfiguration {

    private static final String MONGODB_USERNAME = "mongodb.username";
    private static final String MONGODB_PASSWORD = "mongodb.password";
    private static final String MONGODB_URI = "mongodb.uri";
    private static final String MONGODB_PORT = "mongodb.port";
    private static final String MONGODB_DB = "mongodb.db";

    @Autowired
    private Environment env;

    @Override
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(
                env.getProperty(MONGODB_USERNAME), env.getProperty(MONGODB_DB),
                env.getProperty(MONGODB_PASSWORD).toCharArray());

        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.socketTimeout(10 * 1000);

        return new MongoClient(new ServerAddress(env.getProperty(MONGODB_URI),
                Integer.valueOf(env.getProperty(MONGODB_PORT))),
                credential,
                builder.build());
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        try {
            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (NoSuchBeanDefinitionException ignore) {
        }

        // Don't save _class to mongo
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return mappingConverter;
    }


    @Override
    protected String getDatabaseName() {
        return env.getProperty(MONGODB_DB);
    }
}
