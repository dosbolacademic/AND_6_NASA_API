<!-- (This is a comment) INSTRUCTIONS: Go through this page and fill out any **bolded** entries with their correct values.-->

# AND101 Project 5 - Choose Your Own API

Submitted by: **Dosbol Aliev**

Time spent: **5** hours spent in total

## Summary

**NASA API** is an Android app that displays the Astronomy Picture of the Day (APOD) from NASA's API, including the image, title, description, and date.

If I had to describe this project in three (3) emojis, they would be: 🛰️🌌📷

## Application Features

<!-- (This is a comment) Please be sure to change the [ ] to [x] for any features you completed.  If a feature is not checked [x], you might miss the points for that item! -->

The following REQUIRED features are completed:

- [X] Make an API call to an API of your choice using AsyncHTTPClient
- [X] Display at least three (3) pieces of data for each API entry retrieved
- [X] A working Button requests a new API entry and updates the data displayed

The following STRETCH features are implemented:

- [X] Add a query to the API request
  - The query I added is **date=YYYY-MM-DD**
- [X] Build a UI to allow users to add that query

The following EXTRA features are implemented:

- [X] List anything else that you added to improve the app!

## API Choice

My chosen API for this project is **[NASA_API](https://api.nasa.gov/planetary/apod?count=100&api_key=DEMO_KEY)**.

## Video Demo

Here's a video / GIF that demos all of the app's implemented features:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Demo' width='' alt='Video Demo' />

GIF created with **ScreenToGif**

![AND102-Project-3](https://github.com/user-attachments/assets/2066d2c4-f4d9-4243-9c2f-e47d30df363e)

<!-- Recommended tools:
- [Kap](https://getkap.co/) for macOS
- [ScreenToGif](https://www.screentogif.com/) for Windows
- [peek](https://github.com/phw/peek) for Linux. -->

## ✅ Notes

- I learned how to make API calls using **AsyncHTTPClient** and parse JSON responses in Kotlin.
- This project taught me how to use **RecyclerView** to display dynamic data and update it based on user input.
- I implemented a **query parameter (date)** in the API request, which helped me understand how to interact with real-world APIs.
- I used **Glide** to load images efficiently from URLs into an `ImageView`.
- I gained experience handling user input via **EditText** and updating the UI with **Button** clicks.
- One challenge was understanding the JSON structure and handling optional fields like `hdurl`, but it helped me improve my debugging skills.
- Overall, this unit made me more confident working with APIs and building user-responsive Android apps.

## License

Copyright **2025** **Dosbol Aliev**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
