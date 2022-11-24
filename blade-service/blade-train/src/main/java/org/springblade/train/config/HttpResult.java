package org.springblade.train.config;

import java.io.Serializable;

/**
 * @Description: 结果集实体类
 */
public class HttpResult implements Serializable {

    private static final long serialVersionUID = -7978635757653362784L;

    /**
     * 结果代码:0-成功，否则失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据结果集
     */
    private Object data;

    /**
     * 数据总数
     */
    private Long count;

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /*
     *
     * @param code
     *            执行结果代码
     * @param msg
     *            执行结果描述
     * @param data
     *            执行结果数据
     */
    public HttpResult(Integer code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public HttpResult() {
    }

}
