package cn.qifeng.merchant.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于接收前端提交的数据
 */
@ApiModel(value = "MerchantRegisterVO",description = "商户注册信息")
@Data
public class MerchantRegisterVO implements Serializable {
    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证的key")
    private  String verifiyKey;

    @ApiModelProperty("验证码")
    private String verifiyCode;
}
