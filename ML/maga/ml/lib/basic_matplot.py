import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_blobs
import maga.ml.util as mu

##
# Basic Graph
# -----------
#  - Create 100 equally spaced values between -10 and 10
#  - Get the sigmoid and plot the graph
##
x = np.linspace(-10, 10, num=100)    
y = mu.sigmoid(x)
plt.plot(x, y)
plt.show()

##
# Basic Sklearn data
# ------------------
#  - Get the sklearn data from 'sklearn.datasets.make_blobs'
#  - Scatter plot one feature (X[0]) against the other
## 
data = make_blobs(n_samples=50, n_features=2, centers=2, random_state=75)
X = data[0]
y = data[1]
plt.scatter(X[:,0], X[:,1], c=y, cmap='coolwarm')

x_line = np.linspace(0, 11, num=10)
y_line = 5 - x_line
plt.plot(x_line, y_line)

plt.show()

