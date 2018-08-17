package com.framework.lplibs.json;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author:
 * @time:
 */

public class ToObandList {

    static Gson gson = new Gson();
    public static <T> T getGsonObject(String result, Class<T> tClass){
        T t = gson.fromJson(result,tClass);
        return t;
    }
    public static  <T> List<T> getGsonList(String data, Class<T> klass) {
        return gson.fromJson(data, new ListOfSomething<T>(klass));
    }

    static class ListOfSomething<X> implements ParameterizedType {
        private Class<?> wrapped;

        public ListOfSomething(Class<X> wrapped) {
            this.wrapped = wrapped;
        }
        public Type[] getActualTypeArguments() {
            return new Type[] {wrapped};
        }
        public Type getRawType() {
            return List.class;
        }
        public Type getOwnerType() {
            return null;
        }
    }

}
