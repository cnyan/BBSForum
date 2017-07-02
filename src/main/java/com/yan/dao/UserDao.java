package com.yan.dao;

import com.yan.domain.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 闫继龙 on 2017/7/2.
 */
@Repository
//@ComponentScan
public class UserDao extends BaseDao<User> {

    private static final String GET_USER_BY_USERNAME = "from com.yan.domain.User as u where u.userName = ?";

    private static final String QUERY_USER_BY_USERNAME = "from com.yan.domain.User as u where u.userName like ?";


    /**
     * 根据用户名查询User对象
     *
     * @param userName 用户名
     * @return 对应userName的User对象，如果不存在，返回null。
     */
    public User getUserByUserName(String userName) {

        List<User> users = (List<User>) getHibernateTemplate().find(GET_USER_BY_USERNAME, userName);
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
        //return null;
    }

    /**
     * 根据用户名为模糊查询条件，查询出所有前缀匹配的User对象
     *
     * @param userName 用户名查询条件
     * @return 用户名前缀匹配的所有User对象
     */
    public List<User> queryUserByUserName(String userName) {

        return (List<User>) getHibernateTemplate().find(QUERY_USER_BY_USERNAME, userName + "%");
        //return null;
    }

}
