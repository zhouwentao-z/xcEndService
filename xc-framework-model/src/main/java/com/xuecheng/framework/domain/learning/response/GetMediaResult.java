package com.xuecheng.framework.domain.learning.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 周文韬
 * @create 2021-01-06 18:00:03
 * @desc ...
 */
@Data
@ToString
@NoArgsConstructor
public class GetMediaResult extends ResponseResult {

    public GetMediaResult(ResultCode resultCode,String fileUrl) {
        super(resultCode);
        this.fileUrl=fileUrl;
    }
    //文件播放地址
    private String fileUrl;
}
