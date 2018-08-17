package com.example.asus.liangpinstore.utils;

import java.io.Serializable;

/**
 * Created by z on 2018/02/01.
 * bena对象基类
 */
public class BeanObj implements Serializable {
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
