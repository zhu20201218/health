package com.itheima.health.service;

import com.itheima.health.Exception.MyException;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 *
 */
public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

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
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup,Integer[] checkitemIds);

    /**
     * 通过id删除检查组
     * @param id
     */
    void deleteById(int id) throws MyException;

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
