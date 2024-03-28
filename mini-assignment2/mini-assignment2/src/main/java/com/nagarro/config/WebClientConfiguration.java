package com.nagarro.config;




import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;


@Configuration
public class WebClientConfiguration {

	@Bean(name="userWebClient")
	public WebClient userWebClient() {
		HttpClient httpClient= HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000) // Connection Timeout
                .doOnConnected(connection ->connection
                		.addHandlerLast(new ReadTimeoutHandler(2000)) // Read Timeout
                        .addHandlerLast(new WriteTimeoutHandler(2000))); // Write Timeout
		
		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
		
		return WebClient.builder().clientConnector(connector).baseUrl("https://randomuser.me/api/")
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
	
	@Bean(name="nationalityWebClient")
	public WebClient nationalityWebClient() {
		reactor.netty.http.client.HttpClient httpClient= HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000) // Connection Timeout
                .doOnConnected(connection ->connection
                		.addHandlerLast(new ReadTimeoutHandler(1000)) // Read Timeout
                        .addHandlerLast(new WriteTimeoutHandler(1000))); // Write Timeout
		
		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
		
		return WebClient.builder().clientConnector(connector).baseUrl("https://api.nationalize.io/")
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
	
	@Bean(name="genderWebClient")
	public WebClient genderWebClient() {
		reactor.netty.http.client.HttpClient httpClient= HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000) // Connection Timeout
                .doOnConnected(connection ->connection
                		.addHandlerLast(new ReadTimeoutHandler(1000)) // Read Timeout
                        .addHandlerLast(new WriteTimeoutHandler(1000))); // Write Timeout
		
		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
		
		return WebClient.builder().clientConnector(connector).baseUrl("https://api.genderize.io/")
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
	
}
