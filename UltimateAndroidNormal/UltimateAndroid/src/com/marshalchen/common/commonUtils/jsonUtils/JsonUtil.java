package com.marshalchen.common.commonUtils.jsonUtils;


import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import java.util.*;

/**
 * Use Gson to parse jsons
 */
public class JsonUtil {
    public static <T> T getListFromJson(String jsonString, TypeToken typeToken) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, typeToken.getType());
    }

    public static <T> T getTFromJson(String jsonString, Class<T> t) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, t);
    }


    public static ArrayList getArrayListMapFromJson(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        Gson gson = new Gson();
        JsonElement jsonElement = jsonParser.parse(jsonString);
        Logs.d(jsonElement.isJsonArray() + "   " + jsonElement.isJsonObject());
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        if (jsonElement.isJsonObject()) {
            arrayList.add(gson.fromJson(jsonElement, HashMap.class));
        } else if (jsonElement.isJsonArray()) {
            arrayList = getListFromJson(jsonString, new TypeToken<ArrayList<HashMap<String, Object>>>() {
            });
        }
        return arrayList;
    }

    public static ArrayList getArrayListFromJson(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        Gson gson = new Gson();
        JsonElement jsonElement = jsonParser.parse(jsonString);
        Logs.d(jsonElement.isJsonArray() + "   " + jsonElement.isJsonObject());
        ArrayList arrayList = new ArrayList();
        if (jsonElement.isJsonObject()) {
            arrayList.add(gson.fromJson(jsonElement, HashMap.class));
        } else if (jsonElement.isJsonArray()) {
            arrayList = getListFromJson(jsonString, new TypeToken<ArrayList>() {
            });
        }
        return arrayList;
    }

    public static Object getObjectListFromJson(String jsonString) {
        Gson gson = new Gson();
        Object object = gson.fromJson(jsonString, new TypeToken<List<Object>>() {
        }.getType());
        return object;
    }

    public static Object getObjectFromJson(String jsonString) {
        Gson gson = new Gson();
        Object object = gson.fromJson(jsonString, Object.class);
        return object;
    }



//    public static <T> T fromJSON(final TypeReference<T> type,
//                                 final String jsonPacket) {
//        T data = null;
//
//        try {
//            data = new ObjectMapper().readValue(jsonPacket, type);
//        } catch (Exception e) {
//            // Handle the problem
//        }
//        return data;
//    }

//    public <T>  T fromJson(com.google.gson.JsonElement json, java.lang.Class<T> classOfT) throws com.google.gson.JsonSyntaxException
//
//    public <T>  T fromJson(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT) throws com.google.gson.JsonSyntaxException


}
