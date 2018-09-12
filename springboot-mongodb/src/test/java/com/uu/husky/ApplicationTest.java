package com.uu.husky;

import com.uu.husky.dao.UserDao;
import com.uu.husky.domain.School;
import com.uu.husky.domain.Subject;
import com.uu.husky.domain.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-22 上午10:51
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationContext.class)
public class ApplicationTest {
    @Autowired
    private UserDao userDao;


    /**
     * 批量插入1
     */
    @Test
    public void testInsertUser() {
        String[] schools = {
                "5b681910986bf62c6d4f71e5", "5b681910986bf62c6d4f71e6"
        };
        for (int i = 100; i < 200; i++) {
            User user = new User();

            user.setFirstName("机器人" + i);
            user.setLastName("robot" + i);
            user.setName("编号 " + i);

            user.setProvince("上海");

            int temp2 = new Random().nextInt(25);
            user.setAge(temp2);

            int temp3 = new Random().nextInt(2);
            user.setSchoolId(new ObjectId(
                    schools[temp3]));


            ArrayList<Subject> subjects = new ArrayList<>();
            String[] subjectName = {"数学", "英语", "音乐", "泡妞"};

            for (int j = 0; j < 4; j++) {
                int temp4 = new Random().nextInt(50) + 50;
                Subject subject = new Subject();
                subject.setName(subjectName[j]);
                subject.setScore(temp4);
                subjects.add(subject);
            }
            user.setSubjects(subjects);

            this.mongoTemplate.insert(user);
        }

    }


    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 批量插入2
     */
    @Test
    public void testInsertSchool() {
        List<School> schools = new ArrayList<>();
        for (int i = 20; i < 30; i++) {
            School school = new School();
            school.setName("哈哈" + i);
            school.setDescription("不错" + i);

            schools.add(school);
        }
        this.mongoTemplate.insertAll(schools);
    }


    /**
     * 分页查询
     */
    @Test
    public void testQueryUser() {
        Iterable<User> users = userDao.findAll(
                PageRequest.of(1, 2, Sort.by("age").descending()));
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 查询 数量,根据指定条件
     */
    @Test
    public void testQueryCount1() {
        long robot1 = userDao.countByLastNameAndAge("robot1", 39);
        System.out.println("robot1 的数量是：" + robot1);
    }

    /**
     *
     */
    @Test
    public void testQueryCount2() {
        // 不包括  边界
        long count = userDao.countByAgeBetween(38 - 1, 39 + 1);
        System.out.println("数量 = " + count);

        long count2 = userDao.countByAgeIsBetween(38, 39);
        System.out.println("数量 = " + count2);

        // 不起作用
        long count3 = userDao.countDistinctByAgeBefore(38);
        System.out.println("userDao.countDistinctByAgeBefore(38) =  " + count3);

        // 不起作用
        long count4 = userDao.countDistinctByLastName("robot");
        System.out.println("userDao.countDistinctByLastName(\"robot\") = " + count4);
    }

    /**
     * 空返回值的处理
     */
    @Test
    public void testNull() throws ExecutionException, InterruptedException {
        // User user = userDao.findByFirstName(null);
        // System.out.println(user.toString());

//        Optional<User> userOptional = userDao.findByLastName("robot2");
//        System.out.println(userOptional.isPresent());
//
//        List<User> users = userDao.findDistinctUserByLastName("robot1");
//        users.stream().forEach(
//                entity ->
//                        System.out.println(entity.toString())
//        );

//        List<User> user = userDao.findByLastNameAndFirstNameAllIgnoreCase("robot1","机器人a");
//        user.stream().forEach(
//                entity ->
//                        System.out.println(entity.toString())
//        );

//        List<User> user = userDao.findByLastNameOrderByFirstNameAsc("robot");
//        user.stream().forEach(
//                entity ->
//                        System.out.println(entity.toString())
//        );

//        User user = userDao.findFirstByOrderByLastNameDesc();
//        System.out.println(user.toString());

//          User user = userDao.findFirstByOrderByAgeDesc();
//          System.out.println(user.toString());

//        Page<User> users = userDao.queryFirst10ByLastName("robot", PageRequest.of(0, 1));
//        System.out.println(users.getTotalElements());
//        System.out.println(users.getTotalPages());
//        System.out.println(users.getContent().toString());

//        Slice<User> users = userDao.findTop3ByLastName("robot", PageRequest.of(0, 2));
//        System.out.println(users.getNumber());
//        System.out.println(users.getSize());
//        System.out.println(users.getNumberOfElements());
//        System.out.println(users.getContent().toString());

//        List<User> users = userDao.findFirst5ByLastName("robot", Sort.by("age").descending());
//        users.stream().forEach(entity -> {
//            System.out.println(entity.toString());
//        });

//        List<User> users = userDao.findTop10ByLastName("robot",
//                PageRequest.of(1, 2, Sort.by("age").descending()));
//        users.stream().forEach(entity -> {
//            System.out.println(entity.toString());
//        });


//        Stream<User> users = userDao.readTop10ByFirstNameNotNull();
//        users.forEach(entity -> {
//            System.out.println(entity.toString());
//        });

//        Future<List<User>> usersFuture = userDao.findByLastNameAndAge("robot", 26);
//        List<User> users = usersFuture.get();
//        users.forEach(entity -> {
//            System.out.println(entity.toString());
//        });


//        Predicate predicate = (User user) -> user.getFirstName().equalsIgnoreCase("dave")
//                .and(user.lastName.startsWithIgnoreCase("mathews"));
//        Iterable<User> users = userDao.findAll(predicate);

    }
}
