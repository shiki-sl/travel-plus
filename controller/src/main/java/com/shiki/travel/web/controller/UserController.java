package com.shiki.travel.web.controller;

import com.shiki.travel.api.UserControllerApi;
import com.shiki.travel.pojo.ResultInfo;
import com.shiki.travel.pojo.User;
import com.shiki.travel.web.service.FavoriteService;
import com.shiki.travel.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author: shiki
 * @Date: 2019/04/16 19:39
 */
@RestController
@RequestMapping("/user")
public class UserController implements UserControllerApi {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping("/join")
    public ResultInfo join(User user, String checkCode, HttpSession session) throws Exception {
        ResultInfo x = getRefreshCheckCode(checkCode, session);
        if (x != null) return x;
        User join = userService.join(user);
        if (join != null) {
            return new ResultInfo(true, join.getEmail(), "注册成功");
        } else {
            return new ResultInfo(false, null, "注册失败");
        }
    }

    @Override
    @PostMapping("/login")
    public ResultInfo login(String username, String password, String checkCode,
                            HttpSession session) throws Exception {
        ResultInfo x = getRefreshCheckCode(checkCode, session);
        if (x != null) return x;
        return userService.login(username, password)
                .map(user1 -> {
                    if ("N".equals(user1.getStatus())) {
                        return new ResultInfo(false, null, "用户未激活,请登录邮箱或通过手机验证激活");
                    }
                    session.setAttribute("user", user1);
                    return new ResultInfo(true, user1, "登陆成功");
                })
                .orElseGet(() -> new ResultInfo(false, null, "登陆失败,用户名或密码错误"));

    }

    /**
     * 判断验证码是否过期
     *
     * @param checkCode
     * @param session
     * @return
     */
    private ResultInfo getRefreshCheckCode(String checkCode, HttpSession session) {
        String imageCode = (String) session.getAttribute("imageCode");
        if (imageCode == null) {
            return new ResultInfo(false, null, "登陆失败,验证码过期");
        }
        if (!imageCode.equalsIgnoreCase(checkCode)) {
            return new ResultInfo(false, null, "登陆失败,验证码错误");
        }
        session.removeAttribute("imageCode");
        return null;
    }

    @Override
    @GetMapping("/check")
    public boolean check(String username) throws Exception {
        return !userService.check(username).isPresent();
    }

    @Override
    @GetMapping("/logout")
    public ResultInfo logout(HttpSession session) throws Exception {
        session.removeAttribute("user");
        return new ResultInfo(true, null, "成功退出");
    }

    @Override
    @GetMapping("/getUsername")
    public ResultInfo getUsername(HttpSession session) {
        return getUser(session);
    }

    private ResultInfo getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return new ResultInfo(true, user.getUsername(), "");
        }
        return new ResultInfo(false, null, "请登录");
    }

    @GetMapping("/query4UserFavorite")
    @Override
    public ResultInfo query4UserFavorite(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return new ResultInfo(true, userService.query4UserFavorite(user.getUid()), "");
        }
        return new ResultInfo(false, null, "请登录");
    }

    @Value("${traverWeb.ip}")
    private String traverWebIP;

    @GetMapping("/activate/{code}")
    @Override
    public ResultInfo activate(@PathVariable String code) {
        try {
            User user = userService.activate(code);
            if (user != null) {
                return new ResultInfo(true, user.getUsername(), "激活成功");
            } else {
                return new ResultInfo(true, null, "激活失败,状态过期或错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(true, null, "激活失败");
        }
    }

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/getFavorite")
    @Override
    public ResultInfo getFavorite(Integer rid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return new ResultInfo(true, favoriteService.findByUidAndRid(user.getUid(), rid), "");
    }

    @GetMapping("/delFavorite")
    @Override
    public ResultInfo delFavorite(Integer rid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            favoriteService.delFavorite(user.getUid(), rid);
            return new ResultInfo(true, null, "更改收藏状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, null, "更改收藏状态失败");
        }
    }

    @GetMapping("/saveFavorite")
    @Override
    public ResultInfo saveFavorite(Integer rid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            favoriteService.saveFavorite(user.getUid(), rid);
            return new ResultInfo(true, null, "更改收藏状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, null, "更改收藏状态失败");
        }
    }
}
