package cn.qifeng.common.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultType  {
    @ApiModelProperty(value = "是否返回成功")
    private  Boolean success;

    @ApiModelProperty(value = "返回code码")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data=new HashMap<String,Object>();

    private ResultType(){};//定义为私有方法

    //公有成功方法
    public static ResultType success(){
        ResultType resultType=new ResultType();//创建对象
        resultType.setSuccess(true);//是否成功
        resultType.setCode(ResultCode.success);//返回成功值响应码
        resultType.setMessage("成功");//返回信息
        return resultType;
    }

    //公有失败方法
    public static ResultType error(){
        ResultType resultType=new ResultType();
        resultType.setSuccess(false);
        resultType.setCode(ResultCode.error);
        resultType.setMessage("失败");
        return resultType;
    }

    public ResultType success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultType message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultType code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultType data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResultType data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
