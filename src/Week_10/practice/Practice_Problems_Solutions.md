# Week 10 - Practice Problems & Conceptual Solutions

This document provides complete solutions for the Week 10 Practice Problems, including architectural and complexity analysis for Part A (Coding Problems), answers and explanations for Part B (Quiz Questions Q1–Q10), and thorough answers for Part C (Concept Questions Q1–Q5).

---

## Part A: Coding Problems Summary

| Problem | Title | Key Concepts & Patterns | Time Complexity | Space Complexity |
| :--- | :--- | :--- | :--- | :--- |
| **Problem 1** | Smart Home Platform | Interface segregation (`Dimmable`, `Schedulable`, `EnergyMonitorable`), dynamic type dispatch (`instanceof`) | $O(1)$ per command | $O(N)$ for $N$ registered devices |
| **Problem 2** | Customer Registry | Equality contract (`equals()` & `hashCode()`), content equality, `HashSet` deduplication | $O(1)$ average per add/lookup | $O(N)$ for $N$ unique customers |
| **Problem 3** | Student Marks Grid | Ragged/jagged 2D array representation, null/missing value handling, grid row/column reduction | $O(S \times M)$ where $S$ is students, $M$ is subjects | $O(S \times M)$ |
| **Problem 4** | Product Price Finder | Binary Search lower bound and upper bound ($O(\log N)$ count query) | $O(\log N)$ query | $O(1)$ auxiliary |
| **Problem 5** | Library Platform | Sorted array invariant, binary search insertion and lookup, interface segregation (`Reservable`, `Downloadable`) | $O(N)$ insertion, $O(\log N)$ lookup | $O(N)$ |

---

## Part B: Quiz Answers & Explanations

### Q1. Consider a concrete class, an abstract type, and an interface. Which of the following statements are true regarding their characteristics based on their fundamental definitions? (Select all that apply)
- **Options**:
  - A. A concrete class can always be instantiated.
  - B. An abstract type can have constructors and hold state.
  - C. An interface can define per-object state and implement concrete operations.
  - D. A type with at least one abstract operation must be declared abstract.
  - E. An interface can define default operations.
- **Answer**: **A, B, D, E**
- **Explanation**:
  - **A is correct**: Concrete classes provide full implementations for all methods and can always be instantiated using `new`.
  - **B is correct**: Abstract classes can have instance variables (state) and constructors (invoked by subclass constructors via `super()`).
  - **D is correct**: If any method in a class is abstract (without a body), the containing class must be marked `abstract`.
  - **E is correct**: Since Java 8, interfaces can provide concrete default implementations using the `default` keyword.
  - *(C is incorrect because interfaces cannot declare per-object instance fields/state; all variables in an interface are implicitly `public static final` constants).*

---

### Q2. How is the relationship between types typically classified when one type represents a "family relationship sharing identity and state" and another represents a "capability shared by unrelated types"?
- **Options**:
  - A. The first is IS-A, the second is CAN-DO.
  - B. The first is CAN-DO, the second is IS-A.
  - C. Both are IS-A relationships.
  - D. Both are CAN-DO relationships.
- **Answer**: **A. The first is IS-A, the second is CAN-DO.**
- **Explanation**: Class inheritance (`extends`) represents an **IS-A** relationship sharing identity and state within an object hierarchy (e.g., `Light` IS-A `Device`). Interfaces (`implements`) represent a **CAN-DO** capability contract that can be implemented by entirely unrelated types (e.g., both a `Light` and a `Fan` CAN-DO `Schedulable`).

---

### Q3. Which of the following statements are true about the members of an interface? (Select all that apply)
- **Options**:
  - A. All operations declared in an interface are implicitly public.
  - B. An interface can define instance-specific state.
  - C. Operations in an interface are implicitly abstract unless they are declared as default, static, or private helper operations.
  - D. An interface can have constructors to initialize its state.
  - E. An interface can extend multiple other interfaces.
