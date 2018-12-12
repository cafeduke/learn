import time
import threading
from concurrent import futures
import maga.concurrency.thread_util as tutil
import pyduke.common.core_util as util

def do_something(zz):    
    tutil.tlog("Sleeping for {} milli".format(zz))
    time.sleep(zz/1000)
    tutil.tlog("Done sleeping")

def main():

    sleep_time = [10, 100, 150, 40, 50, 80, 80, 100, 10, 10, 10]

    util.heading("Thread Pool Executor - submit")
    with futures.ThreadPoolExecutor(max_workers=3, thread_name_prefix="t") as executor:
        list_future = []
        for i in range(len(sleep_time)):
            f = executor.submit(do_something, zz=sleep_time[i])
            list_future.append(f)    
        futures.wait(list_future)

    util.heading("Thread Pool Executor - map")
    with futures.ThreadPoolExecutor(max_workers=3, thread_name_prefix="t") as executor:
        list_result = executor.map(do_something, sleep_time)
        for curr in list_result:
            print ("")


if __name__ == '__main__':
    main()
