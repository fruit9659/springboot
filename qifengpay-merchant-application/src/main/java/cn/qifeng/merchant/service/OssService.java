package cn.qifeng.merchant.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传到oss
    String uploadFileAvatar(MultipartFile file);
}
