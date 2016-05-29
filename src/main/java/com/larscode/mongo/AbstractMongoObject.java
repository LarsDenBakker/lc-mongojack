package com.larscode.mongo;

import org.bson.types.ObjectId;

public class AbstractMongoObject<K> implements MongoObject<K> {

    private K id;

    @Override
    public K getId() {
        return id;
    }

    @Override
    public void setId(K id) {
        this.id = id;
    }
}
