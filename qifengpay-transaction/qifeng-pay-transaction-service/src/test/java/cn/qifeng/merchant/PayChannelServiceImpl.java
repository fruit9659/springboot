package cn.qifeng.merchant;

import cn.qifeng.merchant.api.PayChannelService;
import cn.qifeng.merchant.api.dto.PayChannelDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Fruit
 * @version 1.0
 * @create 2022/7/25   9:22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PayChannelServiceImpl {
    @Autowired
    PayChannelService payChannelService;
    @Test
    public void testQueryPayPlatform(){
        List<PayChannelDTO> qifeng_c2b = payChannelService.queryPayChannelByPlatformChannel("qifeng_c2b");
        System.out.println(qifeng_c2b);
    }
}
