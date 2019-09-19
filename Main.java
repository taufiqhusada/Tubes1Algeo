import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main{
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args){
<<<<<<< HEAD


		// System.out.println("Hello World!");
=======
		/*System.out.println("Hello World!");
>>>>>>> 1fc48d59c7e976ebae9bb786ff3a20f79fa969d6
		Matriks mat = new Matriks();
		mat.BacaMatriks();
		mat.OutputMatriks();
		System.out.println(mat.DeterminanOBE());

		
		// Matriks mat1 = new Matriks();
		// mat1.MakeMatriks(mat.GetLastIdxBrs(), mat.GetLastIdxKol(), mat.M);

		
		// Matriks matInverse2 = mat1.MatriksInverseOBE();
		// matInverse2.OutputMatriks();

		// System.out.println("==========");
		// Matriks matInverse = mat.MatriksInverse();
		// matInverse.OutputMatriks();
		

<<<<<<< HEAD
		// double []hasil = mat.Cramer();
		// for (int i = 1; i<=mat.GetLastIdxKol()-1; ++i){
		// 	System.out.println(hasil[i]);
		// }
		/*	Untuk mendebug Spl
=======
		double []hasil = mat.Cramer();
		for (int i = 1; i<=mat.GetLastIdxKol()-1; ++i){
			System.out.println(hasil[i]);
		}
			Untuk mendebug Spl*/
>>>>>>> 1fc48d59c7e976ebae9bb786ff3a20f79fa969d6
		Spl data = new Spl();
		data.BacaSPL();
		int pilihan;
		do{
			pilihan = input.nextInt();
			if (pilihan>0){
				Spl temp = data;
				temp.PenyelesaianSPL(pilihan);
				temp.OutputSPL();
				temp.OutputMatriks();
			}
		}while(pilihan>0);
	}	

}