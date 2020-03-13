curl -X GET -i http://localhost:8080/fhbay/api/hello
curl -X GET -i http://localhost:8080/fhbay/api/customers
curl -X GET -i http://localhost:8080/fhbay/api/customers/1
curl -X POST -i http://localhost:8080/fhbay/api/customers/create -H "Content-Type: application/json" --data '{"email":"marge.simpson@burnspowerplant.com","firstName":"Marge","lastName":"Simpson","password":"Homer","username":"marge.simpson"}'
curl -X PUT -i http://localhost:8080/fhbay/api/customers/update/6 -H "Content-Type: application/json" --data '{"email":"marge.simpson@burnspowerplant.com","firstName":"Marge","id":6,"lastName":"Simpson","password":"ILoveHomerSimpson","username":"marge.simpson"}'
curl -X POST -i http://localhost:8080/fhbay/api/customers/init

