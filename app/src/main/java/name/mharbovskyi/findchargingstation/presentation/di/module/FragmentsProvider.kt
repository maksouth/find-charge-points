package name.mharbovskyi.findchargingstation.presentation.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import name.mharbovskyi.findchargingstation.presentation.view.ChargePointsFragment
import name.mharbovskyi.findchargingstation.presentation.view.GreetingFragment
import name.mharbovskyi.findchargingstation.presentation.view.LoginFragment

@Module
abstract class FragmentsProvider {

    @ContributesAndroidInjector()
    abstract fun provideLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun provideGreetingFragment(): GreetingFragment

    @ContributesAndroidInjector()
    abstract fun provideChargePointsFragment(): ChargePointsFragment
}