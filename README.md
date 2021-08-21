# FPL Compose

An Alternative Android app for Fantasy Premier League. The main ideas we are exploring here:

- Using Jetpack Compose for UI and Compose Navigation
- Avoiding AppCompat
- No Dagger or Hilt. We will try manual dependency injection via LocalProvider and the composition tree.
- No `ViewModel` classes. We will create and handle our states