package org.springblade.train.config;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * @Description: 基础类
 */
public class BaseController {

    protected ModelMapper modelMapper = new ModelMapper();

    public BaseController() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        //将匹配策略设置为严格策略
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * @return
     * @description: 构造成功返回结果
     * @author: : 需要返回的数据，可选
     */
    protected String returnResult(HttpResultEnum code, Object data, String msg, Long count) {
        HttpResult result = new HttpResult();
        if (data != null) {
            result.setData(data);
        }
        if (code != null) {
            result.setCode(code.getValue());
        }
        result.setMsg(msg);
        result.setCount(count);
        return JSONUtils.obj2String(result);
    }

    /**
     * @param code
     * @param data
     * @param count
     * @return
     */
    protected String returnResult(HttpResultEnum code, Object data, Long count) {
        return returnResult(code, data, null, count);
    }

    /**
     * @param code
     * @param data
     * @param msg
     * @return
     */
    protected String returnResult(HttpResultEnum code, Object data, String msg) {
        return this.returnResult(code, data, msg, null);
    }

    /**
     * @param : 需要返回的数据，可选
     * @return
     * @description: 构造成功返回结果
     * @author:
     */
    protected String returnResult(HttpResultEnum code) {
        return this.returnResult(code, null, null, null);
    }

    /**
     * @param : 需要返回的数据，可选
     * @return
     * @description: 构造成功返回结果
     * @author:
     */
    protected String returnSuccessResult(Object data) {
    	return returnResult(HttpResultEnum.SUCCESS, data, null, null);
    }
    
    /**
     * @param : 需要返回的数据，可选
     * @return
     * @description: 构造失败返回结果
     * @author:
     */
    protected String returnSuccessResult(Object data,String msg) {
        return returnResult(HttpResultEnum.SUCCESS, data, msg, null);
    }
    /**
     * @param : 需要返回的数据，可选
     * @return
     * @description: 构造失败返回结果
     * @author:
     */
    protected String returnSuccessResult(Object data, String msg, Long count) {
    	return returnResult(HttpResultEnum.SUCCESS, data, msg, count);
    }
    /**
     * @param : 需要返回的数据，可选
     * @return
     * @description: 构造失败返回结果
     * @author:
     */
    protected String returnErrorResult(String msg) {
    	return returnResult(HttpResultEnum.ERROR, null, msg, null);
    }
    

}
