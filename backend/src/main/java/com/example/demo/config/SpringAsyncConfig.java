package com.example.demo.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@EnableAsync
@Configuration
public class SpringAsyncConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected Logger errorLogger = LoggerFactory.getLogger("error");

    @Bean(name = "cook")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("AsyncThread-");
        taskExecutor.initialize();
        return new HandlingExecutor(taskExecutor);
    }

    public class HandlingExecutor implements AsyncTaskExecutor{

        private AsyncTaskExecutor executor;

        public HandlingExecutor(AsyncTaskExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            executor.execute(createWrappedRunnable(task), startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return null;
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return null;
        }

        @Override
        public void execute(Runnable task) {
            executor.execute(createWrappedRunnable(task));
        }
        private <T> Callable<T> createCallable(final Callable<T> task){
            return new Callable<T>() {
                @Override
                public T call() throws Exception {
                    try {
                        return task.call();
                    }catch (Exception e ){
                        handle(e);
                        throw e;
                    }
                }
            };
        }// createCallable

        private Runnable createWrappedRunnable(final Runnable task){
            return new Runnable() {
                @Override
                public void run() {
                    try{
                        task.run();
                    }catch (Exception e){
                        handle(e);
                    }

                }
            };
        }// createWrappedRunnable

        private void handle(Exception e){
            errorLogger.info("Failed to execute task. : {}", e.getMessage());
            errorLogger.error("Failed to execute task. ",e);
        }

        @PreDestroy
        public void destroy(){
            if(executor instanceof ThreadPoolTaskExecutor){
                ((ThreadPoolTaskExecutor)executor).shutdown();
            }
        }

    }
}
