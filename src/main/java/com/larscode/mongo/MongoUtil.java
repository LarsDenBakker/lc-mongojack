package com.larscode.mongo;

import org.mongojack.DBRef;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MongoUtil {

    @SuppressWarnings("unchecked")
    public static <T extends MongoObject<K>, K> DBRef<T, K> toRef(T t) {
        return new DBRef<>(t.getId(), (Class<T>) t.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <T extends MongoObject<K>, K> List<DBRef<T, K>> toRefs(Collection<T> ts) {
        return ts.stream().map(MongoUtil::toRef).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T extends MongoObject<K>, K> List<DBRef<T, K>> toRefs(T... ts) {
        return Arrays.stream(ts).map(MongoUtil::toRef).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T extends MongoObject<K>, K> List<DBRef<T, K>> toRefs(Stream<T> stream) {
        return stream.map(MongoUtil::toRef).collect(Collectors.toList());
    }
}
