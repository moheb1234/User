

# needed information's for searching kline from database
class SearchInfo:

    def __init__(self , symbol , time_frame , stat_date , end_date ):
        self.symbol = symbol
        self.time_frame= time_frame
        self.start_date = stat_date
        self.end_date = end_date
        

