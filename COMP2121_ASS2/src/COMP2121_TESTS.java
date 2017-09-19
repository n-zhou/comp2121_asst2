import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class COMP2121_TESTS extends Thread {
	
	public final InputStream original = System.in;
	private int mode = 0;
	
	public COMP2121_TESTS() {}
	
	
	public void run() {
		
	}
	
	@Test
	public void test_default_serverInfoList() {
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
	public void test_interleaving_serverInfoList(){
		ServerInfoList si = new ServerInfoList();
		si.initialiseFromFile("ServerInfoFileTheSecond");
		List<ServerInfo> list = si.getServerInfos();
		assertEquals(4, list.size());
		for(int i = 0; i < 2; i++)
			assertNull(list.get(i));
		for(int i = 2; i < 4; i++)
			assertNotNull(list.get(i));
		assertEquals(8333, list.get(2).getPort());
		assertEquals(8334, list.get(3).getPort());
	}

	@Test
	public void test_trickyInput_serverInfoList() {
		try {
			ServerInfoList si = new ServerInfoList();
			si.initialiseFromFile("TrickyFile");
			assertEquals(10, si.getServerInfos().size());
			for(int i = 1; i < si.getServerInfos().size(); i++) {
				assertNull(si.getServerInfos().get(i));
			}
			ServerInfo info = si.getServerInfos().get(0);
			assertNotNull(info);
			assertEquals("123.123.123.123", info.getHost());
			assertEquals(2025, info.getPort());
		}
		catch(Exception e) {
			fail("Exception was not handled");
		}
		
	}
	
	@Test
	public void test_BlockchainClient() {
		
		try {	
			String input = "ls\n";
			input += "rm|10\n";
			input += "rm|-1\n";
			input += "cl\n";
			input += "tx|aaaa1111|fff\n";
			input += "ad|\n";
			input += "ad|ben|-1\n";
			input += "up|111|localhost|3000\n";
			input += "up|1|localhost|3000|ffff\n";
			input += "pb|-1\n";
			input += "pb|100\n";
			input += "sd\n";
			System.setIn(new ByteArrayInputStream(input.getBytes()));
			BlockchainClient.main(new String[] {"ServerInfoFile"});	
		}
		catch(Exception e) {
			System.err.println(e);
			e.printStackTrace();
			fail("Exceptions were caught");
		}
		finally {
			System.setIn(original);
		}
	}
	
}
