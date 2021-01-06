package com.xuecheng.manage_media.controller;

import com.xuecheng.api.media.MediaFileControllerApi;
import com.xuecheng.api.media.MediaUploadControllerApi;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.service.MediaFileService;
import com.xuecheng.manage_media.service.MediaUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 周文韬
 * @create 2021-01-04 16:34:30
 * @desc ...
 */
@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {

    @Autowired
    MediaFileService mediaFileService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<MediaFile> findList(@PathVariable("page") int page, @PathVariable("size")int size, QueryMediaFileRequest queryMediaFileRequest) {
        return mediaFileService.findList(page,size,queryMediaFileRequest);
    }
}
