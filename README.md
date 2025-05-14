# A to Be Product app
## Design and Architecture

For the architecture, there is a 3 layer one, with the local for local database, the remote for api requests, and the domain which is for the application in general

To "transform" this data and have a Single source of truth there is a Repository, so it has the logic to send data from the localDataSource (this one returns data from the DAO) or the remoteDataSource (data from API request).

MVVM approach as Design pattern.

## Features

For this app we have a main screen and a details screen. 

### Main Screen

The main screen (Home in code) consists in a list of the products. The way I implemented it was, to always check first if there are some information in the database, if there is not then it searches on the API

For that was used **ROOM** and **Retrofit**. To achieve the expected results with pagination and limit of the products per page there is a TotalEntity (being the table Total) to save the total results locally (in case the app restarts and there is local data), on the API there is only a ProductCollection in which uses the Total as a member of the data class (List of products and total)

### Details View

The details view, also has a list this one when it's called has a fallback if it cannot find the data locally, it searches on the API. 
Uses the same Object as the main screen, but shows a few more fields, also shows the picture using Coil (for async image request).

