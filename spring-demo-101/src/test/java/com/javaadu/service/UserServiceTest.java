package com.javaadu.service;

import com.javaadu.dao.LoginLogDao;
import com.javaadu.dao.UserDao;
import com.javaadu.domain.LoginLog;
import com.javaadu.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

/**
 * @author javaadu
 * @date 2023/02/17
 **/
@RunWith(JUnit4ClassRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService = new UserService();

    @Mock
    private UserDao userDao;

    @Mock
    private LoginLogDao loginLogDao;

    private AutoCloseable autoCloseable;

    @Before
    public void setUp() throws Exception {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void closeService() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testHasMatchUser() {
        Mockito.doReturn(1).when(userDao).getMatchCount(Mockito.anyString(), Mockito.anyString());

        boolean res = userService.hasMatchUser("admin", "admin");

        Assert.assertTrue(res);
    }

    @Test
    public void testFindUserByUserName() {
        User user = new User();
        user.setUserName("admin");
        user.setPassword("admin");
        user.setUserId(1);
        user.setCredits(5);
        user.setLastVisit(new Date());
        user.setLastIp("10.90.10.1");
        Mockito.doReturn(user).when(userDao).findUserByUserName(Mockito.anyString());

        User user1 = userService.findUserByUserName("admin");

        Assert.assertNotNull(user1);
        Assert.assertEquals(5, user1.getCredits());
    }

    @Test
    public void testLoginSuccess() {
        Mockito.doNothing().when(userDao).updateLoginInfo(Mockito.<User>any());
        Mockito.doNothing().when(loginLogDao).insertLoginLog(Mockito.<LoginLog>any());

        User user = new User();
        user.setUserName("admin");
        user.setPassword("admin");
        user.setUserId(1);
        user.setCredits(5);
        user.setLastVisit(new Date());
        user.setLastIp("10.90.10.1");
        userService.loginSuccess(user);
    }
}