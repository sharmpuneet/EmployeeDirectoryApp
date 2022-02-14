# EmployeeDirectoryApp

An employee directory app that shows a list of employees with their picture, name, team and type.

## Build tools & versions used
* Android Studio using gradle
* Kotlin Plugins and View binding
* Targeting API 31 with minimum version as Oreo (API 26).
Note: Kept minimum version to Oreo which covers 86% of the devices in market.

## Steps to run the app
App can run in multiple build variants and product flavors. Created release and debug version of the app as build variants. Also,
created multiple flavors to showcase different environment (empty and malformed json as single API is not supporting error scenario)

assembleDebug, installProdDebug tasks can be used to run the app. 
Note: Not added any signInConfigs for release versions.

## Focus Areas
* Architecture - Model-View_ViewModel Clean architecture using Coroutines.
* Focused mainly on the phone devices (not tablet devices)
* Image caching achieved using Glide's caching mechanism.
* Retrofit, OkHttp and Gson libraries to integrate with API.
* Packaging structure has been followed to achieve scalability in future.
* Used Navigation component for scalability of the app.
* Leak Canary Integration in debug mode to check for memory leaks.
* Usage of RecyclerView's DiffUtils instead of notifyDataSetChanged for better performance.
* Clean and Crisp UI using CardView to show details.
* Applied Name sorting in the list for better look and feel.
* Created build variants and product flavors to showcase the production readiness of the app.

## Reason for desired focus and problems solved.
* A clean architecture has been followed to abide by SOLID principles. Separation of concerns helped in creating unit test cases.
* Out of box caching solution is used from Glide because it provides multiple ways of caching as per our needs.
* Navigation Component and packaging structure will help in achieving scalability in the app if more features are added.
* Included Leak Canary to ensure there are no memory leaks
* Added a getter in data class to show correct value for employeeType

## How much time was spent on this project? 
Four to Six hours in chunks

## Did you make any trade-offs for this project? What would you have done differently with more time?
* Might have used Kotlin Flows and Jetpack Compose
* Would have used multi-modular architecture with Dependency Injection (Dagger-Hilt) if more time and features are required.
* More test cases around view model factory, etc.

## What do you think is the weakest part of your project?
* Navigation Component and Fragment over activity wasn't necessary to be used for just one screen. However, I have kept it to show case Single App architecture which can used for scalability.
* Known bug in Glide's 4.+ versions - "W/Glide: Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored"
This is not causing issues but can resolved by creating a GlideModule (Should be done if usage is a lot)
  
## Did you copy any code or dependencies? Please make sure to attribute them here!
* Mostly Dependencies
* Minor chunks of layout code.

## Is there any other information you’d like us to know?
* Not added any signing configs for release version assuming the app can be verified in debug mode.
* Wanted to add a UI component like Testing tools to switch URL for API to test empty states. However, I couldn't do it due to lack of time, hence went with product flavors(just to showcase knowledge around product flavors.)
In real world, different environment Urls will be used in the flavors instead of empty or malformed url.
* I haven't added any API features such as retry or timeout because the API available is statically reaching out to server and is pretty quick. While integrating with more secure endpoints, would go for enhanced features such as security, retries, timeouts, etc.
* I have not created styles.xml because of not having a lot of re-usability of textStyles.
* Not added any Splash Screen as there is no initial loading required. I could have called API on Splash and used the data on the screen if the data was huge and needed to be stored.

## Video

