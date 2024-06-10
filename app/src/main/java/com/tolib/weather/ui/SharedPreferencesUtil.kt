import android.content.Context

object SharedPreferencesUtil {

    private const val PREFS_NAME = "MyPrefs"
    internal const val UNIT_KEY = "unit"
    internal const val CITY_KEY = "city"
    fun writeString(context: Context, key: String, value: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun readString(context: Context, key: String, defaultValue: String = ""): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val prefValue = prefs.getString(key, defaultValue)
        return if(!prefValue.isNullOrBlank()) prefValue else defaultValue
    }

    fun writeInt(context: Context, key: String, value: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun readInt(context: Context, key: String, defaultValue: Int = 0): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(key, defaultValue)
    }

    fun writeBoolean(context: Context, key: String, value: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun readBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(key, defaultValue)
    }
}
