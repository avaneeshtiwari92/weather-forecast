### Weather Forecast

**External APIs**
1. For getting the Lat/Long from Zip Code.(zip code base) 
2. For getting the forecast for given lat/long.(open weather map)

**Flow**
1. User will call the `api/v1/weather_forecasts?zip=<zip-code>` API.
2. If the data exist in `Redis`, response will be returned and fulfilled by Cache.
3. If the data doesn't exist in `Redis`,
   1. Get the Lat/Long from the external API.
   2. Call the forecast API to get the external forecast.
   3. Set the value in `Redis` and return the response.
4. The `fromCache` attribute will tell if the response is coming from Cache OR not.

**Future Enhancements**
1. Cache/Store Data(Lat/Long) in the DB OR Cache. So overtime we don't need to call the Lat/Long API.
2. The open weather map APIs currently supports the hourly forecast for Free tier users. We can forecast for more Days and Hours using paid APIs.
3. More unit test can be added.
4. Docker builds can be added to make the local testing easy.

**Sample Request:**
`http://localhost:8080/api/v1/weather_forecasts?zip=560069`

**Sample Response from Cache:**
```
{
"zipCode": "560069",
"maxTemp": 31.79,
"minTemp": 30.47,
"latitude": 12.9407049,
"longitude": 77.58782216,
"currTemp": 30.96,
"fromCache": true
}
```

**Sample Response without using Cache:**
```
{
    "zipCode": "560069",
    "maxTemp": 31.79,
    "minTemp": 30.47,
    "latitude": 12.9407049,
    "longitude": 77.58782216,
    "currTemp": 30.96,
    "fromCache": false
}
```
