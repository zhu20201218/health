package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 *
 */
public interface CheckItemDao {
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
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

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
     * 检查是否有外键关联
     * @param id
     * @return
     */
    int findByCheckitemId(int id);

    /**
     * 根据id删除检查项
     * @param id
     */
    void deleteById(int id);
}
