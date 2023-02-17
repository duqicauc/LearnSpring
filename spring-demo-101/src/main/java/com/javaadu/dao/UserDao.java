package com.javaadu.dao;

import com.javaadu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author javaadu
 * @date 2023/02/15
 **/
@Repository
public class UserDao {

    private final static String MATCH_COUNT_SQL = " SELECT count(*) FROM t_user WHERE user_name = ? and password = ? ";
    private final static String SELECT_QUERY_SQL = " SELECT * FROM t_user WHERE user_name=? and password=? ";
    private final static String UPDATE_LOGIN_INFO_SQL = " UPDATE t_user SET last_visit=?,last_ip=?,credits=? WHERE user_id=? ";

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
        Integer integer = jdbcTemplate.queryForObject(MATCH_COUNT_SQL, new Object[]{userName, password}, Integer.class);
        return integer == null ? 0 : integer;
    }

    public User findUserByUserName(final String userName) {
        final User user = new User();
        jdbcTemplate.query(SELECT_QUERY_SQL, new Object[]{userName}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(userName);
                user.setCredits(resultSet.getInt("credits"));
            }
        });
        return user;
    }

    public void updateLoginInfo(User user) {
        jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL, new Object[]{user.getLastVisit(), user.getLastIp(), user.getCredits(), user.getUserId()});
    }

}
