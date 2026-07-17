package com.wamt.di;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import jakarta.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class ExecutorModule {

    @Provides
    @Singleton
    public ExecutorService provideExecutorService() {
        return Executors.newFixedThreadPool(4);
    }
}