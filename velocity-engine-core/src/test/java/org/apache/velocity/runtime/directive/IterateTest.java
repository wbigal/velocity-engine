package org.apache.velocity.runtime.directive;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.test.BaseTestCase;

public class IterateTest extends BaseTestCase {
	
	static final String RESULT_SUCCESS = "Testing... 4  test 1 ,  test 2 ,  test 3 ,  test 4 ! ";
	static final String RESULT_NULL_INTERACTION = "Testing... "; 
	static final String RESULT_WITH_BREAK = "Testing...  test 1 , ";
	
	public IterateTest(final String name) {
		super(name);
	}

	public void testSuccess() {
		StringWriter strWriter = new StringWriter();
		
		List<String> values = new ArrayList<String>();
		values.add("test 1");
		values.add("test 2");
		values.add("test 3");
		values.add("test 4");
		
		final String templateStr = "Testing... "
				+ "$num "
				+ "#iterate( $iterator )"
				+ " $iterator.next() #if ($iterator.hasNext()),#else!#end "
				+ "#end";
		
		VelocityContext headerContext = new VelocityContext();
		headerContext.put("iterator", values.stream().iterator());
		headerContext.put("num", values.size());
		Velocity.evaluate( headerContext, strWriter, "log tag name", templateStr);
		
		assertEquals(RESULT_SUCCESS, strWriter.toString());
	}
	
	public void testWithBreak() {
		StringWriter strWriter = new StringWriter();
		
		List<String> values = new ArrayList<String>();
		values.add("test 1");
		values.add("test 2");
		values.add("test 3");
		values.add("test 4");
		
		final String templateStr = "Testing... "
				+ "#iterate( $iterator )"
				+ " $iterator.next() #if ($iterator.hasNext()),#else!#end "
				+ "#break"
				+ "#end";
		
		VelocityContext headerContext = new VelocityContext();
		headerContext.put("iterator", values.stream().iterator());
		Velocity.evaluate( headerContext, strWriter, "log tag name", templateStr);
		
		assertEquals(RESULT_WITH_BREAK, strWriter.toString());
	}
	
	public void testWithNullIterator() {
		StringWriter strWriter = new StringWriter();
		
		final String templateStr = "Testing... "
				+ "#iterate( $iterator )"
				+ " $iterator.next() #if ($iterator.hasNext()),#else!#end "
				+ "#end";
		
		VelocityContext headerContext = new VelocityContext();
		headerContext.put("iterator", null);
		Velocity.evaluate( headerContext, strWriter, "log tag name", templateStr);
		
		assertEquals(RESULT_NULL_INTERACTION, strWriter.toString());
	}
	/*		
	public void testWithoutIterator() {
		StringWriter strWriter = new StringWriter();
		
		final String templateStr = "Testing... "
				+ "#while( true )"
				+ " $iterator.next() #if ($iterator.hasNext()),#else!#end "
				+ "#end";
		
		VelocityContext headerContext = new VelocityContext();
		Velocity.evaluate( headerContext, strWriter, "log tag name", templateStr);
		
		assertEquals(RESULT_NULL_INTERACTION, strWriter.toString());
	}

	public void test() {
		StringWriter strWriter = new StringWriter();
		
		final String templateStr = "#set( $counter = 0 )"
				+ "Testing... "
				+ "#while( $counter < 10 )"
				+ " $counter "
				+ "#set( $counter = $counter + 1 )"
				+ "#end";
		
		VelocityContext headerContext = new VelocityContext();
		Velocity.evaluate( headerContext, strWriter, "log tag name", templateStr);
		
		assertEquals(RESULT_NULL_INTERACTION, strWriter.toString());
	}
*/
}
