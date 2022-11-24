package org.springblade.train.config;

/**
 * @Description: http请求枚举结果
 */
public enum HttpResultEnum {
    SUCCESS(0),			//成功
    ERROR(-1),			//失败
	INVALID(-2),		//用户会话失效
    EXISTS(-3);         //数据重复

    private int value;

    private HttpResultEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
