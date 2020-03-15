''' A simple module '''

mesg = "Hello World"
l = [10, "Apple", 3.14159, "Ball", 31, "Cat"]
num = [3, 5, 10, 2, 3.14159]

def greet(str="Raghu"):
  print("Hello {}, Inside fun".format(str))

class Wheel:
  def __init__ (self, size, color):
    self.size = size
    self.color = color

  def spin(self, count=1):
    for x in range(count):
      print("Taking wheel {} for a spin".format(self))  

  @staticmethod
  def info (count=1):
    for x in range(count):
      print("Wheel was the biggest invention of all times!")

  def __str__(self):
    return "Wheel Size={} Color={}".format(self.size, self.color)
