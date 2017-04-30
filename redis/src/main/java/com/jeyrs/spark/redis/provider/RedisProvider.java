package com.jeyrs.spark.redis.provider;

import com.jeyrs.spark.model.Model;
import com.jeyrs.spark.provider.DataProvider;
import com.jeyrs.spark.redis.RedisConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisProvider<T extends Model> implements DataProvider<T> {
  private static final Logger LOGGER = LoggerFactory.getLogger(RedisProvider.class);
  private JedisPool jedisPool;
  private Class<T> clazz;
  private ObjectMapper objectMapper;

  public RedisProvider(RedisConfig redisConfig, Class<T> clazz, ObjectMapper objectMapper) {
    this.jedisPool = new JedisPool(new JedisPoolConfig(), redisConfig.getConnection());
    this.clazz = clazz;
    this.objectMapper = objectMapper;
  }

  @Override
  public List<T> findAll() {
    try (Jedis jedis = jedisPool.getResource()) {
      String prefix = clazz.getSimpleName().toLowerCase();

      if (!jedis.hgetAll(prefix).isEmpty()) {
        Map<String, String> map = jedis.hgetAll(prefix);
        List<String> result = map.entrySet()
          .stream()
          .map(Map.Entry::getValue)
          .collect(Collectors.toList());

        JavaType type = objectMapper.getTypeFactory().
          constructCollectionType(List.class, clazz);
        return objectMapper.readValue(result.toString(), type);
      }
    } catch (IOException e) {
      LOGGER.error("error", e);
    }

    return Collections.emptyList();
  }

  @Override
  public void insert(List<T> list) {

  }

  @Override
  public T findById(String id) {
    return null;
  }

  @Override
  public T findOne(String key, String value) {
    return null;
  }

  @Override
  public List<T> findMany(String key, String value) {
    return null;
  }

  @Override
  public boolean delete(String id) {
    return false;
  }

  @Override
  public T update(T model) {
    try (Jedis jedis = jedisPool.getResource()) {
      String prefix = clazz.getSimpleName().toLowerCase();
      jedis.hset(prefix, model.getId(), objectMapper.writeValueAsString(model));
    } catch (IOException e) {
      LOGGER.error("error", e);
    }

    return model;
  }
}
