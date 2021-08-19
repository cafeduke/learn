class Foo:
    def __init__(self, x, **y):
        self.x = x
        self.y = y
        print("[Foo] x={} y={}".format(self.x, self.y))

# The super class is not implicitly called   -- So, the sub class does NOT inherit x
# In Java, super class is implicityly called
class SubFooA(Foo):
    def __init__(self, x):
        self.x = x
        print("[SubFooA] x={}".format(self.x))


##
# Main
# --------------------------------------------------------------------------------
##
def main():
    print()
    print("--------------------------------------------------------------------------------")
    print("SubFooA with one arg")
    print("--------------------------------------------------------------------------------")
    SubFooA(5)

    print()
    print("--------------------------------------------------------------------------------")
    print("SubFooA with one arg")
    print("--------------------------------------------------------------------------------")
    SubFooA(5, z=True)


if __name__ == "__main__":
    main()
