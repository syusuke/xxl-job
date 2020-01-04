package com.xuxueli.executor.sample.frameless.config;

import com.xuxueli.executor.sample.frameless.jobhandler.*;
import com.xxl.job.core.executor.XxlJobExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author xuxueli 2018-10-31 19:05:43
 */
public class FrameLessXxlJobConfig {
    private static Logger logger = LoggerFactory.getLogger(FrameLessXxlJobConfig.class);


    private static FrameLessXxlJobConfig instance = new FrameLessXxlJobConfig();

    public static FrameLessXxlJobConfig getInstance() {
        return instance;
    }


    private XxlJobExecutor xxlJobExecutor = null;

    /**
     * init
     */
    public void initXxlJobExecutor() {

        // registry jobHandler
        XxlJobExecutor.registerJobHandler("demoJobHandler", new DemoJobHandler());
        XxlJobExecutor.registerJobHandler("shardingJobHandler", new ShardingJobHandler());
        XxlJobExecutor.registerJobHandler("httpJobHandler", new HttpJobHandler());
        XxlJobExecutor.registerJobHandler("commandJobHandler", new CommandJobHandler());
        XxlJobExecutor.registerJobHandler("databaseJobHandler", new DatabaseJobHandler());

        // load executor prop
        Properties xxlJobProp = loadProperties("xxl-job-executor.properties");


        // init executor
        xxlJobExecutor = new XxlJobExecutor();

        // set base log dir
        xxlJobExecutor.setAdminAddresses(xxlJobProp.getProperty("xxl.job.admin.addresses"));
        xxlJobExecutor.setAppName(xxlJobProp.getProperty("xxl.job.executor.appname"));
        xxlJobExecutor.setIp(xxlJobProp.getProperty("xxl.job.executor.ip"));
        xxlJobExecutor.setPort(Integer.valueOf(xxlJobProp.getProperty("xxl.job.executor.port")));
        xxlJobExecutor.setAccessToken(xxlJobProp.getProperty("xxl.job.accessToken"));
        xxlJobExecutor.setLogPath(xxlJobProp.getProperty("xxl.job.executor.logpath"));
        xxlJobExecutor.setLogRetentionDays(Integer.valueOf(xxlJobProp.getProperty("xxl.job.executor.logretentiondays")));

        // start executor
        try {
            xxlJobExecutor.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * destroy
     */
    public void destroyXxlJobExecutor() {
        if (xxlJobExecutor != null) {
            xxlJobExecutor.destroy();
        }
    }


    public static Properties loadProperties(String propertyFileName) {
        InputStreamReader in = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            InputStream resourceAsStream = loader.getResourceAsStream(propertyFileName);
            if (resourceAsStream != null) {
                in = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);
                Properties prop = new Properties();
                prop.load(in);
                return prop;
            }

        } catch (IOException e) {
            logger.error("load {} error!", propertyFileName);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close {} error!", propertyFileName);
                }
            }
        }
        return null;
    }

}
