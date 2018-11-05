package name.mharbovskyi.findchargingstation.presentation.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application) =
            application

    @Provides
    @Singleton
    fun providePreferences(context: Context) =
        context.getSharedPreferences("app_shared_prefs", Context.MODE_PRIVATE)
}