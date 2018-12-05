import pyduke.common.core_util as util
# Super does not necessarily refer to the parent class but the next class in the MRO (Method Resolution Order)

class A:
    def __init__(self):
        print("[A] Init".format(A.__name__))

    def fun(self):
        print("[{0}] Hello".format(A.__name__))


class B1(A):
    def __init__(self):
        print("[{0}] Before Super".format(B1.__name__))
        super().__init__()
        print("[{0}] Init".format(B1.__name__))

    def fun(self):
        print("[{0}] Hello".format(B1.__name__))


class B2(A):
    def __init__(self):
        print("[{0}] Before Super".format(B2.__name__))
        super().__init__()
        print("[{0}] Init".format(B2.__name__))


class C(B1, B2):
    def __init__(self):
        print("[{0}] Before Super".format(C.__name__))
        super().__init__()
        print("[{0}] Init".format(C.__name__))

    def have_fun(self):
        print("Using self")
        self.fun()

        print("Using B2")
        B2.fun(self)

# -----------------------------------------------------------------------------
# Main
# -----------------------------------------------------------------------------


util.heading("Order")
c = C()

util.heading("MRO -- Method resolution order")
print(C.__mro__)

util.heading("Diamond Inheritance")
c.fun()

util.heading("Resolving Diamond Inheritance")
c.have_fun()
