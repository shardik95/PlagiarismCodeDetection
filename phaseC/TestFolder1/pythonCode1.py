class MyStack(Stack):
    def __init__(self, size):
        Stack.__init__(self)
        self.size = size
    def pushToStack(self, val):
        if len(self.stack) < self.size:
            Stack.push(self, val)
        else:
            print "Sorry, stack is full"
    def is_stack_empty(self):
        return len(self.stack) == 0
    def flush(self):
        self.stack = []
