# @Spot
An IntelliJ IDEA Plugin, to control spotify and have fun.

## Features
* Control Spotify directly from IDEA
    * Ctrl + Shift + S to open the @Spot Popup Window. 
    * You can use shortcuts or buttons to navigate through the playlist.
* @Spot macro to insert currently played track whenever you want
* $SPOT$ LiveTemplate to use in your FileTemplates:
    * Use it in your File Header to show the music you were listening when the file is created:
        ``` java
        /**
         * Created by: Oldborn at ${DATE}
         * While listening: ${SPOT}
         */
        ```

## Configuration
1. Register an APP from the spotify developer dashboard https://developer.spotify.com/dashboard
2. Add `http://copyPasteMe` to Redirect URIs from the `EDIT SETTINGS` button
3. In IDEA; from the settings menu find the `@Spot`, usually under the `Other Settings` tab
4. Add your CLIENT ID, SECRET and REDIRECT URL to relevant text boxes
5. Hit apply, it will direct you to Spotify login page
6. After you login Spotify will try to redirect you to redirect url you specified (`http://copyPasteMe`)
7. Copy the whole Url from your browser and paste it to prompt inside the IDEA 
8. That's it!
