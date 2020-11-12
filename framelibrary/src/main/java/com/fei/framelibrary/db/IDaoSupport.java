package com.fei.framelibrary.db;

import com.fei.framelibrary.db.crud.DeleteSupport;
import com.fei.framelibrary.db.crud.InsertSupport;
import com.fei.framelibrary.db.crud.QuerySupport;
import com.fei.framelibrary.db.crud.UpdateSupport;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IDaoSupport
 * @Description: dao接口
 * @Author: Fei
 * @CreateDate: 2020/11/11 14:25
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/11 14:25
 * @UpdateRemark: 包含初始化，增删改查
 * @Version: 1.0
 */
public interface IDaoSupport<T> {

    /**
     * 初始化
     */
    void init();

    //插入
    public InsertSupport<T> getInsertSupport();

    //查询
    public QuerySupport<T> getQuerySupport();

    //删除
    public DeleteSupport<T> getDeleteSupport();

    //更新
    public UpdateSupport<T> getUpdateSupport();

    //删除表
    public void dropTable();
}
