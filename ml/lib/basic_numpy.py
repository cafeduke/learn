import numpy as np

# Set the random seed
np.random.seed(0)

# Create a numpy array of given size with random integers
A = np.random.randint(0, 100, (3, 4))

# Fill values with Guassian distribution with zero mean, varience is 1
B = np.random.randn(3, 4)

# Evenly space 'num' numbers within limits
x = np.linspace(0, 100, num=5)
