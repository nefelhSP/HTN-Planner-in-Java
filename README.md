# Barman HTN Planner

A Java implementation of a **Hierarchical Task Network (HTN) planner** developed for the classic [IPC Barman benchmark domain](https://github.com/ipc2023-htn/ipc2023-domains/tree/main/total-order/Barman-BDI). The planner reads an HDDL problem file, parses the initial world state and goal tasks, and automatically decomposes compound tasks into sequences of primitive robot actions for a simulated robotic bartender.

This project was developed as part of a Computer Science thesis at the **University of Macedonia**.

---

## Overview

The Barman domain models a robotic bartender that must prepare multiple cocktails. Each cocktail requires filling a shaker with specific ingredients, shaking, and pouring into shot glasses. All that managed with limited resources (containers, dispensers, level tracking).

The planner encodes the full domain logic (10 tasks, 22 methods, 11 primitive actions) in Java and solves HDDL problem files of varying complexity.

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

```text
Barman/
├── domain.hddl          # HTN domain rules, methods, and task definitions
├── problemFiles/        # Collection of benchmarks (pfile01.hddl to pfile20.hddl)
├── src/barman/
│   ├── Main.java        # Project entry point and execution controller
│   ├── Planner.java     # Core HTN engine (DFS logic)
│   ├── ProblemParser.java # HDDL file parser
│   ├── WorldState.java  # Current world representation and state tracking
│   ├── [Logic Components]
│   │   ├── Effects.java       # Action state updates
│   │   ├── Preconditions.java # Action feasibility checks
│   │   └── Validate.java      # Plan verification utilities
│   └── [Model Classes]
│       ├── Task.java / Method.java / Action.java # Data models
│       └── Types.java         # Domain object hierarchy
└── run_barman.bat       # Execution script for Windows

```
---

## How to Run

### Prerequisites
- Java 8 or later
- Any standard Java IDE (IntelliJ IDEA, Eclipse) or command line interface

### Command Line Execution
```bash
# Compile the source code
javac -d bin src/barman/*.java
# Run with Java
# Simply pass the problem number (e.g., 1 for pfile01.hddl):
java -cp bin barman.Main
# Windows Batch Script
run_barman.bat
```


---


## Planner Output
During execution, the planner outputs a detailed execution trace including:
- Initial World State: Active predicates at start-up
- Tasks to Decompose: List of high-level goals parsed from the problem file.
- Per-Task Plan: Sequence of primitive actions found by the planner for each goal.
- Validation Phase: Step-by-step re-execution to confirm plan correctness.
- Cleanup Phase: Inter-task actions taken to reset shared resources.
- Full Agenda: Complete hierarchical decomposition trace (Tasks → Methods → Actions).
- Full Plan: Final flattened sequence of primitive actions.
- Execution Time, Nodes Visited and Tree Depth


---


## Limitations
To prevent infinite recursion during decomposition, the depth-first search engine operates within defined bounds:
- Maximum Search Nodes (MaxNodes): 20,000
- Maximum Search Depth (MaxDepth): 60


---


## License
This project was developed for academic thesis purposes at the University of Macedonia. All rights reserved.
