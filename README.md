# Microsoft Teams client for Kotlin (Java)

## Install

```groovy
// Add the Jitpack repository
repositories {
	...
	maven { url 'https://jitpack.io' }
}
// Add the dependency
dependencies {
	compile "com.github.somemove:mstf-teams-kt:0.0.1"
}
```

## Usage

```kotlin
val payload = JSONObject()
payload.put("text", "The message text...")

val webhookURL = "https://..."
val teams = Teams.default().with(webhookURL)

val result : Boolean = teams.publish(payload)
```
