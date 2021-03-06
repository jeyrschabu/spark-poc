package com.jeyrs.spark.morphia.provider;

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

public class MorphiaProvider<T extends Model> implements DataProvider<T> {
  private Datastore datastore;
  private final Class<T> clazz;

  public MorphiaProvider(MongoConfig config, Class<T> clazz) {
    this.datastore = createDatastore(config, getMongoClient(config), new Morphia());
    this.clazz = clazz;
  }

  public MorphiaProvider(Datastore datastore, Class<T> clazz) {
    this.clazz = clazz;
    this.datastore = datastore;
  }

  private MongoClient getMongoClient(MongoConfig config) {
    MongoClient mongoClient = new MongoClient();
    if (StringUtils.isNotEmpty(config.getDatabase()) && StringUtils.isNotEmpty(config.getPassword())) {
      MongoCredential credential = MongoCredential.createCredential(
        config.getUsername(),
        config.getDatabase(),
        config.getPassword().toCharArray()
      );

      mongoClient = new MongoClient(new ServerAddress(config.getHost()), Collections.singletonList(credential));
    }

    return mongoClient;
  }

  private Datastore createDatastore(MongoConfig config,
                                    MongoClient mongoClient,
                                    Morphia morphia) {
    morphia.mapPackage(config.getModelPackageName());
    Datastore datastore = morphia.createDatastore(mongoClient, config.getDatabase());
    datastore.ensureIndexes();
    return datastore;
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
