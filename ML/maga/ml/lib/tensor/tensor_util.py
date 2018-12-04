import numpy as np
import tensorflow as tf

def eval (t, feed_dict=None):
    init = tf.global_variables_initializer()
    with tf.Session() as sess:
        init.run()
        return sess.run(t) if feed_dict == None else sess.run(t, feed_dict=feed_dict)


