package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.AppDTO;
import cn.qifeng.merchant.entity.App;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-26T15:58:43+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class AppConvertImpl implements AppConvert {

    @Override
    public AppDTO entityToDto(App entity) {
        if ( entity == null ) {
            return null;
        }

        AppDTO appDTO = new AppDTO();

        appDTO.setAppId( entity.getAppId() );
        appDTO.setAppName( entity.getAppName() );
        appDTO.setMerchantId( entity.getMerchantId() );
        appDTO.setPublicKey( entity.getPublicKey() );
        appDTO.setNotifyUrl( entity.getNotifyUrl() );

        return appDTO;
    }

    @Override
    public App dtoToEntity(AppDTO appDTO) {
        if ( appDTO == null ) {
            return null;
        }

        App app = new App();

        app.setAppId( appDTO.getAppId() );
        app.setAppName( appDTO.getAppName() );
        app.setMerchantId( appDTO.getMerchantId() );
        app.setPublicKey( appDTO.getPublicKey() );
        app.setNotifyUrl( appDTO.getNotifyUrl() );

        return app;
    }

    @Override
    public List<AppDTO> listEntityToDto(List<App> app) {
        if ( app == null ) {
            return null;
        }

        List<AppDTO> list = new ArrayList<AppDTO>( app.size() );
        for ( App app1 : app ) {
            list.add( entityToDto( app1 ) );
        }

        return list;
    }
}
