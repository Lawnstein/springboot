package com.uu.husky;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import com.uu.husky.domain.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-08-06 下午4:30
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationContext.class)
public class MongoDbTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void insert(){
        User user = new User();
        user.setId(new ObjectId());
        user.setFirstName(" springboot -insert ");
        user.setLastName("1");
        user.setAge(0);
        user.setSchoolId(new ObjectId());
        user.setProvince("1");
        user.setName("1");
        mongoTemplate.insert(user);

    }

    //--------------------------------------------查询 start----------------------------------------------

    /**
     * 简单查询,单个（主键）查询
     */
    @Test
    public void  findOne() {
        // 由此证明，OBjectId 直接传String 也可以
        User user = mongoTemplate.findById("5b67fb40986bf621e99a05be", User.class);

        user = mongoTemplate.findById(new ObjectId("5b67fb40986bf621e99a05be"), User.class);
        System.out.println(user.toString());

    }

    /**
     * 查询数量（按条件）
     */
    @Test
    public void queryCount() {
        Query query = new Query();
        query.addCriteria(Criteria.where("age").is(1));
        long count = this.mongoTemplate.count(query, User.class);
        System.out.println("----------" + count);
    }

    /**
     * 条件（其他 如模糊、in 、notin、gt、lt 等就通过添加Criteria就可以了），分页 ，排序
     */
    @Test
    public void findByQuery() {
        Query query = new Query();

        // 条件添加
       // query.addCriteria(Criteria.where("lastName").is("robot1"));

        // 分页
        //query.skip(0).limit(2);
        // 或者  注意：page 从第0页开始，使用的时候记得-1
        query.with(PageRequest.of(1-1,2));
        //query.with(new PageRequest(1,2));

        // 排序
        //query.with(Sort.by(Sort.Direction.DESC, "age"));

        List<User> user = mongoTemplate.find(query, User.class);
        user.stream().forEach(System.out::println);

    }


    /**
     * 聚合查询1：感觉很鸡肋，可以不用看
     */
    @Test
    public void findQueryByAggregation1() {

        Criteria criteria = new Criteria();
        criteria.where("schoolId").in(
                new String[]{"5b681910986bf62c6d4f71ea", "5b681910986bf62c6d4f71e9"
                        , "5b681910986bf62c6d4f71e8", "5b681910986bf62c6d4f71e7"}
        );

        Aggregation agg = newAggregation(match(criteria),
                group("age")
                        .count().as("count"),
                sort(new Sort(Sort.Direction.DESC, "count")));

        AggregationResults<BasicDBObject> results = this.mongoTemplate.aggregate(agg, User.class,
                BasicDBObject.class);

        Iterator<BasicDBObject> iterator = results.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + "..........");
        }
    }


    /**
     * 聚合查询2,自动封装的，但需要写MongoDb 查询语句
     */
    @Test
    public void findQueryByAggregation2() {

        GroupBy groupBy = GroupBy.key("age").initialDocument("{total:0}")
                .reduceFunction("function(doc, prev){prev.total+=1}");
        Criteria criteria = new Criteria();
        criteria.where("schoolId").in(
                new String[]{"5b681910986bf62c6d4f71ea", "5b681910986bf62c6d4f71e9"
                        , "5b681910986bf62c6d4f71e8", "5b681910986bf62c6d4f71e7"}
        );

        GroupByResults<BasicDBObject> gbr = mongoTemplate.group(criteria,
                "user",
                groupBy, BasicDBObject.class);
        Iterator<BasicDBObject> iterator = gbr.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + "..........");
        }

    }

    /**
     * 聚合查询3,可以实现 _id 的重命名
     */
    @Test
    public void findQueryByAggregation3() {

        Criteria criteria = new Criteria();
        criteria.where("schoolId").in(
                new String[]{"5b681910986bf62c6d4f71ea", "5b681910986bf62c6d4f71e9"
                        , "5b681910986bf62c6d4f71e8", "5b681910986bf62c6d4f71e7"}
        );

        TypedAggregation<User> agg = Aggregation.newAggregation(
                User.class,
                match(criteria),
                project("age")
                , group("age").count().as("totalNum")
                , project("age", "totalNum").and("age").previousOperation()
                //为分组的字段（_id）建立别名
        );

        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(agg, BasicDBObject.class);
        //  System.out.println(agg.toString());
        System.out.println(result.getMappedResults());
    }


    /**
     * 找出上海的学生，统计平均年龄
     */
    @Test
    public void queryExample2() {
        TypedAggregation<User> agge = Aggregation.newAggregation(User.class,
                match(Criteria.where("province").is("上海")),
                group("$province").avg("$age").as("avg"),
                project("$_id", "$avg").and("province").previousOperation()
        );
        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(agge, BasicDBObject.class);
        System.out.println(result.getMappedResults());
    }


    /**
     * <pre>
     *
     * 统计每个省的各科平均成绩
     *      思路：根据数据库文档结构，subjects是数组形式，需要先"劈开"，然后再进行统计
     *      步骤：
     *          1. 先用 $unwind 拆分数组
     *          2. 按照province ,subject 分组并求出各科目平均分
     *             到此就可以了理论上，但其结果如下形式：
     *                  [
     *                      {
     *                          name:"数学",
     *                          avg:66.5,
     *                          province:"北京"
     *                      },
     *                  ]
     *
     *          3. 支线任务： 将同一省份的科目成绩统计到一起( 即，期望
     *                      'province':'xxxxx', avgscores:[ {'xxx':xxx}, ....] 这样的形式)
     *
     *              3.1 要做的有一件事，在前面的统计结果的基础上，先用 $project 将平均分和成绩揉到一起，即形如下面的样子
     *                      { "subjinfo" : { "subjname" : "英语" ,"AvgScores" : 58.1 } ,"province" : "海南" }
     *                  再按省份group，将各科目的平均分push到一块，
     *              3.2 命令如下：
     *                  1) {$project：{province:"$_id.province", subjinfo:{"subjname":"$_id.subjname", "avgscore":"$AvgScore"}}
     *                  2) {$group:{_id:"$province", avginfo:{$push:"$subjinfo"}}}
     * </pre>
     */
    @Test
    public void queryExample3() {
        TypedAggregation<User> aggregation = Aggregation.newAggregation(User.class,
                // 1. 先用 $unwind 拆分数组
                unwind("subjects"),
                //2. 按照province ,subjects.name 分组并求出各科目平均分
                group("subjects.name", "province")
                        .avg("subjects.score").as("avg"),


                project("_id.province").andExclude("_id").
                        and("avginfo")
                        .nested(bind("name", "_id.name")
                                .and("avg", "avg")),

                group("province").push("avginfo").as("subjinfo"),
                project("subjinfo").and("provine").previousOperation()

        );
        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, BasicDBObject.class);

        System.out.println(result.getMappedResults().toString());
    }


    /**
     * 分组后,
     * 使得未分组变量
     */
    @Test
    public void queryExample4() {
        Aggregation aggregation = Aggregation.newAggregation(
                group("province")
                        .last("schoolId").as("one")
                        .first("schoolId").as("two")
                        .push("schoolId").as("学校")
                        .push("name").as("姓名")


        );
        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, User.class, BasicDBObject.class);

        System.out.println(result.getMappedResults().toString());
    }

    //--------------------------------------------查询 end----------------------------------------------


    //-------------------------------------------- 更改    start ----------------------------------------------

    /**
     * <pre>
     * {
     *     "_id" : ObjectId("5b694b0eb63f511944bf0ead"),
     *     "firstName" : "机器人0",
     *     "lastName" : "robot0",
     *     "age" : NumberInt(22),
     *     "schoolId" : ObjectId("5b681910986bf62c6d4f71e5"),
     *     "province" : "河北",
     *     "name" : "编号 0",
     *     "subjects" : [
     *         {
     *             "name" : "数学",
     *             "score" : NumberInt(93)
     *         },
     *         {
     *             "name" : "英语",
     *             "score" : NumberInt(81)
     *         },
     *         {
     *             "name" : "音乐",
     *             "score" : NumberInt(52)
     *         },
     *         {
     *             "name" : "泡妞",
     *             "score" : NumberInt(58)
     *         }
     *     ]
     * }
     *
     *
     * 需求：更改 id 是5b694b0eb63f511944bf0ead的一天数据，将泡妞成绩改为100
     *
     *
     * </pre>
     */
    @Test
    public void updateExample1() {
        Query query = new Query();
        query.addCriteria(Criteria.where("subjects.name").is("泡妞"));
        Update update = Update.update("subjects.$.score", 111);
        UpdateResult updateResult = this.mongoTemplate.updateMulti(query, update, "user");
        System.out.println("修改条数 = " + updateResult.getModifiedCount());
    }


    //-------------------------------------------- 更改    end ----------------------------------------------
}
