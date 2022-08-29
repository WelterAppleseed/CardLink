package com.example.cardlinker.data.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.data.local.dao.RecommendationDao
import com.example.cardlinker.data.local.db.CardLinkDatabase
import com.example.cardlinker.data.repository.*
import com.example.cardlinker.domain.repository.*
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
    fun provideDeleteCardRepository(cardDao: CardDao): DeleteCardRepository = DeleteCardRepositoryImpl(cardDao)
    @Provides
    @Singleton
    fun provideGetCardRepository(cardDao: CardDao): GetCardRepository = GetCardRepositoryImpl(cardDao)

    @Provides
    @Singleton
    fun provideGetAccountRepository(accountDao: AccountDao): GetAccountRepository = GetAccountRepositoryImpl(accountDao)

    @Provides
    @Singleton
    fun provideUpdateAccountDataRepository(accountDao: AccountDao): UpdateAccountDataRepository = UpdateAccountDataRepositoryImpl(accountDao)

    @Provides
    @Singleton
    fun provideGetAllAccountsRepository(accountDao: AccountDao): GetAllAccountsRepository = GetAllAccountsRepositoryImpl(accountDao)

    @Provides
    @Singleton
    fun provideAccountLoginAttemptRepository(accountDao: AccountDao): AccountLoginAttemptRepository = AccountLoginAttemptRepositoryImpl(accountDao)


    @Provides
    @Singleton
    fun provideInsertAccountRepository(accountDao: AccountDao): InsertAccountRepository = InsertAccountRepositoryImpl(accountDao)

    @Provides
    @Singleton
    fun provideGetNotLinkedCardsRepository(cardDao: CardDao): GetNotLinkedCardsRepository = GetNotLinkedCardsRepositoryImpl(cardDao)

    @Provides
    @Singleton
    fun provideGetLinkedCardRepository(cardDao: CardDao): GetLinkedCardsRepository = GetLinkedCardsRepositoryImpl(cardDao)

    @Provides
    @Singleton
    fun provideUpdateCardAccountHashcodeRepository(cardDao: CardDao): UpdateCardAccountHashcodeRepository = UpdateCardAccountHashcodeRepositoryImpl(cardDao)


    @Provides
    @Singleton
    fun provideGetCurrentEncodedPasswordRepository(sharedPreferences: SharedPreferences): GetCurrentEncodedPasswordRepository = GetCurrentEncodedPasswordRepositoryImpl(sharedPreferences)

    @Provides
    @Singleton
    fun provideInsertCurrentEncodedPasswordRepository(sharedPreferences: SharedPreferences): InsertCurrentEncodedPasswordRepository = InsertCurrentEncodedPasswordRepositoryImpl(sharedPreferences)

    @Provides
    fun provideChannelDao(cardLinkDatabase: CardLinkDatabase): CardDao {
        return cardLinkDatabase.cardDao()
    }
    @Provides
    fun provideRecommendationDao(cardLinkDatabase: CardLinkDatabase): RecommendationDao {
        return cardLinkDatabase.recommendationDao()
    }
    @Provides
    fun provideAccountDao(cardLinkDatabase: CardLinkDatabase): AccountDao {
        return cardLinkDatabase.accountDao()
    }
    @Provides
    @Singleton
    fun provideGetRecommendationRepository(getRecommendationDao: RecommendationDao): GetRecommendationRepository = GetRecommendationRepositoryImpl(getRecommendationDao)

    @Provides
    @Singleton
    fun provideAddRecommendationRepository(addRecommendationDao: RecommendationDao): AddRecommendationRepository = AddRecommendationRepositoryImpl(addRecommendationDao)

    @Provides
    @Singleton
    fun provideFirstTimeOnFragmentRepository(sharedPreferences: SharedPreferences): FirstTimeOnFragmentManager = FirstTimeOnFragmenManagerImpl(sharedPreferences)


    @Provides
    @Singleton
    fun provideFirstTimeUsedManager(appPreferences: SharedPreferences): FirstTimeUsedManager = FirstTimeUsedImpl(appPreferences)

    @Provides
    @Singleton
    fun provideCheckIsLoggedInManager(appPreferences: SharedPreferences): CheckIsLoggedInManager = CheckIsLoggedInManagerImpl(appPreferences)
    }
