# LogicalExpressionSystem
This project includes OOP principles and design patterns.

There are basic expressions such as:
*Val that holds a boolean value
*Var that holds a variable.
Each expression has methods such as evaluate - to evaluate the expression according to a map.
assign - assigning another expression instead of the current variable.
and more..

Val & Var both implements the Expression interface.

There are the abstract classes:
* Binary Expression - every binary operation extends this abstract class.
* Unary Expression - the unary operations extends it, such as Not.
* Base Expression - both Binary Expression and Unary expression extends it.

Eventually there are the operation themselves such as - And, Or, Xor, Nor, Nand, Not...
Every opertaion has different implementations. 

