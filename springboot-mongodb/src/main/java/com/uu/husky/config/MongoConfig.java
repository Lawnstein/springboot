package com.uu.husky.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-22 上午11:45
 **/
@Configuration
public class MongoConfig  {
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


   /* @Bean
    @ConditionalOnMissingBean(MongoClient.class)
    public MongoClient getMongodbClients() {

        List<ServerAddress> addresses = new ArrayList<ServerAddress>();
        ServerAddress address1 = new ServerAddress("192.168.22.11", 27017);
        ServerAddress address2 = new ServerAddress("192.168.22.11", 27018);
        ServerAddress address3 = new ServerAddress("192.168.22.11", 27019);

        addresses.add(address1);
        addresses.add(address2);
        addresses.add(address3);




        MongoCredential credential = MongoCredential.createMongoCRCredential("admin", "test", "husky".toCharArray());
        MongoClient client = new MongoClient(addresses, Arrays.asList(credential));
      //  MongoClient client = new MongoClient(addresses);

        return client;
    }*/

}
