package com.lagou.controller;

import com.lagou.domain.PromotionSpace;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {

    @Autowired
    private PromotionSpaceService promotionSpaceService;

    /*
  获取所有广告位
   */
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace(){
        List<PromotionSpace> allPromotionSpace = promotionSpaceService.findAllPromotionSpace();

        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有广告位成功", allPromotionSpace);
        return responseResult;
    }

    /*
    添加广告位
    后面会进行修改的扩展
     */
    @RequestMapping("/saveOrUpdatePromotionSpace")
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace){

        ResponseResult responseResult;
        if(promotionSpace.getId() == null) {
            promotionSpaceService.savePromotionSpace(promotionSpace);
            responseResult = new ResponseResult(true, 200, "添加广告位成功", null);
        }else{
            promotionSpaceService.updatePromotionSpace(promotionSpace);
            responseResult = new ResponseResult(true, 200, "修改广告位成功", null);
        }
        return responseResult;
    }

    /*
    根据id查询广告位信息
     */
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(Integer id) {
        //实际上得到的是对应int（判断Integer，然后就是String变成int，然后自动装箱）
        PromotionSpace promotionSpace = promotionSpaceService.findPromotionSpaceById(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询具体广告位成功", promotionSpace);
        return responseResult;

    }

}

