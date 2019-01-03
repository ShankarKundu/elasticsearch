package com.example.elasticsearch.config;

import brave.sampler.Sampler;
import com.example.elasticsearch.domain.Book;
import lombok.extern.slf4j.Slf4j;

import org.elasticsearch.client.Client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@Slf4j
@EnableElasticsearchRepositories(basePackages="com.exampl.elasticsearch.config")
@EnableCaching
public class ElasticSearchConfiguration  {

//    @Autowired
//    private Environment environment;
    private TransportClient client = null;

    @Value("${es.cluster-name}")
    private String clusterName;

    @Value("${es.host}")
    private String host;

    @Value("${es.port}")
    private String port;

    @Value("${es.node-name}")
    private String nodeName;

    @Value("${es.transport-sniff}")
    private String transportSniff;

    @Value("${es.http-enabled}")
    private String httpEnabled;


    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(){
        ElasticsearchTemplate esTemplate = new ElasticsearchTemplate(client());
        esTemplate.createIndex(Book.class);
        esTemplate.putMapping(Book.class);
        esTemplate.refresh(Book.class);
        return  esTemplate;
    }

    private Client client() {

        try {
            System.setProperty("es.set.netty.runtime.available.processors", "false");
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("node.name", nodeName)
                    .put("client.transport.sniff", transportSniff)
                    .put("http.enabled", httpEnabled)
                    .build();

            client = new PreBuiltTransportClient(settings);
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),
                    Integer.parseInt(port)));
        } catch (UnknownHostException e) {
            log.error("Exception occurred while getting Client : {}", e);
        }
        return client;
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
