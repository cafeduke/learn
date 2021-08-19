class A1:
    def fun(self):
        self.disp()
        print("Inside A1.fun varClass={} varInst={}".format(self.varClass, self.varInst))


class A2:
    def fun(self):
        self.disp()
        print("Inside A2.fun varClass={} varInst={}".format(self.varClass, self.varInst))


class B(A1, A2):
    varClass = 3

    def __init__(self):
        self.varInst = 3
        print("Inside B.__init__ varClass={} varInst={}".format(self.varClass, self.varInst))

    def disp(self):
        print("Inside B.disp varClass={} varInst={}".format(self.varClass, self.varInst))

##
# Main
# --------------------------------------------------------------------------------
##


def main():
    B().fun()


if __name__ == "__main__":
    main()
