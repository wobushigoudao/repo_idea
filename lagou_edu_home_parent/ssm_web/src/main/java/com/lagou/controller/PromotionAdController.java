package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 */
@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    /*
    广告分页查询
     */
    //实际上注解也不一定非得写在所操作的操作对象上面，只要符合操作对象是该注解的下一个就可以，所以变量左边可以写上注解
    //又或者@RequestMapping("/findAllPromotionAdByPage") public ResponseResult...（省略）这样的写法
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage(PromotionAdVo promotionAdVo){
        PageInfo<PromotionAd> allPromotionAdByPage = promotionAdService.findAllPromotionAdByPage(promotionAdVo);
        //allPromotionAdByPage包含了其他分页数据和查询到的数据
        ResponseResult responseResult = new ResponseResult(true, 200, "广告分页查询成功", allPromotionAdByPage);

        return responseResult;
    }

    /*
   广告图片上传
    */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult PromotionAdUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //判断接收到的上传文件是否为空
        //file.isEmpty()判断对应的数据是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
            //主动抛出异常，而不用等出现异常后执行对应异常信息（如try）
            //当然使用这个方法时也是需要对应处理异常的
        }

        //获取项目部署路径
        //D:\apache-tomcat-8.5.56\webapps\ssm_web\
        String realPath = request.getServletContext().getRealPath("/");
        //D:\apache-tomcat-8.5.56\webapps\
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        //获取原文件名
        //如lagou.jpg
        String originalFilename = file.getOriginalFilename();
        //让文件名基本不重复，一个用户基本不可能会有相同的名称
        //多个用户可能会有，但是用户之间肯定是不可互相访问的，所以这里就相当于设置了文件名不重复
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //得到后缀名，用时间戳当名称
        //如1651975001568.jpg

        //开始文件上传
        // D:\apache-tomcat-8.5.56\webapps\ upload\
        //注意：注释里面当出现\ u时，必须要有数，否则报错，所以上面我在\和u之间加了一个空格
        //\ u是转义字符，表示后面跟一个十六进制数,通过这个十六进制数来指定一个字符，若不是则报错
        String uploadPath = substring + "upload\\";
        //加上/也可以，但为了符号windows的目录化，就使用\\了，网页就是/
        File file1 = new File(uploadPath, newFileName);

        //如果目录不存在则创建目录（判断有没有父路径uploadPath，没有则创建）
        if (!file1.getParentFile().exists()) {
            file1.getParentFile().mkdirs();
            System.out.println("创建目录" + file1);
        }

        //图片进行上传
        file.transferTo(file1);

        //将文件名和文件路径返回给前端，使得前端显示图片
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("fileName", newFileName);
        //文件名，如1651975001568.jpg
        objectObjectHashMap.put("filePath", "http://localhost:8080/upload/" + newFileName);
        //在服务器上的访问路径，也就是通过端口访问服务器资源的路径

        ResponseResult responseResult = new ResponseResult(true, 200, "图片上传成功", objectObjectHashMap);

        return responseResult; //会解析集合的，也就是将map进行解析



    }

    /*
    添加广告 后续进行修改扩展
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd){
        ResponseResult responseResult;
        if(promotionAd.getId() == null) {
            promotionAdService.savePromotionAd(promotionAd);
            responseResult= new ResponseResult(true, 200, "添加广告成功", null);

        }else{
            promotionAdService.updatePromotionAd(promotionAd);
            responseResult= new ResponseResult(true, 200, "修改广告成功", null);

        }
        return responseResult;
    }

    /*
    根据id查询广告
     */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(Integer id){

        PromotionAd promotionAdById = promotionAdService.findPromotionAdById(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询具体广告成功", promotionAdById);
        return responseResult;
    }

     /*
   广告动态上下线
    */
    @RequestMapping("/updatePromotionAdStatus")
     public ResponseResult updatePromotionAdStatus(Integer id,Integer status){
         promotionAdService.updatePromotionAdStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true, 200, "广告动态上下线成功", null);
        return responseResult;

     }
}
