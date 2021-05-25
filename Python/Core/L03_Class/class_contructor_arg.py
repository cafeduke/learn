class Foo:
    def __init__(self, x):
        self.x = x
        print("[Foo] x={}".format(self.x))


# The super class is not implicitly called   -- So, the sub class does NOT inherit x
# In Java, super class is implicityly called
class SubFooA(Foo):
    def __init__(self):
        print("[SubFooA] x={}".format(self.x))


# The super class is not implicitly called   -- So, the sub class does NOT inherit x
# In Java, super class is implicityly called
class SubFooB(Foo):
    def __init__(self):
        super().__init__(5)
        print("[SubFooB] x={}".format(self.x))


##
# Main
# --------------------------------------------------------------------------------
##
def main():
    # Create an instance of SubFooA
    try:
        print("-----------------------------------------------------------------------")
        print("SubFooA")
        print("--------------------------------------------------------------------------------")
        SubFooA()
    except Exception as e:
        print("Exception occurred " + type(e).__name__)

    # Create an instance of SubFooB
    print()
    print("--------------------------------------------------------------------------------")
    print("SubFooB")
    print("--------------------------------------------------------------------------------")
    SubFooB()


if __name__ == "__main__":
    main()
