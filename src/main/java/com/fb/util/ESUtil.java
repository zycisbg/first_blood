package com.fb.util;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class ESUtil {

    private static TransportClient client = null;
    protected static final Logger LOG = Logger.getLogger(ESUtil.class);

    public static TransportClient getClient() {
        try {
            if (client == null) {
                synchronized (ESUtil.class) {
                    if (client == null) {
                        client = new PreBuiltTransportClient(Settings.EMPTY)
                                .addTransportAddress(new TransportAddress(InetAddress.getByName
                                        (PropKit.use("config.properties").get("es.address.ip")),
                                        Integer.parseInt(PropKit.use("config.properties").get("es.address.port"))));

                    }
                }
            }

        } catch (Exception e) {
            LOG.error("es链接异常："+e.getMessage());
            e.printStackTrace();
        } finally {
            LOG.warn("es链接client："+client);
            return client;
        }
    }

}
