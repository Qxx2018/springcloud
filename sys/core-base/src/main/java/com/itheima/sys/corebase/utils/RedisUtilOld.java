//package com.itheima.sys.corebase.lock;
//
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//public final class RedisUtil {
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
//
//	/**
//	 * 指定缓存失效时间
//	 *
//	 * @param key  键
//	 * @param time 时间(秒)
//	 * @return
//	 */
//	public boolean expire(String key, long time) {
//		try {
//			if (time > 0) {
//				redisTemplate.expire(key, time, TimeUnit.SECONDS);
//			}
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 根据key 获取过期时间
//	 *
//	 * @param key 键 不能为null
//	 * @return 时间(秒) 返回0代表为永久有效
//	 */
//	public long getExpire(String key) {
//		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
//	}
//
//	/**
//	 * 判断key是否存在
//	 *
//	 * @param key 键
//	 * @return true 存在 false不存在
//	 */
//	public boolean hasKey(String key) {
//		try {
//			return redisTemplate.hasKey(key);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 删除缓存
//	 *
//	 * @param key 可以传一个值 或多个
//	 */
//	@SuppressWarnings("unchecked")
//	public void del(String... key) {
//		if (key != null && key.length > 0) {
//			if (key.length == 1) {
//				redisTemplate.delete(key[0]);
//			} else {
//				redisTemplate.delete(CollectionUtils.arrayToList(key));
//			}
//		}
//	}
//
//	/**
//	 * 批量删除缓存
//	 *
//	 * @param key
//	 *            可以传一个值 或多个
//	 */
//	@SuppressWarnings("unchecked")
//	public void batchDel(String keyPrefix) {
//		redisTemplate.delete(redisTemplate.keys(keyPrefix + "*"));
//	}
//
//// ============================String=============================
//	/**
//	 * 普通缓存获取
//	 *
//	 * @param key 键
//	 * @return 值
//	 */
//	public Object get(String key) {
//		return key == null ? null : redisTemplate.opsForValue().get(key);
//	}
//
//	/**
//	 * 获取redis中以某些字符串为前缀的KEY列表
//	 *
//	 * @param key
//	 *            键
//	 * @return 值
//	 */
//	public Set<String> keys(String prefix) {
//		return prefix == null ? null : redisTemplate.keys(prefix);
//	}
//
//	/**
//	 * 普通缓存放入
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @return true成功 false失败
//	 */
//	public boolean set(String key, Object value) {
//		try {
//			redisTemplate.opsForValue().set(key, value);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 普通缓存放入并设置时间
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
//	 * @return true成功 false 失败
//	 */
//	public boolean set(String key, Object value, long time) {
//		try {
//			if (time > 0) {
//				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
//			} else {
//				set(key, value);
//			}
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 递增
//	 * 如果key不存在则会设置为delta大小,如果已经存在,则会在已经存在的基础上加delta大小。
//	 *
//	 * @param key   键
//	 * @param delta 要增加几(大于0)
//	 * @return
//	 */
//	public long incr(String key, long delta) {
//		if (delta < 0) {
//			throw new RuntimeException("递增因子必须大于0");
//		}
//		return redisTemplate.opsForValue().increment(key, delta);
//	}
//
//	/**
//	 * 递减
//	 *
//	 * @param key   键
//	 * @param delta 要减少几(小于0)
//	 * @return
//	 */
//	public long decr(String key, long delta) {
//		if (delta < 0) {
//			throw new RuntimeException("递减因子必须大于0");
//		}
//		return redisTemplate.opsForValue().increment(key, -delta);
//	}
//
//// ================================Map=================================
//	/**
//	 * HashGet
//	 *
//	 * @param key  键 不能为null
//	 * @param item 项 不能为null
//	 * @return 值
//	 */
//	public Object hget(String key, String item) {
//		return redisTemplate.opsForHash().get(key, item);
//	}
//
//	/**
//	 * 获取hashKey对应的所有键值
//	 *
//	 * @param key 键
//	 * @return 对应的多个键值
//	 */
//	public Map<Object, Object> hmget(String key) {
//		return redisTemplate.opsForHash().entries(key);
//	}
//
//	/**
//	 * HashSet
//	 *
//	 * @param key 键
//	 * @param map 对应多个键值
//	 * @return true 成功 false 失败
//	 */
//	public boolean hmset(String key, Map<String, Object> map) {
//		try {
//			redisTemplate.opsForHash().putAll(key, map);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * HashSet 并设置时间
//	 *
//	 * @param key  键
//	 * @param map  对应多个键值
//	 * @param time 时间(秒)
//	 * @return true成功 false失败
//	 */
//	public boolean hmset(String key, Map<String, Object> map, long time) {
//		try {
//			redisTemplate.opsForHash().putAll(key, map);
//			if (time > 0) {
//				expire(key, time);
//			}
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 向一张hash表中放入数据,如果不存在将创建
//	 *
//	 * @param key   键
//	 * @param item  项
//	 * @param value 值
//	 * @return true 成功 false失败
//	 */
//	public boolean hset(String key, String item, Object value) {
//		try {
//			redisTemplate.opsForHash().put(key, item, value);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 向一张hash表中放入数据,如果不存在将创建
//	 *
//	 * @param key   键
//	 * @param item  项
//	 * @param value 值
//	 * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
//	 * @return true 成功 false失败
//	 */
//	public boolean hset(String key, String item, Object value, long time) {
//		try {
//			redisTemplate.opsForHash().put(key, item, value);
//			if (time > 0) {
//				expire(key, time);
//			}
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 删除hash表中的值
//	 *
//	 * @param key  键 不能为null
//	 * @param item 项 可以使多个 不能为null
//	 */
//	public void hdel(String key, Object... item) {
//		redisTemplate.opsForHash().delete(key, item);
//	}
//
//	/**
//	 * 判断hash表中是否有该项的值
//	 *
//	 * @param key  键 不能为null
//	 * @param item 项 不能为null
//	 * @return true 存在 false不存在
//	 */
//	public boolean hHasKey(String key, String item) {
//		return redisTemplate.opsForHash().hasKey(key, item);
//	}
//
//	/**
//	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
//	 *
//	 * @param key  键
//	 * @param item 项
//	 * @param by   要增加几(大于0)
//	 * @return
//	 */
//	public double hincr(String key, String item, double by) {
//		return redisTemplate.opsForHash().increment(key, item, by);
//	}
//
//	/**
//	 * hash递减
//	 *
//	 * @param key  键
//	 * @param item 项
//	 * @param by   要减少记(小于0)
//	 * @return
//	 */
//	public double hdecr(String key, String item, double by) {
//		return redisTemplate.opsForHash().increment(key, item, -by);
//	}
//
//// ============================set=============================
//	/**
//	 * 根据key获取Set中的所有值
//	 *
//	 * @param key 键
//	 * @return
//	 */
//	public Set<Object> sGet(String key) {
//		try {
//			return redisTemplate.opsForSet().members(key);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return null;
//		}
//	}
//
//	/**
//	 * 根据value从一个set中查询,是否存在
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @return true 存在 false不存在
//	 */
//	public boolean sHasKey(String key, Object value) {
//		try {
//			return redisTemplate.opsForSet().isMember(key, value);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 将数据放入set缓存
//	 *
//	 * @param key    键
//	 * @param values 值 可以是多个
//	 * @return 成功个数
//	 */
//	public long sSet(String key, Object... values) {
//		try {
//			return redisTemplate.opsForSet().add(key, values);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return 0;
//		}
//	}
//
//	/**
//	 * 将set数据放入缓存
//	 *
//	 * @param key    键
//	 * @param time   时间(秒)
//	 * @param values 值 可以是多个
//	 * @return 成功个数
//	 */
//	public long sSetAndTime(String key, long time, Object... values) {
//		try {
//			Long count = redisTemplate.opsForSet().add(key, values);
//			if (time > 0) {
//                expire(key, time);
//            }
//			return count;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return 0;
//		}
//	}
//
//	/**
//	 * 获取set缓存的长度
//	 *
//	 * @param key 键
//	 * @return
//	 */
//	public long sGetSetSize(String key) {
//		try {
//			return redisTemplate.opsForSet().size(key);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return 0;
//		}
//	}
//
//	/**
//	 * 移除值为value的
//	 *
//	 * @param key    键
//	 * @param values 值 可以是多个
//	 * @return 移除的个数
//	 */
//	public long setRemove(String key, Object... values) {
//		try {
//			Long count = redisTemplate.opsForSet().remove(key, values);
//			return count;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return 0;
//		}
//	}
//
//// ===============================list=================================
//	/**
//	 * 获取list缓存的内容
//	 *
//	 * @param key   键
//	 * @param start 开始
//	 * @param end   结束 0 到 -1代表所有值
//	 * @return
//	 */
//	public List<Object> lGet(String key, long start, long end) {
//		try {
//			return redisTemplate.opsForList().range(key, start, end);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return null;
//		}
//	}
//
//	/**
//	 * 获取list缓存的长度
//	 *
//	 * @param key 键
//	 * @return
//	 */
//	public long lGetListSize(String key) {
//		try {
//			return redisTemplate.opsForList().size(key);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return 0;
//		}
//	}
//
//	/**
//	 * 通过索引 获取list中的值
//	 *
//	 * @param key   键
//	 * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
//	 * @return
//	 */
//	public Object lGetIndex(String key, long index) {
//		try {
//			return redisTemplate.opsForList().index(key, index);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return null;
//		}
//	}
//
//	/**
//	 * 将list放入缓存
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @param time  时间(秒)
//	 * @return
//	 */
//	public boolean lSet(String key, Object value) {
//		try {
//			redisTemplate.opsForList().rightPush(key, value);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 将list放入缓存
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @param time  时间(秒)
//	 * @return
//	 */
//	public boolean lSet(String key, Object value, long time) {
//		try {
//			redisTemplate.opsForList().rightPush(key, value);
//			if (time > 0) {
//                expire(key, time);
//            }
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 将list放入缓存
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @param time  时间(秒)
//	 * @return
//	 */
//	public boolean lSet(String key, List<Object> value) {
//		try {
//			redisTemplate.opsForList().rightPushAll(key, value);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 将list放入缓存
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @param time  时间(秒)
//	 * @return
//	 */
//	public boolean lSet(String key, List<Object> value, long time) {
//		try {
//			redisTemplate.opsForList().rightPushAll(key, value);
//			if (time > 0) {
//                expire(key, time);
//            }
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 根据索引修改list中的某条数据
//	 *
//	 * @param key   键
//	 * @param index 索引
//	 * @param value 值
//	 * @return
//	 */
//	public boolean lUpdateIndex(String key, long index, Object value) {
//		try {
//			redisTemplate.opsForList().set(key, index, value);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 移除N个值为value
//	 *
//	 * @param key   键
//	 * @param count 移除多少个
//	 * @param value 值
//	 * @return 移除的个数
//	 */
//	public long lRemove(String key, long count, Object value) {
//		try {
//			Long remove = redisTemplate.opsForList().remove(key, count, value);
//			return remove;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return 0;
//		}
//	}
//
//	/**
//	 * 缓存放入（原子操作）
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @return true成功 false失败
//	 */
//	public boolean getset(String key, Object value) {
//		try {
//			redisTemplate.opsForValue().getAndSet(key, value);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 将数据添加到key对应队列中的最左边,即队列头部
//	 *
//	 * @param key   键
//	 * @param value 值
//	 * @return
//	 */
//	public boolean lPush(String key, Object value) {
//		try {
//			redisTemplate.opsForList().leftPush(key, value);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return false;
//		}
//	}
//
//	/**
//	 * 移除并获取key对应队列终最右边的数据，即队列尾部
//	 *
//	 * @param key   键
//	 * @return
//	 */
//	public Object rPop(String key) {
//		try {
//			return redisTemplate.opsForList().rightPop(key);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return null;
//		}
//	}
//
//	/**
//	 * 分布式锁-加锁
//	 *
//	 * @param key
//	 * @param value 当前时间+超时时间
//	 * @return
//	 */
//	public boolean lock(String key, String value) {
//		if (redisTemplate.opsForValue().setIfAbsent(key, value)) {// 对应setnx命令
//			// 可以成功设置,也就是key不存在
//			return true;
//		}
//		// 判断锁超时 - 防止原来的操作异常，没有运行解锁操作 防止死锁
//		String currentValue = (String) redisTemplate.opsForValue().get(key);
//		// 如果锁过期
//		if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {// currentValue不为空且小于当前时间
//			// 获取上一个锁的时间value
//			String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, value);// 对应getset，如果key存在
//			// 假设两个线程同时进来，key被占用了。获取的值currentValue=A(get取的旧的值肯定是一样的),两个线程的value都是B,key都是K.锁时间已经过期了。
//			// 而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的value已经变成了B。只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
//			if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
//				// oldValue不为空且oldValue等于currentValue，也就是校验是不是上个对应的key的时间戳，也是防止并发
//				return true;
//			}
//		}
//		// 无锁
//		return false;
//	}
//
//	/**
//	 * 分布式锁-解锁
//	 *
//	 * @param key
//	 * @param value
//	 */
//	public void unlock(String key, String value) {
//		try {
//			String currentValue = (String) redisTemplate.opsForValue().get(key);
//			if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
//				redisTemplate.opsForValue().getOperations().delete(key);// 删除key
//			}
//		} catch (Exception e) {
//			logger.error("[Redis分布式锁] 解锁出现异常了，{}", e);
//		}
//	}
//}