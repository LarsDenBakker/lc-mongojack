package com.larscode.mongo;

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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +"id=" + id +'}';
    }
}
