package name.mharbovskyi.findchargingstation.presentation.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import name.mharbovskyi.findchargingstation.presentation.view.ChargePointsFragment
import name.mharbovskyi.findchargingstation.presentation.view.GreetingFragment

@Module
abstract class FragmentsBuilder {

    @ContributesAndroidInjector()
    abstract fun provideGreetingFragment(): GreetingFragment

    @ContributesAndroidInjector()
    abstract fun provideChargePointsFragment(): ChargePointsFragment
}