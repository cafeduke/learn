from datetime import datetime
import threading

def timestamp():
    formatA = "%a, %d-%b-%Y %H:%M:%S"
    formatB = "%z"
    t = datetime.now().astimezone()
    milli   = int(t.microsecond/1000)
    return "{}.{:03d} {}".format(datetime.strftime(t, formatA), milli, datetime.strftime(t, formatB))

def tlog(mesg):
    tname = threading.current_thread().getName()
    record = "[{}][{}] {}".format(timestamp(), tname, mesg)
    print(record)

if __name__ == "__main__":
    tlog("Hello")
