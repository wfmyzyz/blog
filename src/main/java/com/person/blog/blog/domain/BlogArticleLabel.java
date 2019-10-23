package com.person.blog.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xiong
 * @since 2019-05-14
 */
public class BlogArticleLabel implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 文章标签ID
     */
    @TableId(value = "label_id", type = IdType.AUTO)
    private Integer labelId;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不可为空！")
    private String name;

    /**
     * 标签介绍
     */
    private String introduce;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */

    private String updateTime;

    /**
     * 状态：正常；删除
     */
    private String tbStatus;


    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTbStatus() {
        return tbStatus;
    }

    public void setTbStatus(String tbStatus) {
        this.tbStatus = tbStatus;
    }

    @Override
    public String toString() {
        return "BlogArticleLabel{" +
        "labelId=" + labelId +
        ", name=" + name +
        ", introduce=" + introduce +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", tbStatus=" + tbStatus +
        "}";
    }
}
