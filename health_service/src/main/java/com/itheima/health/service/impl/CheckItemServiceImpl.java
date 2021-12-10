package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.Exception.MyException;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * interfaceClass 指定发布服务的接口
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 添加检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {

        //分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //是否有查询条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //调用dao查询
        Page<CheckItem> checkItemPage = checkItemDao.findByCondition(queryPageBean.getQueryString());
        //总记录数
        long total = checkItemPage.getTotal();
        //结果集
        List<CheckItem> checkItems = checkItemPage.getResult();

        return new PageResult<CheckItem>(total,checkItems);
    }

    /**
     * 根据id查询 回显数据
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
     * 修改检查项
     * @param checkItem
     * @return
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 根据id删除检查项
     * @param id
     */
    @Override
    public void deleteById(int id) {
         int count = checkItemDao.findByCheckitemId(id);
         //有外键关联，不能删除，并且抛出异常
         if (count > 0){
             throw new MyException("该检查项被检查组使用了，不能删除");
         }
         //没有外键关联，可以删除
        checkItemDao.deleteById(id);
    }

}
