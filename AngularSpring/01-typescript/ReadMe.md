# Introduction

[Typescript  (TS)](www.typescriptlang.org)  -- A strongly typed language that generates Javascript.

- Object oriented programming (OOP) language developed by Microsoft and made open-source
- VSCode IDE support is available
- Most popular among other languages used for developing angular like Javascript, ECMAScript (standardised JS) and Dart.

>  Angular framework itself is developed using Typescript



## Angular and Node

While Angular does not require Node during runtime. It is required during development time using AngularTS. Tools

| Tool                       | Detail                                                       |
| -------------------------- | ------------------------------------------------------------ |
| NVM (Node Version Manager) | https://github.com/nvm-sh/nvm                                |
| Node                       | NodeJS required for angular developement                     |
| NPM (Node Package Manager) | A package manager (like maven) to manage all angular dependencies |
| TSC (TypeScript Compiler)  | Typescript compiler (compile `.ts` to `.js` also known as **transpiling**) |
| TS-Node                    | Tool required by VSCode to run typescript                    |

```bash
# Install the latest nvm
> curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.3/install.sh | bash
> nvm --version
0.40.3

# Install node
> nvm install node
> node --version
v24.8.0

# npm (installed with node)
> npm --version
11.6.0

# Install typescript and ts-node as a gobal packages (-g option)
> npm install -g typescript
> tsc --version
Version 5.9.2
```

# Typescript

## Configure VSCode

1. Install [CodeRunner](https://marketplace.visualstudio.com/items?itemName=formulahendry.code-runner) extension

2. Add following to settings.json of VSCode

```json
"code-runner.executorMap": {
    "typescript": "cd $dir && tsc $fileName && node $fileNameWithoutExt.js",
}        
```

## Hello World

Transpile (Translation + compilation) typescript to JavaScript and then execute JavaScript

```bash
> cd ~/Projects/TypeScript/01-basics

# Transpile
> tsc 01-hello.ts
> ls 01-hello.js
01-hello.js

# Run JS
> node 01-hello.js
Hello World
```

## Basics

See [01-basics](01-basics) to learn about

- Variables
- Loops 
- Arrays

## Class

See [02-class](02-class) to learn about

- Creating a class
- Create a constructor with parameter properties -- constructor parameters assigned to implicit instance variables
- Accessors -- Getters/setters

## Module

See [03-module](03-module) to learn about

- Exporting class
- Importing and using class

## Inheritance

See [04-inheritance](04-inheritance) to learn about

- Interfaces
- Abstract classes with abstract functions
- Extending classes and implementing interfaces 
- Calling the super constructor
- Overriding functions
