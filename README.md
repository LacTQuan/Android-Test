### How to run the project
1. Clone the repository
2. Start the Redis server at localhost:6379 (you can use the docker-compose file in the root directory to start the Redis server)
3. Replace the values in the local.properties file with your own values (in case of localhost, make sure that the Android phone and the computer are connected to the same network, then use ipconfig to get the IP address of the computer and replace the localhost with the IP address)
4. Run the project

### Technologies Used
1. **Language**: Kotlin
2. **Framework**: Jetpack Compose
3. **Dependency Injection**: Hilt/Dagger
4. **Network**: Retrofit (uses OkHttp as the client)
5. **Object Serialization**: Gson
6. **Image Loading**: Coil
7. **Cache**: Redis
8. **Architecture**: MVVM
9. **Asynchronous Programming**: Coroutines

### Features
1. The app fetches the data from the server and displays images in a grid
2. The app caches the data in the Redis server
3. The search functionality is implemented with auto-complete suggestions
4. The app uses shared transitions to animate the image when clicked
5. The app loads the images in a lazy manner and paginates the data on scroll

### Video Demo
[Video Demo](https://drive.google.com/file/d/1kahQ3o1d3ay0BL0hnCjbgp1iZLb8E7z7/view?usp=sharing)
