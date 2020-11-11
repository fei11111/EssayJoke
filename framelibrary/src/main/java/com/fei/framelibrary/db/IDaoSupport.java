package com.fei.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

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

    /**
     * Create a new row in the database from an object. If the object being created uses
     * from the database.
     *
     * @param data The data item that we are creating in the database.
     * @return The number of rows updated in the database. This should be 1.
     */
    public long insert(T data);

    /**
     * Create a new row in the database from an object. If the object being created uses
     * from the database.
     *
     * @param datas The data item that we are creating in the database.
     * @return The number of rows updated in the database. This should be 1.
     */
    public long insert(List<T> datas);

    /**
     * Retrieves an object associated with a specific ID.
     *
     * @param id Identifier that matches a specific row in the database to find and return.
     * @return The object that has the ID field which equals id or null if no matches.
     * @ on any SQL problems or if more than 1 item with the id are found in the database.
     */
    public T queryById(Long id);

    public List<T> query(Map<String,Object> map);


    /**
     * @return A list of all of the objects in the table.
     * @ on any SQL problems.
     */
    public List<T> queryForAll();


    /**
     * Store the fields from an object to the database row corresponding to the id from the data parameter. If you have
     * made changes to an object, this is how you persist those changes to the database. You cannot use this method to
     * update the id field -- see {@link #updateId} .
     *
     * <p>
     * NOTE: This will not save changes made to foreign objects or to foreign collections.
     * </p>
     *
     * @param data The data item that we are updating in the database.
     * @return The number of rows updated in the database. This should be 1.
     * @throws IllegalArgumentException If there is only an ID field in the object. See the {@link #updateId} method.
     * @ on any SQL problems.
     */
    public int update(T data);

    /**
     * 根据某列去更新对象
     *
     * @param data                 需要更新的对象
     * @param whereColumnName      列名：如果为空，则默认是idField
     * @param excludeColumnNameMap 排除不需要更新的列名（以key来存db的实际列名）
     * @return
     * @
     */
    public int update(T data, String whereColumnName, Map<String, String> excludeColumnNameMap);



    /**
     * Delete the database row corresponding to the id from the data parameter.
     *
     * @param data The data item that we are deleting from the database.
     * @return The number of rows updated in the database. This should be 1.
     * @ on any SQL problems.
     */
    public int delete(T data);

    /**
     * Delete an object from the database that has an id.
     *
     * @param id The id of the item that we are deleting from the database.
     * @return The number of rows updated in the database. This should be 1.
     * @ on any SQL problems.
     */
    public int deleteById(Long id);

    /**
     * Delete a collection of objects from the database using an IN SQL clause. The ids are extracted from the datas
     * parameter and used to remove the corresponding rows in the database with those ids.
     *
     * @param datas A collection of data items to be deleted.
     * @return The number of rows updated in the database. This should be the size() of the collection.
     * @ on any SQL problems.
     */
    public int delete(Collection<T> datas);

    /**
     * Delete the objects that match the collection of ids from the database using an IN SQL clause.
     *
     * @param ids A collection of data ids to be deleted.
     * @return The number of rows updated in the database. This should be the size() of the collection.
     * @ on any SQL problems.
     */
    public int deleteByIds(Collection<Long> ids);

    public void dropTable();
}
