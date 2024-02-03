from datetime import datetime



class Kline():

    def __init__(self , search_info , *kline_data ): # data args are kline data from response of binance api . search_info used for database searching 
        self.open_time = datetime.fromtimestamp(kline_data[0]/1000)
        self.open_price = kline_data[1]
        self.high_price = kline_data[2]
        self.low_price = kline_data[3]
        self.close_price = kline_data[4]
        self.close_time = datetime.fromtimestamp(kline_data[6]/1000)
        self.search_info = search_info
    

    def to_dict(self):
        kline_dict = self.__dict__
        kline_dict['search_info'] = self.search_info.__dict__
        return kline_dict

        
