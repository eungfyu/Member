package com.webapp.threadlocal;

class Result {
	int sum;
}

//리턴값을 전역변수로 저장하려고 함
class Calculator {
	
	public Calculator() {
//		GlobalVariable.sum=0;
	}
	
	void summerize(Result result, int start, int end){
		for (int i=start;i<end;i++){
//			GlobalVariable.sum += i;
			result.sum += i;
		}
	}
	
	void multiplay(Result result, int mul) {
//		GlobalVariable.sum *= mul;
		result.sum *= mul;
	}
}

class MyThread extends Thread {
	@Override
	public void run() {
		Result r = new Result();
		Calculator c = new Calculator();
		c.summerize(r, 1, 11);
		
		try {
			Thread.sleep((int)(Math.random()*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.multiplay(r, 10);
		
//		System.out.println("sum ="+ GlobalVariable.sum);
		System.out.println("sum ="+ r.sum);
	}
}

public class ParameterTransferTest {

	public static void main(String[] args) {
		
		for (int i=0; i<5;i++){
			new MyThread().start();
		}
		
	}
	
	
}
