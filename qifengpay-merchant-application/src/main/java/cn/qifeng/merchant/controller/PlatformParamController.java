package cn.qifeng.merchant.controller;

import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.common.domain.CommonErrorCode;
import cn.qifeng.merchant.api.PayChannelService;
import cn.qifeng.merchant.api.dto.PayChannelDTO;
import cn.qifeng.merchant.api.dto.PayChannelParamDTO;
import cn.qifeng.merchant.api.dto.PlatformChannelDTO;
import cn.qifeng.merchant.common.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fruit
 * @version 1.0
 * @create 2022/7/24   4:41
 */
@Api(value = "支付参数配置",tags = "支付参数配置",description = "支付参数配置")
@RestController
@Slf4j
public class PlatformParamController {
    @Reference
    PayChannelService payChannelService;

    @ApiOperation("商户服务类型")
    @GetMapping("/my/platform-channels")
    public List<PlatformChannelDTO> queryPlatformChannel(){
        return payChannelService.queryPlatformChannel();
    }

    @ApiOperation("绑定服务类型")
    @PostMapping("/my/apps/{appId}/platform-channels")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "应用id",name = "appId",dataType ="string" ,paramType = "path"),
            @ApiImplicitParam(value = "服务类型code",name = "platformChannelCodes",dataType ="string" ,paramType = "query")
    })
    public void bindPlatformFroApp(@PathVariable("appId") String appId, @RequestParam("platformChannelCodes") String platformChannelCodes){
        payChannelService.bindPlatformChannelFroApp(appId,platformChannelCodes);
    }


    @ApiOperation("查询是否绑定了某个服务类型")
    @GetMapping("/my/merchant/platform")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "应用id",name = "appId",required = true,dataType ="string" ,paramType = "query"),
            @ApiImplicitParam(value = "服务类型code",name = "platformChannel",required = true,dataType ="string" ,paramType = "query")
    })
    public int queryBindPlatformFroApp(@RequestParam("appId") String appId, @RequestParam("platformChannel") String platformChannel){
        return payChannelService.queryAppBindPlatformChannel(appId,platformChannel);
    }

    @ApiOperation("根据服务类型查询支付渠道")
    @GetMapping("/my/pay-channel/platform-channel/{platformChannelCode}")
    @ApiImplicitParam(value = "服务类型",name = "platformChannelCode",dataType ="string" ,paramType = "path")
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(@PathVariable("platformChannelCode") String platformChannelCode){
        return payChannelService.queryPayChannelByPlatformChannel(platformChannelCode);
    }

    @ApiOperation("商户配置参数")
    @ApiImplicitParam(name = "payChannelParam",value = "支付参数配置",required = true,dataType = "PayChannelParamDTO",paramType = "body")
    @RequestMapping(value = "/my/pay-channel-params",method = {RequestMethod.POST,RequestMethod.PUT})
    public void createPayChannelParam(@RequestBody  PayChannelParamDTO payChannelParam){
        //非空参数校验
        if(payChannelParam==null||payChannelParam.getChannelName()==null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        Long merchantId = SecurityUtil.getMerchantId();//从token解析
        payChannelParam.setMerchantId(merchantId);
        payChannelService.savePayChannelParam(payChannelParam);
    }


    @ApiOperation("获取指定服务类型下所包含的支付渠道参数列表")
    @GetMapping("/my/pay-channel-params/{appId}/platform-channel/{platformParam}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "appId",value = "应用id",dataType = "string",paramType = "path"),
        @ApiImplicitParam(name = "platformParam",value = "服务类型code",dataType = "string",paramType = "path")
    })
    public List<PayChannelParamDTO> queryParamChannel(@PathVariable("appId") String appId,@PathVariable("platformParam") String platformParam){
        return payChannelService.queryPayChannelParamByAppPlatform(appId,platformParam);
    }

    @ApiOperation("获取指定服务类型下所包含的某个支付渠道参数")
    @GetMapping("/my/pay-channel-params/{appId}/platform-channel/{platformParam}/pay-channel/{paramChannel}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId",value = "应用id",required = true,dataType = "string",paramType = "path"),
            @ApiImplicitParam(name = "platformParam",value = "平台渠道编码",required = true,dataType = "string",paramType = "path"),
            @ApiImplicitParam(name = "paramChannel",value = "实际渠道编码",required = true,dataType = "string",paramType = "path")
    })
   public PayChannelParamDTO queryPayChannelParam(@PathVariable("appId") String appId,
                                                  @PathVariable("platformParam") String platformParam,
                                                  @PathVariable("paramChannel") String paramChannel){
        return payChannelService.queryPayChannelParamByAppParamChannel(appId,platformParam,paramChannel);
    }



}
