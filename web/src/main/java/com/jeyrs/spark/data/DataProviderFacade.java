package com.jeyrs.spark.data;


import com.jeyrs.spark.constants.ApplicationConstants;
import com.jeyrs.spark.model.Model;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;


public class DataProviderFacade<T extends Model> {

    private ModelProvider<T> morphiaProvider(MongoConnectionConfig config, Class<T> clazz) {
        final Morphia morphia = new Morphia();
        morphia.mapPackage(ApplicationConstants.MODELS_PACKAGE);
        final Datastore datastore = morphia.createDatastore(config.getMongoClient(), config.getDatabaseName());
        datastore.ensureIndexes();

//        return new MorphiaProvider<>(datastore, clazz);
      return null; //TODO: implement
    }

    public ModelProvider<T> get(MongoConnectionConfig config, Class<T> clazz) {
        return morphiaProvider(config, clazz);
    }
}
