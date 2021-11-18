package kstrong3.familymap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSerializer {
    public static String toJson(Object o)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(o);
    }

    public static <T> T fromJson(String string, Class<T> myClass)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(string, myClass);
    }
}
