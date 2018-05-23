package edu.northeastern.cs5500.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import edu.northeastern.cs5500.parser.Python3Parser;

public class AstPrinter {

    /**
     * instance variables
     */
    private int previous = -1;
    private int len = 0;
    private StringBuilder ast= new StringBuilder("");
    private boolean ignoringWrappers = true;

    /**
     *
     * @param ignoringWrappers type boolean
     */
    public void setIgnoringWrappers(boolean ignoringWrappers) {
        this.ignoringWrappers = ignoringWrappers;
    }

    /**
     *
     * @param ctx Rule in file
     * @return String
     */
    public String getAst(RuleContext ctx) {
        return exploreAstAsString(ctx, 0);
    }


    /**
     *
     * @return integer length
     */
    public int getLength() {
        return len;
    }

    /**
     *
     * @param ctx Rule in file
     * @param indentation type integer
     * @return String
     */
    private String exploreAstAsString(RuleContext ctx, int indentation) {
        boolean toBeIgnored = ignoringWrappers
                && ctx.getChildCount() == 1
                && ctx.getChild(0) instanceof ParserRuleContext;
        if (!toBeIgnored) {
            String ruleName = Python3Parser.ruleNames[ctx.getRuleIndex()];
            if (previous >= indentation) {
                for (int i = 0; i <= previous - indentation; i++) {
                    ast =ast.append("}");
                }
            }
            ast =ast.append("{");
            ast=ast.append(ruleName);
            previous = indentation;
            len++;
        }

        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            if (element instanceof RuleContext) {
                exploreAstAsString((RuleContext) element, indentation + (toBeIgnored ? 0 : 1));
            }
        }
        return astComplete(ast.toString());
    }

    /**Helper function to append the remaining parenthesis to the resultant ast.
     *
     * @param ast type string
     * @return String
     */
    private String astComplete(String ast) {
        StringBuilder sample= new StringBuilder(ast);
        int temp = 0;
        for (int i = 0; i < ast.length(); i++) {
            if (ast.charAt(i) == '{') {
                temp++;
            }
            if (ast.charAt(i) == '}') {
                temp--;
            }
        }
        while (temp > 0) {
            sample = sample.append("}");
            temp--;
        }
        ast=sample.toString();
        return ast;
    }
}
