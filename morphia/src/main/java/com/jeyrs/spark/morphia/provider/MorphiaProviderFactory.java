package com.jeyrs.spark.morphia.provider;

import com.jeyrs.spark.model.Model;
import com.jeyrs.spark.provider.DataProvider;
import com.jeyrs.spark.provider.ProviderFactory;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MorphiaProviderFactory<T extends Model> implements ProviderFactory {
  private DataProvider<T> morphiaProvider(MongoClient client, Class<T> clazz, String databaseName) {
    final Morphia morphia = new Morphia();
    morphia.mapPackage("com.jeyrs.spark.morphia.model");
    final Datastore datastore = morphia.createDatastore(client, databaseName);
    datastore.ensureIndexes();

    return new MorphiaProvider<>(datastore, clazz);
  }

  @Override
  public DataProvider get(Class clazz) {
    return null; //TODO implement this
  }
}
