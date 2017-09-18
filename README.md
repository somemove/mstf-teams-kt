# Microsoft Teams client for Kotlin (Java)

[![Release](https://jitpack.io/v/somemove/mstf-teams-kt.svg?style=flat-square)](https://jitpack.io/#somemove/mstf-teams-kt)

## Install

```groovy
// Define the dependency version
buildscript {
	ext {
		msftTeamsVersion = 'THE_VERSION'
	}
}
// Add the Jitpack repository
repositories {
	maven { url 'https://jitpack.io' }
}
// Add the dependency
dependencies {
	compile "com.github.somemove:mstf-teams-kt:$msftTeamsVersion"
}
```

## Usage

### Kotlin

```kotlin
val payload = JSONObject()
	.put("text", "The message text...")

val webhookURL : String = "https://..."
val client : Teams = Teams().with(webhookURL)

val result : Boolean = client.publish(payload)
```

### Java

```java
JSONObject payload = new JSONObject()
	.put("text", "The message text...");

String webhookURL = "https://...";
Teams client = new Teams().with(webhookURL);

boolean result = client.publish(payload);
```
