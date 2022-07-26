package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.entity.Merchant;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-26T15:58:43+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class MerchantConvertImpl implements MerchantConvert {

    @Override
    public Merchant dtoToEntity(MerchantDTO merchantDTO) {
        if ( merchantDTO == null ) {
            return null;
        }

        Merchant merchant = new Merchant();

        merchant.setId( merchantDTO.getId() );
        merchant.setMerchantName( merchantDTO.getMerchantName() );
        merchant.setMerchantNo( merchantDTO.getMerchantNo() );
        merchant.setMerchantAddress( merchantDTO.getMerchantAddress() );
        merchant.setMerchantType( merchantDTO.getMerchantType() );
        merchant.setBusinessLicensesImg( merchantDTO.getBusinessLicensesImg() );
        merchant.setIdCardFrontImg( merchantDTO.getIdCardFrontImg() );
        merchant.setIdCardAfterImg( merchantDTO.getIdCardAfterImg() );
        merchant.setUsername( merchantDTO.getUsername() );
        merchant.setMobile( merchantDTO.getMobile() );
        merchant.setContactsAddress( merchantDTO.getContactsAddress() );
        merchant.setAuditStatus( merchantDTO.getAuditStatus() );
        merchant.setTenantId( merchantDTO.getTenantId() );

        return merchant;
    }

    @Override
    public MerchantDTO entityToDto(Merchant merchant) {
        if ( merchant == null ) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();

        merchantDTO.setId( merchant.getId() );
        merchantDTO.setMerchantName( merchant.getMerchantName() );
        merchantDTO.setMerchantNo( merchant.getMerchantNo() );
        merchantDTO.setMerchantAddress( merchant.getMerchantAddress() );
        merchantDTO.setMerchantType( merchant.getMerchantType() );
        merchantDTO.setBusinessLicensesImg( merchant.getBusinessLicensesImg() );
        merchantDTO.setIdCardFrontImg( merchant.getIdCardFrontImg() );
        merchantDTO.setIdCardAfterImg( merchant.getIdCardAfterImg() );
        merchantDTO.setUsername( merchant.getUsername() );
        merchantDTO.setMobile( merchant.getMobile() );
        merchantDTO.setContactsAddress( merchant.getContactsAddress() );
        merchantDTO.setAuditStatus( merchant.getAuditStatus() );
        merchantDTO.setTenantId( merchant.getTenantId() );

        return merchantDTO;
    }
}
