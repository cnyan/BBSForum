package com.yan.dao;

import com.yan.domain.Board;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

/**
 * Created by 闫继龙 on 2017/7/1.
 *
 */
//@Configuration
@Repository
//@ComponentScan
public class BoardDao extends BaseDao<Board> {


    private static final String GET_BOARD_NUM =  "select count(f.boardId) from Board as f";


    /**
     * 获取论坛板块的数目
     * @return
     */
    public long getBoardNum() {

        Iterator iter = getHibernateTemplate().iterate(GET_BOARD_NUM);
        return ((Long)iter.next());
        //return  0;
    }


}
