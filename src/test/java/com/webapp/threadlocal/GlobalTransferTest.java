package com.webapp.threadlocal;

class Result2 {
	int sum;
}

//리턴값을 전역변수로 저장하려고 함
class Calculator2 {
	
	public Calculator2() {
//		GlobalVariable.sum=0;
	}
	
	void summerize(int start, int end){
		for (int i=start;i<end;i++){
			GlobalVariable.result.get().sum += i;
			try {//계산할때 잠깐 딜레이 줬음
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
		GlobalVariable.result.set(new Result2());//트랜잭션 시작
		Calculator2 c = new Calculator2();
		c.summerize(1, 11);//다른 클래스에서 그 트랜잭션 사용
		
		try {
			Thread.sleep((int)(Math.random()*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.multiplay(10);//다른 클래스에서 그 트랜잭션 사용
		
		System.out.println("sum ="+ GlobalVariable.result.get().sum);//그 결과 출력
	}
}

public class GlobalTransferTest {

	public static void main(String[] args) {
		
		for (int i=0; i<5;i++){
			new MyThread2().start();
		}
		
	}
	
	
}
