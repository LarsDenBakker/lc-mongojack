package com.larscode.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;

import java.lang.reflect.InvocationTargetException;

public class MongoRepository<T extends MongoObject> {

    private final DB mongoDB;

    private final Class<T> type;
    private final String collectionName;
    private final JacksonDBCollection<T, ObjectId> collection;

    public MongoRepository(Class<T> type, String collectionName, DB mongoDB) {
        this.type = type;
        this.collectionName = collectionName;
        this.mongoDB = mongoDB;
        this.collection =  configureCollection();
    }

    public Class<T> getType() {
        return type;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public JacksonDBCollection<T, ObjectId> getCollection() {
        return collection;
    }

    public T createNew() throws IllegalArgumentException {
        try {
            T t = instantiate();
            return collection.insert(t).getSavedObject();
        } catch (MongoException e) {
            throw creationException(e);
        }
    }

    private T instantiate() throws IllegalArgumentException {
        try {
            return type.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw creationException(e);
        }
    }

    private IllegalArgumentException creationException(Exception cause) throws IllegalArgumentException {
        return new IllegalArgumentException("Cannot create instance of MongoObject type: " + type, cause);
    }

    private JacksonDBCollection<T, ObjectId> configureCollection() {
        return getJacksonDBCollection(getDBCollection());
    }

    private DBCollection getDBCollection() {
        if (mongoDB.collectionExists(collectionName)) {
            return mongoDB.getCollection(collectionName);
        } else {
            return mongoDB.createCollection(collectionName, null);
        }
    }

    private JacksonDBCollection<T, ObjectId> getJacksonDBCollection(final DBCollection dbCollection) {
        return JacksonDBCollection.wrap(dbCollection, type, ObjectId.class);
    }


}
