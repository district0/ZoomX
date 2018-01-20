# ZoomX

Zoomx is a in-app debugging tool to view and log all netwrok services history in a suitable format. 

[![Webp.net-gifmaker.gif](https://s17.postimg.org/8gz1mfrin/Webp.net-gifmaker.gif)](https://postimg.org/image/cq3rolurv/)

Gradle:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency [ make sure that you are on the latest release number ]

	dependencies {
	        compile 'com.github.fathallah92:ZoomX:0.5'
	}
  
Step 3. Initiate ZoomX service

        ZoomX.init(new Config.Builder(this).build());
	
Step 4. Log your network requests 
        
- Retrofit
	  Just add Zoomx NetworkLogInterceptor:
	  
	  OkHttpClient.Builder httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new NetworkLogInterceptor(this.context));
		
- Volley or any request method 
	  Just create zoomx request entitiy object then send it to zoomx network log manager. 
	  
	For example: 
	
	  RequestEntity.Builder requestBuilder = new RequestEntity.Builder();

        requestBuilder.setMethod("GET")
                .setCode(200)
                .setStartDate("Start-Date")
                .setUrl("https://github.com/district0/ZoomX")
                .setRequestBody("JSON_BODY")
                .setRequestHeaders("HEADERS")
		.setResponseBody(response);

        NetworkLogManager.log(requestBuilder);
  
