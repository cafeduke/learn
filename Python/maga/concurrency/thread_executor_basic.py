import time
import threading
import numpy as np
from functional import seq
from concurrent import futures
import pyduke.common.core_util as util
import maga.concurrency.thread_util as tutil
import pyduke.common.core_util as util

def do_something(zz, mesg=None):    
    mesg = "" if mesg is None else " Mesg=" + mesg
    tutil.tlog("Sleeping for {} milli.{}".format(zz, mesg))
    tutil.sleepMilli(zz)
    tutil.tlog("Done sleeping")

def main ():

    sleep_time = [10, 100, 150, 40, 50, 80, 80, 100, 10, 10, 10]
    mesg = ['X', 'C', 'CL', 'XL', 'L', 'XXC', 'XXC', 'C', 'X', 'X', 'X']
    
    util.heading("Thread Pool Executor - submit")
    with futures.ThreadPoolExecutor(max_workers=3, thread_name_prefix="t") as executor:
        list_future = seq(range(len(sleep_time))).map(lambda i : executor.submit(do_something, zz=sleep_time[i])).to_list()
        futures.wait(list_future)

    util.heading("Thread Pool Executor - map")
    with futures.ThreadPoolExecutor(max_workers=3, thread_name_prefix="t") as executor:
        executor.map(do_something, sleep_time, mesg)        

if __name__ == '__main__':
    main()
