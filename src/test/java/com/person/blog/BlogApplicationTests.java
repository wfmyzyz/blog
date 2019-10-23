package com.person.blog;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        String aaabbbccc = xiaoChu("aaacbsbbccc");
        System.out.println(aaabbbccc);
    }

    public String xiaoChu(String str){
        String[] split = str.split("");
        List<String> stringList = new ArrayList<>(Arrays.asList(split));
        for (int i=0;i<stringList.size()-1;i++){
            if (stringList.get(i).toLowerCase().equals(stringList.get(i+1).toLowerCase())){
                stringList.remove(i);
                i--;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringList.forEach(e->{
            stringBuilder.append(e);
        });
        return new String(stringBuilder);
    }

    public String[] geniterStr(int sum,int strSum){
        String[] charStr = {"a","b","c"};
        String[] numStr = {"1","2","3"};
        Set<String> set = new HashSet<>();
        while (set.size()<sum){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<strSum;i++){
                Random random = new Random();
                if (i%2 == 0){
                    stringBuilder.append(charStr[random.nextInt(charStr.length-1)]);
                }else {
                    stringBuilder.append(numStr[random.nextInt(numStr.length-1)]);
                }
            }
            set.add(new String(stringBuilder));
        }
        List<String> strList = new ArrayList<>();
        set.forEach(e->{
            strList.add(e);
        });
        String[] objects = new String[strList.size()];
        strList.toArray(objects);
        return objects;
    }

}
