package com.person.blog.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xiong
 * @since 2019-05-13
 */
public class BlogAdmin implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 管理员账户ID
     */
    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    /**
     * 管理员账户
     */
    private String adminname;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 盐值加密
     */
    private String salt;


    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "BlogAdmin{" +
        "adminId=" + adminId +
        ", adminname=" + adminname +
        ", password=" + password +
        ", salt=" + salt +
        "}";
    }
}
