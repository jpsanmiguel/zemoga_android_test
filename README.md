
# zemoga_android_test

## How to run?

Clone the repo, open the project in Android Studio, hit "Run". Done!

It should be as simple as that, really. But since sometimes things go wrong, here is a link to an [APK](https://github.com/jpsanmiguel/zemoga_android_test/blob/main/apk/app-debug.apk) of the application just in case so you can still test it.

## Architecture

For this app, the architecture followed was the [architecture recommended by Google for Android Applications](https://developer.android.com/topic/architecture). The app is mainly divided in three main layers: 

![Diagram of a typical app architecture](https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview.png)
This architecture allows a clear separation of concerns between all of our app parts. Now I am going to explain each layer in more detail:

### UI layer
As described by [Google](https://developer.android.com/topic/architecture#ui-layer):

> The role of the UI layer (or _presentation layer_) is to display the application data on the screen. Whenever the data changes, either due to user interaction (such as pressing a button) or external input (such as a network response), the UI should update to reflect the changes.
> 
> The UI layer is made up of two things:
> 
> -   UI elements that render the data on the screen. You build these elements using Views or [Jetpack
> Compose](https://developer.android.com/jetpack/compose) functions.
> -   State holders (such as [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
> classes) that hold data, expose it to the UI, and handle logic.
> 
> ![In a typical architecture, the UI layer's UI elements depend on
> state
>     holders, which in turn depend on classes from either the data layer or the
>     optional domain layer.](https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview-ui.png)
> 
> **Figure 2.** The UI layer's role in app architecture.
> 
> To learn more about this layer, see the [UI layer
> page](https://developer.android.com/jetpack/guide/ui-layer).

And that is exactly how the UI layer of this application looks like:

![UI Layer](https://user-images.githubusercontent.com/31251308/174501719-cfb9ab4e-1bfd-4f07-8c19-b7da9d060b98.png)

UI layer folder is divided between *home* and *detail*, since are the screens developed for this application. Each screen package has it's fragment class, an adapter package, a viewholder package that represent the UI elements that are displayed on each screen. Also, each screen has a viewmodel package that hosts the viewmodel of each screen.

## Domain layer

As described by [Google](https://developer.android.com/topic/architecture#domain-layer):

> The domain layer is an optional layer that sits between the UI and data layers.
> 
> The domain layer is responsible for encapsulating complex business logic, or simple business logic that is reused by multiple ViewModels. This layer is optional because not all apps will have these requirements.
>  
>  ![When it is included, the optional domain layer provides dependencies to     the UI layer and depends on the data layer.](https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview-domain.png)
> 
> **Figure 4.** The domain layer's role in app architecture.
> 
> Classes in this layer are commonly called _use cases_ or _interactors_. Each use case should have responsibility over a _single_ functionality. For example, your app could have a `GetTimeZoneUseCase` class if multiple ViewModels rely on time zones to display the proper message on the screen.
> 
> To learn more about this layer, see the [domain layer page](https://developer.android.com/jetpack/guide/domain-layer).

In this application case, the domain layer hosts the models of the app (*Comment*, *Post*, *PostDetail* and *User*) and a *PostRepository* interface. Is a really simple layer, but helps mainly to separate concerns and for testing purpose with the application repository interface.

![Domain layer](https://user-images.githubusercontent.com/31251308/174502232-3c9087d4-4a6b-48e9-ba58-9498e446c674.png)
## Data layer

As explained by [Google](https://developer.android.com/topic/architecture#data-layer):

> The data layer of an app contains the _business logic_. The business logic is what gives value to your app—it's made of rules that determine how your app creates, stores, and changes data.
> 
> The data layer is made of _repositories_ that each can contain zero to many _data sources_. 
> 
> ![In a typical architecture, the data layer's repositories provide data     to the rest of the app and depend on the data sources.](https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview-data.png)
> 
> **Figure 3.** The data layer's role in app architecture.

And, as well as the UI layer, follows exactly this explanation. This application data layer hosts two main things:
* Data sources
* Repository

### Data sources
This application has two main data sources. A local and remote data source. Each data source hosts also their own *layer* models, entities and data transfer objects respectively.
#### Local data source
The local data source of this application is a Room database that saves all the information received from JSONPlaceholder so the application can be used without internet connection. Also, this layers hosts the entities data classes used in the Room database model. The Room database is implemented following the [Room documentation](https://developer.android.com/training/data-storage/room).

![local data source](https://user-images.githubusercontent.com/31251308/174502388-8bf6f7d0-3f13-41da-b351-aa43fb8c15a2.png)
#### Remote data source
The remote data source of this application is Retrofit service. Is the one in charge of loading all the information for first time and then this information is saved to the local data source. Also, this layers hosts the data transfer object data classes used in the Retrofit service consumption.

![remote data source](https://user-images.githubusercontent.com/31251308/174502453-28ea0f44-541a-4b5b-ba30-ac3bb5e46da3.png)
#### DefaultPostRepository
Additional to data source, the data layer also hosts the application main repository, that actually consumes this data sources. Again, this repository follows the explanation by Google of it's responsibility.

> Repository classes are responsible for the following tasks:
> 
> -   Exposing data to the rest of the app.
> -   Centralizing changes to the data.
> -   Resolving conflicts between multiple data sources.
> -   Abstracting sources of data from the rest of the app.
> -   Containing business logic.

The DefaultPostRepository is in charge of exposing data to the viewmodels of this applications. Centralizes the data, resulting in a single source of truth for all application information. Is also the repository responsibility to know where to get the data from, in this application case, from the local or remote data source. Abstracts sources of data, meaning viewmodels don't know if data comes from a network service or a local database. And contains all the additional data logic required in the application.




## Additional
### DI

This is an additional package of the application to host everything related to Dependency Injection. This app Dependency Injection is made with a third party library, Koin. This library handles all the application injections necessary for the correct execution of the application.

![di](https://user-images.githubusercontent.com/31251308/174502766-8fc3a7dd-3dd3-4d17-a156-f201dbbbf645.png)
### Util

This additional package hosts any shared logic that can be reused in the whole application and does not have a clear place in the architecture. In the case of this application, hosts mainly BindingAdapters, base classes, extension functions and a connection helper class to handle internet connection status.

![util](https://user-images.githubusercontent.com/31251308/174502806-0db33780-84f5-4815-bc46-a931b1506991.png)
## Screens and behavior
### Full app
![full app](https://user-images.githubusercontent.com/31251308/174503554-d8762227-a3f5-456b-8b10-9ebae0fcd77b.gif)

As seen in video, application can work without internet. In case the app is launched without internet a snackbar is going to be showed telling the user there is no internet. As soon as user gets internet connection, user can swipe down to refresh and this is going to load or update the posts list and this list is going to be saved in the local Room database in order to be used on future all launches. When a user navigates to the detail of a post, then the post user and comments are retrieved from the API. This are also saved into the local database so app can work without internet and still see a post detail if saved locally. With this behavior the user the can launch the app without internet and is going to have all the information of posts opened before. In case the has no internet, has the post list locally and the user taps in a post to see it's detail there are three possible outcomes. If the post was saved locally before, then the user is going to see the full post from the local database. If the post is from a user with a saved post locally, then the post is going to show the user, but not the comments, since they are not locally saved. And if no post from that user is saved locally, then the post is only going to show the post title and body.

### Home
**Home screen posts without favorites**

<img src="https://user-images.githubusercontent.com/31251308/174503239-33946abb-fcff-42ad-a365-d806f81f1be1.png" width="300">

**Home screen posts with favorites**

<img src="https://user-images.githubusercontent.com/31251308/174503140-2a2e0505-3beb-435f-a055-b74318247a97.png" width="300">

App shows favorite posts on top ordered by the time they were added to favorites. 

**Home screen empty state**

<img src="https://user-images.githubusercontent.com/31251308/174503206-bfbf80fb-4a4f-48c0-9e75-97ef5bac278e.png" width="300">

**Home screen no internet**

<img src="https://user-images.githubusercontent.com/31251308/174503302-022afac0-c34b-48a8-9b58-dcc8a5cc3e3a.png" width="300">

When app has no internet and tries to call a service a snackbar is showed.

### Detail

**Detail post screen**

<img src="https://user-images.githubusercontent.com/31251308/174503602-df596b27-b2bf-4460-b531-e9f79f275739.png" width="300">

**Detail post screen favorite post**

<img src="https://user-images.githubusercontent.com/31251308/174503610-4d88e5b7-d875-4527-9a3c-1def53234cba.png" width="300">

**Detail post screen no comments**

<img src="https://user-images.githubusercontent.com/31251308/174503623-ce7b1210-4ac6-4a4e-9645-2cf14d751c11.png" width="300">

This happens when there is no internet connection but a previous post from the same user was loaded before and saved the user into the local Room database.

**Detail post screen only title and body**

<img src="https://user-images.githubusercontent.com/31251308/174503630-68370a41-ede4-4ad1-bea9-652a5988654d.png" width="300">

This happens when there is no internet connection meaning the only local information saved is the title and body.
