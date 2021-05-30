This is a sample app to showcase compose and navigation with retrofit. The app uses dog ceo api.

There is a short demo video to have a quick look at the app.

Features: 
- List all dog breeds
- Expand breed to show subbreed if available
- Show random breed/subbreed images on breed/subbreed click
- Swipe to refresh random images

- tests are available to make sure we can properly parse the data returned by the api, and the viewmodel methods.
- Compose previews are available to test the UI components in each relevant class.

Implementation:
The app uses graph navigation to switch between the screens, MVVM architecture, with a viewmodel downloading
the list of dog breeds when the app is launched and updating the corresponding live data. 
The livedata updates the breeds fragment, which is built using compose, to create a list of expandable breed cards.
Each breed card can be expanded if the breed has sub-breeds.
When the user clicks on a breed/sub-breed the viewmodel fetches 10 (if possible) random images of the breed/subbreeds
and updates the corresponding live data which navigate our app to the second screen (also build with compose) reponsible for displaying images.
A pull to refresh was also added to  fetch new random images for the user (created with compose).

Next: 
Use compose navigation instead of graph navigation.

Just download the app and compile! but make sure you have Android Api 28+
Enjoy! 
