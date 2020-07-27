package com.jkrm.education.bean;

import com.jkrm.education.db.ListConvert;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by hzw on 2019/6/10.
 */

@Entity
public class DaoMarkCommonScoreUseBean {

    @Id(autoincrement = true)
    private Long id;
    private String name;
    @Unique
    private String questionId;
    @Convert(columnType = String.class, converter = ListConvert.class)
    private List<String> list = new ArrayList<>();

    public DaoMarkCommonScoreUseBean(String name, String questionId, List<String> list) {
        this.name = name;
        this.questionId = questionId;
        this.list = list;
    }

    @Generated(hash = 1941177267)
    public DaoMarkCommonScoreUseBean(Long id, String name, String questionId,
            List<String> list) {
        this.id = id;
        this.name = name;
        this.questionId = questionId;
        this.list = list;
    }

    @Generated(hash = 1988616116)
    public DaoMarkCommonScoreUseBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DaoMarkCommonScoreUseBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", questionId='" + questionId + '\'' +
                ", list=" + list +
                '}';
    }
}
