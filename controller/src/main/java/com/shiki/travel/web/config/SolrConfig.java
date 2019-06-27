package com.shiki.travel.web.config;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;


@Configuration
public class SolrConfig {
//    @Value("${spring.data.solr.host}")
//    private String solrHost;
//
//    @Value("${spring.data.solr.core}")
//    private String solrCore;

    /**
     * 配置SolrTemplate
     */
    @Bean
    public SolrTemplate solrTemplate(SolrClient client) {
        return new SolrTemplate(client);
    }
}