package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.Exception.MyException;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        // 遍历List<OrderSetting>
        for (OrderSetting orderSetting : orderSettingList) {
            // 通过日期查询预约设置表
            OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            // 如果存在预约设置
            if (null != osInDB){
                // 判断更新后的最大数是否大等于已预约人数
                if (osInDB.getNumber() < orderSetting.getReservations()){
                    // 小于，报错 已预约数超过最大预约数，接口异常声明
                    throw new MyException("最大预约数小于已预约数，导入失败");
                }
                // 大于，则可以更新最大预约数
                orderSettingDao.updateNumber(orderSetting);
                // 不存在，则添加预约设置
                orderSettingDao.add(orderSetting);
                // 事务控制

            }

        }
    }
}
