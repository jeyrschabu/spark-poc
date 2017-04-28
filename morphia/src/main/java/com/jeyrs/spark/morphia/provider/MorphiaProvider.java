package com.jeyrs.spark.morphia.provider;

import com.jeyrs.spark.StorageConfig;
import com.jeyrs.spark.morphia.MongoConfig;
import com.jeyrs.spark.model.Model;
import com.jeyrs.spark.provider.DataProvider;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.Collections;
import java.util.List;

class MorphiaProvider<T extends Model> implements DataProvider<T> {
  private Datastore datastore;
  private final Class<T> clazz;

  MorphiaProvider(StorageConfig config, Class<T> clazz) {
    MongoClient mongoClient = new MongoClient();
    String database = ((MongoConfig) config).getDatabase();
    if (StringUtils.isNotEmpty(database) && StringUtils.isNotEmpty(config.getPassword())) {
      MongoCredential credential = MongoCredential.createCredential(
        config.getUsername(),
        database,
        config.getPassword().toCharArray()
      );

      mongoClient = new MongoClient(new ServerAddress(config.getHost()), Collections.singletonList(credential));
    }

    Morphia morphia = new Morphia();
    morphia.mapPackage(config.getModelPackageName());
    this.datastore = morphia.createDatastore(mongoClient, database);
    datastore.ensureIndexes();
    this.clazz = clazz;
  }

  public List<T> findAll() {
    final Query<T> query = datastore.createQuery(clazz);
    return query.asList();
  }

  public void insert(List<T> list) {
    datastore.save(list);
  }

  public T findById(String id) {
    return datastore.find(clazz).field("_id").equal(new ObjectId(id)).get();
  }

  public T findOne(String key, String value) {
    final Query<T> query = datastore.createQuery(clazz);
    query.field(key).equal(value);
    return query.get();
  }

  public List<T> findMany(String key, String value) {
    final Query<T> query = datastore.createQuery(clazz);
    query.field(key).equal(value);

    return query.asList();
  }

  public boolean delete(String id) {
    return datastore.delete(clazz, new ObjectId(id)).wasAcknowledged();
  }

  @Override
  public T update(T model) {
    datastore.save(model);
    return findById(model.getId());
  }
}
