package team.moroz.flutter_ringer_mode

import android.content.Context
import android.media.AudioManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class FlutterRingerModePlugin: FlutterPlugin, MethodCallHandler {
    private lateinit var channel : MethodChannel
    private lateinit var context: Context
    private lateinit var audioManager: AudioManager

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_ringer_mode")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "getRingerMode" -> {
                val ringerMode = getRingerMode()
                result.success(ringerMode)
            }
            "setRingerMode" -> {
                val mode = call.argument<String>("mode")
                val success = setRingerMode(mode)
                result.success(success)
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    private fun getRingerMode(): String {
        return when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_SILENT -> "silent"
            AudioManager.RINGER_MODE_VIBRATE -> "vibrate"
            AudioManager.RINGER_MODE_NORMAL -> "normal"
            else -> "normal"
        }
    }

    private fun setRingerMode(mode: String?): Boolean {
        return try {
            val ringerMode = when (mode?.lowercase()) {
                "silent" -> AudioManager.RINGER_MODE_SILENT
                "vibrate" -> AudioManager.RINGER_MODE_VIBRATE
                "normal" -> AudioManager.RINGER_MODE_NORMAL
                else -> AudioManager.RINGER_MODE_NORMAL
            }
            audioManager.ringerMode = ringerMode
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
