import re

class Animal:
  def __init__(self):
    self.varAnimal = 10
    print("[Animal] Inside Init")

  def show(self):
    print("[Animal] varAnimal={}".format(self.varAnimal))

class Cow(Animal):
  def __init__(self):
    print("[Cow] Inside Init")

class Elephant(Animal):
  def __init__(self):
    super().__init__()
    print("[Elephant] Inside Init")

class JersyCow(Cow,Elephant):
  def __init__(self):
    super().__init__()


##
# Main
# -----------------------------------------------------------------------------
##
def main():
  print("-------------------------------------------------------------------------------")
  a = Animal()

  print("-------------------------------------------------------------------------------")
  print("Cow: No call to base constructor")
  print("-------------------------------------------------------------------------------")
  c = Cow()
  # c.show() -- ERROR: AttributeError: 'Cow' object has no attribute 'varAnimal'

  print("-------------------------------------------------------------------------------")
  print("Elephant: Explictly call base constructor")
  print("-------------------------------------------------------------------------------")
  e = Elephant()
  e.show()

  print("-------------------------------------------------------------------------------")
  print("JersyCow: God bless America")
  print("-------------------------------------------------------------------------------")
  jc = JersyCow()
  print(JersyCow.mro())

if __name__ == '__main__':
    main()