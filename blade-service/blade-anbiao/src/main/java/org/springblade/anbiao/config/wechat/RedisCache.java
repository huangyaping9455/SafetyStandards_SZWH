package org.springblade.anbiao.config.wechat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import static com.sun.jndi.ldap.LdapPoolManager.expire;

/**
 * @author Administrator
 * @create 2023-10-07 10:54
 */
@Component
public class RedisCache {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 默认过期时长为24小时，单位：秒
	 */
	public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;
	/**
	 * 过期时长为1小时，单位：秒
	 */
	public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
	/**
	 * 过期时长为6小时，单位：秒
	 */
	public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
	/**
	 * 不设置过期时长
	 */
	public final static long NOT_EXPIRE = -1L;

	public void set(String key, Object value, long expire) {
		redisTemplate.opsForValue().set(key, value);
		if (expire != NOT_EXPIRE) {
			expire(key, expire);
		}
	}

	private void expire(String key, long expire) {
	}

	public void set(String key, Object value) {
		set(key, value, DEFAULT_EXPIRE);
	}

	public Object get(String key, long expire) {
		Object value = redisTemplate.opsForValue().get(key);
		if (expire != NOT_EXPIRE) {
			expire(key, expire);
		}
		return value;
	}

	public Object get(String key) {
		return get(key, NOT_EXPIRE);
	}

	/**
	 * 删除缓存
	 *
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}


}

