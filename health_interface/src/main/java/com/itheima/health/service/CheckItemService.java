package com.itheima.health.service;

import com.itheima.health.Exception.MyException;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 *
 */
public interface CheckItemService {
    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查询 回显数据
     * @param id
     * @return
     */
    CheckItem findById(int id);


    /**
     * 修改检查项
     * @param checkItem
     * @return
     */
    void update(CheckItem checkItem);

    /**
     * 根据id删除检查项  必须再接口方法上抛出异常，controller才能接收异常
     * @param id
     */
    void deleteById(int id) throws MyException;
}
