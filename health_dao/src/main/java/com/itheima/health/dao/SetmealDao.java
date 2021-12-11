package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface SetmealDao {

    /**
     * //添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查组的关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckgroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findByCondition(String queryString);

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
     * 修改套餐的内容
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除套餐与检查组的旧关系
     * @param id
     */
    void deleteSetmealCheckgroup(Integer id);


    /**
     * 通过id删除套餐
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询套餐是否被使用
     * @param id
     * @return
     */
    int findOrderSetmeal(int id);

    /**
     * 查询套餐中的图片
     * @return
     */
    List<String> findImg();
}
