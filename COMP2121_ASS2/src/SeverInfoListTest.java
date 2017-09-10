import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SeverInfoListTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void test_default() {
		ServerInfoList server = new ServerInfoList();
		server.initialiseFromFile("ServerInfoFile");
		ArrayList<ServerInfo> list = server.getServerInfos();
		for(ServerInfo si : list)
			if(si == null)
				continue;
			else 
				System.out.printf("%s: %s\n", si.getHost(), si.getPort());
		System.out.println(server.toString());
		assertEquals(3, list.size());
		assertNull(list.get(0));
		assertNull(list.get(1));
		ServerInfo info = list.get(2);
		assertEquals(8333, info.getPort());
		assertEquals("localhost", info.getHost());
		
	}

}
