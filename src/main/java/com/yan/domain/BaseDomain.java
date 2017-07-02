package com.yan.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by 闫继龙 on 2017/7/1.
 *
 */
public class BaseDomain implements Serializable {


    @Override
    public String toString() {


        //ToStringBuilder就可以避免暴内存这种问题的
        // ToStringBuilder类主要用于类的格式化输出
        return ToStringBuilder.reflectionToString(this);
    }
}
