package com.github.heaweavy.common.components.common.bean;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by caimb on 2017/3/13.
 */

@Controller
@RequestMapping("/")
public class DynamicAction {
    @RequestMapping(value = "dynamic", method = RequestMethod.GET)
    public String dynamic() {
        System.out.println("成功注册dynamic");
        return "login";
    }
}
