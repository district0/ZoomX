# ZoomX

Tool to log all network calls in a suitable format.

# [Retrofit & Volley] or your own netwoking methods is easy to integrate

Zoomx is very simple in integration with volley and retrofit or any network library you can even integrate zoomx with your netwoking requests methods. 

# Feature Examples

[![Webp.net-gifmaker.gif](https://s17.postimg.org/8gz1mfrin/Webp.net-gifmaker.gif)](https://postimg.org/image/cq3rolurv/)

# Usage 

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
	
        String requestBody = request.getBody();
        boolean hasRequestBody = requestBody != null;
        Date startDate = new Date(request.getStartTime());

        requestBuilder.setMethod(request.getHttpMethod().name())
                .setCode(statusCode)
                .setStartDate(startDate)
                .setTookTime(Calendar.getInstance().getTimeInMillis()- request.getStartTime())
                .setUrl(request.getUrl())
                .setRequestBody(hasRequestBody ? requestBody : "")
                .setRequestHeaders(request.getBodyParameters());

        if (response != null) {
            requestBuilder.setResponseBody(response);
        }

        NetworkLogManager.log(requestBuilder);
  
  # TODO
  
  - App dependency version abstraction
  - Revisit implementing map
  - Revisit request adapter
  
