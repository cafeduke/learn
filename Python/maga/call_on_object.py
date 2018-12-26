##
# The __call__() method of a class is internally invoked when an object is called, just like a function.
#
#    obj(param1, param2, param3, ..., paramN)
#
# The parameters are passed to the __call__ method!
#
# Connecting calls
# ----------------
# Like anyother method __call__ can take any number of arguments and return an object of any type.
# If the object returned by __call__, is of a class that has __call__ as well, then the next set of parameters will be passed to this __call__ method and so on.
##
class A:
    def __call__(self, x):
        return B(x)

class B:
    def __init__(self, x):
        self.x = x

    def __call__(self):
        return C()

class C:
    pass

##
# Main
# -----------------------------------------------------------------------------
##
def main():
    a = A()
    b = a('Object arg to create instance of B')
    c = b()
    print("Type :", type(c))

    # Short cut for the same thing above
    print("Type :", A()('Arg')())

if __name__ == '__main__':
    main()
