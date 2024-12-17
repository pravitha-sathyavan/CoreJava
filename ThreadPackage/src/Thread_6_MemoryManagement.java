package ThreadPackage.src;

public class Thread_6_MemoryManagement {
    /*
    There are two memory regions: Stack and Heap
    Each thread has a separate stack region.

    Stack: Memory region where methods are called, arguments are passed, local variables are stored
    Stack + Instruction Pointer = State of each Thread's execution

    Heap: Shared memory region common for a process. All thread share heap area
    All objects created with new operator, static variables, members of class

    Objects vs Reference

    Object r1 = new Object();
    Object r2 = r1;

    If references are declared as local variables inside a method, they are allocated in stack.
    if references are members of a class, then they can be allocated on heap

    Objects are always allocated on heap

    Synchronized:
        Used to restrict access to a critical section or entire method to a single thread at a time
    */

    public class SynchronizedExample {

        public synchronized void method1() {

        }

        public synchronized void method2() {

        }
    }
    /*
    In this example, if multiple threads are trying to execute the methods on the same object, then only one thread will be able to execute either of these methods.
    If thread A is executing method1, then thread B is not able to execute method1 and method2, bc the synchronized is applied per object. The term for that is monitor.
     */
}


