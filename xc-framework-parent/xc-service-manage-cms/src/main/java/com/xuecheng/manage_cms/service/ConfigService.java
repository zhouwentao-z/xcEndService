package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigService {

    @Autowired
    CmsConfigRepository cmsConfigRepository;

    /**
     * 根据ID查询配置管理信息
     * @param id
     * @return
     */
    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> optional=cmsConfigRepository.findById(id);
        if (optional.isPresent()){
            CmsConfig cmsConfig= optional.get();
            return cmsConfig;
        }
        return null;
    }
}
