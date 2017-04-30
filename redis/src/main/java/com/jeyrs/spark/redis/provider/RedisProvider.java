package com.jeyrs.spark.redis.provider;

import com.jeyrs.spark.model.Model;
import com.jeyrs.spark.provider.DataProvider;
import com.jeyrs.spark.redis.RedisConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    List<T> result = new ArrayList<>();
    try (Jedis jedis = jedisPool.getResource()) {
      try {
        String prefix = clazz.getSimpleName() + "s";
        result = objectMapper.readValue(jedis.get(prefix), new TypeReference<List<T>>(){});
      } catch (IOException e) {
        LOGGER.error("Failed", e);
      }
    }

    return result;
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
    return null;
  }
}
