from datetime import datetime
import re

class BirthCert:

  listGender = ["male", "female", "m", "f"]

  def __init__(self, gender, yob):
    assert gender.lower() in BirthCert.listGender
    self.gender = gender.lower()[:1]
    self.yob = yob

  @classmethod
  def getInstanceByAge (cls, gender, age):
    return cls(gender, datetime.now().year - age)

  def __str__(self):
    return "[BirthCert Gender={} Year={}]".format("male" if self.gender == "m" else "female", self.yob)    

##
# Main
# -----------------------------------------------------------------------------
##
def main():    
  certA = BirthCert("male", 1981)
  certB = BirthCert.getInstanceByAge("F", 39)
  print (certA)
  print (certB)

if __name__ == '__main__':
    main()  