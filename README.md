# KITABCHA REPORT

## Problem Definition:
Whenever reading a manga, you tend to forget where you had stopped reading the last time and where you now needed to continue from. This tends to happen more especially when you are reading more than one manga or catch up to a new manga and wait for new chapters to be released. Forgetting your progress, you often resort to using some form of indication to remember the last chapter you read through the use of bookmarks, notes or some other means, which can become a tedious and messy task as the number of mangas you are actively reading increases.

## Methodology:
Our mobile application, Kitabcha, allows multiple user profile creations. Once a user creates an account, their credentials are saved in the database for the next time they will login. After signing up or logging in, the users will be brought to their own personal library, unique for each user, where they will be able to create their own custom manga categories. Users can then browse our available collection of mangas, which are fetched from a selected source parsing using their json api or through html scraping depending on the site. The user can either receive all mangas available, or search for related mangas, using a search bar. 
Once a user selects a manga, the user is given the dialog box to select and add the manga in any of their existing categories. Now the user from their library can navigate between their categories in their library and select and open any manga they have added in it. Now on the manga screen, the user can read any of the chapters available for that manga. Once the user reads a specific chapter, the database will automatically save that chapter as read for that particular user. This read chapter will be highlighted on the manga screen next time to indicate that it has been read. This way automatic progress tracking of each user will be done for every manga in their library.

## Toolkit:
- Room Database library (for the backend database)
- Hilt Injection (to handle dependency injection for database and ui)
- Jetpack Compose with For UI Development
- Screen viewmodels to communicate between UI and database
- Coil library (To display images in thumbnails and reader)
- Json parsing library
- Jsoup for html scraping
- Okhtttp for network calls

## TestCases:
- Login/SignUp while any field is empty or both are empty (doesnot allow it)
- Creating a user with an already existing user (doesnot allow it)
- accessing a user account with wrong password  (doesnot allow it)
- Login user with correct username and password (allows it)
- Trying to add a manga with no existing category  (doesnot allow it)
- create any random categories
- Browse mangas with blank field (retrieves all)
- Browse mangas with a non empty field (retrieves relevant mangas)
- Add a manga in any selected category
- Delete a manga in any category
- Delete a category
- Open a manga in any category and read a chapter
- Open a previously read manga to see previous read chapters already read

## Work Distribution:
Mubashir Haroon:
Manga Fetching, manga Reader Development, and App optimization
Muhammad Mukees Khan:
Database development with small logic implementations in user authorization
Muhammad Ahmad:
User Login/Sign Up, Manga Browse, Manga Chapter Screen Logic And UI development
Tayyab-Ur-Rehman:
Library/ category Screen and Manga Screen  Logic and UI Development And Navigation Control handling
