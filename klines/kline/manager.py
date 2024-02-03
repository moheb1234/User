from database import kline_collection
from .search_info import SearchInfo
from .kline import Kline

def kline_create(symbol, time_frame , start_date, end_date ,*kline_data):
    search_info = SearchInfo(symbol , time_frame , start_date , end_date )
    kline =  Kline(search_info , *kline_data)
    return kline



def kline_many_create(symbol, time_frame , start_date, end_date ,*kline_data_list): #return true if all klines insert to database else false
    if not kline_data_list:
        return False , 'no kline founded'
    
    klines = []
    for  kline_data in kline_data_list:
        kline = kline_create(symbol,time_frame,start_date,end_date,*kline_data)
        klines.append(kline.to_dict())
       

    result =  kline_collection.insert_many(klines)
    if result.inserted_ids:
        return True
    return False , 'database is unavailable'