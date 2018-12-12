import threading
from datetime import datetime
from datetime import timezone

def timestamp():
    formatA = "%a, %d-%b-%Y %H:%M:%S"
    formatB = "%Z"
    t = datetime.now().astimezone()
    milli   = int(t.microsecond/1000)
    return "{}.{:03d} {}".format(datetime.strftime(t, formatA), milli, datetime.strftime(t, formatB))

def tlog(mesg):
    tname = threading.current_thread().getName()
    record = "[{}][{}] {}".format(timestamp(), tname, mesg)
    print(record)

if __name__ == "__main__":
    tlog("Hello")
