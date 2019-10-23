package com.person.blog.blog.controller.back.api;

import com.person.blog.tools.ImageTools;
import com.person.blog.tools.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("admin/admin")
public class UploadController {

    @Value("${myuploadurl.url}")
    private String filePath;

    @Autowired
    ImageTools imageTools;

    /**
     * 上传博客头图
     * @param file
     * @param imagePath
     * @return
     */
    @RequestMapping(value = "article/uploadHead",method = RequestMethod.POST)
    public Msg uploadHead(@RequestParam("file") MultipartFile file,@RequestParam("imagePath") String imagePath){
        if (file.isEmpty()){
            return Msg.error().add("error","没有图片文件！");
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
        if (!suffix.toLowerCase().equals(".png")&&!suffix.toLowerCase().equals(".jpg")&&!suffix.toLowerCase().equals(".gif")&&!suffix.toLowerCase().equals(".svg")){
            return Msg.error().add("error","图片仅支持jpg、png、gif、svg格式！");
        }
        /*if (!imagePath.isEmpty()){
            String deletename = imagePath.substring(imagePath.lastIndexOf("/"),imagePath.length());
            String oldfile = filePath+"/admin/articleHead/"+deletename;
            imageTools.deleteImage(oldfile);
        }*/
        String flagName = imageTools.uploadImage(file,filePath+"/admin/articleHead/");
        if (flagName == null){
            return Msg.error().add("error","上传失败！请重新上传");
        }
        return Msg.success().add("data","/outimg/admin/articleHead/"+flagName);
    }

    /**
     * 删除博客头图
     * @param imagePath
     * @return
     */
    @RequestMapping(value = "article/deleteHead",method = RequestMethod.POST)
    public Msg deleteHead(@RequestParam("imagePath") String imagePath){
        if (!imagePath.isEmpty()){
            String deletename = imagePath.substring(imagePath.lastIndexOf("/"),imagePath.length());
            String oldfile = filePath+"/admin/articleHead/"+deletename;
            boolean flag = imageTools.deleteImage(oldfile);
            if (!flag){
                return Msg.error().add("error","删除失败！请重新上传");
            }
            return Msg.success().add("data","删除成功！");
        }
        return Msg.error().add("error","文件路径为空！");
    }

    /**
     * 上传文章内图片
     * @param files
     * @return
     */
    @RequestMapping(value = "article/uploadInto",method = RequestMethod.POST)
    public Map<String,Object> uploadInto(@RequestParam("file") MultipartFile[] files){
        Map<String,Object> map = new HashMap<>();
        if (files.length==0){
            map.put("errno","1");
            map.put("error","没有文件！");
            return map;
        }
        List<String> urlList = new ArrayList<>();
        for (MultipartFile file:files){
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
            if (!suffix.toLowerCase().equals(".png")&&!suffix.toLowerCase().equals(".jpg")&&!suffix.toLowerCase().equals(".gif")&&!suffix.toLowerCase().equals(".svg")){
                continue;
            }
            String flagName = imageTools.uploadImage(file,filePath+"/admin/article/");
            if (flagName != null){
                urlList.add("/outimg/admin/article/"+flagName);
            }
        }
        map.put("errno",0);
        map.put("data",urlList);
        return map;
    }
}
