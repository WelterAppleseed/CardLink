package com.example.cardlinker.data.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.data.local.db.CardLinkDatabase
import com.example.cardlinker.data.repository.CardRepositoryImpl
import com.example.cardlinker.data.repository.UserLoggedInImpl
import com.example.cardlinker.domain.repository.CardRepository
import com.example.cardlinker.domain.repository.UserLoggedInManager
import com.example.cardlinker.util.objects.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun getAppPreferences(@ApplicationContext context: Context) = context.getSharedPreferences(
        Constants.USER_PREFS, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CardLinkDatabase {
        return Room.databaseBuilder(
            appContext,
            CardLinkDatabase::class.java,
            "CardDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCardRepository(cardDao: CardDao): CardRepository = CardRepositoryImpl(cardDao)

    @Provides
    fun provideChannelDao(cardLinkDatabase: CardLinkDatabase): CardDao {
        return cardLinkDatabase.cardDao()
    }
    @Provides
    @Singleton
    fun provideUserLoggedInManager(appPreferences: SharedPreferences): UserLoggedInManager = UserLoggedInImpl(appPreferences)
    }