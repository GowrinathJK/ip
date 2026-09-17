# Vermithor User Guide

Vermithor is a Java 25 task manager with a JavaFX interface.

## Quick start

1. Download `vermithor.jar` from the [latest release](https://github.com/GowrinathJK/ip/releases/latest).
2. Run `java -jar vermithor.jar` with Java 25.
3. Enter commands such as `todo read a book`, `list`, `find book`, or `sort`.

## Task commands

| Command | Example | Purpose |
| --- | --- | --- |
| `todo` | `todo read a book` | Adds a todo task |
| `deadline` | `deadline submit report /by 2026-09-18` | Adds a dated task |
| `event` | `event meeting /from 10:00 /to 11:00` | Adds an event |
| `mark` | `mark 1` | Marks a task complete |
| `find` | `find report` | Searches task descriptions |
| `sort` | `sort` | Sorts tasks alphabetically |
| `bye` | `bye` | Exits the application |

If the application reports an error, check that Java 25 is active and that the data directory is writable.

After `sort`, task numbers refer to the new displayed order. Tasks are saved automatically after changes and when the application closes with `bye`.

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Feature ABC

// Feature details


## Feature XYZ

// Feature details
