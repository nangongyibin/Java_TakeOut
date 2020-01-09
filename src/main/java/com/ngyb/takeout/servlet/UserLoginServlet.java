package com.ngyb.takeout.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngyb.takeout.bean.Response;
import com.ngyb.takeout.bean.User;
import com.ngyb.takeout.utils.CommonUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public void login(@RequestParam(value = "username") String name,
                      @RequestParam(value = "password") String password,
                      @RequestParam(value = "type") String types,
                      @RequestParam(value = "phone") String phones,
                      HttpServletResponse response){
        Random random = new Random(System.currentTimeMillis());
        int num = random.nextInt(9999) + 1;
        if (name == null || name.length() == 0) {
            name = "random" + num;
        }
        int type = Integer.parseInt(types);
        String phone = null;
        if (type == 2) {
            phone = phones;
        }
        Response res = new Response();
        res.setCode("0");
        User user = new User();
        user.set_id(num);
        user.setName(name);
        user.setBalance(1.0f);
        user.setDiscount(0);
        user.setIntegral(0);
        if (phone != null)
            user.setPhone(phone);
        res.setData(JSONObject.fromObject(user).toString());
        CommonUtil.renderJson(response, res);
    }
}
