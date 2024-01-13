package com.example.android_taskmaz.common.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.android_taskmaz.R
import com.example.android_taskmaz.common.AppDispatcher
import com.example.android_taskmaz.common.Dispatcher
import com.example.android_taskmaz.common.utils.Constants.BASE_URL
import com.example.android_taskmaz.data.remote.ApiKeyInterceptor
import com.example.android_taskmaz.data.remote.ApiService
import com.example.android_taskmaz.data.repository.RepositoryImpl
import com.example.android_taskmaz.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object
AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor("""3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?$@+16"""))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
        @Dispatcher(AppDispatcher.IO) ioDispatcher: CoroutineDispatcher
    ): Repository = RepositoryImpl(
        apiService = apiService,
        ioDispatcher = ioDispatcher
    )


    

}