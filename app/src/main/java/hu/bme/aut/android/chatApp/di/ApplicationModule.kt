package hu.bme.aut.android.chatApp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(@get:Provides @Singleton val context: Context)


