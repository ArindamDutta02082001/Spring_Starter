# Getting Started
architectutre diagram


// oauth providers 
/// github  fb , 

JWT Authentication
JWT - JSON WEB TOKEN

3 step process
1. id+pwd sent from the client ( angular / postmen) to server
2. server validates the credentials from stored DB using spring security . If validated JWT tokn sent to client in response
3. client will send the JWT token ( in the header ) from next time instead of id and pwd in his request

