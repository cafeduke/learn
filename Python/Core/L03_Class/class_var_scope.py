class DemoVarScope:

  clsVar = 10

  def __init__(self):
    self.instVarA = 100
    print("[DemoVarScope] Inside init")

  def fun(self):
    clsVar = 9           # This shall create a new local variable of the function
    DemoVarScope.clsVar = 11 # This will modify the class variable (similar to static)
    instVarB = 99        # This shall create a new local variable of the function
    self.instVarB = 100  # This will modify the instance variable

  def __str__(self):
    return "[DemoVarScope classVar={} instVarA={} instVarB={}]".format(DemoVarScope.clsVar, self.instVarA, self.instVarB)

##
# Main
# -----------------------------------------------------------------------------
##
def main():    
  demo = DemoVarScope()
  demo.fun()  
  print(demo)
  
  DemoVarScope.clsVar = 12
  print(demo)

if __name__ == '__main__':
    main()  