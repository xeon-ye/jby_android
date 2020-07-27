package com.jkrm.education.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.jkrm.education.db.greendao.DaoSession;
import com.jkrm.education.db.greendao.DaoMicroLessonBeanDao;
import com.jkrm.education.db.greendao.DaoCatalogueBeanDao;

import java.util.List;
import com.jkrm.education.db.greendao.DaoVideoBeanDao;

/**
 * @Description: 微课目录
 * @Author: xiangqian
 * @CreateDate: 2020/3/17 15:05
 */
@Entity
public class DaoCatalogueBean {
    @Id
    private String id;
    private String key;
    private String mlessonId;
    private String name;
    private String parentId;
    private String pcvId;
    private String title;
    private boolean isCheck;
    @ToMany(referencedJoinProperty = "catalogId")
    private List<DaoVideoBean> mVideoBeanList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1170484300)
    private transient DaoCatalogueBeanDao myDao;
    @Generated(hash = 393843254)
    public DaoCatalogueBean(String id, String key, String mlessonId, String name, String parentId,
            String pcvId, String title, boolean isCheck) {
        this.id = id;
        this.key = key;
        this.mlessonId = mlessonId;
        this.name = name;
        this.parentId = parentId;
        this.pcvId = pcvId;
        this.title = title;
        this.isCheck = isCheck;
    }
    @Generated(hash = 373689582)
    public DaoCatalogueBean() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getMlessonId() {
        return this.mlessonId;
    }
    public void setMlessonId(String mlessonId) {
        this.mlessonId = mlessonId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentId() {
        return this.parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getPcvId() {
        return this.pcvId;
    }
    public void setPcvId(String pcvId) {
        this.pcvId = pcvId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1416477273)
    public List<DaoVideoBean> getMVideoBeanList() {
        if (mVideoBeanList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DaoVideoBeanDao targetDao = daoSession.getDaoVideoBeanDao();
            List<DaoVideoBean> mVideoBeanListNew = targetDao
                    ._queryDaoCatalogueBean_MVideoBeanList(id);
            synchronized (this) {
                if (mVideoBeanList == null) {
                    mVideoBeanList = mVideoBeanListNew;
                }
            }
        }
        return mVideoBeanList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 370107130)
    public synchronized void resetMVideoBeanList() {
        mVideoBeanList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 762243858)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDaoCatalogueBeanDao() : null;
    }
    public boolean getIsCheck() {
        return this.isCheck;
    }
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

}
