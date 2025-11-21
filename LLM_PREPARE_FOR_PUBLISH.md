# Подготовка ringer_mode к публикации на pub.dev

## Текущее состояние
- Package: `ringer_mode`
- Версия: `1.0.0`
- Платформы: Android only (iOS не поддерживается)
- Нативный класс: `FlutterRingerModePlugin` (Android Kotlin)
- Package name: `team.moroz.flutter_ringer_mode`

## Проверки (в порядке приоритета)

### 1. Git и .gitignore
[ ] .gitignore существует и актуален
[ ] Удалить из git: .dart_tool/, .idea/, .metadata, .editorconfig, *.backup
[ ] git status чистый

### 2. Naming Consistency
[ ] Package name = Class names = File names
[ ] Проверить Android: `FlutterRingerModePlugin` ✅
[ ] pluginClass в pubspec.yaml соответствует ✅
[ ] Нет iOS платформы (только Android) ✅

### 3. Код
[ ] Нет debug print()
[ ] Все комментарии на английском
[ ] Нет русского текста в коде

### 4. Документация
[ ] README актуален (примеры, requirements) ✅
[ ] CHANGELOG содержит текущую версию ✅
[ ] LICENSE присутствует ✅

### 5. Валидация
[ ] flutter analyze - No issues found
[ ] flutter pub publish --dry-run - Validation passed

## Известные проблемы
- Нет .gitignore файла (нужно создать)
- Возможны временные файлы в git (.dart_tool/, .idea/, .metadata)
- Есть `pubspec.yaml.backup` - нужно удалить

## Команды
```bash
# Проверка
flutter analyze
flutter pub publish --dry-run

# Очистка git (если нужно)
git rm --cached -r .dart_tool/
git rm --cached .metadata .editorconfig
git rm --cached pubspec.yaml.backup

# Публикация (после проверки)
git add -A
git commit -m "Prepare for pub.dev"
git push
echo "y" | flutter pub publish
```

## Контекст
Эта инструкция создана на основе опыта подготовки wifi_info_enhanced.
