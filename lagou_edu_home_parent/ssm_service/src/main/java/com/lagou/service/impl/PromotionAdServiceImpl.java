package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.PromotionAdMapper;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVo;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class PromotionAdServiceImpl implements PromotionAdService {

    @Autowired
    private PromotionAdMapper promotionAdMapper;

    /*
 分页查询广告信息
  */
    @Override
    public PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdVo promotionAdVo) {
        //使用分页，那么在进行sql语句执行时，会进行对应参数，来实现limit语句操作拼接，也就是添加对应的limit的sql语句
        PageHelper.startPage(promotionAdVo.getCurrentPage(),promotionAdVo.getPageSize());

        List<PromotionAd> allPromotionAdByPage = promotionAdMapper.findAllPromotionAdByPage(promotionAdVo);

        //上面我们根据对应分页操作获得了对应数据了，但是根据需求，我们还需要一些其他数据，对应数据如下
        //要获得这些数据，要通过参数的地址（也就是上面list集合的地址），来确定获得对应数据，因为我们使用分页时
        //也就是进行sql语句的拼接时，会保留这个获得数据的地址信息
        //当确定了是这个地址，那么可以获得我们操作的分页所得到的数据，因为要得到分页的数据
        //一般都会有总条数，当前页显示多少条，总页数，当前是第几页，当前页这些数据等等
        //我们在拼接之前，都会对传递的startPage方法的参数进行操作，而进行这些操作，就会需要对应的数据，如上面的对应数据
        //以及获得对应的数据（如list集合）等等，而我们进行对应sql执行时，拼接sql，返回执行的数据
        //在返回之前，将数据就与获得的地址进行绑定了，也就是说，只能通过这个地址来获得这些数据
        //所以下面的参数就是获得的数据（这里是list集合），但一般只会操纵list集合（源码里只要list集合）
        //因为不是多条数据的，是没有分页的必要的，所以PageInfo类只操作了list集合
        PageInfo<PromotionAd> promotionAdPageInfo = new PageInfo<>(allPromotionAdByPage);

        return promotionAdPageInfo; //返回对应的数据
    }

    //添加广告
    @Override
    public void savePromotionAd(PromotionAd promotionAd) {

        //补全信息，这里我们可以说一下，为什么需要在后台补全信息
        //首先我们需要直到，前端获取的时间数据，若我们在前端直接加上信息，那么也就是需要多加几个参数
        //而后端只需要set方法一下，很明显方便一点，但无论哪一种，都是进行信息的操作
        //而使用方便，也就是维护性
        //也是尽量的好（当然也是可以不好的，或者故意不好的，这就是看你如何操作了，嘿嘿嘿）
        Date date = new Date();
        promotionAd.setCreateTime(date);
        promotionAd.setUpdateTime(date);

        promotionAdMapper.savePromotionAd(promotionAd);
    }

    /*
    根据id查询广告
     */
    @Override
    public PromotionAd findPromotionAdById(Integer id) {
        PromotionAd promotionAdById = promotionAdMapper.findPromotionAdById(id);

        return promotionAdById;
    }

    /*
 修改广告
  */
    @Override
    public void updatePromotionAd(PromotionAd promotionAd) {
        promotionAd.setUpdateTime(new Date());

        promotionAdMapper.updatePromotionAd(promotionAd);

    }

    /*
   广告动态上下线
    */
    @Override
    public void updatePromotionAdStatus(Integer id, Integer status) {
        PromotionAd promotionAd = new PromotionAd();
        promotionAd.setId(id);
        promotionAd.setStatus(status);
        promotionAd.setUpdateTime(new Date());
        promotionAdMapper.updatePromotionAdStatus(promotionAd);
    }
}
