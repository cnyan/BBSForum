package com.yan.service;

import com.yan.dao.BaseDao;
import com.yan.dao.LoginLogDao;
import com.yan.dao.UserDao;
import com.yan.domain.LoginLog;
import com.yan.domain.User;
import com.yan.excepiton.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jsj-9-4 on 03/07/2017.
 * 用户管理服务器，负责 查询、注册以及锁定等功能
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginLogDao loginLogDao;


    /**
     * 用户注册
     * @param user
     * @throws UserExistException
     */
    public void registUser(User user) throws UserExistException {

        User user1 = this.userDao.getUserByUserName(user.getUserName());

        if (user1 != null){
            throw  new UserExistException("用户名已经存在");
        }else {
            user.setCredit(100);//设置100积分
            user.setUserType(1);
            userDao.save(user);
        }

    }

    /**
     * 登陆成功
     * @param user
     */
    public void loginSuccess(User user) {
        user.setCredit( 5 + user.getCredit());
        LoginLog loginLog = new LoginLog();
        loginLog.setUser(user);
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(new Date());
        userDao.update(user);
        BaseDao<LoginLog> loginLogDao;
        this.loginLogDao.save(loginLog);
    }

    /**
     * 根据用户名为条件，执行模糊查询操作
     * @param userName 查询用户名
     * @return 所有用户名前导匹配的userName的用户
     */
    public List<User> queryUserByUserName(String userName){
        return userDao.queryUserByUserName(userName);
    }

    /**
     * 获取所有用户
     * @return 所有用户
     */
    public List<User> getAllUsers(){
        return userDao.loadAll();
    }



    /**
     * 锁定用户，被锁定的用户，不能登录
     * @param userName
     */
    public void lockUser(String userName) throws UserExistException {

        User user = userDao.getUserByUserName(userName);

        if (user == null){
            throw new UserExistException("用户不存在");
        }

        user.setLocked(User.USER_LOCK);
        userDao.update(user);
    }

    /**
     * 解锁用户
     * @param userName
     * @throws UserExistException
     */
    public void unlockUser(String userName) throws UserExistException {

        User user = userDao.getUserByUserName(userName);

        if (user == null){
            throw new UserExistException("用户不存在");
        }
        user.setLocked(User.USER_UNLOCK);
        userDao.update(user);
    }


    /**
     * 更新用户
     * @param user
     */
    public void update(User user){
        userDao.update(user);
    }


    /**
     * 根据用户名/密码查询 User对象
     * @param userName 用户名
     * @return User
     */
    public User getUserByUserName(String userName){
        return userDao.getUserByUserName(userName);
    }


    /**
     * 根据userId加载User对象
     * @param userId
     * @return
     */
    public User getUserById(int userId){
        return userDao.get(userId).get(0);
    }

}
