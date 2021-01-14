package com.xuecheng.api.ucenter;

import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 周文韬
 * @create 2021-01-09 17:01:32
 * @desc ...
 */
@Api(value = "用户中心",description = "用户中心管理")
public interface UcenterControllerApi {
    @ApiOperation("根据账户查询用户信息")
    public XcUserExt getUserext(String username);
}
