package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 周文韬
 * @create 2020-12-18 17:53:10
 * @desc ...
 */
@Api(value = "数据字典接口",produces = "提供数据字典接口的管理、查询功能")
public interface SysDicthinaryControllerApi {

    @ApiOperation(value = "数据字典查询接口")
    public SysDictionary getByType(String type);
}
