package com.nagarro.executor;

import java.util.*;

import org.springframework.web.reactive.function.client.WebClient;

public class ApiCall<T> {
	private final WebClient webClient;
	private String path;
	private Map<String,Object> params;
	private Class<T> classType;
	
	private ApiCall(WebClient webClient,Class<T> classType) {
		this.webClient = webClient;
		params=new HashMap<>();
		this.classType=classType;
		this.path="/";
		
	}
	
	public static <T> ApiCall<T> create(WebClient webClient,Class<T> classType){
		return new ApiCall<>(webClient,classType);
	}
	
	public ApiCall<T> path(String uri){
		this.path=uri;
		return this;
	}
	
	public ApiCall<T> queryParam(String name,Object value){
		this.params.put(name,value);
		return this;
	}
	
	public Task<T> build(){
		return new Task<T>() {
			@Override
			public T execute() {
				return webClient.get().uri(uriBuilder -> {
					uriBuilder=uriBuilder.path(path);
					for (Map.Entry<String,Object> entry:params.entrySet()) {
						uriBuilder=uriBuilder.queryParam(entry.getKey(),entry.getValue());
					}
					return uriBuilder.build();
				}).retrieve().bodyToMono(classType).block();
			}
		};
	}

}
