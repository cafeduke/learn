import numpy as np
from functional import seq

def show_title_data(title, data):
    '''
    Print the title, underline and print data
    
    Parameters
    ----------
    title : string
        Title shown as heading
    data : any object
        Display the string equivalent of the object    
    '''
    print()
    print(title)
    print("-" * len(title))
    print(data)

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


def to_list_of_str (l):
    return seq(l).map(lambda x : str(x)).to_list()
