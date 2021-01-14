package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 周文韬
 * @create 2021-01-09 17:07:22
 * @desc ...
 */
public interface XcCompanyUserRepository extends JpaRepository<XcCompanyUser,String> {
    //根据用户id查询所属公司id
    XcCompanyUser findByUserId(String userId);
}
