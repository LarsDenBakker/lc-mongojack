package com.larscode.mongo;

import org.bson.types.ObjectId;

public class AbstractMongoObject implements MongoObject {

    private ObjectId id;

    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }
}
