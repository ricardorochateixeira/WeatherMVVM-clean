### Weather Forecast
Weather Forecast is an Android Application developed using Kotlin. This application was developed for demonstration purposes and for learning.

### Features
Some of the features implemented untill this moment are listed below:

* Search city by name with auto complete;
* Swipe to delete and undo;
* Current and future weather (next 5 days each 3 hours);
* Possibility to add cities to favorites.
* Dark mode

<img src="https://github.com/ricardorochateixeira/WeatherMVVM-clean/blob/DEV/screenshots/record_giff.gif" width="144" height="304"> <img src="https://github.com/ricardorochateixeira/WeatherMVVM-clean/blob/DEV/screenshots/screenshot4.jpg" width="144" height="304"> <img src="https://github.com/ricardorochateixeira/WeatherMVVM-clean/blob/DEV/screenshots/screenshot2.jpg" width="144" height="304"> <img src="https://github.com/ricardorochateixeira/WeatherMVVM-clean/blob/DEV/screenshots/screenshot3.jpg" width="144" height="304"> 

### App desgin and architecture

This project was developed using clean architecture. There are three main layers:

* Domain layer contains all the business logic;
* Data layer is an abstraction defitnition of the different data sources;
* Framework layer encapsulates the interaction with the framework. The abstractions from the data layer are implemented in this layer. All the presentation is in this layer. 

For the presentation layer MVVM (Model-View-ViewModel) was used.

Some of Android Jetpack Components used are:

* ViewModel;
* RecyclerView;
* Room;
* Navigation;
* DataStore;
* SharedPreferences;
* ViewBinding;
* LiveData:

Some libraries used are:

* Coroutines with Flows;
* Retrofit for makine RESTful API calls;
* Hilt for dependency injection;
* Leak Canary in debug mode to detect memory leaks.

### Author
[Ricardo Teixeira](https://github.com/ricardorochateixeira?tab=repositories)


