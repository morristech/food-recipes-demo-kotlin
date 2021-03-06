<h1 align="center">Recipes Search Demo</h1>
<p>Demo app for testing RestApi Calls.</p>
<p>A food recipe search app with retrofit, livedata and using android best practices. The app is currently not being developed.
 The UI was developed as far as enough for displaying the results.</p>
<img src="recipes-kotlin-mobile-mock.jpg" heigth="150px"/>

# Libraries Used
  <p><b>Lifecycles</b>- Create a UI that automatically responds to lifecycle events.</p>
  <p><b>LiveData</b> - Build data objects that notify views when the underlying database changes.</p>
  <p><b>Room</b> - Caching the network data for offline use.</p> 
  <p><b>ViewModel</b> - Persist the data on configuration changes.</p>
  <p><b>Glide</b> - Image Loading.</p>
  <p><b>Retrofit</b> - Http Requests.</p>
  <p><b>Gson</b> - Convert Json objects.</p>
  <p><b>Circle Imageview</b> - Display images in a circle.</p>
  <p><b>RecyclerView </b> - Display list and handle clicks.</p>
  <br>
  
# Non-Goals
  <p>The focus of this project was on HTTP requests and caching the data for offline use using the MVVM pattern.</p>
  <p>The UI only was worked till the point were we could get and display the results from the requests.</p>
  <br>
  
# App architecture
  <p>The app was developed taking in account the best practices and recommended architecture from Google for building apps.</p>
  <p>It uses the repository pattern and the single source of truth. Each component depends only on the component one level below it. For example, activities depend only on a view model. The repository is the only class that depends on multiple other classes.</p>
  <p align="center">
    <img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" height="400px">
  </p>
  
# Screenshots
<p>
<img src="recipes-kotlin-mobile.png" width="250px"/>
<img src="recipes-kotlin-mobile1.png" width="250px"/>
<img src="recipes-kotlin-mobile2.png" width="250px"/>
</p>


