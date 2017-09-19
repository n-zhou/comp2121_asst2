import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

public class SeverInfoListTest {
	
	@Test
	public void test_default() {
		ServerInfoList server = new ServerInfoList();
		server.initialiseFromFile("ServerInfoFile");
		ArrayList<ServerInfo> list = server.getServerInfos();
		assertEquals(3, list.size());
		assertNull(list.get(0));
		assertNull(list.get(1));
		ServerInfo info = list.get(2);
		assertEquals(8333, info.getPort());
		assertEquals("localhost", info.getHost());
		
	}

	@Test
	public void test_BlockchainClient() {
		InputStream original = System.in;
		try {
			String input = "ls\n";
			input += "rm|10000\n";
			input += "rm|-1\n";
			input += "cl\n";
			input += "sd\n";
			System.setIn(new ByteArrayInputStream(input.getBytes()));
			BlockchainClient.main(new String[] {"ServerInfoFile"});	
		}
		catch(Exception e) {
			
		}
		finally {
			System.setIn(original);
		}
	}
	
	@Test
	public void test_BlockchainServer() {
		
	}
}
