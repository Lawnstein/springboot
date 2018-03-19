package com.uu.husky.dao.UserImpl;

import com.uu.husky.dao.UserDao;
import com.uu.husky.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 描述：
 *
 * @author uusao
 * @create 2018-03-19 18:50
 **/
@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * @param user
     */
    @Override
    public void saveUser(UserEntity user) {
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    @Override
    public UserEntity findUserByUserName(String userName) {
        Query query=new Query(Criteria.where("userName").is(userName));
        UserEntity user =  mongoTemplate.findOne(query , UserEntity.class);
        return user;
    }

    /**
     * 更新对象
     * @param user
     */
    @Override
    public void updateUser(UserEntity user) {
        Query query=new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("userName", user.getUserName()).set("passWord", user.getPassWord());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,UserEntity.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
    }

    /**
     * 删除对象
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,UserEntity.class);
    }

    @Override
    public List<UserEntity> findUserByPage(int currentPage, int pageSize) {
        Query query = new Query();
        query.skip((currentPage - 1 ) * pageSize);  // 从哪查
        query.limit(pageSize);  // 查多少条

        return mongoTemplate.find(query,UserEntity.class);
    }

    @Override
    public List<UserEntity> findUserByGroup(String[] groupArray, Criteria criteria) {
        GroupBy groupBy = GroupBy.key(groupArray).initialDocument("{total:0}")
                .reduceFunction("function(doc, prev){prev.total+=1}");
        GroupByResults<UserEntity> gbr = mongoTemplate.group(criteria,"userEntity",
                groupBy, UserEntity.class);
        Iterator<UserEntity> iterator = gbr.iterator();
        List<UserEntity> userList = new ArrayList<UserEntity>();
        while (iterator.hasNext()) {
            userList.add(iterator.next());
        }
        return userList;
    }


}

