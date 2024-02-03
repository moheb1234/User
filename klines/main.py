from client import client
from kline.manager import kline_many_create


# get history candles
klines = client.get_historical_klines("BNBBTC", client.KLINE_INTERVAL_1DAY, "2024-01-01 00:00:00","2024-01-05 00:00:00")

# insert to database
result = kline_many_create("BNBBTC", client.KLINE_INTERVAL_1DAY, "2024-01-01 00:00:00","2024-01-05 00:00:00", *klines)
print(result)







