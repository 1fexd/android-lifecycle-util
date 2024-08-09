package fe.android.lifecycle.koin.extension

import fe.android.lifecycle.LifecycleServiceRegistry
import org.koin.core.KoinApplication
import org.koin.dsl.module

fun KoinApplication.applicationLifecycle(lifecycleObserver: LifecycleServiceRegistry) {
    koin.loadModules(listOf(module { single<LifecycleServiceRegistry> { lifecycleObserver } }))
}
