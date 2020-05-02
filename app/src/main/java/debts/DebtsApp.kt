package debts

import android.app.Application
import debts.common.TimberCrashlyticsTree
import debts.di.*
import net.thebix.debts.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class DebtsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(TimberCrashlyticsTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@DebtsApp)
            modules(
                listOf(
                    appModule,
                    repositoriesModule,
                    useCasesModule,
                    interactorModule,
                    viewModelModule
                )
            )
        }
    }
}
