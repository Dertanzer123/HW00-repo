package Hw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

	String dirpath1;//dirpath for highscoretable
	String dirpath2;//dirpath for animalnames
	Random rnd = new Random();

	CQueue QName;
	CQueue QScore;

	Stack AnimalStack = new Stack(14);
	Stack LetterStack = new Stack(26);
	Stack WordStack;
	Stack BoardStack;
	Stack MissingLetterStack = new Stack(26);

	Stack transferStack = new Stack(50);// this is an tool for every other stack and its capacity should at least equal
	Stack transferStack2= new Stack(50);								// the biggest other Stack
	boolean[] control1;

	boolean flag1;
	boolean flag2;
	boolean flag3;
	boolean replay;

	int index1;
	int hp;

	public Main() throws IOException {

		/////////////////////////// ----(1) in this part data from files are coppied to
		/////////////////////////// queues and stacks
		FileReader Fr1 = new FileReader(dirpath1);
		BufferedReader Br1 = new BufferedReader(Fr1);
		String line;
		int Qlenght = 0;
		int max = 0;
		while ((line = Br1.readLine()) != null) {
			Qlenght++;
			if (max < Integer.parseInt(line.trim().split(" ")[1])) {
				max = Integer.parseInt(line.trim().split(" ")[1]);
			}
		}
		Br1.close();
		Fr1.close();
		Fr1 = new FileReader(dirpath1);
		Br1 = new BufferedReader(Fr1);
		QName = new CQueue(Qlenght+10);
		QScore = new CQueue(Qlenght+10);
		Br1 = new BufferedReader(Fr1);
		while ((line = Br1.readLine()) != null) {
			QName.Qadd(line.trim().split(" ")[0]);
			QScore.Qadd(Integer.parseInt(line.trim().split(" ")[1]));

		}
		Br1.close();
		Fr1.close();
		while (!QScore.isempty()) {
			for (int i = QScore.size(); i > 0; i--) {
				if (QScore.Peek().equals(max)) {
					transferStack.Push(QScore.Qtake());
					transferStack.Push(QName.Qtake());
				} else {
					QScore.Qadd(QScore.Qtake());
					QName.Qadd(QName.Qtake());
				}
			}
			max = 0;
			for (int i = QScore.size(); i > 0; i--) {
				if (max < (int) QScore.Peek()) {
					max = (int) QScore.Peek();
				}
				QScore.Qadd(QScore.Qtake());
			}
		}
		while(!transferStack.isempty()) 
		{
			transferStack2.Push(transferStack.Pop());
			
		}
		while(!transferStack2.isempty()) 
		{
			QScore.Qadd(transferStack2.Pop());
			QName.Qadd(transferStack2.Pop());
		}
		transferStack2=null;
		FileReader Fr2 = new FileReader(dirpath2);
		BufferedReader Br2 = new BufferedReader(Fr2);
		while ((line = Br2.readLine()) != null) {

			AnimalStack.Push(line.trim().toLowerCase());

		}
		Br2.close();
		Fr2.close();
		////////////////////////////// ------(1)
		///////////////////////////// --------(2) this section will prepare the stacks
		////////////////////////////// for the game and its should be remaked before
		////////////////////////////// every game
		displayscores();
		Scanner scan1 = new Scanner(System.in);
		replay = true;
		while (replay) {
			start: ;
			for (int i = rnd.nextInt(14); i > 0; i--) {
				transferStack.Push(AnimalStack.Pop());
			}
			line = (String) AnimalStack.Peek();
			while (!transferStack.isempty()) {
				AnimalStack.Push(transferStack.Pop());
			}
			int wordsize = line.trim().length();
			WordStack = new Stack(wordsize);
			BoardStack = new Stack(wordsize);
			control1 = new boolean[wordsize];
			for (char a : line.trim().toCharArray()) {
				WordStack.Push((char) (a - 32));
				BoardStack.Push('-');
			}
			for (int i = 65; i < 91; i++) {
				LetterStack.Push((char) i);
			}

			///////////////////////////// --------(2)
			///////////////////////////// ---------(3)this section is main part of the game
			///////////////////////////// where we take input check it and refine stacks

			char a = 0;
			hp = 120;
			while (true) {
				if (iswinned()) {
					System.out.println("Woooleeyoo yoo winn !!!");
					displayStack(BoardStack);
					System.out.print("     usable letters:");
					displayStack(LetterStack);
					System.out.print("     missed letters:");
					displayStack(MissingLetterStack);
					System.out.print("     Hp : " + hp);
					System.out.println();
					if (end(true, scan1)) {
						replay = true;
					} else {
						replay = false;
					}
					break;

				} else if (hp <= 0) {
					System.out.println("Hah loser!!");
					displayStack(BoardStack);
					System.out.print("     usable letters:");
					displayStack(LetterStack);
					System.out.print("     missed letters:");
					displayStack(MissingLetterStack);
					System.out.print("     Hp : " + hp);
					System.out.println();
					if (end(false, scan1)) {
						replay = true;
					} else {
						replay = false;
					}
					break;
				} else {
					while (true) {// flag1 is used to know is input letter used allready,flag2 is used for knowing
									// is input a letter or naa

						System.out.println("guees an letter , find which animal it is");
						displayStack(BoardStack);
						System.out.print("     usable letters:");
						displayStack(LetterStack);
						System.out.print("     missed letters:");
						displayStack(MissingLetterStack);
						System.out.print("     Hp : " + hp);
						System.out.println();
						flag3 = true;
						while (flag3) {
							try {
								flag3 = false;
								a = scan1.nextLine().charAt(0);
							} catch (Exception e) {
								System.out.println("please enter something dont just push the enter button.");
								flag3 = true;
							}
						}
						if (a < 65 || a > 90) {
							a -= 32;
						}
						flag1 = true;
						flag2 = true;
						if (a < 65 || a > 90) {
							flag2 = false;
						}
						// <check is a == any missingletter
						while (!MissingLetterStack.isempty()) {

							if (MissingLetterStack.Peek().equals(a)) {
								flag1 = false;
								break;
							}
							transferStack.Push(MissingLetterStack.Pop());

						}
						while (!transferStack.isempty()) {
							MissingLetterStack.Push(transferStack.Pop());
						}
						// <
						// <check is a == any boardstack letter
						while (!BoardStack.isempty()) {

							if (BoardStack.Peek().equals(a)) {
								flag1 = false;
								break;
							}
							transferStack.Push(BoardStack.Pop());

						}
						while (!transferStack.isempty()) {
							BoardStack.Push(transferStack.Pop());
						}
						// <
						if (!flag2) {
							System.out.println("this input is not valid!!");
						} else if (!flag1) {
							System.out.println("you already tried this letter!!");
						} else {
							break;// if input is a unused letter this while will be breaked
						}
					}
					flag1 = true;////// flag1 in here used for is a is a missedletter or naa

					// < check a==letterstack letters remove equal one
					while (!LetterStack.isempty()) {
						if (LetterStack.Peek().equals(a)) {
							LetterStack.Pop();
							break;
						}
						transferStack.Push(LetterStack.Pop());
					}
					while (!transferStack.isempty()) {
						LetterStack.Push(transferStack.Pop());
					}
					// <
					control1 = new boolean[wordsize];
					index1 = 0;
					// <check a == any wordstack letter mark index
					while (!WordStack.isempty()) {

						if (WordStack.Peek().equals(a)) {
							flag1 = false;
							control1[index1] = true;
						}
						transferStack.Push(WordStack.Pop());
						index1++;
					}
					while (!transferStack.isempty()) {
						WordStack.Push(transferStack.Pop());
					}
					// <
					index1 = 0;
					// <check any marked index == current index if equal replace current element at
					// index with a
					while (!BoardStack.isempty()) {

						if (control1[index1]) {

							transferStack.Push(a);
							BoardStack.Pop();
						} else {
							transferStack.Push(BoardStack.Pop());
						}

						index1++;
					}
					while (!transferStack.isempty()) {
						BoardStack.Push(transferStack.Pop());
					}
					// <
					if (flag1) {
						System.out.println("you guest it wrong haha!");
						MissingLetterStack.Push(a);
						if (a == 'A' || a == 'E' || a == 'I' || a == 'O' || a == 'U') {
							hp -= 15;
						} else {
							hp -= 20;
						}
					}
				}
			}
		}
		///////////////////////////// ---------(3)

	}

	public void displayStack(Stack input) {
		while (!input.isempty()) {
			transferStack.Push(input.Pop());

		}
		while (!transferStack.isempty()) {
			System.out.print(transferStack.Peek());
			input.Push(transferStack.Pop());

		}

	}

	public boolean iswinned() {
		while (!BoardStack.isempty()) {
			transferStack.Push(BoardStack.Pop());
		}
		boolean flager = true;
		while (!transferStack.isempty()) {
			BoardStack.Push(transferStack.Pop());
			if (BoardStack.Peek().equals('-')) {
				flager = false;
			}
		}
		return flager;
	}

	public boolean end(boolean fate, Scanner scan)// if winned fate== true else false
	{
		if (fate) {
			System.out.println("brawoooo my friend you soo good play brawoo, i'll put your name on board.whats your name?");
			String name;
			do
			{
				name=scan.nextLine();	
			}while(name.trim()==null);
			arangeelement(hp, name);
			displayscores();
			System.out.println("you already win but you can play again.");
		} else {
			System.out.println("i didnot see anybody worse like you hahaha!");
			System.out.println("anyway do you want to try again maybe i lough more.");
		}
		String line;
		while (true) {
			line = scan.nextLine();
			if (line.trim().toLowerCase().equals("yes")) {
				System.out.println("you make the right desicion.");
				return true;
			} else if (line.trim().toLowerCase().equals("no")) {
				System.out.println("okkey see you then");
				return false;
			} else {
				System.out.println("please say yes or no,i dont understant any other.");

			}
		}
	}

	public void arangeelement(int score, String name) {
		boolean flag4=true;
		for (int i = QScore.size(); i > 0; i--) {
			
			if(flag4&&score>(int)QScore.Peek())
			{
				QScore.Qadd(score);
				QName.Qadd(name);
				
				flag4=false;
			}
			QScore.Qadd(QScore.Qtake());
			QName.Qadd(QName.Qtake());
			
		}

	}

	public void displayQueue(CQueue input) {
		for (int i = input.size(); i > 0; i--) {
			System.out.print(" "+input.Peek()+" ");
			
			input.Qadd(input.Qtake());
		}
		
	}
	public void displayscores() {
		for (int i = QScore.size(); i > 0; i--) {
			System.out.println("||"+QScore.Peek()+" : "+QName.Peek());
			
			
			QScore.Qadd(QScore.Qtake());
			QName.Qadd(QName.Qtake());
		}
		
	}
}
