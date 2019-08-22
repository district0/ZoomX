# ZoomX

Zoomx is a in-app debugging tool to view and log all netwrok services history in a suitable format. 

<img src="https://j.gifs.com/ANmr79.gif" width="500" height="500" />

YouTube Video
[linkname](https://https://youtu.be/kri9Eyrso5M)

# Usage

Gradle:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```gradle 
allprojects {
	repositories {
		     maven { url 'https://jitpack.io' }
	}
}
```
  
Step 2. Add the dependency [ make sure that you are on the latest release number ]

```gradle
dependencies {
	        implementation 'com.github.district0:ZoomX:1.0.8'
```
  
Step 3. Initiate ZoomX service
```java
        ZoomX.init(new Config.Builder(this).build());
```

Step 4. Start/Stop ZoomX service. You can call it in your app activity cycles like (OnResume/OnPause)
```java
        ZoomX.showMenu();
        ZoomX.hideHead();
```

Step 5. Log your network requests
        
# Retrofit
Just add Zoomx ZoomXLoggerInterceptor:
```java	  
	  OkHttpClient.Builder httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new ZoomXLoggerInterceptor(this.context));
```

# Volley or any request method 

Just create zoomx request entitiy object then send it to zoomx network log manager. 
	  
For example: 
	
```java
	RequestEntity.Builder requestBuilder = new RequestEntity.Builder();

        requestBuilder.setMethod("GET")
                .setCode(200)
                .setStartDate("Start-Date")
                .setUrl("https://github.com/district0/ZoomX")
                .setRequestBody("JSON_BODY")
                .setRequestHeaders("HEADERS")
		.setResponseBody(response);

        ZoomXLogManager.log(requestBuilder);
```

# Features 
  - Display list of requests in real time sorted by date. 
  - Send logged request/response by email.   
  - Search within requests url. 
  - Copy any response/requests and share it via any app ex. (email). 
  - Display request details in a pretty format.
  - Take screenshot
  
  # Upcoming Features
  - Group requests per page.
  - Memory leaks report per page and whole app. 
  - Control internet speed.
  - Shake to change working environment. 
  - Badge title (To indicate user to on which environment you are connected production, staging,...).
  - Crashes report.

