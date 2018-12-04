##
# Static Method -- Annotated with @staticmethod
# ---------------------------------------------
#   - A static method is just another method in the namespace of the class. (Similar to Java static method)
#   - A static method cannot access instance variables
#
# Class Method -- Annotated with @classmethod
# -------------------------------------------
#   - A class method receives 'cls' or the 'class' as first argument
#   - A class method is accessed using a class and is supposed to return an instance of a class!
#   - In Java terms, a class method is a static method that returns an instance of the class.
##

import pyduke.common.core_util as util

class A:

    var_class = "Class Variable"

    def __init__(self, x=None):
        self.var_instance = 'Instance Variable'
        self.x = x

    def method_instance(self):
        print("An instance method -- With 'self' as first arg")
        print("An instance method -- Can access both {0} and {1}".format(self.var_instance, A.var_class))

    @staticmethod
    def method_static(x):
        print("A static method -- Annotated with @staticmethod")        
        print("A static method -- Can access only {0}".format(A.var_class))

    @classmethod
    def method_class(cls, x):
        print("A class method -- With 'cls' as first arg")
        return cls(x=x)

    def __str__(self):
        return "x=" + str(self.x)

# -----------------------------------------------------------------------------
# Main
# -----------------------------------------------------------------------------

a = A()

# Invoke instnace method, style-1
a.method_instance()

# Invoke instnace method, style-2
A.method_instance(a)

# Static method
# -------------
util.heading("Static Method")
A.method_static(3)
print ("")

# Class method
# -------------
util.heading("Class Method")
obj = A.method_class(5)
print("Object returned by class method Type:{0} Str:{1}".format(type(obj), str(obj)))
