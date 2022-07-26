package cn.qifeng.merchant.service;

import cn.qifeng.common.domain.BusinessException;

public interface SmsService {
    /**
     * 发送手机验证码
     * @param phone 手机号
     * @return 验证码对应的key
     */
    String sendMsg(String phone) throws BusinessException;


    /**
     * 校验验证码
     * @param verifiyKey
     * @param verifiyCode
     */
    void checkVerifiyCode(String verifiyKey,String verifiyCode) throws BusinessException;

}
