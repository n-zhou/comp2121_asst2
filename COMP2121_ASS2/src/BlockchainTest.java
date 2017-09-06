import static org.junit.Assert.*;

import org.junit.Test;

public class BlockchainTest {

	public class Bryn implements Runnable {
		
		private Blockchain blockchain;
		
		public Bryn(Blockchain blockchain) {
			this.blockchain = blockchain;
		}
		
		@Override
		public void run() {
			
			blockchain.addTransaction("tx|aaaa0000|testingload " + new java.util.Random().nextInt());
		}
	}
	
	@Test
	public void test_one_transaction() {
		
	}
	
	@Test(timeout=100)
	public void test_two_transactions() throws InterruptedException {
		Bryn bryn = new Bryn(new Blockchain());
		Thread thread[] = new Thread[2];
		for(int i = 0; i < 2; i++)
			thread[i] = new Thread(bryn);
		for(Thread t : thread)
			t.start();
		for(Thread t : thread)
			t.join();
	}
	
	@Test(timeout=1000)
	public void test_100_transactions() throws InterruptedException {
		Blockchain blockchain = new Blockchain();
		Bryn bryn = new Bryn(blockchain);
		Thread thread[] = new Thread[100];
		for(int i = 0; i < 100; i++)
			thread[i] = new Thread(bryn);
		for(Thread t : thread)
			t.start();
		for(Thread t : thread)
			t.join();
		System.out.println(blockchain.toString());
	}
	
	@Test(timeout=1000)
	public void test_1000_transactions() throws InterruptedException {
		Blockchain blockchain = new Blockchain();
		Bryn bryn = new Bryn(blockchain);
		Thread thread[] = new Thread[1000];
		for(int i = 0; i < 1000; i++)
			thread[i] = new Thread(bryn);
		for(Thread t : thread)
			t.start();
		for(Thread t : thread)
			t.join();
		System.out.println(blockchain.toString());
	}
	
	@Test(timeout=10000)
	public void test_10000_transactions() throws InterruptedException {
		Blockchain blockchain = new Blockchain();
		Bryn bryn = new Bryn(blockchain);
		Thread thread[] = new Thread[10000];
		for(int i = 0; i < 10000; i++)
			thread[i] = new Thread(bryn);
		for(Thread t : thread)
			t.start();
		for(Thread t : thread)
			t.join();
		System.out.println(blockchain.toString());
	}
	
	@Test(timeout=20000)
	public void test_100000_transactions() throws InterruptedException {
		Blockchain blockchain = new Blockchain();
		Bryn bryn = new Bryn(blockchain);
		Thread thread[] = new Thread[100000];
		for(int i = 0; i < 100000; i++)
			thread[i] = new Thread(bryn);
		for(Thread t : thread)
			t.start();
		for(Thread t : thread)
			t.join();
	}

}
