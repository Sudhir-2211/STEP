# Week 11 — Data Structures Practice Problems & Assessment

**Document**: `CATEGORY-A-DATA-STRUCTURE_PRACTICE_PROBLEMS.pdf`  
**Candidate**: SUDHIR KUMAR J (RA2511026010182)  
**Track**: STEP SEM-3 · CodInClub | Powered by BridgeLabz  

---

## Part A — Coding Problems

### Problem 1: Remove the N-th Node From the End of a Linked List
* **Source File**: `src/Week_11/PracticeProblem1.java`
* **Approach**: Two-pointer (Fast & Slow) single-pass algorithm using a dummy sentinel node.
* **Algorithm**:
  1. Initialize `dummy` pointing to `head`, and set both `fast` and `slow` to `dummy`.
  2. Advance `fast` by `n + 1` steps.
  3. Advance both `fast` and `slow` in lockstep until `fast == null`.
  4. Perform deletion: `slow.next = slow.next.next`.
* **Edge Case Handling (Head Removal)**:
  * When `n` equals the list length, `fast` advances off the end after $n + 1$ steps while `slow` remains at `dummy`.
  * `dummy.next = dummy.next.next` eliminates the original head without requiring any conditional branches.
* **Complexity**: Time $O(L)$, Space $O(1)$.

---

### Problem 2: Check if a Linked List is a Palindrome
* **Source File**: `src/Week_11/PracticeProblem2.java`
* **Approach**: Three-phase in-place technique:
  1. **Find Middle**: Advance `slow` by 1 step and `fast` by 2 steps until `fast.next == null || fast.next.next == null`.
  2. **In-place Reversal**: Reverse the second half starting at `slow.next`.
  3. **Comparison & Restoration**: Compare nodes sequentially from `head` and the reversed second half. Re-reverse the second half back to its original order before returning.
* **Complexity**: Time $O(n)$, Space $O(1)$.

---

### Problem 3: Add Two Numbers Represented by Linked Lists
* **Source File**: `src/Week_11/PracticeProblem3.java`
* **Approach**: Simultaneous single-pass addition with carry:
  1. Maintain a `carry` variable initialized to 0.
  2. At each step, compute `sum = carry + (a != null ? a.data : 0) + (b != null ? b.data : 0)`.
  3. Update `carry = sum / 10` and append a new node with `sum % 10` to the result list.
  4. If a trailing carry remains after both lists terminate, append a final carry node.
* **Complexity**: Time $O(\max(m, n))$, Space $O(\max(m, n))$.

---

### Problem 4: Find Intersection Node of Two Linked Lists
* **Source File**: `src/Week_11/PracticeProblem4.java`
* **Approach**: Length alignment and simultaneous reference identity traversal:
  1. Calculate the lengths $L_A$ and $L_B$ of both lists.
  2. Advance the pointer for the longer list by $|L_A - L_B|$ steps to align starting distances to the possible merge point.
  3. Traverse both pointers in lockstep comparing reference equality (`currA == currB`).
  4. Return the first matching node reference, or `null` if the end of both lists is reached.
* **Complexity**: Time $O(m + n)$, Space $O(1)$.

---

### Problem 5: Reorder Linked List Alternately from Ends
* **Source File**: `src/Week_11/PracticeProblem5.java`
* **Approach**: Combines three fundamental linked list techniques:
  1. **Fast & Slow Pointers**: Find the midpoint of the linked list.
  2. **In-place Reversal**: Disconnect the first half (`slow.next = null`) and iteratively reverse the second half in-place.
  3. **Alternating Interleave / Merge**: Splice nodes alternately: $L_0 \to L_n \to L_1 \to L_{n-1} \dots$.
* **Complexity**: Time $O(n)$, Space $O(1)$.

---

## Part B — Quiz (Answers & Explanations)

