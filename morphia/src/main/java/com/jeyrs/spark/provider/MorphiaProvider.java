package com.jeyrs.spark.provider;

import com.jeyrs.spark.model.Model;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class MorphiaProvider<T extends Model> implements DataProvider<T> {
    private Datastore datastore;
    private final Class<T> clazz;

    public MorphiaProvider(Datastore datastore, Class<T> clazz) {
        this.datastore = datastore;
        this.clazz =clazz;
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
        datastore.save( model);
        return findById(model.getId());
    }
}
