package com.yan.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 闫继龙 on 2017/7/1.
 * topic 话题表
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_topic")
public class Topic extends BaseDomain {

    /**
     * 精华主题帖子
     */
    public static final int DIGEST_TOPIC = 1;
    /**
     * 普通的主题帖子
     */
    public static final int NOT_DIGEST_TOPIC = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private int topicId;

    @Column(name = "topic_title")
    private String topicTitle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "board_id")
    private int boardId;

    //Transient表示该属性并非一个到数据库表的字段的映射
    @Transient
    private MainPost mainPost = new MainPost();

    @Column(name = "last_post")
    private Date lastPost = new Date();

    @Column(name = "create_time")
    private Date createTime = new Date();

    @Column(name = "topic_views")
    private int views;

    //replies 回复
    @Column(name = "topic_replies")
    private int replies;

    //精华
    private int digest = NOT_DIGEST_TOPIC;


    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public MainPost getMainPost() {
        return mainPost;
    }

    public void setMainPost(MainPost mainPost) {
        this.mainPost = mainPost;
    }

    public Date getLastPost() {
        return lastPost;
    }

    public void setLastPost(Date lastPost) {
        this.lastPost = lastPost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getDigest() {
        return digest;
    }

    public void setDigest(int digest) {
        this.digest = digest;
    }
}
