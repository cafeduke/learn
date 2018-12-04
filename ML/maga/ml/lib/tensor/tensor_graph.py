import numpy as np
from functional import seq
from sklearn.datasets import make_blobs
import pyduke.common.core_util as util

class Operation:
    global _default_graph

    def __init__(self, input_nodes=[]):
        self.input_nodes  = input_nodes
        self.output_nodes = []
        for node in input_nodes:
            node.output_nodes.append(self)
        _default_graph.operations.append(self)

    def compute (self, x, y):
        pass

class add(Operation):
    def __init__(self, x, y):
        super().__init__(input_nodes=[x, y])

    def __str__(self):
        return "(+)"

    def compute(self, x, y):
        self.inputs = [x, y] # Why is this required?
        return x + y


class multiply(Operation):
    def __init__(self, x, y):
        super().__init__(input_nodes=[x, y])

    def __str__(self):
        return "(*)"        

    def compute(self, x, y):
        self.inputs = [x, y] # Why is this required?
        return x * y

class dot(Operation):
    def __init__(self, x, y):
        super().__init__(input_nodes=[x, y])

    def __str__(self):
        return "(.)"        

    def compute(self, x, y):
        self.inputs = [x, y] # Why is this required?
        return x.dot(y)

class Placeholder:
    global _default_graph

    def __init__(self, name=None):
        self.name = name
        self.output_nodes = []
        _default_graph.placeholders.append(self)

    def __str__(self):
        return "({0})".format(self.name)

class Variable:
    global _default_graph

    def __init__(self, initial_value=None):
        self.output_nodes = []
        self.initial_value = initial_value
        _default_graph.variables.append(self)

    def __str__(self):
        return "[{0}]".format(self.initial_value)        

class Graph:

    def __init__(self):
        self.operations = []
        self.placeholders = []
        self.variables = []

    def set_as_default(self):
        global _default_graph 
        _default_graph = self

class Session:

    def run (self, node, feed_dict={}):
        list_node = convert_expression(node)
        for curr in list_node:
            # Note: output was not a declared property of the object, its created dynamically!
            if isinstance(curr, Placeholder):
                curr.output = feed_dict[curr]
            elif isinstance(curr, Variable):
                curr.output = curr.initial_value
            else:
                list_operand = seq(curr.input_nodes).map(lambda x: x.output)
                curr.output = curr.compute(*list_operand)
        return np.array(node.output) if type(node.output) is list else node.output

class Sigmoid(Operation):

    def __init__(self, z):        
        super().__init__(input_nodes=[z])

    def compute (self, z):
        return 1 / (1 + np.exp(-z))

def convert_expression (node, list_nodes=None, to_postfix=True):

    # Mutable object as default argument does not work as exected. See https://stackoverflow.com/questions/1132941/least-astonishment-and-the-mutable-default-argument
    if list_nodes == None:
        list_nodes = []

    if not to_postfix:
        list_nodes.append(node)

    if isinstance(node, Operation):
        for curr in node.input_nodes:
            convert_expression (curr, list_nodes, to_postfix)

    if to_postfix:
        list_nodes.append(node)
    
    return list_nodes

def show_sigmoid_blob_dataset ():
    data = make_blobs(n_samples=50, n_features=2, centers=2, random_state=0)
    X = data[0]
    y = data[1]

def str_list (l):
    return seq(l).map(lambda x : str(x)).to_list()

def main ():
    global _default_graph
    
    # Create a simple graph for 'z = w.x + b'
    g = Graph()
    g.set_as_default()
    w = Variable(10)
    x = Placeholder('x')
    b = Variable(1)    
    z = add(multiply(w, x), b)

    # Get list of nodes that form the postfix expression, print each node
    print(seq(convert_expression(z)).map(lambda x: str(x)).to_list())

    # Run 'z' within a session by feeding placeholders with value.
    sess = Session()
    result = sess.run(z, feed_dict={x:4})
    print("Result=", result)

    # Quick Classfication
    g = Graph ()
    g.set_as_default()
    w = Variable(np.array([1, 1]).reshape(1, -1))   # An array of shape (2,1)
    x = Placeholder('x')
    b = Variable(-5)
    z = add(dot(w, x), b)
    a = Sigmoid(z)

    result = sess.run(a, feed_dict={x:np.array([8, 10]).reshape(-1, 1)})
    print ("Result=", result)
    
if __name__ == '__main__':
    main()


