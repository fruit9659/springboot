package cn.qifeng.merchant.mapper;

import cn.qifeng.merchant.api.dto.authorization.PrivilegeTreeDTO;
import cn.qifeng.merchant.entity.AuthorizationPrivilegeGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 权限组 Mapper 接口
 * </p>
 *
 *
 * @since 2019-08-13
 */
@Repository
public interface AuthorizationPrivilegeGroupMapper extends BaseMapper<AuthorizationPrivilegeGroup> {

    @Select("select * from authorization_privilege_group")
    List<PrivilegeTreeDTO> selectPrivilegeGroup();
}
