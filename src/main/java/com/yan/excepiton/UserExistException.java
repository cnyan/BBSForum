package com.yan.excepiton;

/**
 * Created by jsj-9-4 on 03/07/2017.
 */
public class UserExistException extends Exception {

    public UserExistException(String errorMsg){
        super(errorMsg);
    }
}
