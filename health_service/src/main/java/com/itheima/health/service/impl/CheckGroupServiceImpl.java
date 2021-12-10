package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.Exception.MyException;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 *
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取添加的检查组的id
        Integer checkGroupId = checkGroup.getId();
        //添加检查组与检查项的关系
        if (null != checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupIdCheckItemId(checkGroupId, checkitemId);
            }
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //分页工具 参数为当前页和每页大小
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //如果不为空，拼接 %
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckGroup> checkGroupPage = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(checkGroupPage.getTotal(),checkGroupPage.getResult());
    }

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 通过检查组id检查属于该id的检查项的id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 修改检查组信息
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组信息
         checkGroupDao.update(checkGroup);
         //获取检查组id
        Integer checkGroupId = checkGroup.getId();
        //通过检查组id删除检查组与检查项现有的关系
        checkGroupDao.deleteByCheckGroupId(checkGroupId);
        //通过检查组id添加检查组与检查项的关系
        if (null != checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupIdCheckItemId(checkGroupId, checkitemId);
            }
        }
    }

    /**
     * 通过id删除检查组
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        //通过id查询检查组是否被套餐使用
        int count = checkGroupDao.findCountByCheckgroupId(id);
        //被使用，抛异常
        if (count > 0) {
            throw new MyException("该检查组被套餐使用，不能删除");
        }
        //没有被使用
        //先删除检查组和检查项的关系
        checkGroupDao.deleteByCheckGroupId(id);
        //再删除检查组
        checkGroupDao.deleteById(id);
    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
