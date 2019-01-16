package com.uu.husky.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * 描述：
 * User liupenghao
 * Date 2018/12/01 19:31
 **/
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
    /*@Bean
    public MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }*/

    @Override
    public MongoClient mongoClient() {
        //端口号和ip
        return new MongoClient("139.199.32.78", 27017);
    }

    @Override
    protected String getDatabaseName() {
        //库名
        return "test";
    }
}

