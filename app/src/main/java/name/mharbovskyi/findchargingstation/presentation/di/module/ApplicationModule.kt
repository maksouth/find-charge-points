package name.mharbovskyi.findchargingstation.presentation.di.module

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.PointsApplication
import java.io.InputStreamReader
import java.io.Reader
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: Application): Context =
            application

    @Provides
    fun provideApplication(application: PointsApplication): Application =
        application

    @Provides
    @Singleton
    fun providePreferences(context: Context) =
        context.getSharedPreferences("app_shared_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAssesManager(context: Context) =
        context.assets

    @Provides
    @Named(ASSET_CHARGE_POINTS)
    fun provideChargePointsReader(assetManager: AssetManager): Reader =
            InputStreamReader(assetManager.open("sample-json-chargepoints.json"))
}

const val ASSET_CHARGE_POINTS = "asset_charge_points"