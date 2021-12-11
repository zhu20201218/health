package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class CleanImgJob {

    @Reference
    private SetmealService setmealService;

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);

    @Scheduled(initialDelay = 3000, fixedDelay = 1800000)
    public void cleanImg() {
        log.info("执行清理图片任务");
        //查询七牛上的所有图片
        List<String> img7Niu = QiNiuUtils.listFile();
        log.debug("7牛上有{}张照片", img7Niu.size());
        //查询数据库有多少张照片
        List<String> imgDB = setmealService.findImg();
        log.debug("数据库中有{}张照片", null==imgDB?0:imgDB.size());
        //在七牛中移除数据库中有的照片 剩下七牛中有的就是垃圾照片
        img7Niu.removeAll(imgDB);
        log.debug("要删除的照片有{}张",img7Niu.size());
        //删除
        String[] deleteImg = img7Niu.toArray(new String[]{});
        QiNiuUtils.removeFiles(deleteImg);
        log.info("删除{}张照片成功",img7Niu.size());
    }
}
