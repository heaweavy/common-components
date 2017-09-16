package com.github.heaweavy.common.components.webdemo.controllers.authentication;

import com.github.heaweavy.common.components.common.bean.DynamicRegister;
import com.github.heaweavy.common.components.common.helper.Pager;
import com.github.heaweavy.common.components.datasource.admin.entity.User;
import com.github.heaweavy.common.components.webdemo.config.DynamicInitializer;
import com.github.heaweavy.common.components.webdemo.services.admin.UserService;
import com.google.code.kaptcha.servlet.KaptchaExtend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Rogers on 15-3-18.
 */

@Controller
@RequestMapping("/")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private static KaptchaExtend kaptchaExtend = new KaptchaExtend();
    private static final String captchaFieldName = "captcha";

    @Autowired
    private UserService userService;

    @Autowired
    private Credential currentUser;

    @Autowired
    private Pager pager;

    /**
     * 验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest request,
                           HttpServletResponse response) throws Exception{
        KaptchaExtend kaptchaExtend = new KaptchaExtend();
        kaptchaExtend.captcha(request, response);
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) throws Exception {
        model.addAttribute("credential", new Credential());
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String authenticateUser(@ModelAttribute("credential") Credential credential,
                                   BindingResult bindingResult,
                                   HttpServletRequest request,
                                   ModelMap model) throws Exception{
        /*DynamicRegister.instance().register("dynamicAction","com.github.heaweavy.common.components.common.bean.DynamicAction");
        ApplicationContext applicationContext = DynamicInitializer.getApplicationContext();
        DispatcherServlet dispatcherServlet = applicationContext.getBean(DispatcherServlet.class);
        assert dispatcherServlet != null;
        GenericApplicationContext configurableApplicationContext = (GenericApplicationContext) dispatcherServlet.getWebApplicationContext();
        Field refreshField = GenericApplicationContext.class.getDeclaredField("refreshed");
        refreshField.setAccessible(true);
        refreshField.set(configurableApplicationContext,false);
        refreshField.setAccessible(false);
        dispatcherServlet.refresh();*/
        /*Class dispatcherServletClass = DispatcherServlet.class;
        Method method = dispatcherServletClass.getDeclaredMethod("initStrategies", ApplicationContext.class);
        method.setAccessible(true);
        method.invoke(dispatcherServlet, applicationContext);
        method.setAccessible(false);*/
        if(bindingResult.hasErrors()){
            return "login";
        }

        if( !validateCaptcha(request)){
            return  "login";
        }

        if (userService.validateUserByAccount(credential.getUsername(), credential.getPassword())) {
            try {
                User user = userService.getUserByAccount(credential.getUsername());
                this.currentUser.setId(user.getId());
                this.currentUser.setRole(user.getRole());
                this.currentUser.setUsername(credential.getUsername());
                this.currentUser.setPassword(credential.getPassword());
                this.currentUser.setCaptcha(credential.getCaptcha());
                LoginHelper.login(request.getSession(), this.currentUser);
                logger.debug("User is logged in", credential.getUsername());
            } catch (AuthenticationException e) {
                logger.error("User login failed.");
                model.addAttribute("credential", credential);
                return "login";
            }
        }

        return "redirect:/index";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        LoginHelper.logout(session);
        return "redirect:/login";
    }

    @RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
    public String index(ModelMap modelMap){
        return "index";
    }

    /**
     * 验证码校验
     *
     * @param request
     * @return
     */
    public boolean validateCaptcha(HttpServletRequest request) {
        String captchaKeyInSession = kaptchaExtend.getGeneratedKey(request);
        String captchaFromClient = request.getParameter(captchaFieldName);
     
        if (captchaKeyInSession == null || captchaKeyInSession.isEmpty()) {
            return false;
        }

        if (captchaFromClient == null || captchaFromClient.isEmpty()) {
            return false;
        }

        if (!captchaFromClient.equals(captchaKeyInSession)) {
            return false;
        }

        return true;

    }
}
