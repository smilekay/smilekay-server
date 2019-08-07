package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.dto.QQUserInfo;
import com.nmt.smilekay.dto.SinaAccessToken;
import com.nmt.smilekay.dto.SinaUserInfo;
import com.nmt.smilekay.service.ThirdPartyService;
import com.nmt.smilekay.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@Service("thirdPartyService")
public class ThirdPartyServiceImpl implements ThirdPartyService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${smilekay.qq.appid}")
    private String appId;
    @Value("${smilekay.sina.app_key}")
    private String app_key;
    @Value("${smilekay.sina.app_secret}")
    private String app_secret;
    @Value("${smilekay.sina.redirect_uri}")
    private String redirect_uri;

    @Override
    public String getOpenId(String accessToken) {
        String url = "https://graph.qq.com/oauth2.0/me?access_token={access_token}";
        Map<String, String> map = new HashMap<>();
        map.put("access_token", accessToken);
        String response = restTemplate.getForObject(url, String.class, map);
        if (response != null) {
            String openId = response.split("\"")[7];
            if (openId != null) {
                return openId;
            }
        }
        return null;
    }

    @Override
    public QQUserInfo getQQUserInfo(String accessToken, String openId) {
        String url = "https://graph.qq.com/user/get_user_info?access_token={access_token}&oauth_consumer_key" +
                "={oauth_consumer_key}&openid={openid}";
        Map<String, String> map = new HashMap<>();
        map.put("access_token", accessToken);
        map.put("oauth_consumer_key", appId);
        map.put("openid", openId);
        String response = restTemplate.getForObject(url, String.class, map);
        QQUserInfo qqUserInfo = null;
        try {
            qqUserInfo = MapperUtils.json2pojo(response, QQUserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qqUserInfo;
    }

    @Override
    public SinaAccessToken getSinaAccessToken(String code) {
        String url = "https://api.weibo.com/oauth2/access_token?client_id={client_id}&client_secret" +
                "={client_secret}&grant_type={grant_type}&redirect_uri={redirect_uri}&code={code}";
        Map<String, Object> map = new HashMap<>();
        map.put("client_id", app_key);
        map.put("client_secret", app_secret);
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", redirect_uri);
        map.put("code", code);
        String response = restTemplate.postForObject(url, null, String.class, map);
        SinaAccessToken sinaAccessToken = null;
        try {
            sinaAccessToken = MapperUtils.json2pojo(response, SinaAccessToken.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sinaAccessToken;
    }

    @Override
    public SinaUserInfo getSinaUserInfo(String access_token, String uid) {
        String url = "https://api.weibo.com/2/users/show.json?access_token={access_token}&uid={uid}";
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("uid", String.valueOf(uid));
        String response = restTemplate.getForObject(url, String.class, map);
        SinaUserInfo sinaUserInfo = new SinaUserInfo();
        try {
            Map<String, Object> map1 = MapperUtils.json2map(response);
            sinaUserInfo.setId((long) map1.get("id"));
            sinaUserInfo.setIdstr((String) map1.get("idstr"));
            sinaUserInfo.setScreen_name((String) map1.get("screen_name"));
            sinaUserInfo.setName((String) map1.get("name"));
            sinaUserInfo.setProvince((String) map1.get("province"));
            sinaUserInfo.setCity((String) map1.get("city"));
            sinaUserInfo.setLocation((String) map1.get("location"));
            sinaUserInfo.setProfile_image_url((String) map1.get("profile_image_url"));
            sinaUserInfo.setGender((String) map1.get("gender"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sinaUserInfo;
    }
}
