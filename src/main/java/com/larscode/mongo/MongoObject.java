package com.larscode.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.DBRef;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MongoObject<K> {

    @org.mongojack.ObjectId
    @JsonProperty("_id")
    K getId();

    @org.mongojack.ObjectId
    @JsonProperty("_id")
    void setId(K id);

    @SuppressWarnings("unchecked")
    default <T extends MongoObject<K>, K> DBRef<T, K> toRef(T t) {
        return new DBRef<>(t.getId(), (Class<T>) t.getClass());
    }

    @SuppressWarnings("unchecked")
    default <T extends MongoObject<K>, K> List<DBRef<T, K>> toRefs(Collection<T> ts) {
        return ts.stream().map(this::toRef).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    default <T extends MongoObject<K>, K> List<DBRef<T, K>> toRefs(T... ts) {
        return Arrays.stream(ts).map(this::toRef).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    default <T extends MongoObject<K>, K> List<DBRef<T, K>> toRefs(Stream<T> stream) {
        return stream.map(this::toRef).collect(Collectors.toList());
    }

}
