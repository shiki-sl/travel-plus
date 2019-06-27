package com.shiki.travel.web.controller;

import com.shiki.travel.utils.CheckCodeUtil;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @param
 * @author ld
 * @date 2017年11月6日
 * @desc 图形验证码生成
 */
@RestController
public class CommonController {
    @GetMapping(value = "/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session, @CookieValue String JSESSIONID, @CookieValue String SESSION) throws Exception {
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = CheckCodeUtil.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode", objs[0]);

        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }
}