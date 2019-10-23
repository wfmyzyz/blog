package com.person.blog.blog.controller.back.article;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.person.blog.blog.domain.BlogArticleBindLabel;
import com.person.blog.blog.domain.BlogArticleLabel;
import com.person.blog.blog.service.impl.BlogArticleBindLabelServiceImpl;
import com.person.blog.blog.service.impl.BlogArticleLabelServiceImpl;
import com.person.blog.tools.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xiong
 * @since 2019-05-26
 */
@RestController
@RequestMapping("admin/admin/articleBindLabel")
public class BackArticleBindLabelController {

    @Autowired
    BlogArticleBindLabelServiceImpl blogArticleBindLabelServiceImpl;
    @Autowired
    BlogArticleLabelServiceImpl blogArticleLabelServiceImpl;

    /**
     * 获取文章绑定标签
     * @param id
     * @return
     */
    @RequestMapping(value = "getArticleBindLabel/{id}",method = RequestMethod.GET)
    public Msg getArticleBindLabel(@PathVariable("id") Integer id){
        List<BlogArticleBindLabel> articleBindLabelList = blogArticleBindLabelServiceImpl.list(new QueryWrapper<BlogArticleBindLabel>().eq("article_id", id).eq("tb_status","正常"));
        if (articleBindLabelList.size()==0){
            return Msg.success().add("data","");
        }
        String labelIds = "";
        for (BlogArticleBindLabel articleBindLabel:articleBindLabelList){
            labelIds += articleBindLabel.getLabelId()+",";
        }
        labelIds = labelIds.substring(0,labelIds.length()-1);
        List<BlogArticleLabel> blogArticleLabelList = blogArticleLabelServiceImpl.list(new QueryWrapper<BlogArticleLabel>().inSql("label_id", labelIds).eq("tb_status","正常"));
        return Msg.success().add("data",blogArticleLabelList);
    }

    /**
     * 获取文章非绑定标签
     * @param id
     * @return
     */
    @RequestMapping(value = "getNoArticleBindLabel/{id}",method = RequestMethod.GET)
    public Msg getNoArticleBindLabel(@PathVariable("id") Integer id){
        List<BlogArticleBindLabel> articleBindLabelList = blogArticleBindLabelServiceImpl.list(new QueryWrapper<BlogArticleBindLabel>().eq("article_id", id).eq("tb_status","正常"));
        if (articleBindLabelList.size()==0){
            List<BlogArticleLabel> blogArticleLabelList = blogArticleLabelServiceImpl.list(new QueryWrapper<BlogArticleLabel>().eq("tb_status","正常"));
            return Msg.success().add("data",blogArticleLabelList);
        }
        String labelIds = "";
        for (BlogArticleBindLabel articleBindLabel:articleBindLabelList){
            labelIds += articleBindLabel.getLabelId()+",";
        }
        labelIds = labelIds.substring(0,labelIds.length()-1);
        List<BlogArticleLabel> blogArticleLabelList = blogArticleLabelServiceImpl.list(new QueryWrapper<BlogArticleLabel>().notInSql("label_id", labelIds).eq("tb_status","正常"));
        System.out.println(blogArticleLabelList);
        return Msg.success().add("data",blogArticleLabelList);
    }

}

