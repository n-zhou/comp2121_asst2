public class WhiteBoxTesting {

  @Test
  public void test_serverInfoList() {
    ServerInfoFile sif = new ServerInfoFile();
    try{
      sif.initialiseFromFile("");
    }
    catch(Exception e e){
      fail();
    }

  }

  @Test
  public void test_client() {
    File file = null;
    try {

    }
    catch(Exception e) {
      
    }
  }

}
