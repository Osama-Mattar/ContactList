package com.mattar.contactlist.di

import android.content.Context
import androidx.room.Room
import com.mattar.contactlist.model.source.ContactsDataSource
import com.mattar.contactlist.model.source.ContactsRepository
import com.mattar.contactlist.model.source.DefaultContactsRepository
import com.mattar.contactlist.model.source.local.ContactsDatabase
import com.mattar.contactlist.model.source.local.ContactsLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's ApplicationComponent.
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Qualifier
    @Retention(RUNTIME)
    annotation class LocalContactsDataSource

    @Singleton
    @LocalContactsDataSource
    @Provides
    fun provideContactsLocalDataSource(
            database: ContactsDatabase,
            ioDispatcher: CoroutineDispatcher
    ): ContactsDataSource {
        return ContactsLocalDataSource(
                database.contactDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ContactsDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                ContactsDatabase::class.java,
                "Contacts.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

/**
 * The binding for ContactsRepository is on its own module so that we can replace it easily in tests.
 */
@Module
@InstallIn(ApplicationComponent::class)
object ContactsRepositoryModule {
    @Singleton
    @Provides
    fun provideContactsRepository(
            @AppModule.LocalContactsDataSource localContactsDataSource: ContactsDataSource,
            ioDispatcher: CoroutineDispatcher
    ): ContactsRepository {
        return DefaultContactsRepository(
                localContactsDataSource, ioDispatcher
        )
    }
}
