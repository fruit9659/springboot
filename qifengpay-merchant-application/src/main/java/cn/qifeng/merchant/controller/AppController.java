package cn.qifeng.merchant.controller;

import cn.qifeng.merchant.api.AppService;
import cn.qifeng.merchant.api.dto.AppDTO;
import cn.qifeng.merchant.common.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商户应用管理",tags = "商户应用相关",description = "商户应用相关")
@RestController
public class AppController {
    @Reference
     AppService appService;

    @ApiOperation("应用创建")
    @ApiImplicitParam(name = "appDTO",value = "应用信息",required = true,dataType = "AppDTO",paramType ="body" )
    @PostMapping("/my/apps")
    public AppDTO createApp(@RequestBody AppDTO appDTO){
        Long merchantId = SecurityUtil.getMerchantId();
        return appService.createApp(merchantId,appDTO);
    }

    @ApiOperation("查询商户下的应用列表")
    @GetMapping("/my/apps")
    public List<AppDTO> queryMyApps(){
//        商户id
        Long merchantId = SecurityUtil.getMerchantId();
        return appService.queryAppMerchant(merchantId);
    }

    @ApiOperation("应用id查询应用列表")
    @ApiImplicitParam(name = "appId",value = "商户应用id",required = true,dataType = "String",paramType = "path")
    @GetMapping("/my/apps/{appId}")
    public AppDTO getApp(@PathVariable("appId") String appId){
        return appService.getAppById(appId);
    }


}
