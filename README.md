# Kotlin evaluate expression

Kevex is a Kotlin port of the [Evex evaluation engine](https://github.com/TheDevtop/evex),
originally written in Go.

### Usage:

1. Download the kevex.jar file
2. Write some kevex code into a file
3. Execute the code with: `$ kotlin kevex.jar < file.vx`

### Syntax:

```
fa := 3.0
fx := fa fa fa
```

In this example _fa_ is evaluated as 3.0.
By applying this to _fx_ we get _fx_ = (3.0+3.0+3.0) = 9.

### Operators:

- Reduction **:=**
- Count/Length **#=**
- Selection (highest) **|=**
- Selection (lowest) **&=**
