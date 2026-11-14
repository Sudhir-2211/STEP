# Week 9 - Data Structures & Algorithms Practice Problems Solutions

This document provides complete solutions for the Week 9 Practice Problems, including detailed complexity analyses for the coding problems, answers and explanations for the Part B Quiz questions, and comprehensive answers for the Part C Concept questions.

---

## Part A: Coding Problems Summary

| Problem | Title | Technique | Time Complexity | Space Complexity |
| :--- | :--- | :--- | :--- | :--- |
| **Problem 1** | Pair Sum in a Sorted Array | Two Pointers (left & right) | $O(n)$ | $O(1)$ |
| **Problem 2** | Warehouse Bin Grid Scan | 2D Array Traversal | $O(m \times n)$ | $O(1)$ |
| **Problem 3** | Library Catalog Lookup | Binary Search | $O(\log n)$ | $O(1)$ |
| **Problem 4** | Library Catalog Lookup (Iterative/Recursive) | Binary Search | $O(\log n)$ | $O(1)$ iterative / $O(\log n)$ recursive |
| **Problem 5** | Maximum Sum Subarray of Fixed Size K | Sliding Window | $O(n)$ | $O(1)$ |

---

## Part B: Quiz Answers & Explanations

### Q1. What is the primary advantage of the two-pointer technique for finding a pair sum in a sorted array compared to a naive nested-loop approach?
- **Options**:
  - A. It converts an $O(n^2)$ time complexity to $O(\log n)$ time complexity.
  - B. It reduces an $O(n^2)$ time complexity to $O(n)$ time complexity while using $O(1)$ additional space.
  - C. It requires $O(n)$ additional space for a lookup table.
  - D. It only works on unsorted arrays, so sorting is an additional cost.
- **Answer**: **B. It reduces an $O(n^2)$ time complexity to $O(n)$ time complexity while using $O(1)$ additional space.**
- **Explanation**: The two-pointer technique starts with one pointer at index 0 and another at $n - 1$. By inspecting the sum and advancing inward, each step eliminates one value, examining at most $n$ elements in $O(n)$ time without allocating extra data structures ($O(1)$ auxiliary memory).

---

### Q2. Which common data structure operation involves systematically visiting every element, typically for reading or processing purposes?
- **Options**:
  - A. Insertion
  - B. Deletion
  - C. Traversal
  - D. Updating
- **Answer**: **C. Traversal**
- **Explanation**: Traversal is defined as systematically visiting each element in a collection or data structure (e.g., array, linked list, tree, or grid) exactly once to perform an operation such as reading, searching, or printing.

---

### Q3. After a prefix sum array is built from an original array, what is the time complexity to calculate the sum of any contiguous range of elements?
- **Options**:
  - A. $O(n)$
  - B. $O(\log n)$
  - C. $O(1)$
  - D. $O(n^2)$
- **Answer**: **C. $O(1)$**
- **Explanation**: For any range $[L, R]$, the range sum is evaluated directly as $\text{prefix}[R] - \text{prefix}[L - 1]$ (or $\text{prefix}[R]$ if $L = 0$). This involves a single subtraction, completing in constant $O(1)$ time.

---

### Q4. In which scenario would it be most appropriate to prioritize speed (time complexity) over memory (space complexity)?
- **Options**:
  - A. Memory is an extremely tight constraint, such as in embedded systems.
  - B. The operation is performed very infrequently and is not critical.
  - C. Users expect immediate responses for a frequently used feature.
  - D. The cost of storing extra data is very high (e.g., massive datasets on expensive storage).
- **Answer**: **C. Users expect immediate responses for a frequently used feature.**
- **Explanation**: In high-throughput, latency-critical user-facing applications (e.g., real-time search autocompletion, web queries, financial trading), trading extra space (caching, precomputed tables, index hashes) for $O(1)$ or $O(\log n)$ latency is the standard architectural design pattern.

---

### Q5. A system frequently inserts and deletes elements at arbitrary positions in a collection, but rarely needs to access elements by their specific index. Why is a standard 1D array often considered a poor choice for this scenario?
- **Options**:
  - A. Arrays cannot store duplicate values.
  - B. Arrays have a fixed size and cannot grow dynamically.
  - C. Inserting or deleting elements at arbitrary positions in an array typically requires shifting many subsequent elements, resulting in $O(n)$ time complexity for each operation.
  - D. Arrays only support numerical data types, which might not fit all element types.
