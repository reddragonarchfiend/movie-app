package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.db.MoviesDao
import com.example.movieapp.data.db.MoviesDatabase
import com.example.movieapp.data.repository.popular.PopularListRepository
import com.example.movieapp.networking.NetworkService
import com.example.movieapp.util.Const
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(Gson())

    @Singleton
    @Provides
    fun provideNetworkServiceV11(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okHttpClient, converterFactory, NetworkService::class.java)

    //build retrofit
    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        clazz: Class<T>
    ): T {
        return createRetrofit(okHttpClient, converterFactory).create(clazz)
    }

    //build database
    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "movie_database"
        ).build()

    @Provides
    fun provideMoviesDao(appDatabase: MoviesDatabase): MoviesDao {
        return appDatabase.getMoviesDao()
    }
}