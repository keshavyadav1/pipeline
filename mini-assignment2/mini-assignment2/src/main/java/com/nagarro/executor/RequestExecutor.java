package com.nagarro.executor;
import java.util.List;
import java.util.concurrent.*;

public class RequestExecutor {
	 private final ExecutorService service;
	 
	 public RequestExecutor() {    
	        service = Executors.newFixedThreadPool(2);
	    }
     public <T> T execute(Task<T> task) {
    	 return task.execute();
     }
     
     public <T,E> List<Object> executeConcurrently(Task<T> task1,Task<E> task2){
    	 CompletableFuture<T> completableFuture1=CompletableFuture.supplyAsync(task1::execute,service);
    	 CompletableFuture<E> completableFuture2=CompletableFuture.supplyAsync(task2::execute,service);
		 return completableFuture1.thenCombine(completableFuture2,(response1,response2) -> List.of(response1,response2)).join();
    	 
     }
     public void shutDownExecutor() {
    	 service.shutdown();
     }
}
