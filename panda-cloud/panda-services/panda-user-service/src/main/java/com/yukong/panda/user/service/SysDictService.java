package com.yukong.panda.user.service;

import com.yukong.panda.common.base.service.BaseService;
import com.yukong.panda.user.model.entity.SysDict;

import java.util.List;

/**
 * @author yukong
 * @date 2019-01-23 10:56
 */
public interface SysDictService extends BaseService<SysDict> {

    /**
     * 获取顶级字典列表
     * @param desc
     * @return 顶级字典列表
     */
    List<SysDict> getTopDictListByDesc(String desc);

}
