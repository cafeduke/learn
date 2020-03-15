class Foo:
  def __init__(self, x):
    self.x = x
    print("[Foo] x={}".format(self.x))

# The super class is not implicitly called   -- So, the sub class does NOT inherit x
# In Java, super class is implicityly called
class SubFoo(Foo):
  def __init__(self):
    print("[SubFoo] x={}".format(self.x))

##
# Main
# --------------------------------------------------------------------------------
##
def main():    
  print("--------------------------------------------------------------------------------")
  print("Error: SubFoo has not inherited")
  print("--------------------------------------------------------------------------------")
  sf = SubFoo()


if __name__ == '__main__':
    main()      