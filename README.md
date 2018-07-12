# Anesthesia Plans

This app showcases the following Android Architecture Components: `Room`, `ViewModels` and `LiveData`. It also uses the `Data Binding` Library to display data and bind UI elements to actions.

This app implements some of Google's architecture tools and patterns for Android apps as presented [here](https://github.com/googlesamples/android-architecture/tree/dev-todo-mvvm-live).

## Features

This app contains three screens: a main screen that lists all the plans, a plan detail screen and a screen to add or edit a plan.
The data is saved locally using Room.

### Presentation Layer

The presentation layer uses a `MVVM` architecture.

Each screen consists of:
* an Activity that handles the navigation
* a Fragment that displays the different views
* a View Model that exposes data to the View using LiveData.

### Data Layer

The database is created using Room and has three entities: a `Plan` entity, a `Question` entity and a `Choice` entity.
A plan has a one-to-many relationship to a question and a question has a one-to-many relationship to a choice.

Room's `@Relation` is used to automatically fetch related entities via a single query: a plan is fetched with all its questions, a question with all its choices. ([Based on this post](https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1)).

All queries return a LiveData object that the `PlanRepository` exposes to the UI layer.  

## Live Version

[On Google Play](https://play.google.com/store/apps/details?id=com.elishou.anesthesiaplan)
