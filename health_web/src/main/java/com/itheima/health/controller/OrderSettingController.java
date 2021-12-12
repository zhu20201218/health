package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            //接收上传的文件，MultipartFile 参数名为excelFile
            //调用POIUtils解析excel，List<String[]>
            List<String[]> orderInfoStringArrList = POIUtils.readExcel(excelFile);
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            //再将List<String[]> 转成List<OrderSetting>
            List<OrderSetting> orderSettingList = orderInfoStringArrList.stream().map(orderInfoStringArr -> {
                OrderSetting os = new OrderSetting();
                try {
                    //格式化日期
                    os.setOrderDate(sdf.parse(orderInfoStringArr[0]));
                } catch (ParseException e) {
                }
                //最大预约数
                os.setNumber(Integer.parseInt(orderInfoStringArr[1]));
                return os;
            }).collect(Collectors.toList());
            //调用服务导入
            orderSettingService.add(orderSettingList);
        } catch (IOException e) {
            log.error("导入失败", e);
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        //返回操作的结果给页面
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }
}
