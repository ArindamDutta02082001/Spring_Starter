# Wallet Service

- It performs 5 functions
1. consuming  the event from `transaction_initiated` topic and update the sender and receiver wallet
2. pushing the status of wallet updation in the wallet_updated topic
3. it will be consumed by the transaction-service to finally update the Status of the transaction as SUCCESS or FAILED
4. get the transaction history of a particular phone number by calling the transaction-service
5. get the wallet balance of a phone number