- **Answer**: **A, C, E**
- **Explanation**:
  - **A is correct**: All interface methods are implicitly `public`.
  - **C is correct**: Interface methods without a body are implicitly `abstract`.
  - **E is correct**: Java supports multiple inheritance for interfaces (e.g., `interface C extends A, B`).
  - *(B is incorrect because interfaces have no instance fields; D is incorrect because interfaces cannot have constructors).*

---

### Q4. What is the primary purpose of redefining an object's textual representation, such as by overriding a method like `toString()`?
- **Options**:
  - A. To enable shallow copying of the object.
  - B. To ensure proper hash code generation for collections.
  - C. To provide a meaningful self-description for logging and display.
  - D. To establish content equality between objects.
- **Answer**: **C. To provide a meaningful self-description for logging and display.**
- **Explanation**: Overriding `toString()` converts an object's internal state into a readable string representation, facilitating debugging, diagnostics, and human-readable UI display instead of printing default hash addresses like `ClassName@15db9742`.

---

### Q5. If a class redefines its `equals()` method to establish content equality, what critical consistency rule must also be followed for the class to behave correctly in hash-based collections?
- **Options**:
  - A. The toString() method must also be redefined.
  - B. All instances of the class must return the same hash value.
  - C. The hashCode() method must be redefined such that equal objects produce equal hash values.
  - D. The class must implement the Comparable interface.
- **Answer**: **C. The hashCode() method must be redefined such that equal objects produce equal hash values.**
- **Explanation**: The Java `equals`-`hashCode` contract dictates: if `a.equals(b)` is true, then `a.hashCode() == b.hashCode()` must be strictly true. Violating this rule causes hash-based collections (`HashSet`, `HashMap`) to look for equal objects in different bucket locations, resulting in duplicate entries or failed lookups.

---

### Q6. Consider an object `original` that contains a reference to another mutable object `nestedObject`. If a shallow copy of `original` is created, what is the consequence when `nestedObject` in the copied object is modified?
- **Options**:
  - A. The nestedObject in the original object remains unchanged.
  - B. A new nestedObject is automatically created for the original object.
  - C. The nestedObject in the original object will also reflect the modification.
  - D. The copy operation will fail due to shared mutable state.
- **Answer**: **C. The nestedObject in the original object will also reflect the modification.**
- **Explanation**: In a shallow copy, the outer object is duplicated, but references to nested objects are copied by value (copying the memory reference address). Both the original and cloned objects point to the exact same nested object in heap memory, so mutating the nested object through one instance affects both.

---

### Q7. In a fixed-size array, what is the typical time complexity for inserting or deleting an element in the middle of the array, assuming the elements need to be shifted?
- **Options**:
  - A. $O(1)$
  - B. $O(\log n)$
  - C. $O(n)$
  - D. $O(n \log n)$
- **Answer**: **C. $O(n)$**
- **Explanation**: Inserting or deleting an element at index $i$ in a linear contiguous array requires shifting all subsequent elements ($n - i - 1$ items) to open a slot or close a gap, which scales linearly as $O(n)$.

---

### Q8. What is the time complexity for accessing an element at a specific index in a fixed-size array?
- **Options**:
  - A. $O(1)$
  - B. $O(\log n)$
  - C. $O(n)$
  - D. $O(n^2)$
- **Answer**: **A. $O(1)$**
- **Explanation**: Arrays occupy contiguous memory blocks. Element memory addresses are calculated in constant time via pointer arithmetic: $\text{Address} = \text{BaseAddress} + (\text{index} \times \text{elementSize})$, providing true $O(1)$ random access.

---

### Q9. What is a mandatory precondition for a binary search algorithm to function correctly and efficiently on a data set?
- **Options**:
  - A. The data set must contain unique elements.
  - B. The data set must be sorted.
  - C. The data set must be stored in a linked list.
  - D. The data set must be entirely numeric.
- **Answer**: **B. The data set must be sorted.**
- **Explanation**: Binary search depends on monotonic ordering so that comparing the target with the midpoint unambiguously proves which half of the dataset can be discarded. Without sorted order, discarding a partition could eliminate the target.

