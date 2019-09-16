package test;
import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import org.xmlunit.matchers.CompareMatcher;

public class XmlCompare {
	

    @Test
    public void testSame() throws SAXException, IOException {
    	
    	String actualFileName = "C://samples//actual.xml";
    	String expectedFileName = "C://samples//expected.xml";
    	
    	Source actualInput = getInput(actualFileName);
    	Source expectedInput = getInput(expectedFileName);

    	Diff myDiff = DiffBuilder.compare(actualInput)
    	              .withTest(expectedInput)
    	              .ignoreComments()
    	              .ignoreWhitespace()    	              
    	              .build();
    	
    	System.out.println(myDiff.getDifferences());
    	//Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
    	MatcherAssert.assertThat(actualInput,getCompareMatcher(expectedInput));    	
    }    
    
    public CompareMatcher getCompareMatcher(Source expectedInput) {
    	return CompareMatcher.isIdenticalTo(expectedInput)
    			.ignoreComments()
    			.ignoreWhitespace()
    			.normalizeWhitespace()
    			.throwComparisonFailure();
    }
    
	public Source getInput(String fileName) {
		File file = new File(fileName);
		return Input.fromFile(file).build();
	}

}