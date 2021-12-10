package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface CheckGroupDao {
    /**
     *  添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项的关系
     * @param checkGroupId
     * @param checkitemId
     */
    void addCheckGroupIdCheckItemId(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过检查组id检查属于该id的检查项的id
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 修改检查组信息
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 通过检查组id删除检查组与检查项现有的关系
     * @param checkGroupId
     */
    void deleteByCheckGroupId(Integer checkGroupId);


    /**
     * 通过id查询检查组是否被套餐使用
     * @param id
     * @return
     */
    int findCountByCheckgroupId(int id);

    /**
     * 通过id删除检查组
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
