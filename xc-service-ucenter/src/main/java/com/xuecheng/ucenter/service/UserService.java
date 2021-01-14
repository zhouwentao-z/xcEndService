package com.xuecheng.ucenter.service;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.ucenter.dao.XcCompanyUserRepository;
import com.xuecheng.ucenter.dao.XcMenuMapper;
import com.xuecheng.ucenter.dao.XcUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 周文韬
 * @create 2021-01-09 17:09:59
 * @desc ...
 */
@Service
public class UserService {

    @Autowired
    XcUserRepository xcUserRepository;
    @Autowired
    XcCompanyUserRepository companyUserRepository;
    @Autowired
    XcMenuMapper xcMenuMapper;

    //根据用户账号查询用户信息
    public XcUser findXcUserByUsername(String username){
        return  xcUserRepository.findXcUserByUsername(username);
    }
    //根据账号查询用户信息，返回 用户扩展信息
    public XcUserExt getUserExt(String username){
        XcUser xcUser = this.findXcUserByUsername(username);
        if (xcUser== null){
            return  null;
        }
        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(xcUser,xcUserExt);
        //用户Id
        String userId = xcUserExt.getId();
        //查询用户的所有的权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserid(userId);
        //查询用户所属的公司
        XcCompanyUser companyUser = companyUserRepository.findByUserId(userId);
        if (companyUser != null){
            String companyId = companyUser.getCompanyId();
            xcUserExt.setCompanyId(companyId);
            //设置权限
            xcUserExt.setPermissions(xcMenus);
        }
        return xcUserExt;
    }
}
