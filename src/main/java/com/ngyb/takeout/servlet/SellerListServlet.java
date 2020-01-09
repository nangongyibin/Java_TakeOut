package com.ngyb.takeout.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ngyb.takeout.bean.Response;
import com.ngyb.takeout.bean.Seller;
import com.ngyb.takeout.utils.CommonUtil;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/SellerListServlet")
public class SellerListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @RequestMapping(value = "/SellerList", produces = "application/json;charset=UTF-8")
    public void SellerList(HttpServletResponse response){
        System.out.println("--------获取商家列表--------");
        List<Seller> list = new ArrayList<Seller>();
        for (int i = 1; i < 10; i++) {
            Seller seller = new Seller();
            seller.setId(i);
            seller.setName("南宫燚滨第" + i + "家分店");
            list.add(seller);
        }
        Response res = new Response();
        res.setCode("0");
        String data = "{\"list\":" + JSONArray.fromObject(list).toString() + "}";
        res.setData(data);
        CommonUtil.renderJson(response, res);
    }
}
