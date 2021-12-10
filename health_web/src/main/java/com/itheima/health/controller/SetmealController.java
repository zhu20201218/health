package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 *
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);

    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        // 获取文件名，获取它的后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 产生唯一名称，拼接后缀名，就是图片名
        String imgName = UUID.randomUUID().toString() + suffix;
        // 调用QiNiuUtils上传
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),imgName);
            // 成功后返回图片名和域名
            Map<String,String> map = new HashMap<String, String>();
            map.put("imgName",imgName);
            map.put("domain",QiNiuUtils.DOMAIN);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            //e.printStackTrace();
            log.error("上传失败",e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setmealService.add(setmeal,checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页条件查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> setmeals = setmealService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeals);
    }

    /**
     * 回显套餐数据
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        Setmeal setmeal = setmealService.findById(id);
        //域名
        String domain = QiNiuUtils.DOMAIN;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("domain",domain);
        map.put("setmeal",setmeal);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,map);
    }

    /**
     * 根据套餐id查询属于该id的检查组
     */
    @GetMapping("/findSetmealCheckgroup")
     public Result findSetmealCheckgroup(int id){
        List<Integer> checkitemIds = setmealService.findSetmealCheckgroup(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitemIds);
      }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
      @PostMapping("/update")
     public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
          setmealService.update(setmeal,checkgroupIds);
          return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
      }

    /**
     * 通过id删除套餐
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        setmealService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
