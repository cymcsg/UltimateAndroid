package com.marshalchen.common.commonUtils.jsonUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonMetaReader
 */
public class JsonMetaReader<T> {
    private Class<T> persistentClass;

    public Class<T> getPersistentClass() {
        if (persistentClass == null) {
            this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return persistentClass;
    }

    public List<T> read(Reader reader) throws IOException {
        com.google.gson.stream.JsonReader jsonReader = new com.google.gson.stream.JsonReader(reader);
        List<T> objs = new ArrayList<T>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            T obj = (new Gson()).fromJson(jsonReader, getPersistentClass());
            objs.add(obj);
        }
        jsonReader.endArray();
        jsonReader.close();
        return objs;
    }

    public Reader getStringReader(String jsonString) {
        return new StringReader(jsonString);
    }
}
