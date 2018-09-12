package com.uu.husky;

import com.uu.husky.domain.Manager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-08-14 上午9:40
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationContext.class)
public class MongoIndexTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void insertData() {

        Calendar instance = Calendar.getInstance();
       /* System.out.println(instance.getTime());
        instance.add(Calendar.DATE, -1);
        System.out.println(instance.getTime());*/

        for (int i = 0; i < 1000000; i++) {
            instance.add(Calendar.DATE, -i);

            Manager manager = new Manager();
            manager.setAge(i);
            manager.setDate(instance.getTime());
            manager.setScore(i);
            manager.setName("---------所谓“面向集合”（Collection-Oriented），意思是数据被分组存储在数据集中，被称为一个集合（Collection)。每个集合在数据库中都有一个唯一的标识名，并且可以包含无限数目的文档。集合的概念类似关系型数据库（RDBMS）里的表（table），不同的是它不需要定义任何模式（schema)。Nytro MegaRAID技术中的闪存高速缓存算法，能够快速识别数据库内大数据集中的热数据，提供一致的性能改进。\n" +
                    "模式自由（schema-free)，意味着对于存储在mongodb数据库中的文件，我们不需要知道它的任何结构定义。如果需要的话，你完全可以把不同结构的文件存储在同一个数据库里。\n" +
                    "存储在集合中的文档，被存储为键-值对的形式。键用于唯一标识一个文档，为字符串类型，而值则可以是各种复杂的文件类型。我们称这种存储形式为BSON（Binary Serialized Document Format）。 [3] \n" +
                    "[4]  MongoDB已经在多个站点部署，其主要场景如下：\n" +
                    "1）网站实时数据处理。它非常适合实时的插入、更新与查询，并具备网站实时数据存储所需的复制及高度伸缩性。\n" +
                    "2）缓存。由于性能很高，它适合作为信息基础设施的缓存层。在系统重启之后，由它搭建的持久化缓存层可以避免下层的数据源过载。\n" +
                    "3）高伸缩性的场景。非常适合由数十或数百台服务器组成的数据库，它的路线图中已经包含对MapReduce引擎的内置支持。\n" +
                    "不适用的场景如下：1）要求高度事务性的系统。\n" +
                    "2）传统的商业智能应用。\n" +
                    "3）复杂的跨文档（表）级联查询。 [4]  ------------------");

            this.mongoTemplate.insert(manager);
        }
    }

    @Test
    public void queryNoIndex(){
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "date"));

        query.skip(100000).limit(20);
        List<Manager> managers = this.mongoTemplate.find(query, Manager.class);

    }
}
