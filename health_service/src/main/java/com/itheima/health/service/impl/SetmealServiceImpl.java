package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.Exception.MyException;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 *
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
         //添加套餐
        setmealDao.add(setmeal);
        //获取添加的套餐的id
        Integer setmealId = setmeal.getId();
        //遍历检查项的id
        if (null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                //添加套餐与检查组的关系
                setmealDao.addSetmealCheckgroup(setmealId,checkgroupId);
            }
        }
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        //分页工具
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断是否有条件，如果有，添加 %
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //调用dao查询
        Page<Setmeal> setmealPage = setmealDao.findByCondition(queryPageBean.getQueryString());

        //返回总记录数和结果集
        return new PageResult<Setmeal>(setmealPage.getTotal(),setmealPage.getResult());
    }

    /**
     * 回显套餐数据
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
     * 根据套餐id查询属于该id的检查组
     * @param id
     * @return
     */
    @Override
    public List<Integer> findSetmealCheckgroup(int id) {
        return setmealDao.findSetmealCheckgroup(id);
    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.修改套餐的内容
        setmealDao.update(setmeal);
        //2.删除套餐与检查组的旧关系
        setmealDao.deleteSetmealCheckgroup(setmeal.getId());
        //3.再添加套餐与检查组的新关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckgroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    /**
     * 通过id删除套餐
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        //查询套餐是否被使用
       int count =  setmealDao.findOrderSetmeal(id);
        //如果使用，抛异常
        if (count > 0){
            throw new MyException("该套餐被使用,不能删除");
        }
        //没有被使用先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckgroup(id);
        //通过id删除套餐
        setmealDao.deleteById(id);
    }

    /**
     * 查询套餐中的图片
     * @return
     */
    @Override
    public List<String> findImg() {
        return setmealDao.findImg();
    }
}
