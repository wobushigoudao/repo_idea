package com.lagou.dao;




import com.lagou.domain.Test;

import java.util.List;

/**
 *
 */
public interface TestMapper {
    /*
    对test表进行查询所有
    可以发现，在我们进行对ssm-domain进行install命令使得变成jar包后，导入了进来
    使得可以使用对应的Text了，jar包里面存放了对应的class文件，导入后maven根据一系列操作
    使得可以被提示，以及导入操作，而我们复制过来的class，一般很难在编译的时候，放入对应类里面
    所有基本操作不了（没有导入对应包）
    当自己写的类于jar包的类同名时，自己的类会先加载，根据class加载顺序
    当自己写的类先加载时，那么后面就不会加载同名类了
     */

  public List<Test> findAllTest();




}
