package com.janta.billing.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SolrConfiguration {

	@Value("${solr.host}")
	private String solrHoString;
	
	@Bean
	public SolrClient soleClient() {
		return new HttpSolrClient.Builder(solrHoString).build();
	}
}
