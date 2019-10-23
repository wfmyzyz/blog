package com.person.blog.tools;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageTools {

    /**
     * 上传单个图片
     * @param file
     * @param filePath
     * @return
     */
    public String uploadImage(MultipartFile file,String filePath){
        String newfilename = "";
        if (!file.isEmpty()){
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
            newfilename = UUID.randomUUID().toString()+suffix;
            try {
                file.transferTo(new File(filePath+newfilename));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return newfilename;
        }
        return null;
    }

    /**
     * 删除单个文件
     * @param filePath
     * @return
     */
    public boolean deleteImage(String filePath){
        File oldfile = new File(filePath);
        boolean flag = oldfile.delete();
        if (!flag){
            return false;
        }
        return true;
    }
}
