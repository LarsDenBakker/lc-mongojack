package com.larscode.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import org.mongojack.JacksonDBCollection;

import java.lang.reflect.InvocationTargetException;

public class MongoRepository<K, V extends MongoObject<K>> {

    private final DB mongoDB;

    private final Class<K> keyType;
    private final Class<V> valueType;
    private final String collectionName;
    private final JacksonDBCollection<V, K> collection;

    public MongoRepository(DB mongoDB, Class<K> keyType, Class<V> valueType, String collectionName) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.collectionName = collectionName;
        this.mongoDB = mongoDB;
        this.collection =  configureCollection();
    }

    public MongoRepository(DB mongoDB, Class<K> keyType, Class<V> valueType) {
        this(mongoDB, keyType, valueType, valueType.getSimpleName().toLowerCase());
    }

    public Class<V> getValueType() {
        return valueType;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public JacksonDBCollection<V, K> getCollection() {
        return collection;
    }

    public V createNew() throws IllegalArgumentException {
        try {
            V v = instantiate();
            return collection.insert(v).getSavedObject();
        } catch (MongoException e) {
            throw creationException(e);
        }
    }

    private V instantiate() throws IllegalArgumentException {
        try {
            return valueType.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw creationException(e);
        }
    }

    private IllegalArgumentException creationException(Exception cause) throws IllegalArgumentException {
        return new IllegalArgumentException("Cannot create instance of MongoObject valueType: " + valueType, cause);
    }

    private JacksonDBCollection<V, K> configureCollection() {
        return getJacksonDBCollection(getDBCollection());
    }

    private DBCollection getDBCollection() {
        if (mongoDB.collectionExists(collectionName)) {
            return mongoDB.getCollection(collectionName);
        } else {
            return mongoDB.createCollection(collectionName, null);
        }
    }

    private JacksonDBCollection<V, K> getJacksonDBCollection(final DBCollection dbCollection) {
        return JacksonDBCollection.wrap(dbCollection, valueType, keyType);
    }


}
