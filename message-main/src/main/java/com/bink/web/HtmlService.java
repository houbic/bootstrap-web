package com.bink.web;

import com.bink.base.ManagerService;
import com.bink.entity.Menu;
import com.bink.entity.User;
import com.bink.fastjson.JsonMapper;
import com.bink.manager.ManagerServiceImpl;
import com.bink.tools.SpringContextUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenbinghao on 16/8/27.
 */
@Component
public class HtmlService {

    @Autowired
    private ManagerServiceImpl managerService;

    private Logger logger = LogManager.getLogger();

    private void buildOrder(StringBuilder builder, List<Menu> menuList, int order) {

        // 获取order等级子菜单
        TreeMap<Integer, Menu> menuTreeMap = new TreeMap<>();
        menuList.forEach(menu -> {
            if (menu.getParentId() == order) {
                menuTreeMap.put(menu.getMenuId(), menu);
            }
        });

        // 有子菜单, 遍历所有子菜单
        for (Map.Entry<Integer, Menu> entry : menuTreeMap.entrySet()) {
            Menu menu = entry.getValue();
            // 如果此菜单有html处理地址, 表示此菜单为处理菜单, 不存在子菜单
            if (!StringUtils.isEmpty(menu.getHtmlPath())) {
                builder.append("<li><a href=\"javascript:void(0)\" onclick=\"one('").
                        append(menu.getHtmlPath()).
                        append("')\">").
                        append(menu.getMenuName()).
                        append("</a></li>\n");
                continue;
            }

            //没有连接地址表示属于父级目录
            builder.append("<li><a href=\"javascript:;\">" +
                   "<i class=\""+ menu.getImagePath() +"\"></i> " +
                   "<span class=\"title\">" + menu.getMenuName() +
                    "</span><span class=\"arrow\"></span></a>").append("\n");
            builder.append("<ul class=\"sub-menu\">").append("\n");

            // 拼接子菜单的子菜单, 一直递归直到没有菜单
            buildOrder(builder, menuList, menu.getMenuId());
            builder.append("</ul>").append("\n");
            builder.append("</li>").append("\n");

        }

    }

    public String setLeftTree(User user) {

        //默认赋值最高级别
        if (user == null){
            user=new User();
            user.setRoleType(10);
        }

        StringBuilder builder = new StringBuilder();

        List<Menu> menuList = SpringContextUtils.getBean(ManagerService.class).getMenuByRoleType(user.getRoleType());

        menuList.forEach(menu -> logger.info("menu :{}", JsonMapper.toJson(menu)));

        buildOrder(builder, menuList, 0);

        logger.info(builder.toString());

        return builder.toString();

    }

    public static void main(String[] args) {

        System.out.println("starting content");

        System.setProperty("spring.profiles.active", "developer");
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        context.start();

        User user = new User();
        user.setUserName("system");
        user.setRoleType(10);

        try {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("begin to running");
            new HtmlService().setLeftTree(user);
            HtmlService.class.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
