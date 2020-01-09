package com.ngyb.takeout.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ngyb.takeout.bean.*;
import com.ngyb.takeout.utils.CommonUtil;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/OrderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, OrderOverview> map = new HashMap<String, OrderOverview>();
    ;

    @RequestMapping(value = "/order", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public void orderGet(@RequestParam(value = "userId") String userid, HttpServletResponse response) {
        int userId = 0;
        try {
            userId = Integer.parseInt(userid);
        } catch (Exception e) {
        }
        Response res = new Response();
        if (userId != 0) {
            List<Order> orderList = new ArrayList<Order>();
            for (int i = 9; i > 0; i--) {
                Order order = new Order();
                setOrder(order, i);
                orderList.add(order);
            }
            res.setCode("0");
            res.setData(JSONArray.fromObject(orderList).toString());
        } else {
            res.setCode("-1");
            res.setData("");
        }
        CommonUtil.renderJson(response, res);
    }

    @RequestMapping(value = "/order", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public void orderPost(@RequestParam(value = "orderOverview") String orderOverview, HttpServletResponse response) {
        Response res = new Response();
        Random random = new Random();
        int num = random.nextInt(9999);
        if (orderOverview != null) {
            res.setCode("0");
            // 将用户订单保存到数据库中
            OrderOverview overview = JSON.parseObject(orderOverview, OrderOverview.class);
            overview.id = "1010 8027 3652 5688 " + num;
            map.put(overview.id, overview);
            res.setData(overview.id);
        } else {
            res.setCode("-1");
            res.setData("");
        }
        CommonUtil.renderJson(response, res);
    }

    private void setOrder(Order order, int i) {
        order.setId("1010 8027 3652 5689 3" + i);
        if (i == 9) {
            order.setType("10");
        } else {
            order.setType("50");
        }
        List<GoodsInfo> goodsInfos = new ArrayList<GoodsInfo>();
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setName("红烧肉");
        goodsInfo.setNewPrice(25);
        goodsInfos.add(goodsInfo);
        GoodsInfo goodsInfo1 = new GoodsInfo();
        goodsInfo1.setName("米饭");
        goodsInfo1.setNewPrice(2);
        goodsInfos.add(goodsInfo1);
        GoodsInfo goodsInfo2 = new GoodsInfo();
        goodsInfo2.setName("雪碧");
        goodsInfo2.setNewPrice(4);
        goodsInfos.add(goodsInfo2);
        order.setGoodsInfos(goodsInfos);
        Rider rider = new Rider();
        rider.setId(100);
        rider.setName("张三");
        rider.setPhone("13200000000");
        Location location = new Location();
        location.setLatitude("106.23");
        location.setLongitude("43.123");
        rider.setLocation(location);
        order.setRider(rider);
        Distribution distribution = new Distribution();
        distribution.setDes("南宫燚滨配送");
        distribution.setType("1");
        order.setDistribution(distribution);
        OrderDetail detail = new OrderDetail();
        detail.setAddress("南宫燚滨");
        detail.setPay("在线支付");
        detail.setPhone("135000000000");
        detail.setTime("2020-10-10 10:10:10");
        detail.setUsername("老黑");
        order.setDetail(detail);
        Seller itcast = new Seller();
        itcast.setId(1);
        itcast.setPic("/imgs/category/" + 1 + ".png");
        itcast.setName("南宫燚滨外卖项目");
        itcast.setScore("5");
        order.setSeller(itcast);
    }
}
