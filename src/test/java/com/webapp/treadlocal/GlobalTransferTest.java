package com.webapp.treadlocal;

class Result2 {
	int sum;
}

class Calcurator2 {
	
	public Calcurator2() {
	}
	
	void summerize(int start, int end) {
		for (int i=start; i<end; i++) {
			GlobalVariable.result.get().sum += i;
			try {
				Thread.sleep((int)(Math.random()*100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	void multiplay(int mul) {
		GlobalVariable.result.get().sum *= mul;
	}
	
}

class MyThread2 extends Thread {
	
	@Override
	public void run() {
		GlobalVariable.result.set(new Result2());
		
		Calcurator2 c = new Calcurator2();
		c.summerize(1, 11);
		
		try {
			Thread.sleep((int)(Math.random()*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.multiplay(10);
		
		System.out.println("sum = " + GlobalVariable.result.get().sum);
	}
}

public class GlobalTransferTest {

	public static void main(String[] args) {
		
		for (int i=0; i<50; i++)
			new MyThread2().start();
	}
	
}





