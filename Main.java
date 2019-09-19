import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main{
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args){


		// System.out.println("Hello World!");
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
		

		// double []hasil = mat.Cramer();
		// for (int i = 1; i<=mat.GetLastIdxKol()-1; ++i){
		// 	System.out.println(hasil[i]);
		// }
		/*	Untuk mendebug Spl
		Spl data = new Spl();
		data.BacaSPL();
		int pilihan;
		do{
			pilihan = input.nextInt();
			if (pilihan>0){
				Spl temp = data;
				temp.PenyelesaianSPL(pilihan);
				temp.OutputSPL();
			}
		}while(pilihan>0);*/
	}	

}