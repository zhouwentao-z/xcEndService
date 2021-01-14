package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 周文韬
 * @create 2021-01-09 17:05:41
 * @desc ...
 */
public interface XcUserRepository extends JpaRepository<XcUser,String> {

    XcUser findXcUserByUsername(String username);
}
