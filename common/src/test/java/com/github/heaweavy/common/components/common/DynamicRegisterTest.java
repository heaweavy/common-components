package com.github.heaweavy.common.components.common;

import com.github.heaweavy.common.components.common.bean.DynamicRegister;
import com.github.heaweavy.common.components.common.helper.Pager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * Created by caimb on 2017/3/13.
 */
public class DynamicRegisterTest {

    {
        DynamicRegister register = DynamicRegister.instance();
        register.register("Pager","com.github.heaweavy.common.components.common.helper.Pager");
    }
    @Autowired
    private Pager pager;

    @Test
    public void testRegister() {
        Assert.notNull(pager);

    }
}
