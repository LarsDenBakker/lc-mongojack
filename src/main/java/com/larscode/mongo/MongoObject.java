package com.larscode.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongojack.DBRef;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MongoObject {

    @org.mongojack.ObjectId
    @JsonProperty("_id")
    ObjectId getId();

    @org.mongojack.ObjectId
    @JsonProperty("_id")
    void setId(ObjectId id);

    @SuppressWarnings("unchecked")
    default <T extends MongoObject> DBRef<T, ObjectId> toRef(T t) {
        return new DBRef<>(t.getId(), (Class<T>) t.getClass());
    }

    @SuppressWarnings("unchecked")
    default <T extends MongoObject> List<DBRef<T, ObjectId>> toRefs(Collection<T> ts) {
        return ts.stream().map(this::toRef).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    default <T extends MongoObject> List<DBRef<T, ObjectId>> toRefs(T... ts) {
        return Arrays.stream(ts).map(this::toRef).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    default <T extends MongoObject> List<DBRef<T, ObjectId>> toRefs(Stream<T> stream) {
        return stream.map(this::toRef).collect(Collectors.toList());
    }

}
