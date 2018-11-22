import numpy as np
from functional import seq

def sigmoid (z):
    '''
    Sigmoid function
    
    Parameters
    ----------
    z : numpy array
    
    Returns
    -------
    numpy array
        sigmoid of z
    '''
    return 1 / (1 + np.exp(-z))


def to_list (l):
    return seq(l).map(lambda x : str(x)).to_list()