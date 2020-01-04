package com.xuxueli.executor.sample.frameless.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author kerryzhang on 2020/1/4
 */

public class DatabaseJobHandler extends IJobHandler {
    @Override
    public ReturnT<String> execute(String param) throws Exception {

        Connection connection = DriverManager.getConnection("url", "", "");


        return null;
    }
}
