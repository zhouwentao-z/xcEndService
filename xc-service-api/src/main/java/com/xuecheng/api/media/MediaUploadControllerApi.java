package com.xuecheng.api.media;

import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 周文韬
 * @create 2021-01-04 16:24:22
 * @desc ...
 */
@Api(value = "媒资管理接口",description = "媒资管理接口，提供文件上传，文件处理等接口")
public interface MediaUploadControllerApi {

    @ApiOperation(value = "文件上传注册")
    public ResponseResult register(String fileMd5,
                                   String fileName,
                                   Long fileSize,
                                   String mimetype,
                                   String fileExt);
    @ApiOperation(value = "分块检查")
    public ResponseResult checkchunk(String fileMd5,
                                     Integer chunk,
                                     Integer chunkSize);
    @ApiOperation(value = "上传分块")
    public ResponseResult uploadchunk(MultipartFile file,
                                      Integer chunk,
                                      String fileMd5);
    @ApiOperation(value = "合并分块")
    public  ResponseResult mergechunks(String fileMd5,
                                       String fileName,
                                       Long fileSize,
                                       String mimetype,
                                       String fileExt);
}
