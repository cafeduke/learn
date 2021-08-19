class DemoVarScope:

    clsVar = 10

    def __init__(self):
        self.instVarA = 100
        print("[DemoVarScope] Inside init")

    def fun(self):
        clsVar = 9                # This shall create a new local variable of the function
        DemoVarScope.clsVar = 11  # This will modify the class variable (similar to static)
        instVarB = 99             # This shall create a new local variable of the function
        self.instVarB = 100       # This will modify the instance variable

    def __str__(self):
        return "[DemoVarScope classVar={} instVarA={} instVarB={}]".format(DemoVarScope.clsVar, self.instVarA, self.instVarB)


##
# Main
# -----------------------------------------------------------------------------
# In python, the structure (skeleton/schema) of an object is not fixed! -- In java this is fixed by the instance/class variables defined.
# Think of an object as a map (key-value) pairs. Keys are instance and class variables -- Execute dir(<object>) to get the keys
# 
##
def main():
    d1 = DemoVarScope()
    d2 = DemoVarScope()
    d1.fun()
    d2.fun()
       
    print(d1, d2, sep='\n')
    # [DemoVarScope classVar=11 instVarA=100 instVarB=100]
    # [DemoVarScope classVar=11 instVarA=100 instVarB=100]


    # We are updating the clsVar (class scoped)
    DemoVarScope.clsVar = 12
    print(d1, d2, sep='\n')
    # [DemoVarScope classVar=12 instVarA=100 instVarB=100]
    # [DemoVarScope classVar=12 instVarA=100 instVarB=100]
    
    # Dynamically adding variables to objects
    # ---------------------------------------
    
    # We are just added a class variable 'myVar' on the fly!
    # Yes, class variables are accessible via object
    DemoVarScope.myVar="ThereYouGo"
    print(DemoVarScope.myVar, d1.myVar)
    # ThereYouGo ThereYouGo
    
    
    # Dynamically adding functions to objects
    # ---------------------------------------
    
    # Define a function
    def line (x):
        print("-" * x)

    # Prints a line
    line(3)
    # ---

    # Prints a line of length 6
    d1.myLine=line
    d1.myLine(6)
    # ------

    # This errors out as d2 knows nothing about myLine function
    # d2.myLine(6)
    
    # Modifying clsVar using object!!!
    # --------------------------------

    # We find that clsVar is accessible via objects as well as class -- Just like static var is accessible via objects in java (This usage is discouraged)   
    print(DemoVarScope.clsVar, d1.clsVar, d2.clsVar)
    # 12 12 12
    
    # We did NOT update class scoped clsVar, no Sir!
    # We created an instance variable called 'clsVar' for object 'd1' and set this to 13
    # Think of an object as a map (key-value) pairs. Keys are instance and class variables
    #
    # What happened here?
    #   - Earlier, d1.clsVar and d2.clsVar were both pointing to a shared object '12'
    #   - Now, d1.clsVar reference is pointing to its own object '13'
    d1.clsVar = 13
    print(DemoVarScope.clsVar, d1.clsVar, d2.clsVar)
    # 12 13 12
    
    # This was unintended? You can remove this d1.clsVar reference from the object
    # Now, d1.clsVar and d2.clsVar point to the shared object as before!!! -- What you heard is a java programmer fall off his chair
    del d1.clsVar
    print(DemoVarScope.clsVar, d1.clsVar, d2.clsVar)
    # 12 13 12


if __name__ == "__main__":
    main()
