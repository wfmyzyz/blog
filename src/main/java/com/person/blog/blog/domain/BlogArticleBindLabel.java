package com.person.blog.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xiong
 * @since 2019-07-08
 */
public class BlogArticleBindLabel implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 博客标签绑定ID
     */
    @TableId(value = "article_label_id", type = IdType.AUTO)
    private Integer articleLabelId;

    /**
     * 博客ID
     */
    private Integer articleId;

    /**
     * 标签ID
     */
    private Integer labelId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态：正常，删除
     */
    private String tbStatus;


    public Integer getArticleLabelId() {
        return articleLabelId;
    }

    public void setArticleLabelId(Integer articleLabelId) {
        this.articleLabelId = articleLabelId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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
        return "BlogArticleBindLabel{" +
        "articleLabelId=" + articleLabelId +
        ", articleId=" + articleId +
        ", labelId=" + labelId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", tbStatus=" + tbStatus +
        "}";
    }
}
