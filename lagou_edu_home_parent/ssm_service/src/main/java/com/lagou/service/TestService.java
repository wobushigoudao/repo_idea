package com.lagou.service;



import com.lagou.domain.Test;


import java.util.List;

/**
 *
 */
public interface TestService {

    /*
    对test表进行查询所有
     */
    //可以发现也可以使用Test类的，这是因为依赖是有传递的
    //当dao得到domain的jar包时，那么service得到dao就也会得到这个jar包
    //但需要dao先得到，然后service才可得到，所以说，最底层的一定是jar包的存放处
    //其实可以将导入理解成一个文件，文件里有文件，将其所有jar包放在这个工程里面（当然只会显示文件，不会显示对应jar包）
    //那为什么不显示我们导入的包呢，那是因为这是当前maven创建的jar包，当前maven是知道的
    //当去其他maven导入时，就会出现
    //当然pom.xml虽然可以帮你导入到项目里，但删除对应配置刷新时，也是会帮你移除的
    public List<Test> findAllTest();
}
