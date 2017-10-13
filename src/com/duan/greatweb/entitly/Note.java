package com.duan.greatweb.entitly;

import com.duan.greatweb.dao.mapping.Mapping;

/**
 * 基本数据类型定义时要使用其包装类类型，{@link com.duan.greatweb.dao.db.DataBase}中利用反射获取值时需要判断
 * 成员变量是否被赋值，而基本数据类型默认会被赋值为 0，而不是 null 。
 * 参考{@link com.duan.greatweb.dao.db.DataBase#update(Object[])}
 */
@Mapping("note")
public class Note {

    @Mapping("id")
    private Integer id;

    @Mapping("title")
    private String title;

    @Mapping("content")
    private String content;

    @Mapping("datetime")
    private Long dateTime;

    @Mapping("uid")
    private Integer userId;

    @Mapping("state")
    private Integer state;

    public Note() {
    }

    public Note(Integer id, String title, String content, Long dateTime, Integer userId, Integer state) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.state = state;
        this.userId = userId;
    }

    public Note(String title, String content, Long dateTime, Integer userId, Integer state) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.userId = userId;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateTime=" + dateTime +
                ", userId=" + userId +
                ", state=" + state +
                '}';
    }

}
