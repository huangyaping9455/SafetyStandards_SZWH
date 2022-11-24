package org.springblade.train.config.model;

/**
 * @Description: 全局缓存设置条件使用
 * @Author: stydm
 * @CreateDate: 2019-10-09$ 10:07$
 * @UpdateUser: stydm
 * @UpdateDate: 2019-10-09$ 10:07$
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */

public class CacheManagerEntity {
    /**
     * 保存的数据
     */
    private Object datas;

    /**
     * 设置数据失效时间,为0表示永不失效
     */
    private long timeOut;

    /**
     * 最后刷新时间
     */
    private long lastRefeshTime;

    public CacheManagerEntity(Object datas) {
        this.datas = datas;
    }

    public CacheManagerEntity(Object datas, long timeOut, long lastRefeshTime) {
        this.datas = datas;
        this.timeOut = timeOut;
        this.lastRefeshTime = lastRefeshTime;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public long getLastRefeshTime() {
        return lastRefeshTime;
    }

    public void setLastRefeshTime(long lastRefeshTime) {
        this.lastRefeshTime = lastRefeshTime;
    }
}
