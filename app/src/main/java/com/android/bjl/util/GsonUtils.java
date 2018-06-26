package com.android.bjl.util;

import com.android.bjl.bean.Result;
import com.android.bjl.bean.SubsidyBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by john on 2018/4/13.
 */

public class GsonUtils {
    /**
     * 1解析data是object的情况
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Result<T> fromJsonObject(String json, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(Result.class, new Class[]{clazz});
        return new Gson().fromJson(json, type);
    }

    /**
     *2解析data是array的情况
     * @param reader
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Result<List<T>> fromJsonArray(String reader, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(Result.class, new Type[]{listType});
        return new Gson().fromJson(reader, type);
    }

    public static <T> T parseJSON(String json, Class<T> clazz) {

        Gson gson = new Gson();

        T info = gson.fromJson(json, clazz);

        return info;

    }


    public static <T> List<T> jsonToList(String json, Class<? extends T[]> clazz)

    {

        Gson gson = new Gson();

        T[] array = gson.fromJson(json, clazz);

        return Arrays.asList(array);

    }



    /**

     * @param json

     * @param clazz

     * @return

     */

    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)

    {

        Type type = new TypeToken<ArrayList<JsonObject>>()

        {}.getType();

        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();

        for (JsonObject jsonObject : jsonObjects)

        {

            arrayList.add(new Gson().fromJson(jsonObject, clazz));

        }

        return arrayList;

    }



}
