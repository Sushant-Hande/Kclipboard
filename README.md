
# Kclipboard

Kclipboard is a Kotlin Multiplatform library that provides a small, idiomatic API for copying text to the clipboard on Android and iOS, with more platform targets planned. Minimal dependencies, easy integration, and test coverage for core logic.

## Platforms

| Platform | Support     |
| :---: | :---: |
| Android | ✅           |
| iOS | ✅           |
| Desktop (JVM) | Coming Soon |
| Wasm / JS | Coming Soon|

## Download

```kotlin
dependencies {
    implementation("dev.sushanthande.kclipboard:<version>")
}
```

## Quick start

```kotlin
@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    
    // Create an instance of KclipboardManager
    val kclipboardManager : KclipboardManager = rememberKclipboardManager()

    scope.launch {
        // Call copy API asynchronously
        kclipboardManager.copy("Sushant Hande")

        // Call getCopiedText API asynchronously
        val copiedText : String? = kclipboardManager.getCopiedText()
    }
}
```

## License

```
Copyright 2026 Sushant Hande

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```