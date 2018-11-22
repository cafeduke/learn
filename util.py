def heading (mesg, ch='-', len=80):
    '''
    Highlight message `mesg` between lines formed by char `ch`
    
    Parameters
    ----------
    mesg : string, required
        Message

    ch : str, optional
        char the line is made up of (the default is '-')

    len : int, optional
        Length of the line (the default is 80)    
    '''
    print ()
    print (ch * len)
    print (mesg)
    print (ch * len)
