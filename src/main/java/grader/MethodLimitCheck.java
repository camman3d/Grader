package grader;

import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;



public class MethodLimitCheck extends Check
{
    private static final int DEFAULT_MAX = 30;
    private int max = DEFAULT_MAX;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
    }
    
 // code from above omitted for brevity
    public void setMax(int limit)
    {
        max = limit;
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        // find the OBJBLOCK node below the CLASS_DEF/INTERFACE_DEF
    	int childCount = ast.getChildCount();
    	AST child = ast.getFirstChild();;
//    	while (true) {
//        
//    	if (child == null)
//    		break;
//    	System.out.println(child);
//    	child = child.getNextSibling();    
//    	}
        
     
        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        // count the number of direct children of the OBJBLOCK
        // that are METHOD_DEFS
        int methodDefs = objBlock.getChildCount(TokenTypes.METHOD_DEF);
        // report error if limit is reached
        if (methodDefs > this.max) {
            log(ast.getLineNo(),
                "too many methods, only " + this.max + " are allowed");
        }
   }
}
