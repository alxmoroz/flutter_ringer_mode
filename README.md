# Ringer Mode

A Flutter plugin to **check** and manage ringer mode on Android devices.

## Features

- ✅ **Get current ringer mode** (primary functionality)
- ✅ Set ringer mode (silent, vibrate, normal) - additional feature
- ✅ Stream support for real-time ringer mode changes
- ✅ Android platform support
- ✅ Simple and easy to use API

## Installation

Add this to your package's `pubspec.yaml` file:

```yaml
dependencies:
  ringer_mode: ^1.0.0
```

## Usage

### Basic Usage

```dart
import 'package:ringer_mode/ringer_mode.dart';

// Check current ringer mode (primary use case)
RingerMode currentMode = await RingerModeService.getRingerMode();
print('Current ringer mode: $currentMode');

// Set ringer mode (additional feature)
await RingerModeService.setRingerMode(RingerMode.silent);

// Listen to ringer mode changes
RingerModeService.ringerModeStream.listen((mode) {
  print('Ringer mode changed to: $mode');
});
```

### Available Ringer Modes

```dart
enum RingerMode {
  silent,    // Silent mode
  vibrate,   // Vibrate mode
  normal,    // Normal mode
}
```

## Platform Support

### Android
- ✅ **Full support** - Uses `AudioManager` to get and set ringer mode
- ✅ Requires `MODIFY_AUDIO_SETTINGS` permission for set operations
- ✅ Real-time stream support for ringer mode changes

### iOS
- ❌ **Not supported** - iOS does not provide any API to access or modify ringer mode
- ❌ Apple restricts access to ringer switch state for privacy and security reasons
- ❌ No workarounds available - this is a platform limitation

## Permissions

### Android
Add to your `android/app/src/main/AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
```

**Note:** This permission is only required for `setRingerMode()`. The `getRingerMode()` function works without additional permissions.

## Example

```dart
import 'package:ringer_mode/ringer_mode.dart';

class RingerModeExample {
  Future<void> checkRingerMode() async {
    try {
      RingerMode mode = await RingerModeService.getRingerMode();
      print('Current ringer mode: $mode');
    } catch (e) {
      print('Error getting ringer mode: $e');
    }
  }
  
  Future<void> setSilentMode() async {
    try {
      await RingerModeService.setRingerMode(RingerMode.silent);
      print('Ringer mode set to silent');
    } catch (e) {
      print('Error setting ringer mode: $e');
    }
  }
  
  void listenToChanges() {
    RingerModeService.ringerModeStream.listen((mode) {
      print('Ringer mode changed to: $mode');
    });
  }
}
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.