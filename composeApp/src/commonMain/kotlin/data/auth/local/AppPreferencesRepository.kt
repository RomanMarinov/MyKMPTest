package data.auth.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent


data class AppPreferences(
//    val lastOnboardingScreen: Int = 0,
//    val isOnboardingComplete: Boolean = false,
//    val userId: Int = 0,
//    val isSignedOut: Boolean,
//    val notificationsDenied: Boolean


    val phone: Long = 0L,
    val accessToken: String = "",
    val refreshToken: String = "",
    val fingerprint: String = ""

)

class AppPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : KoinComponent {

    private val logger = Logger.withTag("UserPreferencesManager")

    private object PreferencesKeys {
        val PHONE_KEY = longPreferencesKey("PHONE_KEY")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN_KEY")
        val FINGER_PRINT_KEY = stringPreferencesKey("FINGER_PRINT_KEY")


//        val LAST_ONBOARDING_SCREEN = intPreferencesKey("last_onboarding")
//        val USER_ID = intPreferencesKey("userId")
//        val IS_SIGNED_OUT = booleanPreferencesKey("isSignedOut")
//        val NOTIFICATIONS_DENIED = booleanPreferencesKey("notificationsDenied")
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    /**
     * Use this if you don't want to observe a flow.
     */
    suspend fun fetchInitialPreferences() =
        mapAppPreferences(dataStore.data.first().toPreferences())

    /**
     * Получите поток пользовательских настроек. Когда он собран, ключи сопоставляются с
     * Класс данных [UserPreferences].
     */
    val userPreferencesFlow: Flow<AppPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                logger.d { "Error reading preferences: $exception" }
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapAppPreferences(preferences)
        }

    /**
     * Sets the last onboarding screen that was viewed (on button click).
     */
//    suspend fun setLastOnboardingScreen(viewedScreen: Int) {
//        // updateData handles data transactionally, ensuring that if the key is updated at the same
//        // time from another thread, we won't have conflicts
//        dataStore.edit { preferences ->
//            preferences[PreferencesKeys.LAST_ONBOARDING_SCREEN] = viewedScreen
//        }
//    }

    /**
     * Sets the userId that we get from the Ktor API (on button click).
     */
//    suspend fun setUserId(userId: Int) {
//        dataStore.edit { preferences ->
//            preferences[PreferencesKeys.USER_ID] = userId
//        }
//    }
//
//    suspend fun isSignedOut(isSignedOut: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[PreferencesKeys.IS_SIGNED_OUT] = isSignedOut
//        }
//    }
//
//    suspend fun showNotifications(denied: Boolean) {
//        dataStore.edit {
//            it[PreferencesKeys.NOTIFICATIONS_DENIED] = denied
//        }
//    }


    suspend fun setAuthToPrefsAndAuthState(
        phone: Long,
        accessToken: String,
        refreshToken: String,
        fingerprint: String
    ) {
        dataStore.edit {
            it[PreferencesKeys.PHONE_KEY] = phone
        }
        dataStore.edit {
            it[PreferencesKeys.ACCESS_TOKEN_KEY] = accessToken
        }
        dataStore.edit {
            it[PreferencesKeys.REFRESH_TOKEN_KEY] = refreshToken
        }
        dataStore.edit {
            it[PreferencesKeys.FINGER_PRINT_KEY] = fingerprint
        }
    }

    /**
     * Get the preferences key, then map it to the data class.
     */
    private fun mapAppPreferences(preferences: Preferences): AppPreferences {
//        val lastScreen = preferences[PreferencesKeys.LAST_ONBOARDING_SCREEN] ?: 0
//        Logger.d { "lastScreen: $lastScreen" }

        val phone: Long = preferences[PreferencesKeys.PHONE_KEY] ?: 0L
        val accessToken: String = preferences[PreferencesKeys.ACCESS_TOKEN_KEY] ?: ""
        val refreshToken: String = preferences[PreferencesKeys.REFRESH_TOKEN_KEY] ?: ""
        val fingerprint: String = preferences[PreferencesKeys.FINGER_PRINT_KEY] ?: ""


//        val isOnBoardingComplete: Boolean = (lastScreen >= 1)
//        val userId = preferences[PreferencesKeys.USER_ID] ?: 0
//        val isSignedOut = preferences[PreferencesKeys.IS_SIGNED_OUT] ?: false
//        val showNotifications = preferences[PreferencesKeys.NOTIFICATIONS_DENIED] ?: false

        return AppPreferences(
//            lastScreen,
//            isOnBoardingComplete,
//            userId,
//            isSignedOut,
//            showNotifications
            phone = phone,
            accessToken = accessToken,
            refreshToken = refreshToken,
            fingerprint = fingerprint

        )
    }
}