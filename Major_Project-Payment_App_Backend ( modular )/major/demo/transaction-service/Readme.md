# Transaction Service

- It performs 4 functions 
1. initiating the transaction between 2 users and putting the Txn into the `transaction_initiated` topic to be consumed by the wallet service and update the users wallet
2. consuming the event from the `wallet_updated` topic and update the state of the transaction as SUCCESS of FAILED
3. getting all the transaction list of a phone number
4. loading the sender details through loadUserByUserName , and see if he/she is a registered or not

**transaction-complete-and-notify** is a topic where the transaction is placed

