import time
import threading
import numpy as np
from concurrent import futures
import maga.concurrency.thread_util as tutil
import pyduke.common.core_util as util
from functional import seq

def sop (matA, matB, row, col):
    tutil.tlog("A={} B={}".format(row,col))
    assert matA.shape[1] == matB.shape[0], "Matrix cannot be multiplied. A={}, B={}".format(matA.shape, matB.shape)
    tutil.sleepMilli(10)
    return np.sum(matA[row,:] * matB[:,col])

def matmul (A, B):
    # A list of tuples with (row, col) index pairs to be SOPed (Sum of Product)
    list_rc = []
    for i in range(A.shape[0]):
        for j in range(B.shape[1]):
            list_rc.append((i, j))

    with futures.ThreadPoolExecutor(max_workers=3, thread_name_prefix="t") as executor:
        list_future = seq(list_rc).map(lambda curr_rc: executor.submit(sop, A, B, *curr_rc)).to_list()
        futures.wait(list_future)
        list_result = seq(list_future).map(lambda f : f.result()).to_list()
        C = np.reshape(list_result, newshape=(A.shape[0], B.shape[1]))    
    return C

def main ():
    A = np.random.randint(1, 6, size=(4, 3))
    B = np.random.randint(1, 6, size=(3, 5))

    util.heading("Matrix")
    print("A:\n", A)
    print("B:\n", B)
    C = matmul(A, B)
    print("C:\n", C)
    
if __name__ == "__main__":
    main()