---

### Q10. How does the worst-case time complexity of a linear search compare to that of a binary search for a collection of 'n' elements?
- **Options**:
  - A. Linear search is $O(\log n)$ and binary search is $O(n)$.
  - B. Both linear and binary search are $O(n)$.
  - C. Linear search is $O(n)$ and binary search is $O(\log n)$.
  - D. Both linear and binary search are $O(\log n)$.
- **Answer**: **C. Linear search is $O(n)$ and binary search is $O(\log n)$.**
- **Explanation**: Linear search checks elements sequentially, taking up to $n$ comparisons in the worst case ($O(n)$). Binary search halves the remaining search interval at each step, taking at most $\lceil\log_2 n\rceil + 1$ comparisons ($O(\log n)$).

---

## Part C: Concept Questions

### Question 1: Abstract Types vs. Interfaces
**Prompt**: Differentiate between an abstract type and an interface in an object-oriented programming context. Discuss their primary use cases and how they contribute to abstraction, particularly concerning state and method implementation.

**Answer**:
- **Abstract Classes**:
  - Represent an **IS-A** family relationship, capturing core shared identity and common state across closely related subclasses.
  - Can declare instance fields (state), constructors, concrete methods, and abstract methods with any visibility modifier (`protected`, `package-private`, `public`).
  - Subclasses are limited to single inheritance (`extends`).
  - *Primary Use Case*: Providing a reusable foundational skeleton with shared state and template methods (e.g., an abstract `Device` with `id` and `isOn` state).
- **Interfaces**:
  - Represent a **CAN-DO** contract defining capabilities that can be shared across entirely unrelated classes.
  - Cannot declare per-object instance state (only `public static final` constants) and cannot have constructors. Since Java 8/9, they can provide `default`, `static`, and `private` helper implementations.
  - Classes can implement multiple interfaces (`implements A, B, C`).
  - *Primary Use Case*: Decoupling behavior from concrete inheritance hierarchies (e.g., `Dimmable`, `Schedulable`, `Comparable`).

---

### Question 2: The Equality Contract and Hash-Based Collections
**Prompt**: Explain the 'equality contract' when overriding methods for object comparison. Specifically, describe the implications of redefining content equality without ensuring consistency with hash values, especially in the context of hash-based collections.

**Answer**:
The Java Equality Contract requires that when overriding `equals(Object o)`, it must satisfy five mathematical properties:
1. **Reflexive**: `x.equals(x)` is true.
2. **Symmetric**: `x.equals(y)` implies `y.equals(x)`.
3. **Transitive**: `x.equals(y)` and `y.equals(z)` imply `x.equals(z)`.
4. **Consistent**: Multiple invocations return consistent results unless object state was mutated.
5. **Non-null**: `x.equals(null)` is false.

**The Hash Consistency Rule**:
Whenever `equals()` is overridden, `hashCode()` **must** be overridden to ensure:
$$\text{If } a.\text{equals}(b) \implies a.\text{hashCode}() == b.\text{hashCode}()$$

**Implications of Inconsistency**:
Hash collections (`HashSet`, `HashMap`) determine storage buckets using `hashCode() % numBuckets`. If two equal objects produce different hash codes:
- They are placed into different buckets.
- A `HashSet` will accept duplicate entries of the same logical entity.
- A `map.get(key)` call using an equivalent object instance will look in the wrong bucket and return `null`.

---

### Question 3: Inner (Instance-Bound) vs. Static Nested Types
**Prompt**: Compare and contrast instance-bound (inner) nested types with independent (static) nested types. Explain scenarios where one would be preferred over the other, considering their access to outer class members and instantiation requirements.

