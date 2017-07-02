package com.yan.dao;

import com.yan.domain.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * Created by 闫继龙 on 2017/7/2.
 *
 */
@Repository
public class LoginLogDao extends BaseDao<LoginLog> {

    public void save(LoginLog loginLog) {
        this.getHibernateTemplate().save(loginLog);
    }
}
