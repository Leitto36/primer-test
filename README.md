# Primer Test App

## How to Test

1. Register at https://sandbox.braintreegateway.com/ 

2. Export your braintree Sandbox API keys as environment variables
```bash
	export MERCHANT_ID=<your_merchant_id>
	export PUBLIC_KEY=<your_public_key>
	export PRIVATE_KEY=<your_private_key>
```
3. Build & Run application

```bash
    ./gradlew :build
    ./gradlew :bootRun
```
Application will run on address http://localhost:8080

There is also swagger-ui to test in browser, can be found at http://localhost:8080/swagger-ui.html

4. Endpoints

- POST http://localhost:8080/card

Payload Request Example
```bash
{
  "merchantId": "c0c039e6-3b39-47c5-ad81-3dadaa801012",
  "cardHolderName": "John Doe",
  "number": "5555555555554444",
  "expirationMonth": "10",
  "expirationYear": "2024",
  "cvv": "532"
}
```

Payload Response

```bash
{
  "token": "bffnwjb"
}
```

- POST http://localhost:8080/transaction

Payload Request Example
```bash
{
  "merchantId": "c0c039e6-3b39-47c5-ad81-3dadaa801012",
  "amount": 100,
  "currency": "EUR",
  "type": "SALE",
  "token": "bffnwjb"
}
```

Payload Response

```bash
{
  "id": "6mgng5wy",
  "token": "bffnwjb",
  "amount": 100,
  "currency": "EUR",
  "type": "SALE",
  "createdAt": 1615449933000
}
```


