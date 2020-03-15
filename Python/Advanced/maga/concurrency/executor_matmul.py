from concurrent import futures
import numpy as np
from functional import seq
import pyduke.common.core_util as util
import maga.concurrency.thread_util as tutil


def sop(matA, matB, row, col):
    tutil.tlog("A={} B={}".format(row, col))
    assert matA.shape[1] == matB.shape[0], "Matrix cannot be multiplied. A={}, B={}".format(matA.shape, matB.shape)
    # Sleep for a small random interval to see the threads in play
    tutil.sleepMilli(np.random.randint(10))
    return np.sum(matA[row, :] * matB[:, col])


def matmul(A, B):
    # A list of tuples with (row, col) index pairs to be SOPed (Sum of Product)
    list_rc = []
    for i in range(A.shape[0]):
        for j in range(B.shape[1]):
            list_rc.append((i, j))

    with futures.ThreadPoolExecutor(max_workers=3, thread_name_prefix="t") as executor:
        # Submit each (row,col) pair. Gather the resultant future objects in a list
        list_future = seq(list_rc).map(lambda curr_rc: executor.submit(sop, A, B, *curr_rc)).to_list()
        # Wait for all the future to finish
        futures.wait(list_future)
        # The result of each future object is a cell in the target matrix
        list_result = seq(list_future).map(lambda f: f.result()).to_list()
        # Reshape result list to the shape of C
        C = np.reshape(list_result, newshape=(A.shape[0], B.shape[1]))
    return C


def main():
    A = np.random.randint(1, 6, size=(4, 3))
    B = np.random.randint(1, 6, size=(3, 5))

    util.heading("Matrix")
    print("A:\n", A)
    print("B:\n", B)
    C = matmul(A, B)
    print("C:\n", C)


if __name__ == "__main__":
    main()
