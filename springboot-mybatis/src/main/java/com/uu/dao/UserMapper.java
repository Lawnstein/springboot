package com.uu.dao;

import com.uu.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    public String tableName = "user";
    public String columnNoId = "ages, password";

    @Insert({
            "insert into user (id, ages, ",
            "password)",
            "values (#{id,jdbcType=INTEGER}, #{ages,jdbcType=INTEGER}, ",
            "#{password,jdbcType=VARCHAR})"
    })
    int insert(User record);

    @InsertProvider(type = UserSqlProvider.class, method = "insertSelective")
    int insertSelective(User record);

    @Select({
            "select",
            "id, ages, password",
            "from user",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "ages", property = "ages", jdbcType = JdbcType.INTEGER),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR)
    })
    User selectByPrimaryKey(Integer id);

    @Insert("<script> " +
            "insert into " + tableName +
            "(" + columnNoId + ") " +
            "values " +
            "<foreach collection=\"items\" index=\"index\" item=\"item\" separator=\",\"> "
            +
            "(#{item.ages},#{item.password})"
            +
            "</foreach> " +
            "</script>")
    int batchSave(@Param("items") List<User> items);

}