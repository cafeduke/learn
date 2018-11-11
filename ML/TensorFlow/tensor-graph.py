import numpy as np
from functional import seq

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
        assert type(x) == np.ndarray, 'Type mismatch. Type of x is {0}, expected type is numpy.ndarray'.format(type(x)) 
        assert type(y) == np.ndarray, 'Type mismatch. Type of y is {0}, expected type is numpy.ndarray'.format(type(x)) 
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
             curr.output = feed_dict [curr]
            elif isinstance(curr, Variable):
             curr.output = curr.initial_value
            else:
                list_operand = seq(curr.input_nodes).map(lambda x: x.output)
                curr.output = curr.compute(*list_operand)
        return np.array(node.output) if type(node.output) is list else node.output

def convert_expression (node, list_nodes=[], to_postfix=True):
    if not to_postfix:
        list_nodes.append(node)

    if isinstance(node, Operation):
        for curr in node.input_nodes:
            convert_expression (curr, list_nodes, to_postfix)

    if to_postfix:
        list_nodes.append(node)
    
    return list_nodes


def main ():
    g = Graph()
    g.set_as_default()
    A = Variable(10)
    b = Variable(1)
    x = Placeholder('x')
    z = add(multiply(A, x), b)
    print(seq(convert_expression(z)).map(lambda x: str(x)).to_list())

    sess = Session()
    result = sess.run(z, feed_dict={x:4})
    print(result)

if __name__ == '__main__':
    main()

