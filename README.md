
# Weather App README

This Weather App is designed to provide users with up-to-date weather information fetched from a remote server. It follows the Model-View-ViewModel (MVVM) architecture pattern, separating concerns and ensuring a clean, maintainable codebase. The app utilizes Retrofit for making network requests, implements caching using SharedPreferences for data, and employs HTTP level cache for downloaded images to optimize performance and reduce bandwidth consumption.

## Features

- **Current Weather**: View the current weather conditions for a specified location or get it by gps.
- **Forecast**: Access a multi-day weather forecast to plan ahead.
- **Image Caching**: Images downloaded from the server are cached at the HTTP level, enhancing app responsiveness and reducing data usage.
- **Data Caching**: Weather data is cached locally using SharedPreferences, ensuring quick access to previously fetched data and reducing network requests.

## Architecture

The app is structured following the MVVM architecture pattern:

- **Model** folder: Represents the data and business logic, including data retrieval and manipulation. It interacts with the remote server using Retrofit and manages caching operations.
- **WeatherFragment.kt**: The UI components responsible for displaying data and capturing user interactions. Activities and Fragments serve as the views in this architecture.
- **WeatherFragmentViewModel.kt**: Acts as a mediator between the View and the Model. It retrieves data from the Model and prepares it for presentation in the View. ViewModels are lifecycle-aware components, ensuring data consistency and efficient resource management.

## Dependencies

- **Retrofit**: A type-safe HTTP client for Android and Java, used for making network requests to fetch weather data from the remote server.
- **SharedPreferences**: Android's built-in mechanism for storing key-value pairs persistently. Utilized for caching weather data locally.
- **Glide**: An image loading and caching library for Android. Used for efficiently loading and caching images downloaded from the server.

## Installation

1. Clone the repository: `git clone https://github.com/djtolib/weather.git`
2. Import the project into Android Studio.
3. Build and run the app on your device or emulator.

## Configuration

Ensure you have the necessary permissions and dependencies configured in your AndroidManifest.xml file, including internet access permissions and any necessary dependencies for Retrofit, SharedPreferences, and Glide.

## Usage

1. Launch the app on your device.
2. Enter the desired location to fetch the current weather and forecast data.
3. Explore the weather information displayed on the screen.
4. Images will be automatically cached for improved performance in subsequent uses.

## Contributing

Contributions are welcome! If you encounter any issues or have suggestions for improvements, please submit a pull request or open an issue on the GitHub repository.

## License

This Weather App is open-source software licensed under the MIT License. See the LICENSE file for more details.

## Credits

This app was developed by [Sohibov Tolibjon].
