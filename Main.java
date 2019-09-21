import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main{
	static Scanner input = new Scanner(System.in);

	public static void menu(){
		System.out.println("Selamat datang di operasi-operasi matriks");
		System.out.println("Menu:");
		System.out.println("1. Sistem Persamaan Linier");
		System.out.println("2. Determinan");
		System.out.println("3. Matriks Balikan");
		System.out.println("4. Matriks Kofaktor");
		System.out.println("5. Adjoin");
		System.out.println("6. Interpolasi Polinom");
		System.out.println("7. Keluar");
	}

	public static void metode(){
		System.out.println("Metode apa yang ingin digunakan?");
		System.out.println("1. Metode Eliminasi Gauss");
		System.out.println("2. Metode Eliminasi Gauss Jordan");
		System.out.println("3. Metode Matriks Balikan");
		System.out.println("4. Kaidah Cramer");
	}
	public static void main(String[] args){
		int pil;
		do{
			menu();
			pil = input.nextInt();
			if (pil!=7) System.out.println();

			if (pil==1){
				Spl spl = new Spl();
				Spl temp = new Spl();
				int met;
				spl.BacaSPL();
				temp.NPers=spl.NPers;
				temp.NPeubah = spl.NPeubah;
				temp.MakeMatriks(spl.NBrsEff, spl.NKolEff);
				do{
					metode();
					met = input.nextInt();
					System.out.println();

					if (met>0){
						temp.CopyTab(spl.M, temp.M, spl.NBrsEff, spl.NKolEff);
						temp.PenyelesaianSPL(met);
						temp.OutputSPL();
						temp.OutputMatriks(); //buat debug matriks
					}
				}while (met>0);
			}
		}while(pil!=7);

		/*Matriks mat = new Matriks();
		mat.BacaMatriks();
		mat.OutputMatriks();
		System.out.println(mat.DeterminanOBE());*/

		
		// Matriks mat1 = new Matriks();
		// mat1.MakeMatriks(mat.GetLastIdxBrs(), mat.GetLastIdxKol(), mat.M);

		
		// Matriks matInverse2 = mat1.MatriksInverseOBE();
		// matInverse2.OutputMatriks();

		// System.out.println("==========");
		// Matriks matInverse = mat.MatriksInverse();
		// matInverse.OutputMatriks();
		
	}
}