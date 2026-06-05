# AGENTS.md

## Project Overview

This project is a Kotlin Multiplatform (KMP) library that provides a unified clipboard API across multiple platforms.

Current supported platforms:

* Android
* iOS

Planned future platforms:

* Desktop (JVM)
* macOS
* Windows
* Linux
* Web (WASM/JS)

The primary goal is to provide a simple, consistent, and reliable API for clipboard operations while hiding platform-specific implementation details from library consumers.

---

# Core Principles

When contributing to this project, always optimize for:

1. API simplicity
2. Developer experience
3. Long-term maintainability
4. Backward compatibility
5. Performance
6. Security
7. Multiplatform correctness
8. Small library footprint

Think from the perspective of a library consumer first.

---

# Consumer-First Development

Before making any code changes, ask:

* Is this API intuitive?
* Does this increase complexity for consumers?
* Can this be achieved with fewer configuration steps?
* Is the behavior consistent across platforms?
* Will this break existing consumers?

Prefer APIs that are:

* Discoverable
* Predictable
* Self-documenting
* Easy to integrate

Good:

```kotlin
ClipboardManager.copy("Hello")
```

Avoid:

```kotlin
ClipboardManager.initialize(config)
ClipboardManager.performClipboardOperation(...)
```

unless additional complexity is absolutely necessary.

---

# Kotlin Best Practices

Follow modern Kotlin best practices:

## General

* Prefer immutability (`val`) over mutability (`var`)
* Use expression bodies where appropriate
* Prefer sealed types over enums when extensibility is needed
* Avoid unnecessary nullable types
* Prefer composition over inheritance
* Keep functions small and focused

## API Design

* Expose the smallest public API possible
* Prefer internal visibility whenever feasible
* Avoid leaking implementation details
* Avoid exposing platform-specific types in common code
* Design APIs for long-term stability

## Coroutines

* Do not introduce coroutines unless genuinely required
* Avoid blocking operations
* Keep clipboard operations lightweight

---

# Kotlin Multiplatform Guidelines

## Common Code First

Always implement business logic in:

```text
commonMain
```

Use platform-specific source sets only when required.

## Expect/Actual

Use `expect/actual` only when:

* Platform APIs differ
* No common abstraction is available

Keep expect declarations minimal.

Good:

```kotlin
expect object PlatformClipboard
```

Avoid large expect interfaces containing many platform-specific details.

---

# Compose Multiplatform Guidelines

Although this is primarily a library project:

* Do not introduce Compose dependencies unless required
* Keep core clipboard functionality UI-framework agnostic
* Consumers should be able to use the library in:

    * Jetpack Compose
    * Compose Multiplatform
    * SwiftUI

Never couple clipboard functionality to Compose runtime.

---

# SDK / Library Development Standards

## Binary Compatibility

Public APIs are contracts.

Before changing public APIs:

* Consider backward compatibility
* Prefer additive changes
* Avoid breaking changes

If a breaking change is unavoidable:

* Deprecate first
* Provide migration guidance

---

## Semantic Versioning

Follow SemVer:

* MAJOR = Breaking changes
* MINOR = New functionality
* PATCH = Bug fixes

Examples:

```text
1.0.0
1.1.0
1.1.1
2.0.0
```

---

## Dependency Management

Minimize dependencies.

Before adding a dependency ask:

* Can this be implemented without it?
* Is the dependency actively maintained?
* Does it increase binary size?
* Does it introduce security risks?

Prefer zero-dependency implementations whenever possible.

---

# Performance Guidelines

Clipboard operations should be extremely lightweight.

Avoid:

* Unnecessary allocations
* Reflection
* Heavy dependency graphs
* Expensive initialization

Prefer:

* Lazy initialization
* Lightweight abstractions
* Direct platform API usage

Performance should remain predictable on low-end devices.

---

# Security Guidelines

Clipboard data may contain sensitive user information.

Always consider:

* Privacy
* Data leakage
* Memory retention

Rules:

* Never log clipboard contents
* Never persist clipboard contents
* Never send clipboard contents over the network
* Never collect analytics on clipboard data
* Keep clipboard access local to the device

If future functionality introduces clipboard monitoring, require explicit user consent.

---

# Error Handling

Provide meaningful errors.

Avoid:

```kotlin
Exception()
```

Prefer:

```kotlin
ClipboardException(
    message = "Clipboard service unavailable"
)
```

Error messages should help consumers understand what happened.

---

# Testing Requirements

Every feature should be accompanied by tests when possible.

Focus on:

## Common Tests

* API behavior
* Validation
* Error handling

## Platform Tests

* Android implementation
* iOS implementation

Test both success and failure scenarios.

---

# Documentation Standards

Every public API should include KDoc.

Example:

```kotlin
/**
 * Copies the provided text to the system clipboard.
 *
 * @param text Text to copy.
 */
fun copy(text: String)
```

Documentation should explain:

* What the API does
* Parameters
* Return values
* Exceptions
* Platform-specific behavior

---

# Naming Conventions

Prefer clear names.

Good:

```kotlin
ClipboardManager
ClipboardProvider
ClipboardException
```

Avoid:

```kotlin
ClipUtil
ClipboardHelper
ClipboardStuff
```

Names should clearly communicate intent.

---

# Code Review Checklist

Before accepting changes verify:

* API remains intuitive
* Public surface area is minimal
* No platform-specific types leak into common code
* Documentation is updated
* Tests are included
* Binary compatibility is preserved
* No unnecessary dependencies added
* Performance impact considered
* Security implications reviewed

---

# Future Platform Expansion

Design code with future platforms in mind.

Avoid assumptions that:

* Android will always be the primary platform
* iOS behavior defines the API
* Clipboard APIs behave identically everywhere

Every abstraction should be evaluated for:

* Android
* iOS
* Desktop JVM
* macOS
* Linux
* Windows
* Web

---

# Preferred Architecture

```text
commonMain
 ├── Public API
 ├── Contracts
 └── Shared Logic

androidMain
 └── Android Clipboard Implementation

iosMain
 └── iOS Clipboard Implementation
```

Keep platform-specific code isolated.

---

# Project Goal

Provide the best clipboard experience for Kotlin Multiplatform developers through:

* Clean APIs
* Reliable behavior
* Excellent documentation
* Strong compatibility guarantees
* Minimal dependencies
* High performance
* Strong security practices
* Consistent multiplatform experience
