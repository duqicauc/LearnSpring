package com.javaadu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author javaadu
 * @date 2023/02/15
 **/
@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * queryForInt被取消了，使用下面的方式替代
     * <a href="https://mkyong.com/spring/jdbctemplate-queryforint-is-deprecated/">...</a>
     *
     * @param userName 用户昵称
     * @param password 用户密码
     * @return 匹配的用户的个数
     */
    public int getMatchCount(String userName, String password) {
        String sqlStr = "SELECT count(*) FROM t_user WHERE user_name = ? and password = ?";
        Integer integer = jdbcTemplate.queryForObject(sqlStr, new Object[]{userName, password}, Integer.class);
        return integer == null ? 0 : integer;
    }

}
