package cn.qifeng.merchant.service.SmsServiceImpl;

import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.common.domain.CommonErrorCode;
import cn.qifeng.merchant.service.SmsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${sms.url}")
    String url;

    @Value("${sms.effectiveTime}")
    String effectiveTime;

    @Override
    public String sendMsg(String phone) throws BusinessException {
        String smsUrl=url+ "/generate?name=sms&effectiveTime="+effectiveTime;
        //请求体
        Map<String,Object> body=new HashMap<>();
        body.put("mobile",phone);
        //请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        //指定为json文件
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //传入头和体
        HttpEntity httpEntity=new HttpEntity(body,httpHeaders);
        //发送请求
        ResponseEntity<Map> exchange = null;
        Map bodyMap =null;
        try {
            exchange=restTemplate.exchange(smsUrl, HttpMethod.POST, httpEntity, Map.class);
            log.info("请求验证码服务，得到响应：{}", JSON.toJSONString(exchange));
            bodyMap= exchange.getBody();
        }catch (RestClientException e){
            e.printStackTrace();
            //发送验证码错误
            throw new BusinessException(CommonErrorCode.E_100100);
        }
        if(bodyMap == null || bodyMap.get("result") == null){
            throw new BusinessException(CommonErrorCode.E_100100);
//            throw new RuntimeException("发送失败");
        }

        Map result = (Map) bodyMap.get("result");
        String key=(String)result.get("key");
        log.info("得到发送验证码对应的key:{}",key);
        return key;
    }

    //校验验证码
    @Override
    public void checkVerifiyCode(String verifiyKey, String verifiyCode) throws BusinessException {
        //校验url
        String url="http://localhost:56085/sailing/verify?name=sms&verificationCode="+verifiyCode+"&verificationKey="+verifiyKey;


        Map bodyMap =null;
        try {
            //使用restTemplate请求服务
            ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, Map.class);
            log.info("请求验证码服务，得到响应：{}", JSON.toJSONString(exchange));
            bodyMap = exchange.getBody();
        }
        catch (Exception e){
            e.printStackTrace();
            throw  new BusinessException(CommonErrorCode.E_100102);
//            throw new RuntimeException("校验验证码失败");
        }
        if(bodyMap == null || bodyMap.get("result") == null || !(Boolean) bodyMap.get("result")){
            throw  new BusinessException(CommonErrorCode.E_100102);
//            throw new RuntimeException("校验验证码失败");
        }
    }
}
