package cn.qifeng.common.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
//执行InitializingBean接口操作
public class ConstanUntils implements InitializingBean {

    //读取配置文件内容
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String keyid;

    @Value("${aliyun.oss.accessKeySecret}")
    private String keySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    //公开定义常量
    public static String END_POIND;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POIND=endpoint;
        ACCESS_KEY_ID=keyid;
        ACCESS_KEY_SECRET=keySecret;
        BUCKET_NAME=bucketName;

    }
}
