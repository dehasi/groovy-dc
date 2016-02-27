package decompiler.ast;

import org.codehaus.groovy.antlr.GroovySourceAST;
import org.codehaus.groovy.antlr.LineColumn;
import org.codehaus.groovy.antlr.SourceBuffer;
import org.codehaus.groovy.antlr.treewalker.VisitorAdapter;

/**
 * Source AST Visitor that will assert each node has a correct line/column info
 * given a SourceBuffer
 *
 * @author Jeremy Rayner
 */
public class LineColumnChecker extends VisitorAdapter {
    private SourceBuffer sourceBuffer;
    private String[] tokenNames;

    public LineColumnChecker(SourceBuffer sourceBuffer, String[] tokenNames) {
        this.sourceBuffer = sourceBuffer;
        this.tokenNames = tokenNames;
    }
    public void visitDefault(GroovySourceAST t,int visit) {
        if (visit == OPENING_VISIT ) {
            System.out.println("[" + tokenNames[t.getType()] + "]");
            int line = t.getLine();
            int column = t.getColumn();
            int lineLast = t.getLineLast();
            int columnLast = t.getColumnLast();

            System.out.println("" + line + " / " +  column + " - " + lineLast + " / " + columnLast);
            if (line > 0 && column > 0 && lineLast > 0 && columnLast > 0) {
                System.out.println("" + sourceBuffer.getSnippet(new LineColumn(line, column), new LineColumn(lineLast, columnLast)));
            } else {
                System.out.println("ZERO");
            }
        } else if (visit == CLOSING_VISIT) {
            System.out.println();
        }

    }
}
