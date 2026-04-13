# Jason Chen's Project Portfolio Page

## Project: MoneyBagProMax

MoneyBagProMax is a command-line personal finance management application designed to help users track
income and expenses, manage budgets, and gain insights into spending habits through financial statistics.
The user interacts with it using a CLI, and the application is written in Java.

Given below are my contributions to the project.

---

## Summary of Contributions

### New Feature: Sort Command
Sorts and displays transactions by date (ascending), amount (descending), or category (alphabetical)
without altering the underlying list order. Users accumulate transactions in insertion order,
making it hard to spot the largest expenses — sort gives an instant reordered view on demand.
The sort operates on a defensive copy so insertion-order indices used by `delete` and `edit` are
unaffected, and `isMutating()` returns false so undo/redo history is untouched.

### New Feature: Undo/Redo
Reverses or reapplies the most recent mutating command (add, delete, edit); multiple sequential
undos and redos are supported. Accidental deletions are a common data-entry mistake — without undo
the only recovery is manually re-entering data. Implemented using a dual-stack design in
`UndoRedoManager`: each mutating command pushes an `ActionPair` (action type, transaction, index)
onto the undo stack and clears the redo stack, following the standard linear-history contract.

### New Feature: Recurring Transactions
Lets users define recurring templates (e.g. monthly salary, weekly groceries) that are resolved
into real transactions when `generate-recurring` is run. Many real-world entries repeat on a fixed
schedule; without this, users must re-enter identical transactions every period. `RecurringTransaction`
is deliberately *not* a subclass of `Transaction` — it is a template with a frequency, start date,
and last-generated watermark, keeping non-transactional metadata out of the main list.

### New Feature: Transaction Date and Description Fields
Extended `Transaction` with an optional `LocalDate` date field (defaults to today) and an optional
free-text description field, parsed from `d/` and `desc/` tokens. Without a date field it is
impossible to backfill historical entries. Both fields are optional so existing commands work
unchanged; the date field is the sort key in `SortCommand` and a filter criterion in `FilterCommand`.

### Enhancements Implemented
- Added `assert` statements to `Transaction`, `Income`, `Expense`, `UndoRedoManager`, and
  `RecurringTransaction` to catch violated preconditions early.
- Added Javadoc to all authored classes and methods.
- Wrote JUnit tests for `SortCommand`, `UndoRedoManager`, `RecurringTransaction`, and `Transaction`
  enhancements.

[Jason's RepoSense](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=Nishuy52&breakdown=true)

### Contributions to the User Guide
Sort, Undo, Redo, Add/Delete/List/Generate recurring transaction commands.

### Contributions to the Developer Guide
- **Sort**: architecture and flow, design considerations, `SortSequenceDiagram.png`, `SortClassDiagram.png`
- **Undo/Redo**: architecture, implementation details, design considerations, `UndoSequenceDiagram.png`, `RedoSequenceDiagram.png`, `UndoRedoClassDiagram.png`
- **Recurring Transactions**: full feature section (all four commands, storage persistence, design considerations)
- **Transaction Class**: abstract class overview, field table, `protected final` immutability rationale

### Contributions to Team-Based Tasks
- Reviewed teammates' pull requests with inline comments
  ([#84](https://github.com/AY2526S2-CS2113-T14-4/tp/pull/84),
  [#85](https://github.com/AY2526S2-CS2113-T14-4/tp/pull/85),
  [#88](https://github.com/AY2526S2-CS2113-T14-4/tp/pull/88)).
- Managed the team's GitHub Issues board and assigned issues to relevant members.
- Fixed checkstyle issues and improved code quality across the codebase.
- Reported 22 bugs during the Practical Exam (Dry Run) for another team's product.
