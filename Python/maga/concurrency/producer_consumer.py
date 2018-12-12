import maga.concurrency.thread_util as tutil
import threading
from queue import Queue

def producer(q, milli=None):
    for i in range(10):
        mesg = "Item #{:03d}".format(i+1) 
        q.put(mesg)
        tutil.tlog(mesg)
        tutil.sleepMilli(milli)

def consumer(q, milli=None):
    for i in range(10):
        mesg = q.get()
        tutil.tlog(mesg)
        tutil.sleepMilli(milli)

def main():
    q = Queue(maxsize=3)
    tutil.tlog("Main started")
    threading.Thread(target=producer, name="tMaker", args=[q, 100]).start()
    threading.Thread(target=consumer, name="tTaker", args=[q, 300]).start()
    tutil.tlog("Main exited")

if __name__ == "__main__":
    main()    