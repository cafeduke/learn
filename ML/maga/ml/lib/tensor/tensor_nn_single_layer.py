import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
from functional import seq
import maga.ml.lib.tensor.tensor_util as tu
import pyduke.common.core_util as util

util.heading("Single NN layer")

# Number of features
n = 10

# First layer
L1 = 3

# X shall be a column matrix with 'n' feature rows.
# Each column shall be an instance
X = tf.placeholder(tf.float32, shape=(n, None))

W = tf.Variable(initial_value=tf.random_normal(shape=(L1, n)))
b = tf.Variable(initial_value=tf.ones(shape=(L1)))
Z = tf.matmul(W, X) + b
A = tf.sigmoid(Z)
result = tu.eval(A, feed_dict={X: np.random.rand(n, 3)})

# Note that the activation values are between 0 and 1 since we use sigmoid function.
print("A = sigmoid (W.X + b)", "\n", result)

# -----------------------------------------------------------------------------
# Simple Regression
# -----------------------------------------------------------------------------

util.heading("Simple Regression")

# x and y are made of 10 numbers equally spaced b/w 0 and 10 with some noise added
x = np.linspace(0, 10, num=10) + np.random.uniform(low=-1.5, high=1.5, size=10)
y = np.linspace(0, 10, num=10) + np.random.uniform(low=-1.5, high=1.5, size=10)
plt.plot(x, y, 'b*')

# Create weight and bias with random values
rand = np.random.rand(2)
x = tf.Variable(initial_value=x)
y = tf.Variable(initial_value=y)
w = tf.Variable(initial_value=rand[0])
b = tf.Variable(initial_value=rand[1])

print("Initial w =", tu.eval(w), " Initial b =", tu.eval(b))

# Predicted value
y_hat = w*x + b
plt.plot(tu.eval(x), tu.eval(y_hat))

# Cost function -- Penalizes error in prediction
cost_function = (y_hat - y) ** 2

# Create an optimizer
optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.001)
train = optimizer.minimize(cost_function)

# Perform gradient descent
init = tf.global_variables_initializer()

with tf.Session() as sess:
    sess.run(init)
    step = 10
    for i in range(step):
        sess.run(train)
        curr_w, curr_b = sess.run([w, b])
        print("{0}) w={1} b={2}".format(i, curr_w, curr_b))
    curr_w, curr_b = sess.run([w, b])

y_pred = curr_w*x  + b

plt.plot(tu.eval(x), tu.eval(y_pred), 'g')
plt.show()
