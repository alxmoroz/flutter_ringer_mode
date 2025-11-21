package team.moroz.flutter_ringer_mode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class FlutterRingerModePlugin: FlutterPlugin, MethodCallHandler, EventChannel.StreamHandler {
    private lateinit var channel : MethodChannel
    private lateinit var eventChannel: EventChannel
    private lateinit var context: Context
    private lateinit var audioManager: AudioManager
    private var eventSink: EventChannel.EventSink? = null
    private var ringerModeReceiver: BroadcastReceiver? = null

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_ringer_mode")
        channel.setMethodCallHandler(this)
        
        eventChannel = EventChannel(flutterPluginBinding.binaryMessenger, "flutter_ringer_mode_stream")
        eventChannel.setStreamHandler(this)
        
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

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events
        
        // Register BroadcastReceiver to track ringer mode changes
        ringerModeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == AudioManager.RINGER_MODE_CHANGED_ACTION) {
                    val ringerMode = getRingerMode()
                    eventSink?.success(ringerMode)
                }
            }
        }
        
        val filter = IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
        context.registerReceiver(ringerModeReceiver, filter)
        
        // Emit current ringer mode immediately
        val currentMode = getRingerMode()
        eventSink?.success(currentMode)
    }

    override fun onCancel(arguments: Any?) {
        // Unregister BroadcastReceiver
        ringerModeReceiver?.let {
            try {
                context.unregisterReceiver(it)
            } catch (e: Exception) {
                // Receiver might not be registered
            }
        }
        ringerModeReceiver = null
        eventSink = null
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
        eventChannel.setStreamHandler(null)
        
        // Clean up receiver if still registered
        ringerModeReceiver?.let {
            try {
                context.unregisterReceiver(it)
            } catch (e: Exception) {
                // Receiver might not be registered
            }
        }
        ringerModeReceiver = null
    }
}
