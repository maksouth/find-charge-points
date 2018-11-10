package name.mharbovskyi.findchargingstation.presentation.di.module

import dagger.Module
import dagger.Provides
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.view.MainActivity

@Module
class MainActivityModule {
    @Provides
    fun provideRouter(mainActivity: MainActivity): Router =
        mainActivity
}