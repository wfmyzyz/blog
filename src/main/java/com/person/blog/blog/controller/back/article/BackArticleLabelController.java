package com.person.blog.blog.controller.back.article;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.person.blog.blog.domain.BlogArticleBindLabel;
import com.person.blog.blog.domain.BlogArticleLabel;
import com.person.blog.blog.service.impl.BlogArticleBindLabelServiceImpl;
import com.person.blog.blog.service.impl.BlogArticleLabelServiceImpl;
import com.person.blog.tools.LayuiBackData;
import com.person.blog.tools.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xiong
 * @since 2019-05-14
 */
@RestController
@RequestMapping("admin/admin/article/label")
public class BackArticleLabelController {

    @Autowired
    BlogArticleLabelServiceImpl blogArticleLabelServiceImpl;
    @Autowired
    BlogArticleBindLabelServiceImpl blogArticleBindLabelServiceImpl;

    /**
     * 按分页条件获取所有标签数据
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public LayuiBackData getList(@RequestParam("page") Integer page,@RequestParam("limit") Integer limit,@RequestParam(value = "labelId",required = false) String labelId,
                                       @RequestParam(value = "name",required = false) String name,@RequestParam(value = "startTime",required = false) String startTime,
                                       @RequestParam(value = "endTime",required = false) String endTime){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (labelId != null && !labelId.isEmpty()){
            queryWrapper.eq("label_id",labelId);
        }
        if (name != null && !name.isEmpty()){
            queryWrapper.like("name",name);
        }
        if (startTime !=null && endTime != null &&!startTime.isEmpty()&&!endTime.isEmpty()){
            queryWrapper.between("create_time",startTime,endTime);
        }
        queryWrapper.eq("tb_status","正常");
        IPage<BlogArticleLabel> pageList = blogArticleLabelServiceImpl.page(new Page<>(page,limit),queryWrapper);
        return  LayuiBackData.bringData(pageList);
    }

    /**
     * 获取所有的标签
     * @return
     */
    @RequestMapping(value = "getAllLabel",method = RequestMethod.GET)
    public Msg getAllLabel(){
        List<BlogArticleLabel> labelList = blogArticleLabelServiceImpl.list(new QueryWrapper<BlogArticleLabel>().eq("tb_status","正常"));
        if (labelList != null){
            return Msg.success().add("data",labelList);
        }
        return Msg.error().add("error","没有标签数据！");
    }

    /**
     * 添加标签
     * @param label
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Msg layuiBackData(@Valid BlogArticleLabel label, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Msg.resultError(bindingResult);
        }
        List<BlogArticleLabel> blogArticleLabelList = blogArticleLabelServiceImpl.list(new QueryWrapper<BlogArticleLabel>().eq("tb_status","正常"));
        List<BlogArticleLabel> blogArticleLabelList1 = blogArticleLabelList.stream().filter(e->e.getName().equals(label.getName())).collect(Collectors.toList());
        if (blogArticleLabelList1.size()>0){
            return Msg.error().add("error","该标签已存在！");
        }
        boolean flag = blogArticleLabelServiceImpl.save(label);
        if (!flag){
            return Msg.error().add("data","添加失败！请重新添加");
        }
        return  Msg.success();
    }

    /**
     * 修改标签
     * @param label
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Msg updateLabel(@Valid BlogArticleLabel label, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Msg.resultError(bindingResult);
        }
        boolean flag = blogArticleLabelServiceImpl.updateById(label);
        if (!flag){
            return Msg.error().add("error","修改失败！请重新修改");
        }
        return Msg.success();
    }

    /**
     * 获取一条标签数据
     * @param id
     * @return
     */
    @RequestMapping(value = "get/{id}",method = RequestMethod.GET)
    public Msg getLabel(@PathVariable("id") String id){
        BlogArticleLabel blogArticleLabel = blogArticleLabelServiceImpl.getById(id);
        if(blogArticleLabel == null){
            return Msg.error().add("error","没有该标签！");
        }
        return Msg.success().add("data",blogArticleLabel);
    }

    /**
     * 删除单个标签
     * @param id
     * @return
     */
    @Transactional
    @RequestMapping(value = "remove/{id}",method = RequestMethod.GET)
    public Msg removeLabel(@PathVariable("id") String id){
        BlogArticleLabel blogArticleLabel = blogArticleLabelServiceImpl.getById(id);
        blogArticleLabel.setTbStatus("删除");
        boolean flag = blogArticleLabelServiceImpl.updateById(blogArticleLabel);
        if(!flag){
            return Msg.error().add("error","删除失败！");
        }
        List<BlogArticleBindLabel> blogArticleBindLabelList = blogArticleBindLabelServiceImpl.list(new QueryWrapper<BlogArticleBindLabel>().eq("tb_status", "正常").eq("label_id", id));
        for (BlogArticleBindLabel blogArticleBindLabel:blogArticleBindLabelList){
            blogArticleBindLabel.setTbStatus("删除");
        }
        blogArticleBindLabelServiceImpl.updateBatchById(blogArticleBindLabelList);
        return Msg.success().add("data","删除成功！");
    }

    /**
     * 批量删除标签
     * @param labelIds
     * @return
     */
    @Transactional
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    public Msg removeBatchLabel(@RequestBody List<BlogArticleLabel> labelIds){
        List<BlogArticleLabel> blogArticleLabelList = new ArrayList<>();
        String labelIdStr = "(";
        for (BlogArticleLabel label:labelIds){
            label.setTbStatus("删除");
            blogArticleLabelList.add(label);
            labelIdStr += label.getLabelId()+",";
        }
        labelIdStr = labelIdStr.substring(0,labelIdStr.length()-1) + ")";
        boolean flag = blogArticleLabelServiceImpl.updateBatchById(blogArticleLabelList);
        if (!flag){
            return Msg.error().add("error","删除失败！请重新删除");
        }
        List<BlogArticleBindLabel> blogArticleBindLabelList = blogArticleBindLabelServiceImpl.list(new QueryWrapper<BlogArticleBindLabel>().eq("tb_status", "正常").inSql("label_id", labelIdStr));
        for (BlogArticleBindLabel blogArticleBindLabel:blogArticleBindLabelList){
            blogArticleBindLabel.setTbStatus("删除");
        }
        blogArticleBindLabelServiceImpl.updateBatchById(blogArticleBindLabelList);
        return Msg.success().add("data","删除成功！");
    }


}

