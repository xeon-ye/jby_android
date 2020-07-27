package com.jkrm.education.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.jkrm.education.db.greendao.DaoSession;
import com.jkrm.education.db.greendao.DaoCatalogueBeanDao;
import com.jkrm.education.db.greendao.DaoMicroLessonBeanDao;

/**
 * @Description: 微课视频列表数据
 * @Author: xiangqian
 * @CreateDate: 2020/3/17 13:55
 */
@Entity
public class DaoMicroLessonBean {
    @Id
    private String id;
    private String keywords;
    private String mlessonCount;
    private String mlessonExplain;
    private String mlessonName;
    private String mlessonPrice;
    private String mlessonUrl;
    private String oneId;
    private String order;
    private String orderBy;
    private String orderKey;
    private String orderType;
    private String pcvId;
    private String twoId;
    private String typeId;
    private String typeName;
    private String whetherBuy;
    private String whetherFree;
    private boolean isCheck;
    private String cacheNum;
    private String cacheSize;
    private boolean isPause;

    @ToMany(referencedJoinProperty = "mlessonId")
    private List<DaoCatalogueBean> list;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1551087098)
    private transient DaoMicroLessonBeanDao myDao;
    @Generated(hash = 1202436457)
    public DaoMicroLessonBean(String id, String keywords, String mlessonCount, String mlessonExplain,
            String mlessonName, String mlessonPrice, String mlessonUrl, String oneId, String order,
            String orderBy, String orderKey, String orderType, String pcvId, String twoId,
            String typeId, String typeName, String whetherBuy, String whetherFree, boolean isCheck,
            String cacheNum, String cacheSize, boolean isPause) {
        this.id = id;
        this.keywords = keywords;
        this.mlessonCount = mlessonCount;
        this.mlessonExplain = mlessonExplain;
        this.mlessonName = mlessonName;
        this.mlessonPrice = mlessonPrice;
        this.mlessonUrl = mlessonUrl;
        this.oneId = oneId;
        this.order = order;
        this.orderBy = orderBy;
        this.orderKey = orderKey;
        this.orderType = orderType;
        this.pcvId = pcvId;
        this.twoId = twoId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.whetherBuy = whetherBuy;
        this.whetherFree = whetherFree;
        this.isCheck = isCheck;
        this.cacheNum = cacheNum;
        this.cacheSize = cacheSize;
        this.isPause = isPause;
    }
    @Generated(hash = 1917411822)
    public DaoMicroLessonBean() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getKeywords() {
        return this.keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getMlessonCount() {
        return this.mlessonCount;
    }
    public void setMlessonCount(String mlessonCount) {
        this.mlessonCount = mlessonCount;
    }
    public String getMlessonExplain() {
        return this.mlessonExplain;
    }
    public void setMlessonExplain(String mlessonExplain) {
        this.mlessonExplain = mlessonExplain;
    }
    public String getMlessonName() {
        return this.mlessonName;
    }
    public void setMlessonName(String mlessonName) {
        this.mlessonName = mlessonName;
    }
    public String getMlessonPrice() {
        return this.mlessonPrice;
    }
    public void setMlessonPrice(String mlessonPrice) {
        this.mlessonPrice = mlessonPrice;
    }
    public String getMlessonUrl() {
        return this.mlessonUrl;
    }
    public void setMlessonUrl(String mlessonUrl) {
        this.mlessonUrl = mlessonUrl;
    }
    public String getOneId() {
        return this.oneId;
    }
    public void setOneId(String oneId) {
        this.oneId = oneId;
    }
    public String getOrder() {
        return this.order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public String getOrderBy() {
        return this.orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public String getOrderKey() {
        return this.orderKey;
    }
    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }
    public String getOrderType() {
        return this.orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public String getPcvId() {
        return this.pcvId;
    }
    public void setPcvId(String pcvId) {
        this.pcvId = pcvId;
    }
    public String getTwoId() {
        return this.twoId;
    }
    public void setTwoId(String twoId) {
        this.twoId = twoId;
    }
    public String getTypeId() {
        return this.typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getWhetherBuy() {
        return this.whetherBuy;
    }
    public void setWhetherBuy(String whetherBuy) {
        this.whetherBuy = whetherBuy;
    }
    public String getWhetherFree() {
        return this.whetherFree;
    }
    public void setWhetherFree(String whetherFree) {
        this.whetherFree = whetherFree;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 513392109)
    public List<DaoCatalogueBean> getList() {
        if (list == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DaoCatalogueBeanDao targetDao = daoSession.getDaoCatalogueBeanDao();
            List<DaoCatalogueBean> listNew = targetDao
                    ._queryDaoMicroLessonBean_List(id);
            synchronized (this) {
                if (list == null) {
                    list = listNew;
                }
            }
        }
        return list;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 589833612)
    public synchronized void resetList() {
        list = null;
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
    @Generated(hash = 402282614)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDaoMicroLessonBeanDao() : null;
    }
    public boolean getIsCheck() {
        return this.isCheck;
    }
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
    public String getCacheNum() {
        return this.cacheNum;
    }
    public void setCacheNum(String cacheNum) {
        this.cacheNum = cacheNum;
    }
    public String getCacheSize() {
        return this.cacheSize;
    }
    public void setCacheSize(String cacheSize) {
        this.cacheSize = cacheSize;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
    public boolean getIsPause() {
        return this.isPause;
    }
    public void setIsPause(boolean isPause) {
        this.isPause = isPause;
    }
}
