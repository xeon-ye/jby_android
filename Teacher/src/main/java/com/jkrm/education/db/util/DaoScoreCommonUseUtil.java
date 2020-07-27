package com.jkrm.education.db.util;


import com.hzw.baselib.util.AwLog;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.DaoMarkCommonScoreUseBean;
import com.jkrm.education.db.DaoManager;
import com.jkrm.education.db.greendao.DaoMarkCommonScoreUseBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by hzw on 2017/11/16.
 */

public class DaoScoreCommonUseUtil {

    private static final String TAG = DaoScoreCommonUseUtil.class.getSimpleName();
    private DaoManager mDaoManager;
    private static DaoScoreCommonUseUtil instance;

    public static DaoScoreCommonUseUtil getInstance() {
        if(instance == null) {
            instance = new DaoScoreCommonUseUtil();
        }
        return instance;
    }

    private DaoScoreCommonUseUtil() {
        mDaoManager = DaoManager.getInstance();
        mDaoManager.init(MyApp.getInstance());
    }



    /**
     * 完成 Bean记录的插入，如果表未创建，先创建 Bean表
     * @param bean
     * @return
     */
    public boolean insertBean(DaoMarkCommonScoreUseBean bean){
        boolean flag = false;
        try {
            flag = mDaoManager.getDaoSession().getDaoMarkCommonScoreUseBeanDao().insert(bean) == -1 ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        AwLog.d(TAG, "insert Bean :" + flag + "-->" + bean.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     * @param beanList
     * @return
     */
    public boolean insertBeanList(final List<DaoMarkCommonScoreUseBean> beanList) {
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DaoMarkCommonScoreUseBean bean : beanList) {
                        mDaoManager.getDaoSession().insertOrReplace(bean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @param bean
     * @return
     */
    public boolean updateBean(DaoMarkCommonScoreUseBean bean){
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().update(bean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @param bean
     * @return
     */
    public boolean deleteBean(DaoMarkCommonScoreUseBean bean){
        boolean flag = false;
        try {
            //按照id删除
            mDaoManager.getDaoSession().delete(bean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAllBean(){
        boolean flag = false;
        try {
            //按照id删除
            mDaoManager.getDaoSession().deleteAll(DaoMarkCommonScoreUseBean.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<DaoMarkCommonScoreUseBean> queryAllBean(){
        return mDaoManager.getDaoSession().loadAll(DaoMarkCommonScoreUseBean.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public DaoMarkCommonScoreUseBean queryBeanById(long key){
        return mDaoManager.getDaoSession().load(DaoMarkCommonScoreUseBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */

    /**
     * * String sql = "where _id > ?";
     String[] condition = new String[]{"3"};
     List<GreenDaoTestBean> list = mMeiziDaoUtils.queryBeanByNativeSql(sql, condition);
     for (GreenDaoTestBean bean : list) {
     Log.i(TAG, bean.toString());
     }
     */
    public List<DaoMarkCommonScoreUseBean> queryBeanByNativeSql(String sql, String[] conditions){
        return mDaoManager.getDaoSession().queryRaw(DaoMarkCommonScoreUseBean.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     */
    public List<DaoMarkCommonScoreUseBean> queryBeanByQueryBuilder(long id){
        QueryBuilder<DaoMarkCommonScoreUseBean> queryBuilder = mDaoManager.getDaoSession().queryBuilder(DaoMarkCommonScoreUseBean.class);
        return queryBuilder.where(DaoMarkCommonScoreUseBeanDao.Properties.Id.eq(id)).list();
    }

    /**
     * 使用queryBuilder进行查询
     */
    public List<DaoMarkCommonScoreUseBean> queryBeanByQueryBuilderOfName(String params){
        QueryBuilder<DaoMarkCommonScoreUseBean> queryBuilder = mDaoManager.getDaoSession().queryBuilder(DaoMarkCommonScoreUseBean.class);
        return queryBuilder.where(DaoMarkCommonScoreUseBeanDao.Properties.QuestionId.eq(params)).list();
    }
}