- **Answer**: **C. Inserting or deleting elements at arbitrary positions in an array typically requires shifting many subsequent elements, resulting in $O(n)$ time complexity for each operation.**
- **Explanation**: Array elements reside in contiguous memory locations. Inserting or deleting an element at index $i$ requires shifting all $(n - i - 1)$ elements to maintain contiguity, costing $O(n)$ time per operation. A doubly linked list would allow $O(1)$ insertion/deletion once the pointer is located.

---

### Q6. Which data structure is typically chosen for scenarios requiring fast lookups by a key, along with efficient insertions and deletions, often achieving $O(1)$ average-case time complexity for these operations?
- **Options**:
  - A. Sorted Array
  - B. Linked List
  - C. Hash-based structure
  - D. Balanced Tree
- **Answer**: **C. Hash-based structure**
- **Explanation**: Hash tables (`HashMap`, `HashSet`) use hash functions to compute array indices, providing average-case $O(1)$ time complexity for search, insertion, and deletion operations.

---

### Q7. What is the primary prerequisite for successfully applying binary search to a collection of data?
- **Options**:
  - A. The data must be stored in a non-linear data structure.
  - B. The data must be small enough to fit in cache memory.
  - C. The data must be sorted.
  - D. The data must contain only unique elements.
- **Answer**: **C. The data must be sorted.**
- **Explanation**: Binary search relies on monotonicity: comparing the target with the midpoint value allows the algorithm to discard half of the search interval. If elements are not sorted, discarding a half could eliminate the target without inspecting it.

---

### Q8. Which of the following are correct rules for determining the Big-O time complexity of an algorithm? (Select all that apply)
- **Options**:
  - A. Drop constant multipliers.
  - B. Prioritize lower-order terms over higher-order terms as input size grows.
  - C. If loops iterate over different inputs ($m$ and $n$) sequentially, their complexities are added.
  - D. Assume best-case scenario by default.
  - E. Nested loops over the same input ($n$) multiply their complexities.
- **Answer**: **A, C, E**
  - **A is correct**: Constants do not change the rate of growth as $n \to \infty$ (e.g., $O(5n) = O(n)$).
  - **C is correct**: Sequential execution of operations with sizes $m$ and $n$ yields $O(m + n)$.
  - **E is correct**: A loop of $n$ iterations containing an inner loop of $n$ iterations results in $n \times n = O(n^2)$.
  - *(B is false because higher-order terms dominate; D is false because Big-O represents asymptotic upper bounds, typically worst-case or average-case).*

---

### Q9. Which statements accurately describe characteristics of the sliding window technique? (Select all that apply)
- **Options**:
  - A. The window always maintains a fixed size, 'k'.
  - B. It is used to maintain a contiguous range of elements over an array.
  - C. Each element within the array is added to and removed from the window at most once.
  - D. It typically reduces an $O(n^2)$ or $O(n \times k)$ naive recomputation to $O(n)$.
  - E. It is primarily used for non-linear data structures.
- **Answer**: **B, C, D**
  - **B is correct**: The sliding window operates over contiguous subarrays or subsegments.
  - **C is correct**: Each element enters the window once at the right edge and exits at most once from the left edge.
  - **D is correct**: Incremental adjustments avoid re-evaluating the entire window from scratch, optimizing $O(n \times k)$ naive nested loops into linear $O(n)$ time.
  - *(A is false because sliding windows can be variable/dynamic in size; E is false because sliding windows apply to linear sequences such as arrays, strings, and lists).*

---

### Q10. What is the typical time complexity for inserting an element at the beginning or in the middle of a 1D array?
- **Options**:
  - A. $O(1)$
  - B. $O(\log n)$
  - C. $O(n)$
  - D. $O(n^2)$
- **Answer**: **C. $O(n)$**
- **Explanation**: Because an array stores elements sequentially in contiguous memory blocks, inserting an element requires shifting all subsequent elements rightward by one position to make room, which takes $O(n)$ steps in the worst/average case.

---

## Part C: Concept Questions

### Question 1: Asymptotic Analysis and Big-O Dominance
**Prompt**: Algorithm A runs in $3n + 10$ steps, and Algorithm B runs in $n^2 + 2$ steps for an input of size $n$. For small $n$, Algorithm B might perform fewer total steps. Despite this, Big-O classifies Algorithm A as generally more efficient. Explain why Big-O focuses on the growth trend as $n$ becomes large, and how constants and lower-order terms are handled in this analysis.

