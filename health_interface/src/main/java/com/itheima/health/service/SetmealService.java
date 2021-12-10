package com.itheima.health.service;

import com.itheima.health.Exception.MyException;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 *
 */
public interface SetmealService {

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 回显套餐数据
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 根据套餐id查询属于该id的检查组
     * @param id
     * @return
     */
    List<Integer> findSetmealCheckgroup(int id);

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 通过id删除套餐
     * @param id
     */
    void deleteById(int id) throws MyException;
}
