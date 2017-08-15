package com.mcylm.clay.securityservice.controller;

import com.mcylm.clay.securityservice.module.ParameterModel;
import com.mcylm.clay.securityservice.module.Uauth;
import com.mcylm.clay.securityservice.service.UauthService;
import com.mcylm.clay.securityservice.util.IPUtil;
import com.mcylm.clay.securityservice.util.SMSMessageLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2017/8/7.
 * 登录控制层
 */
@Controller
@RequestMapping("/author")
public class LoginContoller {

    @Autowired
    private UauthService uauthService;

    /**
     * 登录
     *
     * @param request
     * @param parameterModel
     * @return
     */
    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public ResponseEntity<ParameterModel> doLogin(HttpServletRequest request, ParameterModel parameterModel) {
        //客户端ip
        String hostIp = IPUtil.getIpAddr(request);
        //主机ip
        String sessionId = request.getSession().getId();
        ResponseEntity<ParameterModel> result = uauthService.getUuidByUsernameAndPassword(parameterModel, hostIp, sessionId);
        return result;
    }

    /**
     * 根据用户名检测用户是否存在
     *
     * @param parameterModel
     * @return
     */
    @RequestMapping(value = "/checkName")
    @ResponseBody
    public Uauth checkName(ParameterModel parameterModel) {
        return uauthService.checkName(parameterModel);
    }

    /**
     * 根据手机号获取验证码
     *
     * @param phone
     * @param request
     * @return
     */
    @RequestMapping(value = "/valphonewhatever")
    @ResponseBody
    public String valphonewhatever(String phone, HttpServletRequest request) {
        String send = SMSMessageLib.send(phone, request);
        return send;
    }

    /**
     * 验证验证码是否正确
     *
     * @param request
     * @param verphone
     * @return
     */
    @RequestMapping(value = "/verphone")
    @ResponseBody
    public String verphone(HttpServletRequest request, String verphone, String phonever) {
        if (verphone.equals(phonever))
            return "success";
        else
            return "failed";
    }

    /**
     * 根据手机号修改密码
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public String updatePassword(String password, String phone) {
        return uauthService.updatePassword(password, phone);
    }


}
