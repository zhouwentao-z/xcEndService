package com.xuecheng.order.dao;

import com.xuecheng.framework.domain.task.XcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author 周文韬
 * @create 2021-01-13 16:45:30
 * @desc ...
 */
public interface XcTaskRepostory extends JpaRepository<XcTask,String> {
    //查询某个时间之前的n条任务
    Page<XcTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime );
    //更新updateTime
    @Modifying
    @Query("update XcTask t set t.updateTime=:updateTime where t.id =:id ")
    public int updateTaskTime(@Param(value = "id") String id, @Param("updateTime") Date updateTime);

    @Modifying
    @Query("update XcTask t set t.version =:version+1 where t.id=:id and t.version=:version")
    public int updateTaskVersion(@Param(value = "id")String id,@Param(value = "version") int version);
}
