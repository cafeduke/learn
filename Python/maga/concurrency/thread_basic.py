import time
import threading
import pyduke.common.core_util as util

def tinfo ():
    t = threading.current_thread()
    print('[{0}] [{1}] Hello'.format(util.now(), t.name))
    time.sleep(1)

def main():
    for i in range(3):
        threading.Thread(target=tinfo).start()

if __name__ == '__main__':
    main()
