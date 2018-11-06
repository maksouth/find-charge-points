package name.mharbovskyi.findchargingstation.presentation.di.module

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import name.mharbovskyi.findchargingstation.data.di.StubRepositoryModule
import name.mharbovskyi.findchargingstation.domain.di.UsecaseModule
import name.mharbovskyi.findchargingstation.presentation.EventDisplayer
import name.mharbovskyi.findchargingstation.presentation.Router
import name.mharbovskyi.findchargingstation.presentation.view.MainActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [
        FragmentsProvider::class,
        ViewModelModule::class,
        StubRepositoryModule::class,
        UsecaseModule::class,
        ActivityModule::class
    ])
    abstract fun bindMainActivity(): MainActivity
}

@Module
class ActivityModule {
    @Provides
    fun provideRouter(mainActivity: MainActivity): Router =
        mainActivity

    @Provides
    fun provideEventDisplayer(mainActivity: MainActivity): EventDisplayer =
            mainActivity
}