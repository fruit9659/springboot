package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.PlatformChannelDTO;
import cn.qifeng.merchant.entity.PlatformChannel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-26T15:59:07+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class PlatformChannelConvertImpl implements PlatformChannelConvert {

    @Override
    public PlatformChannelDTO entity2dto(PlatformChannel entity) {
        if ( entity == null ) {
            return null;
        }

        PlatformChannelDTO platformChannelDTO = new PlatformChannelDTO();

        platformChannelDTO.setId( entity.getId() );
        platformChannelDTO.setChannelName( entity.getChannelName() );
        platformChannelDTO.setChannelCode( entity.getChannelCode() );

        return platformChannelDTO;
    }

    @Override
    public PlatformChannel dto2entity(PlatformChannelDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PlatformChannel platformChannel = new PlatformChannel();

        platformChannel.setId( dto.getId() );
        platformChannel.setChannelName( dto.getChannelName() );
        platformChannel.setChannelCode( dto.getChannelCode() );

        return platformChannel;
    }

    @Override
    public List<PlatformChannelDTO> listentity2listdto(List<PlatformChannel> PlatformChannel) {
        if ( PlatformChannel == null ) {
            return null;
        }

        List<PlatformChannelDTO> list = new ArrayList<PlatformChannelDTO>( PlatformChannel.size() );
        for ( PlatformChannel platformChannel : PlatformChannel ) {
            list.add( entity2dto( platformChannel ) );
        }

        return list;
    }
}
