package com.shiki.travel.web.service;

import com.shiki.travel.pojo.Favorite;

/**
 * @Author: shiki
 * @Date: 2019/05/07 1:18
 */

public interface FavoriteService {
    Favorite findByUidAndRid(Integer uid, Integer rid);
    void delFavorite(Integer uid, Integer rid);
    void saveFavorite(Integer uid, Integer rid);
}
