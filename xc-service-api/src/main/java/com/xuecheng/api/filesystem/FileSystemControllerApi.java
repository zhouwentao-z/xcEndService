package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 周文韬
 * @create 2020-12-18 10:23:55
 * @desc ...
 */
@Api(value ="文件管理接口",produces = "文件管理的接口，提供文件的增、删、改、查")
public interface FileSystemControllerApi {
    @ApiOperation("上传文件接口")
    public UploadFileResult upload(MultipartFile multipartFile,String filetag, String businesskey, String metadata);
}
