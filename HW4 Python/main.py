# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
from binarysearchtree import BinarySearchTree
from binarysearchtree import Node


def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == "__main__":
    t1 = BinarySearchTree(name="Oak", root=Node())
    t1.add_all(5, 3, 9, 0)  # adds the elements in the order 5, 3, 9, and then 0
    print(t1)

    t2 = BinarySearchTree(name="Birch", root=Node())
    t2.add_all(1, -1, 0, 0, 7)
    print(t2)

    t3 = BinarySearchTree(name="Birch", root=Node())

    for x in t1.root:
        print(x)
    print(" ")
    for x in t2.root:
        print(x)


