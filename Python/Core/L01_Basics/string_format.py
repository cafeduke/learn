# The place holder '{}' is replaced by parameters passed to the format function one after the other.
strSimple = "My name is {}, I am {} years old. I have always been called {}, but I won't be {} forever!"
print(strSimple.format("Raghu", 38, "Raghu", 38))

# The place holder {<n>} is replaced with the nth parameter.
strPositional = "My name is {0}, I am {1} years old. I have always been called {0}, but I won't be {1} forever!"
print(strPositional.format("Raghu", 38))

# The place holder {<name>} is replaced with the 
strNamed = "My name is {name}, I am {age} years old. I have always been called {name}, but I won't be {age} forever!"
print(strNamed.format(name="Raghu", age=38))
print(strNamed.format(age=41, name="Madhu"))