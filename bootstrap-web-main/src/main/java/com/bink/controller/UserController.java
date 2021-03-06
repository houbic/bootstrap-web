package com.bink.controller;

import com.bink.base.UserService;
import com.bink.constant.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by chenbinghao on 16/9/1.下午3:45
 */
@Controller
@RequestMapping("bootstrap")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LogManager.getLogger();

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "checkLogin", method = RequestMethod.POST)
    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("user :{}, log in", username);
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        logger.debug("session id :{}", session.getId());
        int resultCode = userService.checkLogin(username, password);
        response.getWriter().print(resultCode);

    }

    @RequestMapping("LogOut")
    public String logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        String username = (String)httpSession.getAttribute("username");
        logger.info("user[{}] logOut", username);
        httpSession.invalidate();
        return "login";
    }

}
