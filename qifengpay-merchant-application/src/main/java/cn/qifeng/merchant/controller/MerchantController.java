package cn.qifeng.merchant.controller;


import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.common.domain.CommonErrorCode;
import cn.qifeng.common.util.PhoneUtil;
import cn.qifeng.common.util.ResultType;
import cn.qifeng.merchant.api.AppService;
import cn.qifeng.merchant.api.MerchantService;
import cn.qifeng.merchant.api.dto.AppDTO;
import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.common.util.SecurityUtil;
import cn.qifeng.merchant.convert.MerchantDetailConvert;
import cn.qifeng.merchant.convert.MerchantRegisterConvert;
import cn.qifeng.merchant.service.OssService;
import cn.qifeng.merchant.service.SmsService;
import cn.qifeng.merchant.vo.MerchantDetailVo;
import cn.qifeng.merchant.vo.MerchantRegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Security;
import java.util.List;

/**
 * @author Fruit
 */
@RestController
@Api(value = "商户平台接口",tags = "商户平台接口",description = "商户平台接口")
public class MerchantController {
    //生成代理对象,远程调用
    @Reference
    MerchantService merchantService;

    //注入本地调用
    @Autowired
    SmsService smsService;
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "根据id查询")
    @GetMapping("/merchants/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") Long id){
        MerchantDTO merchantDTO = merchantService.queryMerchantById(id);
        return merchantDTO;
    }

    //手机验证码
    @ApiOperation(value = "获取手机验证码")
    @ApiImplicitParam(name = "phone",value = "手机号",required = true,dataType="String",paramType="query")
    @GetMapping("/sms")
    public String getSmsCode(@RequestParam("phone") String phone){
        //发送验证码
        return  smsService.sendMsg(phone);
    }

    //商户注册
    @ApiOperation(value = "商户注册")
    @ApiImplicitParam(value = "商户注册信息",name ="merchantRegisterVO" ,required = true,
            dataType = "MerchantRegisterVO",paramType = "body")
    @PostMapping("/merchants/register")
    public MerchantRegisterVO registerMerchant(@RequestBody  MerchantRegisterVO merchantRegisterVO){
        //校验参数合法性
        //校验参数合法性
        if (merchantRegisterVO == null){
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        //手机号非空验证
        if (StringUtils.isBlank(merchantRegisterVO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        //手机号校验
        if(!PhoneUtil.isMatches(merchantRegisterVO.getMobile())){
            throw  new BusinessException(CommonErrorCode.E_100109);
        }
        //校验验证码
        smsService.checkVerifiyCode(merchantRegisterVO.getVerifiyKey(),merchantRegisterVO.getVerifiyCode());
        //调用dubbo服务
//        MerchantDTO merchantDTO=new MerchantDTO();
        //向dto写入商户注册信息
//        merchantDTO.setMobile(merchantRegisterVO.getMobile());
//        merchantDTO.setUsername(merchantRegisterVO.getUsername());
//        使用mapstruct进行对象转换
        MerchantDTO merchantDTO = MerchantRegisterConvert.INSTANCE.voToDto(merchantRegisterVO);
        //....
        merchantService.createMerchant(merchantDTO);
        return merchantRegisterVO;
    }


        //上传头像方法
        @ApiOperation(value = "上传图片")
        @PostMapping("/merchants/image")
        public ResultType upload(MultipartFile file){
            //获取上传文件  MultipartFile
            //返回上传到oss路径
            String uploadurl=ossService.uploadFileAvatar(file);
            return ResultType.success().message("文件上传成功").data("url",uploadurl);

    }

    //资质申请
    @ApiOperation(value = "资质申请")
    @ApiImplicitParam(value = "商户认证资料",name ="merchantInfo" ,required = true,
            dataType = "MerchantDetailVo",paramType = "body")
    @PostMapping("/my/merchants/save")
    public void saveMerchant(@RequestBody MerchantDetailVo merchantInfo){
        //解析当前登录的id    Bearer eyJtZXJjaGFudElkIjoxNTQ5MjQxMTY1MDAwMjYxNjMzfQ==
        Long merchantId = SecurityUtil.getMerchantId();
        MerchantDTO merchantDTO = MerchantDetailConvert.INSTANCE.voToDto(merchantInfo);
        merchantService.applyMerchant(merchantId,merchantDTO);
    }

    //审核
    @ApiOperation(value = "资质审核")
    @ApiImplicitParam(value = "审核商户认证资料",name ="merchantDetailVo" ,required = true,
            dataType = "MerchantDetailVo",paramType = "body")
    @PostMapping("/my/merchants/update")
    public void updateMerchant(@RequestBody MerchantDetailVo merchantDetailVo){
        //解析当前登录的id    Bearer eyJtZXJjaGFudElkIjoxNTQ5MjQxMTY1MDAwMjYxNjMzfQ==
        Long merchantId = SecurityUtil.getMerchantId();
        MerchantDTO merchantDTO = MerchantDetailConvert.INSTANCE.voToDto(merchantDetailVo);
        merchantService.updateMerchant(merchantId,merchantDTO);
    }







}
