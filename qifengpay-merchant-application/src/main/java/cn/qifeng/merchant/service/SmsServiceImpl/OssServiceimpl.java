package cn.qifeng.merchant.service.SmsServiceImpl;

import cn.qifeng.common.util.ConstanUntils;
import cn.qifeng.merchant.service.OssService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceimpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取相关常量
        String endpoint = ConstanUntils.END_POIND;
        String keyid = ConstanUntils.ACCESS_KEY_ID;
        String keySecret = ConstanUntils.ACCESS_KEY_SECRET;
        String bucketName = ConstanUntils.BUCKET_NAME;

        try {
            //创建ossclient实例对象
            OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keySecret);
            //获取上传输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            //添加文件名随机值并去除"-"
            String randUid = UUID.randomUUID().toString().replaceAll("-","");
            fileName=randUid+fileName;

            //按照日期分类管理
            String datePath = new DateTime().toString("yyyy/MM/dd");//获取当前日期
            //拼接
            fileName=datePath+"/"+fileName;

            //调用oss方法
            ossClient.putObject(bucketName,fileName,inputStream);

            //关闭ossclient
            ossClient.shutdown();

            //把上传文件路径返回
            String uploadurl = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return uploadurl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
