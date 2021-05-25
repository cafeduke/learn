try:
    a = 5
    b = 0
    print("About to do something silly")
    a / b
    print("Did something silly")

# Here 'e' is an object that will have the additional
except Exception as e:
    print("Following exception occurred :" + type(e).__name__)
    print("Exception Arguments Type :" + type(e.args).__name__)
    print("Exception Arguments      :" + str(e.args))

print("Done handling exception")
