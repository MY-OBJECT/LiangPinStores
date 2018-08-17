package com.example.asus.liangpinstore.controller;

/**
 * Created by asus on 2018/2/1.
 */

public class UserController {

    private static UserController controller;

    public UserController() {
    }

    /**
     * 单一实例
     */
    public static UserController getInstance() {
        if (controller == null) {
            synchronized (UserController.class) {
                if (controller == null) {
                    controller = new UserController();
                }
            }
        }
        return controller;
    }

}
