package com.nappy.burger.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
@Log
public class Jqurey {


    @RequestMapping("jq/kakaopayment")
    @ResponseBody
    public String kakaopay(String item_name, String quantity, String partner_user_id, String total_amount) {
        try {
            URL address = new URL("https://kapi.kakao.com/v1/payment/ready");
            HttpURLConnection urlCon = (HttpURLConnection) address.openConnection(); // 서버연결
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Authorization", "KakaoAK 1efc4419f8fe56b1690f8df020638491");
            urlCon.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            urlCon.setDoOutput(true);
            String parameter = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=" + partner_user_id +
                    "&item_name=" + item_name + "&quantity=" + quantity + "&total_amount=" + total_amount +
                    "&tax_free_amount=0&approval_url=http://localhost:8080&fail_url=http://localhost:8080&cancel_url=http://localhost:8080";

//            String parameter = "cid=TC0ONETIME&partner_order_id=partner_order_id" +
//                    "&partner_user_id=partner_user_id&item_name=초코파이" +
//                    "&quantity=1&total_amount=2200&tax_free_amount=0&" +
//                    "approval_url=http://localhost:8080&fail_url=http://localhost:8080&cancel_url=http://localhost:8080";

            OutputStream send = urlCon.getOutputStream();
            DataOutputStream dataSend = new DataOutputStream(send);
            dataSend.writeBytes(parameter);
            dataSend.close();
            int result = urlCon.getResponseCode();

            InputStream dataGet;
            if (result == 200) {
                dataGet = urlCon.getInputStream();
            } else {
                dataGet = urlCon.getErrorStream();
            }
            InputStreamReader reader = new InputStreamReader(dataGet);
            BufferedReader wrap = new BufferedReader(reader);
            return wrap.readLine();
        } catch (MalformedURLException e) {
            log.info("error1");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("error2");
            e.printStackTrace();
        }
        return "(\"result\":\"NO\")";
    }

}
