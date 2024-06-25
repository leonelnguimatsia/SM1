This document is written in markdown.

# Toolbox

Toolbox is a set of commandline tools.

## Prerequisites

You need a working JDK installation.

## Installing

Compile  `src/Toolbox.java`

    javac Toolbox.java

## Usage

The basic usage pattern is
  
    java Toolbox <command> <command-specific arguments>

where `<command>` is one of the following

* **length:** Prints out the length of the string passed in the `<command-specific argument>`
* (more to follow)

Change Log
----------

## [Unreleased]
### Added
- Implemented command 'reverse'.*
- remark about Markdown in README.md.

## [1.0.0] - 2022-11-23
### Added
- Initial version. Supports command 'length'.
