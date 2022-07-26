package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.tenant.TenantDTO;
import cn.qifeng.merchant.entity.Tenant;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-26T17:53:06+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class TenantConvertImpl implements TenantConvert {

    @Override
    public TenantDTO entity2dto(Tenant entity) {
        if ( entity == null ) {
            return null;
        }

        TenantDTO tenantDTO = new TenantDTO();

        tenantDTO.setId( entity.getId() );
        tenantDTO.setName( entity.getName() );
        tenantDTO.setTenantTypeCode( entity.getTenantTypeCode() );
        tenantDTO.setBundleCode( entity.getBundleCode() );

        return tenantDTO;
    }

    @Override
    public Tenant dto2entity(TenantDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Tenant tenant = new Tenant();

        tenant.setId( dto.getId() );
        tenant.setName( dto.getName() );
        tenant.setTenantTypeCode( dto.getTenantTypeCode() );
        tenant.setBundleCode( dto.getBundleCode() );

        return tenant;
    }
}
