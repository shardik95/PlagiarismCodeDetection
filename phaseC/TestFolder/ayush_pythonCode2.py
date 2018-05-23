class MyStack(Stack):
    def __init__(self, size):
        Stack.__init__(self)
        self.size = size
    def push(self, val):
        if len(self.stack) < self.size:
            Stack.push(self, val)
        else:
            print "Sorry, stack is full"
    def peek(self):
        temp = self.pop()
        self.push(temp)
        return temp
    def is_empty(self):
        return len(self.stack) == 0
    def flush(self):
        self.stack = []

