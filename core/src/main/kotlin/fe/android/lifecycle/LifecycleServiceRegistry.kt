package fe.android.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleCoroutineScope

interface LifecycleServiceRegistry : DefaultLifecycleObserver {
    val lifecycleCoroutineScope: LifecycleCoroutineScope

    fun onAppInitialized()

    fun register(service: LifecycleService)

    fun unregister(service: LifecycleService)
}