**Answer**:
Big-O analysis focuses on the asymptotic behavior of algorithms—how computational time scales as the input size $n$ tends toward infinity ($n \to \infty$). For small input sizes (such as $n = 1$ or $n = 2$), hardware details, constant factors, and lower-order overhead dominate runtimes (e.g., $1^2 + 2 = 3$ steps vs $3(1) + 10 = 13$ steps). However, modern computing systems easily handle small datasets in microseconds regardless of algorithmic efficiency; scalability issues emerge exclusively when datasets grow large (e.g., $n = 10^6$).

As $n$ increases:
- Algorithm A scales linearly: $O(n)$. For $n = 10^6$, steps $\approx 3 \times 10^6$.
- Algorithm B scales quadratically: $O(n^2)$. For $n = 10^6$, steps $\approx 10^{12}$ (requiring hours or failing entirely).

In asymptotic analysis, lower-order terms (like $+10$ or $+2$) and constant coefficients (like $3$) are omitted because as $n \to \infty$, the highest-order term $n^2$ grows infinitely faster than any linear function $c \cdot n$. Therefore, Algorithm A is fundamentally superior for scalable, production-grade applications.

---

### Question 2: Primitive vs. Non-Primitive Data Structures
**Prompt**: Explain how primitive and non-primitive data structures fundamentally differ in their purpose and typical memory representation. Provide a scenario where a non-primitive structure is essential for organizing related values, rather than just using a collection of primitive types.

**Answer**:
- **Primitive Data Structures**:
  - **Purpose**: Store basic, single atomic values directly (e.g., `int`, `float`, `char`, `boolean`).
  - **Memory Representation**: Stored directly on the call stack or inlined within an object's memory block as fixed-size raw binary values (e.g., 4 bytes for a 32-bit `int`). They possess no internal members or methods.
- **Non-Primitive Data Structures**:
  - **Purpose**: Group and organize complex, heterogeneous, or collection-based data relationships (e.g., classes/objects, arrays, linked lists, trees, hash maps).
  - **Memory Representation**: Typically allocated dynamically on the heap. Variables hold references (memory pointers) that point to the underlying structured object in heap memory, often accompanied by object headers, pointers to metadata, and child references.

**Essential Scenario**:
Consider an **E-Commerce Order Management System**. An `Order` consists of multiple attributes: `orderId` (string), `customer` (Customer object), `orderDate` (timestamp), `status` (Enum), and a list of `OrderItem` objects, each with `productId`, `quantity`, and `unitPrice`. Using disconnected primitive collections (parallel primitive arrays) would result in fragile code, race conditions, lack of encapsulation, and inability to enforce business invariants (such as total calculation or stock validation). A non-primitive custom class `Order` encapsulates these related attributes, enforces invariants, and provides object-oriented modularity.

---

### Question 3: Prerequisites and Trade-offs for Binary Search
**Prompt**: Explain why binary search cannot be directly applied to an unsorted array to find a target element. What essential data property must be present for binary search to function correctly, and what is the typical time complexity cost if this property needs to be established on an unsorted array before performing binary search?

**Answer**:
- **Why Binary Search Fails on Unsorted Arrays**:
  Binary search operates on the divide-and-conquer principle of *monotonicity*. At each iteration, it examines the middle element $M$:
  - If target $< M$, it safely discards the entire right half.
  - If target $> M$, it safely discards the entire left half.
  If the array is unsorted, this ordering guarantee is absent: a target smaller than $M$ could reside anywhere in either the left or right partition. Discarding either half leads to false negatives where existing elements are missed.
- **Essential Property**:
  The elements must satisfy a strict **total order / monotonic sorted sequence** with respect to the search key (either non-decreasing or non-increasing order).
- **Complexity Trade-off**:
  Establishing sorted order on an unsorted array requires sorting, which takes $O(n \log n)$ time using efficient algorithms like Merge Sort or Quick Sort.
  - **Single Query**: Sorting ($O(n \log n)$) + Binary Search ($O(\log n)$) = $O(n \log n)$. This is strictly worse than a simple Linear Search which takes $O(n)$ without sorting.
  - **Multiple Queries ($q \gg 1$)**: When executing $q$ queries on static data, sorting once costs $O(n \log n)$ followed by $q \cdot O(\log n)$ searches, totaling $O(n \log n + q \log n)$. Compared to $q \cdot O(n)$ for linear search, pre-sorting pays massive dividends as $q$ becomes large.
