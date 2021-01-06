package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 周文韬
 * @create 2020-12-22 11:21:06
 * @desc ...
 */
@Data
@ToString
@NoArgsConstructor
public class CoursePublishResult implements Serializable {
    String previewUrl;
    public CoursePublishResult(ResultCode resultCode,String previewUrl){
        super();
        this.previewUrl=previewUrl;
    }
}
