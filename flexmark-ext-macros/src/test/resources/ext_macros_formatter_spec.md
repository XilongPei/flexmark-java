---
title: Macros Extension Formatter Spec
author: Vladimir Schneider 
version: 1.0 
date: '2018-09-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Macros  

Converts macros text to macro definitions and macro references.

Macro Definitions are block elements which can contain any markdown element

Macro definitions start with a line of `>>>macroName` where macro name consists of a-z, A-Z,
0-9, - or _ and is terminated by a line with `<<<`. All text between these lines is parsed as
markdown blocks. Macro definition blocks can only be defined at the document level without any
indentation and are not rendered in the output.

Macro reference is an inline element of the form `<<<macroName>>>` and is replaced by the macro
definition rendered content, including any block elements. This implies that it is possible to
include block elements where they are normally not possible using plain markdown.

When a macro definition contains a single paragraph node then it will be rendered as text
contained by the paragraph without the `<p></p>` wrapper to allow plain text to be rendered as
an inline text.

If a macro reference contains an undefined `macroName` then the node will be rendered as plain
text consisting of `<<<macroName>>>`

Recursive macro references are resolved by stopping macro expansion on the first macro reference which
refers to a macro definition which is currently being expanded. The first recursive macro
reference will result in no expansion.

takes valid characters

```````````````````````````````` example Macros: 1
>>>abc-xyzABC_XYZ09
<<<

.
>>>abc-xyzABC_XYZ09
<<<

````````````````````````````````


empty macro content is allowed

```````````````````````````````` example Macros: 2
>>>macro
<<<

Plain text <<<macro>>>
.
>>>macro
<<<

Plain text <<<macro>>>
````````````````````````````````


simple text

```````````````````````````````` example Macros: 3
>>>macro
simple text
<<<

Plain text <<<macro>>>
.
>>>macro
simple text
<<<

Plain text <<<macro>>>
````````````````````````````````


complex content

```````````````````````````````` example Macros: 4
>>>macro
* list item 1
* list item 2

| heading     |
|:------------|
| column data |
<<<

Plain text <<<macro>>>
.
>>>macro
* list item 1
* list item 2

| heading     |
|:------------|
| column data |

<<<

Plain text <<<macro>>>
````````````````````````````````


macro tables

```````````````````````````````` example Macros: 5
>>>macro
| heading     |
|:------------|
| column `data` |
| column **data** 2 |
<<<

<<<macro>>>
.
>>>macro

| heading           |
|:------------------|
| column `data`     |
| column **data** 2 |

<<<

<<<macro>>>
````````````````````````````````


complex content for tables

```````````````````````````````` example Macros: 6
>>>macro
| heading     |
|:------------|
| column `data` |
| column **data** 2 |
<<<

| outer first  | outer heading |
|:-------------|:--------------|
| Regular Text | <<<macro>>>   |
.
>>>macro

| heading           |
|:------------------|
| column `data`     |
| column **data** 2 |

<<<

| outer first  | outer heading |
|:-------------|:--------------|
| Regular Text | <<<macro>>>   |

````````````````````````````````


GitLab multi-line block quotes

```````````````````````````````` example Macros: 7
>>>macro
>>>
Block Quote
<<<
<<<

Plain text <<<macro>>>
.
>>>macro
>>>
Block Quote
<<<
<<<

Plain text <<<macro>>>
.
Document[0, 57]
  MacroDefinitionBlock[0, 33] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[29, 32, "<<<"] closeTrail:[32, 33, "\n"]
    GitLabBlockQuote[9, 29] open:[9, 12, ">>>"] openTrail:[12, 13, "\n"] close:[25, 28, "<<<"] closeTrail:[28, 29, "\n"]
      Paragraph[13, 25] isTrailingBlankLine
        Text[13, 24] chars:[13, 24, "Block … Quote"]
  BlankLine[33, 34]
  Paragraph[34, 57]
    Text[34, 45] chars:[34, 45, "Plain … text "]
    MacroReference[45, 56] textOpen:[45, 48, "<<<"] text:[48, 53, "macro"] textClose:[53, 56, ">>>"]
````````````````````````````````


Nested GitLab multi-line block quotes

```````````````````````````````` example Macros: 8
>>>macro
>>>
Block Quote
>>>
Nested Block Quote
<<<
<<<
<<<

Plain text <<<macro>>>
.
>>>macro
>>>
Block Quote
>>>
Nested Block Quote
<<<
<<<
<<<

Plain text <<<macro>>>
.
Document[0, 84]
  MacroDefinitionBlock[0, 60] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[56, 59, "<<<"] closeTrail:[59, 60, "\n"]
    GitLabBlockQuote[9, 56] open:[9, 12, ">>>"] openTrail:[12, 13, "\n"] close:[52, 55, "<<<"] closeTrail:[55, 56, "\n"]
      Paragraph[13, 25]
        Text[13, 24] chars:[13, 24, "Block … Quote"]
      GitLabBlockQuote[25, 52] open:[25, 28, ">>>"] openTrail:[28, 29, "\n"] close:[48, 51, "<<<"] closeTrail:[51, 52, "\n"]
        Paragraph[29, 48] isTrailingBlankLine
          Text[29, 47] chars:[29, 47, "Neste … Quote"]
  BlankLine[60, 61]
  Paragraph[61, 84]
    Text[61, 72] chars:[61, 72, "Plain … text "]
    MacroReference[72, 83] textOpen:[72, 75, "<<<"] text:[75, 80, "macro"] textClose:[80, 83, ">>>"]
````````````````````````````````


