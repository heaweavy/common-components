package com.github.heaweavy.common.components.webdemo.controllers.authentication;

import com.github.heaweavy.common.components.datasource.admin.entity.User;
import com.github.heaweavy.common.components.webdemo.services.admin.UserService;
import com.google.code.kaptcha.servlet.KaptchaExtend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