| Question | Answer | Key Reason / Explanation |
| :--- | :---: | :--- |
| **Q1** | **B** | `newNode.next = node.next; then node.next = newNode;` ensures the link to the subsequent part of the list is safely captured before overwriting `node.next`. |
| **Q2** | **C** | **Employing two pointers moving at different speeds** (Floyd's Cycle Finding Algorithm / Tortoise and Hare) uses strictly $O(1)$ constant extra space. |
| **Q3** | **D** | **Approximately at the middle of the list.** Because the fast pointer travels twice as fast as the slow pointer, when fast reaches the end ($n$), slow has covered $n/2$ distance. |
| **Q4** | **C** | **O(1) constant space.** Iterative reversal modifies only three local pointers (`prev`, `curr`, `next`) in-place without auxiliary data structures or call-stack overhead. |
| **Q5** | **D** | **O(1) constant space.** Merging two sorted linked lists is performed by relinking the existing nodes' `next` pointers in-place using a single dummy node. |
| **Q6** | **C** | **It simplifies the logic by handling edge cases where the head node changes without special conditional checks.** Operations like inserting before the head or deleting the head use identical pointer manipulation logic. |
| **Q7** | **B** | **A singly linked list.** Inserting or removing at the head is an $O(1)$ operation (pointer update only), unlike an array which requires shifting all subsequent elements ($O(n)$). |
| **Q8** | **C** | **O(m + n) time, O(m) space.** Storing all $m$ nodes of the first list in a hash set requires $O(m)$ time and $O(m)$ space. Checking $n$ nodes of the second list against the set takes $O(n)$ time. |
| **Q9** | **C** | **The portion of the list that follows the current node will become unreachable.** Modifying `current.next` before preserving `current.next` destroys the only reference to the remainder of the list. |
| **Q10** | **C** | **A runtime error, such as a NullPointerException, will occur.** Dereferencing `null` (e.g. evaluating `current.next.data` when `current.next == null`) throws an unhandled exception at runtime. |

---

## Part C — Concept Questions (Detailed Answers)

### Question 1: Singly vs. Doubly Linked Lists
* **Memory Use per Node**: A singly linked list node stores one data reference and one `next` pointer (typically 24–32 bytes in 64-bit Java JVM). A doubly linked list node stores data and two pointers (`prev` and `next`), increasing node memory overhead by 33%–50%.
* **Supported Operations**: Doubly linked lists support bidirectional traversal, $O(1)$ deletion of a node given only its direct reference (without scanning from the head to find the predecessor), and $O(1)$ insertion before a given node. Singly linked lists can only traverse forward and require $O(n)$ search to locate a node's predecessor.
* **Risk of Pointer Errors**: Doubly linked lists require updating twice as many pointers per operation (`prev` and `next`), increasing the likelihood of orphaned pointers, dangling references, or inconsistent states.
* **When Justified**: The overhead is justified in applications requiring frequent bidirectional navigation or arbitrary in-place removals given node pointers, such as LRU Caches, Browser History (Back/Forward), and Deques/Text Editor Cursors.

---

### Question 2: Cycle Detection (Fast & Slow Pointers vs. Hash Set)
* **Complexity Comparison**:
  * **Hash Set**: Time $O(n)$, Auxiliary Space $O(n)$ to store node references.
  * **Floyd's Fast & Slow Pointers**: Time $O(n)$, Auxiliary Space $O(1)$.
* **Mathematical Intuition**:
  * Suppose the list has a cycle of length $C$, and both pointers have entered the cycle.
  * In each step, the fast pointer advances by 2 positions while the slow pointer advances by 1 position.
  * The relative distance between the fast pointer and the slow pointer decreases by exactly $2 - 1 = 1$ node per step modulo $C$.
  * Because the distance decreases by 1 on every iteration, the gap is guaranteed to reach 0 within at most $C$ iterations after entering the loop. Hence, the pointers must meet, proving the cycle in $O(n)$ time with $O(1)$ space.

---

### Question 3: In-Place Merging of Linked Lists vs. Arrays
* **Process**: Two pointers compare heads of both lists. The smaller node is linked to the tail of the merged sequence (`tail.next = smaller`), and that pointer advances. When one list is exhausted, the remainder of the other list is attached with a single pointer assignment in $O(1)$ time.
* **Memory Advantage over Arrays**:
  * Merging two sorted arrays of sizes $m$ and $n$ requires allocating a contiguous block of size $m + n$ ($O(m + n)$ space), because shifting elements in-place inside the original arrays would take $O((m + n)^2)$ time.
  * Linked list nodes are allocated independently on the heap and linked via pointers. Reordering elements only requires re-pointing existing memory references, achieving $O(1)$ auxiliary space while preserving $O(m + n)$ linear runtime.

---

### Question 4: Sentinel (Dummy) Nodes
* **Role**: A dummy node is placed at the front of a linked list (`dummy.next = head`), providing an invariant non-null predecessor for every legitimate node in the list.
* **Streamlining Insertion and Deletion**: Every node, including the head, now has a preceding node. Edge cases where the head changes or is removed are treated identically to modifications in the middle of the list.
* **Concrete Example**: Removing the head node without a dummy requires checking `if (head.data == target) { head = head.next; } else { ... }`. With a dummy node, `prev.next = prev.next.next` works uniformly for all nodes, and the final list is simply returned as `dummy.next`.

---

### Question 5: Null-Reference Exceptions in Linked Lists
1. **Scenario 1: Two-step Lookahead (`current.next.next`)**
   * *Root Cause*: Accessing `.next` on `current.next` when `current.next` is `null` (e.g. at the penultimate node or in short lists).
   * *Prevention*: Always guard with compound conditional checks: `while (fast != null && fast.next != null)`.
2. **Scenario 2: Dereferencing Unfound Search Targets**
   * *Root Cause*: Attempting to read `target.data` or update `target.next` after a search loop has traversed past the tail and terminated with `target == null`.
   * *Prevention*: Check `if (target != null)` before accessing any fields or invoking methods.

---

### Question 6: Roles of Pointers in Iterative In-Place Reversal
* **Pointer Roles**:
  * `prev`: Points to the already-reversed prefix (initially `null`).
  * `curr`: Points to the current node being processed.
  * `next`: Temporarily preserves the unreversed remainder of the list before `curr.next` is rewritten.
* **Consequence of Omitting `next` Preservation**:
  * If `curr.next = prev` is executed without first saving `next = curr.next`, the reference to the subsequent node is immediately overwritten and permanently lost.
  * The remainder of the list becomes unreachable and orphaned in memory, prematurely truncating the list after the first node.

---

### Question 7: Is "Inserting in the Middle of a Linked List an O(1) Operation"?
* **When Technically Accurate**: If you already hold a direct pointer to the predecessor node where the insertion occurs, creating the new node and updating two pointers (`newNode.next = curr.next; curr.next = newNode;`) executes in constant $O(1)$ time.
* **Why Misleading in Practice**:
  * In real-world applications, you rarely possess a direct pointer to an interior node. Locating the $k$-th position or searching for a specific value requires traversing $k$ elements from the head, which takes $O(k) = O(n)$ time.
  * Therefore, the complete operation of "find and insert" is $O(n)$ in linked lists. In an array, searching takes $O(n)$ (or $O(\log n)$ if sorted) and inserting takes $O(n)$ due to element shifting. Thus, linked lists do not provide an asymptotic advantage for random-position insertions unless the target position is already cached.

---

### Question 8: Circular Linked Lists
* **Structural Difference**: In a standard singly linked list, the tail node's `next` pointer is `null`. In a circular linked list, the tail node's `next` pointer loops back to point to the `head` node, forming a closed ring.
* **Practical Use Case**:
  * Round-Robin CPU scheduling, where processes take turns executing in circular order.
  * Multiplayer turn-based board games (e.g., passing control in a circle).
  * Audio/Video playlist looping.
* **Traversal Pitfall & Termination**:
  * Checking `while (curr != null)` causes an infinite loop because no node ever points to `null`.
  * The correct termination condition is to record the starting node (e.g. `head`) and stop when `curr.next == head` or loop using a `do { ... } while (curr != head);` construct.

---

### Question 9: Linked Lists vs. Arrays Performance & Cache Utilization
* **Memory Layout Difference**:
  * **Arrays**: Elements are stored in contiguous blocks of physical memory.
  * **Linked Lists**: Nodes are allocated dynamically across the heap at scattered, non-contiguous memory addresses connected by pointer references.
* **Impact on CPU Cache**:
  * When the CPU accesses an array element, spatial locality causes hardware prefetchers to load the entire 64-byte cache line into the CPU cache (L1/L2/L3), allowing subsequent contiguous elements to be accessed in sub-nanosecond cache hits.
  * With linked lists, traversing `curr = curr.next` jumps to arbitrary heap locations. Each step causes a cache miss, forcing the CPU pipeline to stall while fetching memory from high-latency main RAM. Consequently, array traversal is typically 5–10× faster than linked list traversal in practice despite identical $O(n)$ asymptotic complexity.

---

### Question 10: Deleting Nodes & Unreachability: GC vs. Manual Memory Management
* **Automatic Garbage Collection (e.g., Java)**:
  * When a node is unlinked (`prev.next = curr.next`), it is no longer reachable through reference traversal from any GC roots (active threads, static references, local stack variables).
  * The JVM's Garbage Collector identifies it as unreachable and automatically reclaims its memory during sweeping/compacting cycles.
* **Manual Memory Management (e.g., C/C++)**:
  * Severing pointers does **not** free heap memory allocated via `malloc()` or `new`.
  * The node remains allocated in the heap, but with no remaining pointers pointing to it, its address is permanently lost.
  * This causes a **memory leak**. In long-running servers or embedded systems, accumulating unreferenced leaked memory gradually exhausts available RAM, leading to memory fragmentation, system slowdowns, and eventual fatal Out-Of-Memory crashes.
