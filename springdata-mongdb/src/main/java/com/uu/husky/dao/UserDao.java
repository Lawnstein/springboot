package com.uu.husky.dao;

import com.uu.husky.domain.UserEntity;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface UserDao {
    public void saveUser(UserEntity user);

    public UserEntity findUserByUserName(String userName);

    public void updateUser(UserEntity user);

    public void deleteUserById(Long id);

    /**
     * 只是一个实例，分页时请自行编写 Pagination 分页类
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<UserEntity> findUserByPage(int currentPage,int pageSize);

    /**
     *  分组 查询
     * @param groupArray
     * @param criteria
     * @return
     */
    public List<UserEntity> findUserByGroup(String[] groupArray, Criteria criteria);
}
