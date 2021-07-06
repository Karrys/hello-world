package com.kay.demo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.kay.demo.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void testa() {
        String url = "http://localhost:9001";
        String result = HttpUtil.get(url);
        System.out.println(result);
    }

    @Test
    public void testb() {
        RedisUtil redisUtil = new RedisUtil(redisTemplate);

        List<String> valueList = new ArrayList<>();
        for(int i=0;i<101;i++){
            String date = i<64?"20210704":"20210705";
            valueList.add(date + "_" + i + "ts");
        }
        int fileCount = 10;
        for(String value : valueList)
        {
            String deviceChannel = "d001_c001";
            String redisKey = "device_channel:" + deviceChannel;
            Set<Object> tsFileFullPathList = redisUtil.zRange(redisKey, 0, fileCount - 1);
            if (!CollectionUtils.isEmpty(tsFileFullPathList)) {
                System.out.println("size:{}" + tsFileFullPathList.size());
                String curDate = value.substring(0,8);
                String lastDate = redisUtil.get(redisKey+1 ).toString();
                int a = DateUtil.compare(DateUtil.parse(curDate, DatePattern.PURE_DATE_PATTERN),DateUtil.parse(lastDate,DatePattern.PURE_DATE_PATTERN));
               System.err.println("当期日期  上次日期" + a);
                if (a>0 || tsFileFullPathList.size() >= fileCount) {
                    redisUtil.del(redisKey);
                    StringBuilder sb = new StringBuilder("concat:");
                    List<String> tsList = new ArrayList(tsFileFullPathList);
                    for (String ts : tsList) {
                        sb.append(ts).append("|");
                    }
                    String concatStr = sb.substring(0, sb.length() - 1);
                    System.out.println("concatStr:" + concatStr);
                }
            }
            long l = System.currentTimeMillis();
            redisUtil.zAdd(redisKey, value, l);
            redisUtil.set(redisKey+ 1,value.substring(0,8),-1);
        }
    }

    @Test
    public void testc() {
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
            String deviceChannel = "d001_c001";
            String redisKey = "device_channel:" + deviceChannel;
            System.out.println(redisUtil.zRevRange(redisKey, 0, 0));


    }

}
