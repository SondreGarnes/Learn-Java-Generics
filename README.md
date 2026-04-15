# Java Generics — Learning Path

A structured, hands-on curriculum for mastering Java Generics from the ground up.

## How to use this course

1. Fork this repository to your own GitHub account.
2. Read each explanation file in order.
3. Open the `Tasks` file at the end of each chapter and complete the exercises.
4. Only look in `Solutions/` after you have made a genuine attempt.
5. Compile and run your code as you go — the compiler is your best teacher.

---

## Chapter Overview

| Chapter | Topic | Key concepts |
|---------|-------|-------------|
| 01 | Introduction | What generics are, the problem they solve |
| 02 | Generic Classes | Type parameters, generic containers |
| 03 | Generic Methods | Per-method type parameters, inference |
| 04 | Bounded Type Parameters | `extends`, multiple bounds |
| 05 | Wildcards | `?`, PECS, upper/lower bounds |
| 06 | Generic Interfaces | Implementing & combining generic interfaces |
| 07 | Type Erasure | How the JVM sees generics at runtime |
| 08 | Advanced Generics | Recursive bounds, self-referential patterns |
| 09 | Real-World Examples | Stack, Repository, Pair — production-grade usage |

---

## Recommended Order

```
Chapter01 → Chapter02 → Chapter03 → Chapter04
                                        ↓
                          Chapter05 (Wildcards)
                                        ↓
                          Chapter06 (Generic Interfaces)
                                        ↓
                          Chapter07 (Type Erasure)
                                        ↓
                          Chapter08 (Advanced)
                                        ↓
                          Chapter09 (Real World)
```

---

## Compiling and running

From the repo root:

```bash
javac Chapter01_Introduction/01_WhatAreGenerics.java
java  Chapter01_Introduction.WhatAreGenerics        # if package declared
# or simply open in your IDE and run main()
```

Good luck!
