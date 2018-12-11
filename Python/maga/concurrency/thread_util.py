import threading
from datetime import datetime
from datetime import timezone

_TIME_FORMAT_A = '%a, %d-%b-%Y %H:%M:%S'
_TIME_FORMAT_B = '%Z'

def timestamp ():
    # Get time with timezone aware. By default datetime returns an object with blank timezone
    t = datetime.now(timezone.utc).astimezone()
    return "{}.{} {}".format(t.strftime(_TIME_FORMAT_A), int(t.microsecond/1000), t.strftime(_TIME_FORMAT_B))

def tlog (mesg):    
    return "[{}] [{}] {}".format(timestamp(), threading.current_thread().name, mesg)
