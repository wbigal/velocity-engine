package org.apache.velocity.runtime.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.node.ASTReference;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

public class Iterate extends Directive {

	private String elementKey;

	@Override
	public String getName() {
		return "iterate";
	}

	@Override
	public int getType() {
		return BLOCK;
	}

	public void init(RuntimeServices rs, InternalContextAdapter context,
			Node node) throws TemplateInitException {
		super.init(rs, context, node);

		/*
		 * this is really the only thing we can do here as everything else is
		 * context sensitive
		 */
		SimpleNode sn = (SimpleNode) node.jjtGetChild(0);

		if (sn instanceof ASTReference) {
			elementKey = ((ASTReference) sn).getRootString();
		} else {
			/*
			 * the default, error-prone way which we'll remove TODO : remove if
			 * all goes well
			 */
			elementKey = sn.getFirstToken().image.substring(1);
		}
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {

		Node block = node.jjtGetChild(node.jjtGetNumChildren() - 1);
		Iterator<?> iterator = (Iterator<?>) context.get(elementKey);
		
		if (iterator == null) {
			return false;
		}

		while (iterator.hasNext()) {
			try
            {
                renderBlock(context, writer, block);
            }
            catch (StopCommand stop)
            {
                if (stop.isFor(this))
                {
                    break;
                }
                else
                {
                    // clean up first
                    clean(context, iterator);
                    throw stop;
                }
            }
		}
		
		clean(context, iterator);
		return true;
	}

	protected void renderBlock(InternalContextAdapter context, Writer writer,
			Node block) throws IOException {
		block.render(context, writer);
	}
	
	protected void clean(InternalContextAdapter context, Object o)
    {
        /*
         *  restores element key if exists
         *  otherwise just removes
         */
        if (o != null)
        {
            context.put(elementKey, o);
        }
        else
        {
            context.remove(elementKey);
        }

        // clean up after the ForeachScope
        postRender(context);
    }
}
