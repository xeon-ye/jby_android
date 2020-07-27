package com.jkrm.education.db.util;

import android.util.Log;

import com.hzw.baselib.util.AwLog;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.db.DaoCatalogueBean;
import com.jkrm.education.db.DaoMicroLessonBean;
import com.jkrm.education.db.DaoVideoBean;
import com.jkrm.education.db.greendao.DaoCatalogueBeanDao;
import com.jkrm.education.db.greendao.DaoMicroLessonBeanDao;
import com.jkrm.education.db.greendao.DaoVideoBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Description:
 * @Author: xiangqian
 * @CreateDate: 2020/3/17 14:40
 */

public class DaoUtil {

    private static final String TAG = "DaoUtil";
    private DaoManager mDaoManager;
    private static DaoUtil instance;

    public static DaoUtil getInstance() {
        if (instance == null) {
            instance = new DaoUtil();
        }
        return instance;
    }

    private DaoUtil() {
        mDaoManager = DaoManager.getInstance();
        mDaoManager.init(MyApp.getInstance());
    }

    /**
     * 记录微课课程数据
     *
     * @return
     */
    public boolean insertMicro(DaoMicroLessonBean bean) {
        boolean flag = false;
        try {
            flag = mDaoManager.getDaoSession().getDaoMicroLessonBeanDao().insert(bean) == -1 ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        AwLog.d(TAG, "insert Bean :" + flag + "-->" + bean.toString());
        return flag;
    }

    public DaoMicroLessonBean queryMicro(String id){
        QueryBuilder<DaoMicroLessonBean> where = mDaoManager.getDaoSession().getDaoMicroLessonBeanDao().queryBuilder().where(DaoMicroLessonBeanDao.Properties.Id.eq(id));
        return where.unique();
    }
    /**
     * 删除单条微课课程数据
     *
     * @param bean
     * @return
     */
    public boolean deleteMicro(DaoMicroLessonBean bean) {
        boolean flag = false;
        try {
            //按照id删除
            mDaoManager.getDaoSession().delete(bean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有微课课程数据
     *
     * @return
     */
    public List<DaoMicroLessonBean> queryMicro() {
        return mDaoManager.getDaoSession().loadAll(DaoMicroLessonBean.class);
    }

    /**
     * 记录微课目录
     *
     * @param bean
     * @return
     */
    public boolean insertCatalogue(DaoCatalogueBean bean) {
        boolean flag = false;
        try {
            flag = mDaoManager.getDaoSession().getDaoCatalogueBeanDao().insert(bean) == -1 ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        AwLog.d(TAG, "insert Bean :" + flag + "-->" + bean.toString());
        return flag;
    }


    /**
     * 插入多条目录数据
     *
     * @param beanList
     * @return
     */
    public boolean insertCatalogueList(final List<DaoCatalogueBean> beanList) {
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DaoCatalogueBean bean : beanList) {
                        mDaoManager.getDaoSession().getDaoCatalogueBeanDao().insertOrReplace(bean);
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
     * 删除单条微课目录
     *
     * @param bean
     * @return
     */
    public boolean deleteCatalogue(DaoCatalogueBean bean) {
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().getDaoCatalogueBeanDao().delete(bean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        AwLog.d(TAG, "insert Bean :" + flag + "-->" + bean.toString());
        return flag;
    }

    /**
     * 删除所有微课目录
     *
     * @return
     */
    public boolean deleteAllCatalogue() {
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().getDaoCatalogueBeanDao().deleteAll();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有目录信息
     *
     * @return
     */
    public List<DaoCatalogueBean> queryAllCatalogue() {
        return mDaoManager.getDaoSession().loadAll(DaoCatalogueBean.class);
    }

    /**
     * 根据目录id查询所属微课课程列表
     */
    public List<DaoCatalogueBean> queryCatalogueListByQueryBuilder(String id) {
        QueryBuilder<DaoCatalogueBean> queryBuilder = mDaoManager.getDaoSession().queryBuilder(DaoCatalogueBean.class);
        List<DaoCatalogueBean> list = queryBuilder.where(DaoCatalogueBeanDao.Properties.MlessonId.eq(id)).list();
        return list;
    }

    /**
     * 记录单条视频数据
     *
     * @param daoVideoBean
     * @return
     */
    public boolean insertVideoBean(DaoVideoBean daoVideoBean) {
        boolean flag = false;
        try {
            flag = mDaoManager.getDaoSession().getDaoVideoBeanDao().insert(daoVideoBean) == -1 ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
            AwLog.e(TAG,e.toString());
        }
        return flag;
    }


    /**
     * 删除单条视频数据
     *
     * @param bean
     * @return
     */
    public boolean deleteVideoBean(DaoVideoBean bean) {
        DaoVideoBean daoVideoBean = queryVideoByUrl(bean);
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().getDaoVideoBeanDao().delete(daoVideoBean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *  批量删除
     * @param list
     */
    public void deleteVideoList(List<DaoVideoBean> list){
        mDaoManager.getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (DaoVideoBean daoVideoBean : list) {
                    deleteVideoBean(daoVideoBean);
                }
            }
        });
    }
    /**
     * 插入多条视频数据
     *
     * @param beanList
     * @return
     */
    public boolean insertVideoList(final List<DaoVideoBean> beanList) {
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DaoVideoBean bean : beanList) {
                        mDaoManager.getDaoSession().getDaoVideoBeanDao().insertOrReplace(bean);
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
     * 修改视频状态
     *
     * @param bean
     * @return
     */
    public boolean updateVideoState(DaoVideoBean bean) {
        DaoVideoBean daoVideoBean = queryVideoByUrl(bean);
        if(daoVideoBean==null){
            return false;
        }
        daoVideoBean.setDownloadStatus(bean.getDownloadStatus());
        daoVideoBean.setTotal(bean.getTotal());
        String fileName = bean.getUrl().substring(bean.getUrl().lastIndexOf("/"));
        daoVideoBean.setFileName(fileName);
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().getDaoVideoBeanDao().update(daoVideoBean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新视频下载进度
     * @param bean
     * @return
     */
    public boolean updateVideoProgress(DaoVideoBean bean){
        DaoVideoBean daoVideoBean = queryVideoByUrl(bean);
        daoVideoBean.setProgress(bean.getProgress());
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().getDaoVideoBeanDao().update(daoVideoBean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void updateVideoProgressList(List<DaoVideoBean> list){
        mDaoManager.getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (DaoVideoBean bean : list) {
                    DaoVideoBean daoVideoBean = DaoUtil.getInstance().queryVideoByUrl(bean);
                    daoVideoBean.setProgress(bean.getProgress());
                    mDaoManager.getDaoSession().getDaoVideoBeanDao().insertOrReplace(daoVideoBean);
                }
            }
        });
    }

    public boolean updateVideo(DaoVideoBean bean) {
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().getDaoVideoBeanDao().update(bean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 根据目录muid查询所有视频
     */
    public List<DaoVideoBean> queryVideoListByQueryBuilder(String id) {
        QueryBuilder<DaoVideoBean> queryBuilder = mDaoManager.getDaoSession().queryBuilder(DaoVideoBean.class);
        return queryBuilder.where(DaoVideoBeanDao.Properties.CatalogId.eq(id)).list();
    }

    /**
     * 查询所有视频
     *
     * @return
     */
    public List<DaoVideoBean> queryAllVideo() {
        return mDaoManager.getDaoSession().loadAll(DaoVideoBean.class);
    }

    public List<DaoVideoBean> queryNeedDownVideoList(){
        QueryBuilder<DaoVideoBean> daoVideoBeanQueryBuilder = mDaoManager.getDaoSession().queryBuilder(DaoVideoBean.class);
        return daoVideoBeanQueryBuilder.where(DaoVideoBeanDao.Properties.DownloadStatus.notEq(DaoVideoBean.DOWNLOAD_OVER)).list();
    }

    /**
     * 根据id查询video信息
     * @param daoVideoBean
     * @return
     */
    public DaoVideoBean queryVideoByUrl(DaoVideoBean daoVideoBean){
        QueryBuilder<DaoVideoBean> where = mDaoManager.getDaoSession().getDaoVideoBeanDao().queryBuilder().where(DaoVideoBeanDao.Properties.Url.eq(daoVideoBean.getUrl()));
        List<DaoVideoBean> list = where.list();
        return where.unique();
    }

}
