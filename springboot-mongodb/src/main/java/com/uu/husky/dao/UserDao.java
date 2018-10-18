package com.uu.husky.dao;


import com.uu.husky.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 *
 *  基于springData ,个人感觉一般操作可以用这个来进行，
 *  复杂的就算了吧，还是老老实实的用MongTemplate
 * user 操作类
 *
 *
 */
@Repository
public interface UserDao extends CrudRepository<User, String>, MongoRepository<User, String> {
    /**
     * 根据firstname 查询User
     *
     * @param firstName
     * @return
     */
    @Nullable
    User findByFirstName(@Nullable String firstName);

    /**
     * @param sort
     * @return
     */
    List<User> findAll(Sort sort);

    /**
     * 分页查询 User
     *
     * @param pageable
     * @return
     */
    Page<User> findAll(Pageable pageable);


    /**
     * 查询数量 根据指定条件
     *
     * @param lastName
     * @return
     */
    long countByLastNameAndAge(String lastName, Integer age);

    /**
     * 根据范围查询数量：不包括边界
     *
     * @param start
     * @param over
     * @return
     */
    long countByAgeBetween(Integer start, Integer over);

    /**
     * 和上一个没啥区别
     *
     * @param start
     * @param over
     * @return
     */
    long countByAgeIsBetween(Integer start, Integer over);

    /**
     * 不起作用
     *
     * @param age
     * @return
     */
    long countDistinctByAgeBefore(Integer age);


    /**
     * 不起作用
     *
     * @param lastName
     * @return
     */
    long countDistinctByLastName(String lastName);


    /**
     * optional 的使用
     *
     * @param lastName
     * @return
     */
   /* Optional<User> findByLastName(String lastName);*/

    /**
     * 没啥用----- 无法去重（不会用）
     *
     * @param lastName
     * @return
     */
    List<User> findDistinctUserByLastName(String lastName);

    /**
     * 忽略大小写----- 查询1
     *
     * @param lastName
     * @return
     */
    List<User> findByLastNameIgnoreCase(String lastName);


    /**
     * 忽略大小写----- 查询2
     *
     * @param lastname
     * @param firstname
     * @return
     */
    List<User> findByLastNameAndFirstNameAllIgnoreCase(String lastname, String firstname);

    /**
     * 带排序的查询 ----- min to max
     *
     * @param lastName
     * @return
     */
    List<User> findByLastNameOrderByFirstNameAsc(String lastName);

    /**
     * 带排序的查询 ----- max to min
     *
     * @param lastname
     * @return
     */
    List<User> findByLastNameOrderByFirstNameDesc(String lastname);

    /**
     * 查询一个元素根据 （按照lastName 倒序）
     *
     * @return
     */
    User findFirstByOrderByLastNameDesc();

    /**
     * 查询一个元素根据 （按照age 倒序） 实测根上个方法使用一样
     *
     * @return
     */
    User findFirstByOrderByAgeDesc();

    /**
     * <pre>
     *  查询前10个，并给这前10个分页查询
     *      暂时 没有遇到这么奇葩的需求（此方法可以忽略）
     * </pre>
     *
     * @param lastName
     * @param pageable
     * @return
     */
    Page<User> queryFirst10ByLastName(String lastName, Pageable pageable);

    /**
     * 和上个方法一样，但是不好用，建议有需求用上一个方法
     *
     * @param lastName
     * @param pageable
     * @return
     */
    Slice<User> findTop3ByLastName(String lastName, Pageable pageable);

    /**
     * <pre>
     *
     * 查询前几个元素，
     *      条件：lastName = ？
     *      排序：自行设定
     * </pre>
     *
     * @param lastname
     * @param sort
     * @return
     */
    List<User> findFirst5ByLastName(String lastname, Sort sort);


    /**
     * <pre>
     *     查询前10个，按照lastName,分页查询，
     *          返回的是users的list集合，从10个里面选 几个（按照pageable的参数执行）
     * </pre>
     *
     * @param lastname
     * @param pageable
     * @return
     */
    List<User> findTop10ByLastName(String lastname, Pageable pageable);

    /**
     * <pre>
     *      此方式 是 执行MySQL 查询的 ，
     *          MongoDB 的话，你想写查询语句？天真
     *
     * </pre>
     *
     * @return
     */
    // @Query("select u from User u")
    // Stream<User> findAllByCustomQueryAndStream();

    Stream<User> readTop10ByFirstNameNotNull();

    // @Query("select u from User u")
    // Stream<User> streamAllPaged(Pageable pageable);


    //--------------------------------------------异步查询 ----------------------------------------------

    @Async
    Future<List<User>> findByLastNameAndAge(String firstname, Integer age);

    @Async
    CompletableFuture<User> findOneByFirstName(String firstname);

    @Async
    ListenableFuture<User> findOneByLastName(String lastname);


    //-------------------------------------------- Predicate  谓词Lambda 表达式查询，才疏学浅，不会使用----------------------------------------------
   /* Optional<User> findById(Predicate predicate);

    Iterable<User> findAll(Predicate predicate);

    long count(Predicate predicate);

    boolean exists(Predicate predicate);


    List<User> findAll(Predicate predicate, Pageable pageable);*/

}
