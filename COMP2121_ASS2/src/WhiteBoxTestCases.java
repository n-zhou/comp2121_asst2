import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.omg.CORBA.portable.InputStream;

public class WhiteBoxTestCases {

	@Test
	public void test_client() {
		BlockchainClient.main(new String[] {});
		InputStream og =  System.in;
		
		try {
			final FileInputStream file = new FileInputStream(new File("TestClientFile"));
			System.setIn(file);
		} catch (FileNotFoundException e) {
			System.out.println("Test Case Files missing");
		}
		
		
		
		System.setIn(og);
	}
	
	@Test
	public void test_server() {
		try {
			BlockchainServer.main(new String[] {"95555555"});
		}
		catch(java.lang.IllegalArgumentException e) {
			fail("java.lang.IllegalArgumentException was not caught");
		}
		
	}

}
