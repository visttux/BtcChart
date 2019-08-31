# BtcChart
This project is an example of an Android App that fetches Bitcoin Market Price using Clean Architecture.

# You'll find
* Multi-module project:
  * Presentation module, responsible of presentation (views & rendering using MVP) and injection graph
  * Domain module with the business logic & use cases
  * Data module responsible of fetching data consumed by the app
* Unit Tests in all clases with logic
* UI Tests with espresso & data mocked with Oktthps MockWebServer
* Rx for asynchronous work & mapping between layers
* Dagger2 for Dependency Injection
* Retrofit for API consumption
