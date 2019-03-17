# Popular Movies

This repo covers both Stage 1, Stage 2 of Popular Movies project.

This project continued here.
https://github.com/sayaMahi/Popular-Movies

## Creating API Key:

Create API Key (v3 auth) at https://www.themoviedb.org/ -> Settings -> API -> Create.

Enter API-KEY, in gradle.properties file, and use the variable in build.gradle (Module: app).

https://www.androidcitizen.com/hiding-api-keys/


## Libraries Used:

- DataBinding

- Retrofit 2 with Gson Converter

- Gson

- Stetho

- Glide / Picaso


## Commits:

Enter 'git log --graph --oneline` to get actual list of commits.

Enter 'git checkout <####commit_code>' to go to particular version

#### This project continued in a different repo

https://github.com/sayaMahi/Popular-Movies

I forgot to clean the project somewhere in below commits and file size got big, so moved to new repo.

#### commit 9591548 

 - movie trailers

#### commit 4a566de 

 - movie reviews

#### commit ede3959

- Fav Items to database - query/insert/delete, few fixes, code cleaning.

#### commit 93e4bce 

- added Contract, dbHelper, Content Provider

#### commit c5ac0c74

- Updated Singleton class (MovieInterface.java).

	- https://medium.com/exploring-code/how-to-make-the-perfect-singleton-de6b951dfdb0

#### commit 271eebb2

- Added AsyncLoader with Retrofit (Sync Call).

#### commit 0162f80  

- Network Debugging Added.

	- https://www.androidcitizen.com/retrofit-2-and-network-logging/

#### commit 8ad1a86

- Retrofit Added (Async Call).

#### commit 0f83eff 

- Stage 1 *

#### commit bfaabc2 

- Added Data Binding (Basic).

#### commit 4475adc 

- Added continuous scrolling.

#### commit b15678a

- AsyncTask is changed to AsyncLoader.
- Added Filter options to select Top-Rated/Popular.

#### commit 3288f3e 

- Gson lib for JSON Parsing.
- Parcelable for sending object data b/n activities.

#### commit 0eca0da

- Updated Movie Details Activity.(AsyncTask)
- Included, Scrollview for Image.

#### commit 1e3f8ed5a32b2e94df1d196a95aa565828ec123b 

- Have used AsyncTask for fetching movie data from imdb api.
