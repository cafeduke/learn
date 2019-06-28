import numpy as np
import pandas as pd
import tensorflow as tf
import matplotlib.pyplot as plt

# Large dataset - A million points, equally spaced between 0 and 10
x = np.linspace(0, 10, num=1000000)
noise = np.random.randn(len(x))

# y = w * x + b + noise
# Here, w=0.5 and b=5. Noise is added so that detecting w,b by algo is not very easy
y = 0.5 * x + 5 + noise

# Now we have a million points as x and for each of those we have corresponding y along a line
# but a little off ( + or - ) due to noise. The regression alorithm should be able to detect the
# pattern and determine w and b. 
#
# Essentially, the regression alogrithm has found the weights and bias with with any further input
# (new x) can be fairly predicted (new y)
x_df = pd.DataFrame(x, columns=['X'])
y_df = pd.DataFrame(y, columns=['Y'])
xy_df = pd.concat([x_df, y_df], axis=1)

# Plot a sample x Vs y
xy_df.sample(n=250).plot(kind='scatter', x='X', y='Y')
plt.show()

batch_size = 8
(w,b) = np.random.rand(2)

x_batch     = tf.placeholder(tf.float32, [batch_size])
y_hat_batch = tf.placeholder(tf.float32, [batch_size])

y_batch      = w * x_batch + b
error_batch  = tf.reduce_sum(tf.square(y_hat_batch - y_batch))
optimizer    = tf.train.GradientDescentOptimizer(learning_rate=0.001)
train        = optimizer.minimize(error_batch)



