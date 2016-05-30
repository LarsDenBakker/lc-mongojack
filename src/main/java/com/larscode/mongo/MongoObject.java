package com.larscode.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface MongoObject<K> {

    @org.mongojack.ObjectId
    @JsonProperty("_id")
    K getId();

    @org.mongojack.ObjectId
    @JsonProperty("_id")
    void setId(K id);

}
