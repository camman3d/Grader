package grader;

import util.misc.Console;
import bus.uigen.ObjectEditor;

public class ThreadKillerTest implements Runnable {
	Thread thread;
	public  ThreadKillerTest () {
		thread = new Thread(this);
		thread.setName("Looper");
		thread.start();
	}

	@Override
	public void run() {
		
		while (true) {
			try {
			System.out.println("Looping");
				System.out.println(Console.readLine());
			} catch (Exception e) {
				System.out.println("exiting");
				e.printStackTrace();
				break;
			}
			

		}
			
		
	}
	
	public void killThread() {
		thread.stop();
	}
	
	public static void main (String[] args) {
		ThreadKillerTest test = new ThreadKillerTest();
		ObjectEditor.edit(test);
	}
	

}
