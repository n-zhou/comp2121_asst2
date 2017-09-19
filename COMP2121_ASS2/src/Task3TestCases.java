import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class Task3TestCases {

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
	public void test_config() {
		ServerInfoList server = new ServerInfoList();
		server.initialiseFromFile("ServerInfoFilepp");
		ArrayList<ServerInfo> list = server.getServerInfos();
		assertEquals(4, list.size());
		assertNull(list.get(0));
		assertNull(list.get(1));
		ServerInfo info = list.get(2);
		assertEquals(8333, info.getPort());
		assertEquals("localhost", info.getHost());
		info = list.get(3);
		assertEquals(8334, info.getPort());
		assertEquals("localhost", info.getHost());
	}
	public void test() {
		fail("Not yet implemented");
	}

}
