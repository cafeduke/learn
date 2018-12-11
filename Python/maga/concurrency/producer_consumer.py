import threading
import time

def producer (lock, l, size, active=True):
    lock.acquire()
    for i in range(10):
        if len(l) == size:
            lock.release()
            time.sleep(1)



def main():
    lock = threading.Lock()
    

if __name__ == '__main__':
    main()