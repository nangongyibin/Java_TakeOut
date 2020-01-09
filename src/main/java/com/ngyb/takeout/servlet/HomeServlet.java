package com.ngyb.takeout.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ngyb.takeout.bean.*;
import com.ngyb.takeout.utils.CommonUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Servlet implementation class HomeServlet
 */
@RestController
@RequestMapping(value = "/HomeServlet")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @RequestMapping(value = "/home", produces = "application/json;charset=UTF-8")
    public void home(HttpServletResponse response){
        // 获取经纬度信息，并依据经纬度信息从数据库中获取需要检索的附近商家信息（mysql自持距离范围检索）
        // 测试使用静态数据
        Response resp = new Response();
        resp.setCode("0");
        // 设置data部分数据
        // 促销信息
        ArrayList<Promotion> promotionList = new ArrayList<Promotion>();
        for (int id = 1; id < 4; id++) {
            Promotion promotion = new Promotion();
            promotion.setId(id);
            // http://localhost:8080/TakeoutService/home/
            promotion.setPic("/imgs/promotion/" + id + ".jpg");

            promotion.setInfo("promotion info...");
            promotionList.add(promotion);
        }
        // 分类信息
        String[] catetories = new String[]{"美食", "甜品饮料", "商店超市", "早餐", "果蔬", "新店", "下午茶", "麻辣烫"};
        ArrayList<Category> categorieList = new ArrayList<Category>();
        for (int id = 1; id < 9; id++) {
            Category category = new Category();
            category.setId(id);
            category.setPic("/imgs/category/" + id + ".png");
            category.setName(catetories[id - 1]);
            categorieList.add(category);
        }
        // nearbySellerList
        ArrayList<Seller> nearbySellerList = new ArrayList<Seller>();
        Seller itcast = new Seller();
        itcast.setId(1);
        itcast.setPic("/imgs/category/" + 1 + ".png");
        itcast.setName("南宫燚滨外卖项目");
        itcast.setSendPrice("10");
        itcast.setDeliveryFee("5");
        itcast.setScore("5");
        nearbySellerList.add(itcast);
        for (int i = 2; i < 10; i++) {
            Seller seller = new Seller();
            seller.setId(i);
            seller.setName("南宫燚滨第" + i + "家分店");
            seller.setSendPrice("10");
            seller.setDeliveryFee("5");
            nearbySellerList.add(seller);
        }
        // ortherSellerList
        ArrayList<Seller> ortherSellerList = new ArrayList<Seller>();
        for (int i = 10; i < 20; i++) {
            Seller seller = new Seller();
            seller.setId(i);
            seller.setName("南宫燚滨第" + i + "家分店");
            seller.setSendPrice("10");
            seller.setDeliveryFee("5");
            ortherSellerList.add(seller);
        }
        List<HomeItem> body = new ArrayList<HomeItem>();
        for (Seller seller : nearbySellerList) {
            HomeItem sellerItem = new HomeItem();
            sellerItem.type = 0;
            sellerItem.seller = seller;
            body.add(sellerItem);
        }
        HomeItem item = new HomeItem();
        item.type = 1;
        item.recommendInfos = new ArrayList<String>();
        item.recommendInfos.add("黄焖鸡");
        item.recommendInfos.add("米线");
        item.recommendInfos.add("南宫燚滨");
        item.recommendInfos.add("南宫燚滨");
        item.recommendInfos.add("酷丁鱼");
        item.recommendInfos.add("博学谷");
        body.add(item);
        for (Seller seller : ortherSellerList) {
            HomeItem sellerItem = new HomeItem();
            sellerItem.type = 0;
            sellerItem.seller = seller;
            body.add(sellerItem);
        }
        HomeInfo info = new HomeInfo(new Head(promotionList, categorieList), body);
        Response res = new Response();
        res.setCode("0");
        res.setData(JSONObject.fromObject(info).toString());
        CommonUtil.renderJson(response, res);
    }
}