**Answer**:
- **Instance-Bound (Non-static) Inner Classes**:
  - Maintain an implicit hidden reference to an enclosing instance of the outer class.
  - Can directly access all instance fields and methods of the outer class, including `private` members.
  - Cannot be instantiated without an existing outer instance (`outerInstance.new InnerClass()`).
  - *Preferred Scenario*: When the nested entity inherently belongs to the lifecycle of a specific enclosing object, such as custom iterators (`Iterator` implementation inside a custom collection) or UI event listeners.
- **Independent (Static) Nested Classes**:
  - Do not hold a reference to an outer class instance.
  - Cannot access instance variables or methods of the outer class directly; can only access `static` members.
  - Can be instantiated independently (`new OuterClass.StaticNestedClass()`), preventing memory leaks caused by unwanted retention of the outer instance.
  - *Preferred Scenario*: Helper records, builders, comparison strategies, or data transfer objects (DTOs) closely associated with the outer class namespace (e.g., `Problem1.Device`, `Problem2.Customer`, `Map.Entry`).

---

### Question 4: Performance of Insertions and Deletions in Fixed-Size Arrays
**Prompt**: Describe the performance characteristics, in terms of Big-O notation, for inserting and deleting an element in the middle of a fixed-size linear array. Explain why these operations might be costly and discuss potential strategies to mitigate this cost.

**Answer**:
- **Performance Characteristics**:
  - Insertion and deletion at index $k$ in an array of size $n$ has a time complexity of **$O(n)$** in both average and worst cases.
- **Why It Is Costly**:
  - Arrays are allocated as single contiguous blocks of memory to support $O(1)$ random indexing.
  - Inserting an element requires shifting $(n - k)$ elements to the right to create an empty slot.
  - Deleting an element requires shifting $(n - k - 1)$ elements to the left to close the contiguous gap.
- **Mitigation Strategies**:
  1. **Doubly Linked Lists**: Provide $O(1)$ insertion and deletion once the target node reference is known (eliminating memory shifting, at the expense of $O(n)$ sequential search).
  2. **Gap Buffers / Circular Buffers**: Useful in text editors or queues where insertions/deletions cluster around a floating cursor.
  3. **Balanced Search Trees / Skip Lists**: Provide $O(\log n)$ insertion, deletion, and lookup while maintaining order.
  4. **System-level Block Copies**: Using `System.arraycopy()` leverages native memory copy instructions, minimizing Java bytecode overhead.

---

### Question 5: Linear Search vs. Binary Search Trade-Offs
**Prompt**: Discuss the trade-offs between using a linear search and a binary search algorithm for finding elements in a data collection. Under what specific conditions would you choose one over the other, considering factors like data order, data size, and query frequency?

**Answer**:
- **Comparative Trade-offs**:
  - **Linear Search**: $O(n)$ time complexity, $O(1)$ auxiliary space. Operates on any linear collection (unsorted arrays, linked lists, streams) with zero preprocessing.
  - **Binary Search**: $O(\log n)$ time complexity, $O(1)$ space. Requires data to be **sorted** and stored in a structure with $O(1)$ random access (such as arrays or array lists).
- **Decision Criteria**:
  1. **Unsorted Data with Single or Rare Queries ($Q = 1$)**:
     - Choose **Linear Search**. Cost is $O(n)$.
     - Sorting first costs $O(n \log n)$ followed by $O(\log n)$ search, which is strictly slower than a direct linear scan.
  2. **Frequent Queries on Static Data ($Q \gg 1$)**:
     - Choose **Binary Search**. Sorting once costs $O(n \log n)$, after which every query takes only $O(\log n)$. For $10^6$ queries on $10^5$ items, binary search requires $\approx 1.7 \times 10^7$ operations versus $10^{11}$ operations for linear search.
  3. **Small Datasets ($n < 20$)**:
     - The difference between $O(n)$ and $O(\log n)$ is negligible ($\le 20$ CPU instructions). Linear search is often chosen for its simplicity and cache locality.
  4. **Linked Lists**:
     - Binary search cannot achieve $O(\log n)$ on linked lists because finding the midpoint requires $O(n)$ traversal. Linear search is preferred unless an auxiliary index or tree structure is built.
