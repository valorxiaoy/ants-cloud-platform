package com.ants.mini.programs.config.sentinel;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;

import java.util.Properties;
import java.util.concurrent.*;

/**
 * Nacos Management Sentinel Config Rules
 *
 * @author Yueyang
 * @create 2020-11-18 22:13
 **/
public class NacosDataSource<T> extends AbstractDataSource<String, T> {

    private static final int DEFAULT_TIMEOUT = 3000;

    /**
     * Single-thread pool. Once the thread pool is blocked, we throw up the old task.
     */
    private final ExecutorService pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1), new NamedThreadFactory("sentinel-nacos-ds-update"),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    private final Listener configListener;
    private final String groupId;
    private final String dataId;
    private final Properties properties;

    /**
     * Note: The Nacos config might be null if its initialization failed.
     */
    private ConfigService configService = null;

    /**
     * Constructs an read-only DataSource with Nacos backend.
     *
     * @param serverAddr server address of Nacos, cannot be empty
     * @param groupId    group ID, cannot be empty
     * @param dataId     data ID, cannot be empty
     * @param parser     customized data parser, cannot be empty
     */
    public NacosDataSource(final String serverAddr, final String groupId, final String dataId,
                           Converter<String, T> parser) {
        this(NacosDataSource.buildProperties(serverAddr), groupId, dataId, parser);
    }

    /**
     * @param properties properties for construct {@link ConfigService} using {@link NacosFactory#createConfigService(Properties)}
     * @param groupId    group ID, cannot be empty
     * @param dataId     data ID, cannot be empty
     * @param parser     customized data parser, cannot be empty
     */
    public NacosDataSource(final Properties properties, final String groupId, final String dataId,
                           Converter<String, T> parser) {
        super(parser);
        if (StringUtil.isBlank(groupId) || StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException(String.format("Bad argument: groupId=[%s], dataId=[%s]",
                    groupId, dataId));
        }
        AssertUtil.notNull(properties, "Nacos properties must not be null, you could put some keys from PropertyKeyConst");
        this.groupId = groupId;
        this.dataId = dataId;
        this.properties = properties;
        this.configListener = new Listener() {
            @Override
            public Executor getExecutor() {
                return pool;
            }

            @Override
            public void receiveConfigInfo(final String configInfo) {
                RecordLog.info("[NacosDataSource] New property value received for (properties: {}) (dataId: {}, groupId: {}): {}",
                        properties, dataId, groupId, configInfo);
                T newValue = NacosDataSource.this.parser.convert(configInfo);
                // Update the new value to the property.
                getProperty().updateValue(newValue);
            }
        };
        initNacosListener();
        loadInitialConfig();
    }

    private static Properties buildProperties(String serverAddr) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        return properties;
    }

    private void loadInitialConfig() {
        try {
            T newValue = loadConfig();
            if (newValue == null) {
                RecordLog.warn("[NacosDataSource] WARN: initial config is null, you may have to check your data source");
            }
            getProperty().updateValue(newValue);
        } catch (Exception ex) {
            RecordLog.warn("[NacosDataSource] Error when loading initial config", ex);
        }
    }

    private void initNacosListener() {
        try {
            this.configService = NacosFactory.createConfigService(this.properties);
            // Add config listener.
            configService.addListener(dataId, groupId, configListener);
        } catch (Exception e) {
            RecordLog.warn("[NacosDataSource] Error occurred when initializing Nacos data source", e);
            e.printStackTrace();
        }
    }

    @Override
    public String readSource() throws Exception {
        if (configService == null) {
            throw new IllegalStateException("Nacos config service has not been initialized or error occurred");
        }
        return configService.getConfig(dataId, groupId, DEFAULT_TIMEOUT);
    }

    @Override
    public void close() {
        if (configService != null) {
            configService.removeListener(dataId, groupId, configListener);
        }
        pool.shutdownNow();
    }
}