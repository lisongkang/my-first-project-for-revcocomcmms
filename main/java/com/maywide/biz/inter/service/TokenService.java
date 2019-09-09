package com.maywide.biz.inter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.inter.pojo.login.TokenResp;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenService {

    /**
     * 获取token
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo getToken(TokenResp resp) throws Exception {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        ObjectMapper mapper = new ObjectMapper();
        String loginInfoJson = mapper.writeValueAsString(loginInfo);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzipout = new GZIPOutputStream(out);
        gzipout.write(loginInfoJson.getBytes("utf-8"));
        gzipout.close();

        String encode = new BASE64Encoder().encode(out.toByteArray());

        String jwt = JwtUtil.buildJWT(encode, UUID.randomUUID().toString(), 24 * 60 * 60);

        resp.setToken(jwt);

        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(0L);
        returnInfo.setMessage("操作成功！");
        return returnInfo;
    }
}
