package com.person.blog.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xiong
 * @since 2019-05-26
 */
public class BlogArticle implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 博客ID
     */
    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;

    /**
     * 博客标题
     */
    @NotBlank(message = "博客标题不可为空！")
    private String title;

    /**
     * 博客头图
     */
    @NotBlank(message = "博客头图不可为空！")
    private String headImage;

    /**
     * 博客简介
     */
    @NotBlank(message = "博客简介不可为空！")
    private String introduce;

    /**
     * 博客内容
     */
    @NotBlank(message = "博客内容不可为空！")
    private String text;

    /**
     * 博客作者
     */
    private String author;

    /**
     * 浏览人数
     */
    private String browse;

    /**
     * 点赞人数
     */
    private String praise;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 状态：正常，删除
     */
    private String tbStatus;


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BlogArticle{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", headImage='" + headImage + '\'' +
                ", introduce='" + introduce + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", browse='" + browse + '\'' +
                ", praise='" + praise + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", tbStatus='" + tbStatus + '\'' +
                '}';
    }
}
