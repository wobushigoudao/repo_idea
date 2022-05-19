package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVo;

import java.util.List;

/**
 *
 */
public interface PromotionAdService {

    /*
 分页查询广告信息
  */
    public PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdVo promotionAdVo);

    /*
    添加广告
     */
    public void savePromotionAd(PromotionAd promotionAd);

    /*
    根据id查询广告
     */
    public PromotionAd findPromotionAdById(Integer id);

    /*
 修改广告
  */
    public void updatePromotionAd(PromotionAd promotionAd);

    /*
    广告动态上下线
     */
    public void updatePromotionAdStatus(Integer id, Integer status);
    //在这里说明一下，之所以我们在dao层使用类，主要是因为sql语句的原因，使用类比使用多个参数方便
    //这里由于基本不会修改参数，所以我们进行固定的参数设置
    //而findAllPromotionAdByPage方法参数是类，主要是防止突然需要其他参数，使得变得修改麻烦
}
