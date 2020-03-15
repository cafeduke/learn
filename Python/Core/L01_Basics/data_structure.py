import numpy as np

# Show stuff
def show(ignore, l):
  print(l)

##
# -------------------------------------------------------------------------------------------------
# List
# -------------------------------------------------------------------------------------------------
# A list is the most common mutable (can be modified) collection of objects.
##
l = [10, "Hello", 3.14159, 20, "Bye"]

show(l.append("AddIt"), l)    # Add to the end (Stack push)
show(l.insert(3, "Ok"), l)    # Insert at index

show(l.pop()          , l)    # Remove and return last element (Stack pop)
show(l.pop(2)         , l)    # Remove at index
show(l.remove(20)     , l)    # Remove by object

l[2] = "World"
show(None, l)                 # Replace at index

##
# -------------------------------------------------------------------------------------------------
# Tuple
# -------------------------------------------------------------------------------------------------
# A tuple is a immutable (cannot be altered) collection of objects
##
t = (10, "Hello", 3.14159, 20, "Bye")

# 'tuple' object does not support item assignment
# t[0] = 20

##
# -------------------------------------------------------------------------------------------------
# Set
# -------------------------------------------------------------------------------------------------
# A set contains unique (no duplicates) collection of objects.
##
s1 = {10, "Hello", 3.14159, 20, "Bye"}
s2 = {20, 1.14, "Hello", "Greetings", "GetLost", 69}

show(s1.add(99), s1)          # 99 is added
show(s1.add(20), s1)          # 20 is NOT added again
show(s1.discard(99), s1)      # 99 is removed

show(None, s1.union(s2))