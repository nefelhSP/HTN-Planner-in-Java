# Barman HTN Planner

A Java implementation of a **Hierarchical Task Network (HTN) planner** developed for the classic [IPC Barman benchmark domain](https://github.com/ipc2023-htn/ipc2023-domains/tree/main/total-order/Barman-BDI). The planner reads an HDDL problem file, parses the initial world state and goal tasks, and automatically decomposes compound tasks into sequences of primitive robot actions for a simulated robotic bartender.

This project was developed as part of a Computer Science thesis at the **University of Macedonia**.

---

## Overview

The Barman domain models a robotic bartender that must prepare multiple cocktails. Each cocktail requires filling a shaker with specific ingredients, shaking, and pouring into shot glasses. All that managed with limited resources (containers, dispensers, level tracking).

The planner encodes the full domain logic (10 tasks, ~20 methods, 11 primitive actions) in Java and solves HDDL problem files of varying complexity.

---

## Features

- **HTN Planning with DFS**: Depth-first search with backtracking over method decompositions
- **HDDL Problem Parser**: Reads standard HDDL problem files (objects, initial state, tasks to decompose)
- **Full Precondition Checking**: Verifies all domain predicates before applying any action
- **Effect Application**: Correctly updates world state after each primitive action
- **Plan Validation**: Re-executes the found plan step-by-step to verify correctness
- **Cleanup Phase**: Between goal tasks, automatically cleans containers used by previous tasks without disturbing already-filled goal shots
- **20 Benchmark Problem Files**: Ranging from simple 1-cocktail problems to complex 50 cocktail scenarios

---

## Project Structure
Barman/ ├── domain.hddl # HTN domain definition (tasks, methods, actions) ├── problemFiles/ │ ├── pfile01.hddl – pfile20.hddl # Benchmark problem instances ├── src/barman/ │ ├── Main.java # Entry point │ ├── Planner.java # DFS-based HTN planner │ ├── ProblemParser.java # HDDL problem file parser │ ├── WorldState.java # World state representation │ ├── Effects.java # Action effect application │ ├── Preconditions.java # Precondition checking │ ├── Validate.java # Plan validator │ ├── Cleanup.java # Inter-task cleanup phase │ ├── Factory.java # Task/Method/Action factory │ ├── Types.java # Domain type hierarchy │ ├── DomainHelper.java # Object lookup utilities │ ├── Task.java / Method.java / Action.java │ └── [Domain Task & Action classes] └── run_barman.bat # Windows run script


---

## How to Run

### Prerequisites
- Java 8 or later
- Any standard Java IDE (IntelliJ IDEA, Eclipse)

### From command line
```bash
# Compile
javac -d bin src/barman/*.java
# Run (default: pfile10.hddl)
java -cp bin barman.Main
run_barman.bat
```


---


## Output
The planner prints:
Initial world state — all predicates active at the start
Tasks to decompose — the list of high-level goals parsed from the problem file
Per-task plan — the sequence of primitive actions found by the DFS
Validation phase — step-by-step re-execution to confirm plan correctness
Cleanup phase — actions taken between tasks to reset shared resources
Full agenda — the complete hierarchical decomposition trace (tasks → methods → actions)
Full plan — the final flat action sequence
Execution time


---


## Limitations
Planner uses DFS with fixed node and depth limits (MaxNodes = 20000, MaxDepth = 60); very large problems may hit these limits
Problem file path is hardcoded in 
Main.java
 — no command-line argument support yet
Single-threaded execution


---


## License
This project was developed for academic thesis purposes. All rights reserved.
