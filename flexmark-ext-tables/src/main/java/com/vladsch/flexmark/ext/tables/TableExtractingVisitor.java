package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeVisitor;
import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.List;

public class TableExtractingVisitor {
    private final TableFormatOptions options;

    private NodeVisitor myVisitor = new NodeVisitor(
            new VisitHandler<>(TableBlock.class, new Visitor<TableBlock>() {
                @Override
                public void visit(TableBlock node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableHead.class, new Visitor<TableHead>() {
                @Override
                public void visit(TableHead node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableSeparator.class, new Visitor<TableSeparator>() {
                @Override
                public void visit(TableSeparator node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableBody.class, new Visitor<TableBody>() {
                @Override
                public void visit(TableBody node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableRow.class, new Visitor<TableRow>() {
                @Override
                public void visit(TableRow node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableCell.class, new Visitor<TableCell>() {
                @Override
                public void visit(TableCell node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableCaption.class, new Visitor<TableCaption>() {
                @Override
                public void visit(TableCaption node) {
                    TableExtractingVisitor.this.visit(node);
                }
            })
    );

    private MarkdownTable myTable;
    private final List<MarkdownTable> myTables;

    public TableExtractingVisitor(DataHolder options) {
        this.options = new TableFormatOptions(options);
        myTables = new ArrayList<>();
    }

    public MarkdownTable[] getTables(Node node) {
        myTable = null;
        myVisitor.visit(node);
        return myTables.toArray(new MarkdownTable[0]);
    }

    private void visit(final TableBlock node) {
        myTable = new MarkdownTable(options);
        myVisitor.visitChildren(node);
        myTables.add(myTable);

        myTable = null;
    }

    private void visit(final TableHead node) {
        myTable.setSeparator(false);
        myTable.setHeader(true);
        myVisitor.visitChildren(node);
    }

    private void visit(TableSeparator node) {
        myTable.setSeparator(true);
        myVisitor.visitChildren(node);
    }

    private void visit(final TableBody node) {
        myTable.setSeparator(false);
        myTable.setHeader(false);
        myVisitor.visitChildren(node);
    }

    private void visit(final TableRow node) {
        myVisitor.visitChildren(node);
        if (!myTable.isSeparator()) myTable.nextRow();
    }

    private void visit(final TableCaption node) {
        myTable.setCaptionWithMarkers(node.getOpeningMarker(), node.getText(), node.getClosingMarker());
    }

    private void visit(final TableCell node) {
        BasedSequence text = node.getText();
        if (options.trimCellWhitespace) {
            if (text.isBlank() && !text.isEmpty()) {
                text = text.subSequence(0, 1);
            } else {
                text = text.trim();
            }
        }
        myTable.addCell(new com.vladsch.flexmark.util.format.TableCell(node.getOpeningMarker(), text, node.getClosingMarker(), 1, node.getSpan(), node.getAlignment() == null ? CellAlignment.NONE : node.getAlignment().cellAlignment()));
    }
}
