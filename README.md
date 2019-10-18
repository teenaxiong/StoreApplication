# StoreApplication

This is an experiment working with new frameworks and language. 

Experiment with Node.js and Express as back-end.
Have mongoose as data storage.
Front-end is done in Android Studio, coding in Java and experimenting with Kotlin for the first time.
Application is pushed to Heroku. 

The application is an online store. User signs up for an account. The back-end will register user into the mongoose database. A JWT token will generated and will be used to identify each user. 
User will be able to shop for items. When user checks out, they will be automatically register for a braintree account. They will checkout through braintree services by using a creditcard. This works as follow:

1. User's JWT is sent to backend to be checked. If passed, it will generate a braintree account for the user if they don't have one already. If they already have one, a client-token will be generated in the backend. 

2. The client-token is sent to the front-end, where it will be used get the braintree Drop-in UI. This will allow user to click credit card and enter a creditcard. 

3. Once a credit card has been entered and verify, the total Cost and the generated paymentMethodNononce will be sent to the backend. It will create a transaction. If successful, user has completed a transaction. 
