Stock Price Service
-------------------------
 To start the service execute the below command

 $ java -jar ./stockPriceService.jar

 Close Price API
 ------------------

 curl --request GET \
   --url 'http://localhost:8080/api/v2/GE/closePrice?startDate=2017-04-05&endDate=2017-04-04' \
   --header 'cache-control: no-cache'

 200 day moving average
 -------------------------
 curl --request GET \
   --url 'http://localhost:8080/api/v2/GE/dma200?startDate=2017-04-05' \
   --header 'cache-control: no-cache'

 200 day moving average for 1000 Ticker Symbols
 --------------------------------------------------

 curl --request GET \
   --url 'http://localhost:8080/api/v2/GE,AAPL,NKE/dma200bulk?startDate=2017-04-05' \
   --header 'cache-control: no-cache'