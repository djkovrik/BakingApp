Backing App created as a part of Udacity [Android Developer Nanodegree Program](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801).

## Used Libraries
* [RxJava 2](https://github.com/ReactiveX/RxJava), [RxAndroid](https://github.com/ReactiveX/RxAndroid), [RxJava2Interop](https://github.com/akarnokd/RxJava2Interop)
* [Dagger 2](https://github.com/google/dagger)
* [Retrofit 2](https://github.com/square/retrofit) with [RxJava adapter](https://github.com/square/retrofit/tree/master/retrofit-adapters/rxjava2) and [Gson converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
* [OkHttp](https://github.com/square/okhttp)
* [Retrolambda](https://github.com/evant/gradle-retrolambda)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Gson](https://github.com/google/gson)
* [AutoValue](https://github.com/google/auto) with [Gson extension](https://github.com/rharter/auto-value-gson)
* [SqlBrite](https://github.com/square/sqlbrite)
* [Stetho](https://github.com/facebook/stetho)
* [LeakCanary](https://github.com/square/leakcanary)
* [Timber](https://github.com/JakeWharton/timber)
* [ExoPlayer](https://github.com/google/ExoPlayer)

## Project Overview
You will productionize an app, taking it from a functional state to a production-ready state. This will involve finding and handling error cases, adding accessibility features, allowing for localization, adding a widget, and adding a library.

## Why this Project?
As a working Android developer, you often have to create and implement apps where you are responsible for designing and planning the steps you need to take to create a production-ready app. Unlike Popular Movies where we gave you an implementation guide, it will be up to you to figure things out for the Baking App.

## What Will I Learn?
In this project you will:
* Use MediaPlayer/Exoplayer to display videos.
* Handle error cases in Android.
* Add a widget to your app experience.
* Leverage a third-party library in your app.
* Use Fragments to create a responsive design that works on phones and tablets.

## Rubric

### General App Usage
- [ ] App should display recipes from provided network resource.
- [ ] App should allow navigation between individual recipes and recipe steps.
- [ ] App uses RecyclerView and can handle recipe steps that include videos or images.
- [ ] App conforms to common standards found in the Android Nanodegree General Project Guidelines.

### Components and Libraries
- [x] Application uses Master Detail Flow to display recipe steps and navigation between them.
- [ ] Application uses Exoplayer to display videos.
- [ ] Application properly initializes and releases video assets when appropriate.
- [ ] Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
- [ ] Application makes use of Espresso to test aspects of the UI.
- [ ] Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with Content Providers if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.

### Homescreen Widget
- [ ] Application has a companion homescreen widget.
- [ ] Widget displays ingredient list for desired recipe.