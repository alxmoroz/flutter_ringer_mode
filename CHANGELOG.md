# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.2] - 2025-11-21

### Fixed
- Fixed MissingPluginException when subscribing to ringerModeStream on Android
- Added EventChannel registration and StreamHandler implementation
- Added BroadcastReceiver to track RINGER_MODE_CHANGED_ACTION events
- Added iOS support in Dart layer (returns silent mode as fallback)

### Changed
- ringerModeStream now properly emits events when ringer mode changes on Android
- ringerModeStream on iOS returns a stream with single silent value (iOS doesn't support ringer mode detection)

## [1.0.1] - 2025-10-25

### Fixed
- Fixed parameter passing in setRingerMode method (Dart to Kotlin communication)
- Fixed AndroidManifest.xml permission from ACCESS_NOTIFICATION_POLICY to MODIFY_AUDIO_SETTINGS
- Added proper .gitignore for Flutter plugin

## [1.0.0] - 2025-10-16

### Added
- Initial release of ringer_mode plugin
- Support for Android platform only
- **Primary functionality**: Get current ringer mode
- **Additional feature**: Set ringer mode (silent, vibrate, normal)
- Stream support for real-time ringer mode changes
- Comprehensive error handling with fallback values
- Complete documentation and README

### Technical Details
- Android: Uses AudioManager to get and set ringer mode
- Stream implementation using EventChannel for real-time updates
- Cross-platform API design (iOS not supported due to platform limitations)
- Proper error handling and exception management

### Platform Support
- ✅ Android: Full support for get/set operations
- ❌ iOS: Not supported (Apple restricts access to ringer mode API)
