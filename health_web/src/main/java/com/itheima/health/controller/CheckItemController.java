package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有检查项
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页条件查询
     */

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
       return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     * 根据id查询 回显数据
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
       CheckItem checkItem = checkItemService.findById(id);
       return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 修改检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 根据id删除检查项
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkItemService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);

    }
}
