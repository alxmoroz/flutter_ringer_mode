// Copyright (c) 2024. Alexandr Moroz

import 'package:flutter/services.dart';

/// Enum representing ringer modes
enum RingerMode {
  silent,
  vibrate,
  normal,
}

/// A Flutter plugin to detect and manage ringer mode on Android devices.
/// 
/// Note: This plugin only supports Android. iOS does not provide API
/// to access or modify ringer mode.
class RingerModeService {
  static const MethodChannel _channel = MethodChannel('flutter_ringer_mode');
  static const EventChannel _eventChannel = EventChannel('flutter_ringer_mode_stream');

  /// Get the current ringer mode.
  /// 
  /// Returns the current [RingerMode] of the device.
  /// On error, returns [RingerMode.normal] as fallback.
  /// 
  /// Platform support: Android only.
  static Future<RingerMode> getRingerMode() async {
    try {
      final String mode = await _channel.invokeMethod('getRingerMode');
      return _stringToRingerMode(mode);
    } catch (e) {
      // Return normal mode as fallback on error
      return RingerMode.normal;
    }
  }

  /// Set the ringer mode.
  /// 
  /// Changes the device ringer mode to the specified [mode].
  /// Returns `true` if successful, `false` otherwise.
  /// 
  /// Platform support: Android only.
  /// Note: Requires MODIFY_AUDIO_SETTINGS permission.
  static Future<bool> setRingerMode(RingerMode mode) async {
    try {
      final String modeString = _ringerModeToString(mode);
      final bool result = await _channel.invokeMethod('setRingerMode', {'mode': modeString});
      return result;
    } catch (e) {
      return false;
    }
  }

  /// Get a stream of ringer mode changes.
  /// 
  /// Emits [RingerMode] values whenever the ringer mode changes.
  /// 
  /// Platform support: Android only.
  static Stream<RingerMode> get ringerModeStream {
    return _eventChannel.receiveBroadcastStream().map((event) {
      return _stringToRingerMode(event as String);
    });
  }

  /// Convert string to [RingerMode].
  static RingerMode _stringToRingerMode(String mode) {
    switch (mode.toLowerCase()) {
      case 'silent':
        return RingerMode.silent;
      case 'vibrate':
        return RingerMode.vibrate;
      case 'normal':
      default:
        return RingerMode.normal;
    }
  }

  /// Convert [RingerMode] to string.
  static String _ringerModeToString(RingerMode mode) {
    switch (mode) {
      case RingerMode.silent:
        return 'silent';
      case RingerMode.vibrate:
        return 'vibrate';
      case RingerMode.normal:
        return 'normal';
    }
  }
}
