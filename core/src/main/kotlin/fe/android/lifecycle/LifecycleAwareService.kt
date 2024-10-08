package fe.android.lifecycle

import androidx.lifecycle.LifecycleOwner

interface LifecycleAwareService {
    suspend fun onAppInitialized(owner: LifecycleOwner) {}

    suspend fun onCreate() {}

    suspend fun onResume() {}

    suspend fun onPause() {}

    suspend fun onStop() {}
}
