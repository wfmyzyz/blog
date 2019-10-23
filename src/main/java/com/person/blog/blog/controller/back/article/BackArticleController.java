package com.person.blog.blog.controller.back.article;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.person.blog.blog.domain.BlogArticle;
import com.person.blog.blog.domain.BlogArticleBindLabel;
import com.person.blog.blog.domain.BlogArticleLabel;
import com.person.blog.blog.service.impl.BlogArticleBindLabelServiceImpl;
import com.person.blog.blog.service.impl.BlogArticleServiceImpl;
import com.person.blog.common.JsonResult;
import com.person.blog.tools.LayuiBackData;
import com.person.blog.tools.Msg;
import com.person.blog.tools.UserToos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("admin/admin/article")
public class BackArticleController {

    @Autowired
    BlogArticleServiceImpl blogArticleServiceImpl;
    @Autowired
    BlogArticleBindLabelServiceImpl blogArticleBindLabelServiceImpl;
    @Autowired
    UserToos userToos;

    /**
     * 按分页条件获取博客
     * @param page
     * @param limit
     * @param articleId
     * @param title
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "getArticleList",method = RequestMethod.GET)
    public LayuiBackData getArticleList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam(value = "articleId",required = false) String articleId,
                                 @RequestParam(value = "title",required = false) String title, @RequestParam(value = "startTime",required = false) String startTime,
                                 @RequestParam(value = "endTime",required = false) String endTime){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (articleId != null && !articleId.isEmpty()){
            queryWrapper.eq("article_id",articleId);
        }
        if (title != null && !title.isEmpty()){
            queryWrapper.like("title",title);
        }
        if (startTime !=null && endTime != null &&!startTime.isEmpty()&&!endTime.isEmpty()){
            queryWrapper.between("create_time",startTime,endTime);
        }
        queryWrapper.eq("tb_status","正常");
        IPage<BlogArticleLabel> pageList = blogArticleServiceImpl.page(new Page<>(page,limit),queryWrapper);
        return  LayuiBackData.bringData(pageList);
    }

    /**
     * 根据ID获取一条博客
     * @param id
     * @return
     */
    @RequestMapping(value = "get/{id}",method = RequestMethod.GET)
    public Msg getOneArticle(@PathVariable("id") Integer id){
        BlogArticle article = blogArticleServiceImpl.getById(id);
        if (article == null){
            return Msg.error().add("error","获取失败！");
        }
        return Msg.success().add("data",article);
    }

    /**
     * 添加博客
     * @param article
     * @param bindingResult
     * @param labelIds
     * @return
     */
    @Transactional
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Msg add(@Valid BlogArticle article, BindingResult bindingResult, String labelIds, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return Msg.resultError(bindingResult);
        }
        //article.setAuthor(userToos.getUsername(request));
        article.setAuthor("admin");
        boolean flag = blogArticleServiceImpl.save(article);
        if (!flag){
            return Msg.error().add("error","博客添加错误！");
        }
        List<BlogArticleBindLabel> articleBindLabels = new ArrayList<>();
        String[] labelIdArr = labelIds.split(",");
        for (String labelId:labelIdArr){
            BlogArticleBindLabel articleBindLabel = new BlogArticleBindLabel();
            articleBindLabel.setArticleId(article.getArticleId());
            articleBindLabel.setLabelId(Integer.parseInt(labelId));
            articleBindLabels.add(articleBindLabel);
        }
        blogArticleBindLabelServiceImpl.saveBatch(articleBindLabels);
        return Msg.success().add("success","博客添加成功！");
    }

    /**
     * 修改博客
     * @param article
     * @param bindingResult
     * @param labelIds
     * @return
     */
    @Transactional
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Msg update(@Valid BlogArticle article, BindingResult bindingResult, String labelIds, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return Msg.resultError(bindingResult);
        }
        boolean flag = blogArticleServiceImpl.updateById(article);
        if (!flag){
            return Msg.error().add("error","修改失败！请重新修改");
        }
        List<String> labelIdList = Arrays.asList(labelIds.split(","));
        List<BlogArticleBindLabel> addBlogArticleBindLabelList = new ArrayList<>();
        List<BlogArticleBindLabel> blogArticleBindLabelList = blogArticleBindLabelServiceImpl.list(new QueryWrapper<BlogArticleBindLabel>().eq("article_id", article.getArticleId()).eq("tb_status", "正常"));
        for (BlogArticleBindLabel blogArticleBindLabel:blogArticleBindLabelList){
            blogArticleBindLabel.setTbStatus("删除");
        }
        blogArticleBindLabelServiceImpl.updateBatchById(blogArticleBindLabelList);
        for (String labelId:labelIdList){
            BlogArticleBindLabel blogArticleBindLabel = new BlogArticleBindLabel();
            blogArticleBindLabel.setArticleId(article.getArticleId());
            blogArticleBindLabel.setLabelId(Integer.parseInt(labelId));
            addBlogArticleBindLabelList.add(blogArticleBindLabel);
        }
        blogArticleBindLabelServiceImpl.saveBatch(addBlogArticleBindLabelList);
        return Msg.success().add("success","修改成功！");
    }

    /**
     * 根据博客ID删除博客
     * @param id
     * @return
     */
    @Transactional
    @RequestMapping(value = "remove/{id}",method = RequestMethod.GET)
    public Msg remove(@PathVariable("id") Integer id){
        BlogArticle article = blogArticleServiceImpl.getById(id);
        article.setTbStatus("删除");
        boolean flag = blogArticleServiceImpl.updateById(article);
        if (!flag){
            return Msg.error().add("error","删除失败！请重新删除");
        }
        List<BlogArticleBindLabel> articleBindLabellist = blogArticleBindLabelServiceImpl.list(new QueryWrapper<BlogArticleBindLabel>().eq("article_id", id).eq("tb_status", "正常"));
        for (BlogArticleBindLabel articleBindLabel:articleBindLabellist){
            articleBindLabel.setTbStatus("删除");
        }
        boolean flagLabel = blogArticleBindLabelServiceImpl.updateBatchById(articleBindLabellist);
        return Msg.success().add("success","删除成功！");
    }

    /**
     * 根据ID批量删除博客
     * @param blogArticleList
     * @return
     */
    @Transactional
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    public Msg removeBatch(@RequestBody List<BlogArticle> blogArticleList){
        List<Integer> articleIdList = new ArrayList<>();
        for (BlogArticle blogArticle:blogArticleList){
            articleIdList.add(blogArticle.getArticleId());
        }
        List<BlogArticle> articleList = blogArticleServiceImpl.list(new QueryWrapper<BlogArticle>().eq("tb_status", "正常").in("article_id", articleIdList));
        for (BlogArticle blogArticle:articleList){
            blogArticle.setTbStatus("删除");
        }
        boolean flag = blogArticleServiceImpl.updateBatchById(articleList);
        if (flag){
            for (Integer articleId:articleIdList){
                List<BlogArticleBindLabel> articleBindLabellist = blogArticleBindLabelServiceImpl.list(new QueryWrapper<BlogArticleBindLabel>().eq("article_id", articleId).eq("tb_status", "正常"));
                for (BlogArticleBindLabel articleBindLabel:articleBindLabellist){
                    articleBindLabel.setTbStatus("删除");
                }
                if (articleBindLabellist.size()!=0){
                    blogArticleBindLabelServiceImpl.updateBatchById(articleBindLabellist);
                }
            }
            return Msg.success().add("success","删除成功！");
        }
        return Msg.error().add("error","删除失败！请重新删除");
    }
}

