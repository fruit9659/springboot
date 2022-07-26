package cn.qifeng.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 资质申请信息
 */
@ApiModel(value = "MerchantDetailVo",description = "商户资质申请s")
@Data
public class MerchantDetailVo  implements Serializable {
    @ApiModelProperty("企业名称")
    private String merchantName;

    @ApiModelProperty(value = "企业编号")
    private String merchantNo;

    @ApiModelProperty(value = "企业地址")
    private String merchantAddress;

    @ApiModelProperty(value = "商户类型")
    private String merchantType;

    @ApiModelProperty(value = "营业执照（企业证明）")
    private String businessLicensesImg;

    @ApiModelProperty(value = "法人身份证正面照片")
    private String idCardFrontImg;

    @ApiModelProperty(value = "法人身份证反面照片")
    private String idCardAfterImg;

    @ApiModelProperty(value = "联系人姓名")
    private String username;

    @ApiModelProperty(value = "联系人地址")
    private String contactsAddress;

}
