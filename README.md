### Objective

An Android app that fetches the data from iTunes API and shows a spotify player kind of layout

With added Unit Tests!

## 🏗️️ Built with ❤️ using Jetpack Compose 😁

| What            | How                        |
|----------------	|------------------------------	|
| 🎭 User Interface (Android)   | [Jetpack Compose](https://developer.android.com/jetpack/compose)                |
| 🏗 Architecture    | [Clean](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)                            |
| 💉 DI (Android)                | [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)                        |
| 🌊 Async            | [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)                |
| 🌐 Networking        | Retrofit                     |
| 📄 Parsing            | GSON

### Working

The App shows the top albums from the API and shows in a horizontal pager,
you can swipe the album art to switch to next album, You can also mark an album as favourite,
You can search the records and filter them from the search bar.
- Shows top 'n' albums based on the json feed here: `https://itunes.apple.com/us/rss/topalbums/limit=100/json`
- Spotify styled modern look with Palette API for Background Colors
- A good user experience
- Allow the local records to be searchable
- Allows to mark a album as a favourite
- Contains Unit Test Cases


License
=======
    Copyright 2022 Anmol Verma

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

