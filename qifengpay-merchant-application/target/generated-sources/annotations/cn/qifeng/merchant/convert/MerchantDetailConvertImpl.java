package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.vo.MerchantDetailVo;
import cn.qifeng.merchant.vo.MerchantRegisterVO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-26T15:58:55+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class MerchantDetailConvertImpl implements MerchantDetailConvert {

    @Override
    public MerchantRegisterVO dtoToVo(MerchantDTO merchantDTO) {
        if ( merchantDTO == null ) {
            return null;
        }

        MerchantRegisterVO merchantRegisterVO = new MerchantRegisterVO();

        merchantRegisterVO.setMobile( merchantDTO.getMobile() );
        merchantRegisterVO.setUsername( merchantDTO.getUsername() );
        merchantRegisterVO.setPassword( merchantDTO.getPassword() );

        return merchantRegisterVO;
    }

    @Override
    public MerchantDTO voToDto(MerchantDetailVo MerchantDetailVo) {
        if ( MerchantDetailVo == null ) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();

        merchantDTO.setMerchantName( MerchantDetailVo.getMerchantName() );
        merchantDTO.setMerchantNo( MerchantDetailVo.getMerchantNo() );
        merchantDTO.setMerchantAddress( MerchantDetailVo.getMerchantAddress() );
        merchantDTO.setMerchantType( MerchantDetailVo.getMerchantType() );
        merchantDTO.setBusinessLicensesImg( MerchantDetailVo.getBusinessLicensesImg() );
        merchantDTO.setIdCardFrontImg( MerchantDetailVo.getIdCardFrontImg() );
        merchantDTO.setIdCardAfterImg( MerchantDetailVo.getIdCardAfterImg() );
        merchantDTO.setUsername( MerchantDetailVo.getUsername() );
        merchantDTO.setContactsAddress( MerchantDetailVo.getContactsAddress() );

        return merchantDTO;
    }
}
