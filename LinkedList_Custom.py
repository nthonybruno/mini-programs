import random

class Node:
    def __init__(self,data):
        self.data = data
        self.next = None

class LinkedList:
    def __init__(self):
        self.head = None

    def represent(self):
        node = self.head
        nodes = []
        while node is not None:
            nodes.append(node.data)
            node = node.next
        nodes.append(None)
        return "->".join(str(a) for a in nodes)

    def append(self, new_data):
        append_node = Node(new_data)

        if self.head is None:
            self.head = append_node
            return

        temp_node = self.head
        while temp_node.next:
            temp_node = temp_node.next
        temp_node.next = append_node
        return

    def push(self, new_data):
        New_node = Node(new_data)

        if self.head is None:
            self.head = New_node
            return
        
        New_node.next = self.head
        self.head = New_node
        return

    def insert(self, new_data, insert_node):
        New_node = Node(new_data)
        
        if self.head is None:
            self.head = New_node
            return
        
        current_node = self.head
        while current_node != insert_node:
            current_node = current_node.next
        
        next_node = current_node.next
        current_node.next = New_node
        New_node.next = next_node
        return

if __name__ == "__main__":
    # Playground code for testing LinkedList.py
    LL = LinkedList
    LL.head = Node(random.randint(0,100))
    last_node = LL.head

    Node_to_test_insert = Node(random.randint(0,100))

    for x in range(0,20):
        if x == 10:
            last_node.next = Node_to_test_insert
            last_node = Node_to_test_insert
        else:
            temp_node = Node(random.randint(0,100))
            last_node.next = temp_node
            last_node = temp_node

    # Test append
    print("Testing Append")
    print(LinkedList.represent(LL))
    LL.append(LL, random.randint(0,100))
    print(LinkedList.represent(LL))
    print("---------------------------")
    
    #Test push
    print("Testing Push")
    print(LinkedList.represent(LL))
    LL.push(LL,random.randint(0,100))
    print(LinkedList.represent(LL))
    print("---------------------------")

    #Test insert
    print("Testing Insert")
    print(LinkedList.represent(LL))
    LL.insert(LL,random.randint(0,100), Node_to_test_insert)
    print(LinkedList.represent(LL))