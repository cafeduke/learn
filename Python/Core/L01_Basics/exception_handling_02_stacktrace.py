import traceback


def foo(a, b):
    bar(a, b)


def bar(a, b):
    a / b


try:
    a = 5
    b = 0
    print("About to do something silly")
    foo(a, b)
    print("Did something silly")

# Here 'e' is an object that will have the additional
except Exception as e:
    print("Exception name :" + type(e).__name__)
    print("Stacktrace:")
    print("-----------")
    traceback.print_exc()
