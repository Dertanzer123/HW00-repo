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

	CQueue QName = new CQueue(12);
	CQueue QScore = new CQueue(12);

	Stack AnimalStack = new Stack(14);
	Stack LetterStack = new Stack(26);
	Stack WordStack;
	Stack BoardStack;
	Stack MissingLetterStack = new Stack(26);

	Stack transferStack = new Stack(40);// this is an tool for every other stack and its capacity should at least equal
										// the biggest other Stack

	boolean flag1;
	boolean flag2;

	public Main() throws IOException {

		/////////////////////////// ----(1) in this part data from files are coppied to
		/////////////////////////// queues and stacks
		FileReader Fr1 = new FileReader(dirpath1);
		BufferedReader Br1 = new BufferedReader(Fr1);
		String line;
		while ((line = Br1.readLine()) != null) {
			QScore.Qadd(Integer.parseInt(line.trim().split(" ")[1]));
			QName.Qadd(line.trim().split(" ")[0]);

		}
		Br1.close();
		Fr1.close();

		FileReader Fr2 = new FileReader(dirpath2);
		BufferedReader Br2 = new BufferedReader(Fr2);
		while ((line = Br2.readLine()) != null) {

			AnimalStack.Push(line.trim());

		}
		Br2.close();
		Fr2.close();
		////////////////////////////// ------(1)
		///////////////////////////// --------(2) this section will prepare the stacks
		////////////////////////////// for the game and its should be remaked before
		////////////////////////////// every game
		for (int i = rnd.nextInt(14); i > 0; i--) {
			transferStack.Push(AnimalStack.Pop());
		}
		line = (String) AnimalStack.Peek();
		while (!transferStack.isempty()) {
			AnimalStack.Push(transferStack.Pop());
		}
		WordStack = new Stack(line.trim().length());
		BoardStack = new Stack(line.trim().length());
		for (char a : line.trim().toCharArray()) {
			WordStack.Push(a);
			BoardStack.Push('-');
		}
		for (int i = 65; i < 91; i++) {
			LetterStack.Push((char) i);
		}

		///////////////////////////// --------(2)
		///////////////////////////// ---------(3)
		Scanner scan1 = new Scanner(System.in);
		char a;
		
		while (true) {
			System.out.println("guees an letter , find which animal it is");
			displayStack(BoardStack);
			System.out.print("     usable letters:");
			displayStack(LetterStack);
			System.out.print("     used letters:");
			displayStack(MissingLetterStack);
			System.out.println();
			a = scan1.nextLine().toUpperCase().charAt(0);
			flag1 = true;
			flag2 = true;
			if (a < 65 || a > 90) {
				flag2 = false;
			}

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
			if (!flag2) {
				System.out.println("this input is not valid!!");
			} else if (!flag1) {
				System.out.println("you already tried this letter!!");
			} else {

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

}
