package fe.android.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleCoroutineScope

interface LifecycleServiceRegistry : DefaultLifecycleObserver {
    val lifecycleCoroutineScope: LifecycleCoroutineScope

    fun onAppInitialized()

    fun register(service: LifecycleAwareService)

    fun unregister(service: LifecycleAwareService)
}
