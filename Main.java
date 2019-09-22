import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.*;


public class Main{
	static Scanner input = new Scanner(System.in);
	public static int valUndef = 9999;

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

	public static void metodeOutput(){
		System.out.println("Apakah ingin di save ke file?");
		System.out.println("1. iya");
		System.out.println("2. tidak");
	}

	public static void metodeInput(){
		System.out.println("Metode input apa yang ingin digunakan?");
		System.out.println("1. input dari terminal");
		System.out.println("2. input dari file .txt");
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		int pil;
		do{
			menu();
			pil = input.nextInt();	
			if (pil!=7) System.out.println();

			// SPL
			if (pil==1){
				Spl spl = new Spl();
				Spl temp = new Spl();
				int met = valUndef;
				spl.BacaSPL();
				temp.NPers=spl.NPers;
				temp.NPeubah = spl.NPeubah;
				temp.MakeMatriks(spl.NBrsEff, spl.NKolEff);
				do{
					metode();
					met = input.nextInt();
					if (met>0){
						temp.CopyTab(spl.M, temp.M, spl.NBrsEff, spl.NKolEff);
						temp.PenyelesaianSPL(met);
						temp.OutputSPL();
						temp.OutputMatriks();
					}
				}while (!(met>0));
			}

			// determinan
			else if (pil==2){
				Matriks mat = new Matriks();

				metodeInput();
				int metInput = input.nextInt();
				if (metInput==1) mat.BacaMatriks();
				else{
					System.out.print("Masukkan nama file: ");
					String inFile = input.nextLine();
					mat.BacaFileMatriks(inFile);
				}

				int met = valUndef;
				if (mat.IsSquare()){
					do{
						System.out.print("Pilihan metode (1. Metode kofaktor, 2. Metode eliminasi Gauss): ");
						met = input.nextInt();	
						if (met==1){
							System.out.print("determinan matriks tsb adalah: ");
							System.out.println(mat.Determinan());
							
						}
						else if (met==2){
							System.out.print("determinan matriks tsb adalah: ");
							System.out.println(mat.DeterminanOBE());
						}
						else{
							System.out.println("Masukan salah, ulangi masukan");
						}
					}while (!(met>0 && met<=2));
				}
				else{
					System.out.println("Matriks ini bukan matriks persegi, sehingga tidak punya determinan");
				}
			}

			// inverse
			else if (pil==3){
				Matriks mat = new Matriks();

				metodeInput();
				int metInput = input.nextInt();
				if (metInput==1) mat.BacaMatriks();
				else{
					System.out.print("Masukkan nama file: ");
					String inFile = input.nextLine();
					mat.BacaFileMatriks(inFile);
				}

				if (mat.IsSquare() && mat.Determinan()!=0){
					int met = valUndef;
					do{
						System.out.print("Pilihan metode (1. Metode adjoin, 2. Metode eliminasi Gauss): ");
						
						met = input.nextInt();	
						if (met==1){
							Matriks matInv = mat.MatriksInverse();
							System.out.println("matriks inversenya sebagai berikut");
							matInv.OutputMatriks();
						}
						else if (met==2){
							Matriks matInv = mat.MatriksInverseOBE();
							System.out.println("matriks inversenya sebagai berikut");
							matInv.OutputMatriks();
						}
						else{
							System.out.println("Masukan salah, ulangi masukan");
						}
					}while (!(met>0 && met<=2));
				}
				else{
					System.out.println("Matriks ini bukan matriks persegi/ determinan = 0, sehingga tidak punya inverse");	
				}
			}

			// kofaktor
			else if (pil==4){
				Matriks mat = new Matriks();
				
				metodeInput();
				int metInput = input.nextInt();
				if (metInput==1) mat.BacaMatriks();
				else{
					System.out.print("Masukkan nama file: ");
					String inFile = input.nextLine();
					mat.BacaFileMatriks(inFile);
				}

				if (mat.IsSquare()){
					Matriks matCof = mat.MatriksCofactor();
					System.out.println("Berikut adalah matriks kofaktornya: ");
					matCof.OutputMatriks();
				}
				else{
					System.out.println("Matriks ini bukan matriks persegi, sehingga tidak punya matriks kofaktor");	
				}
			}
			
			// adjoin
			else if (pil==5){
				Matriks mat = new Matriks();

				metodeInput();
				int metInput = input.nextInt();
				if (metInput==1) mat.BacaMatriks();
				else{
					System.out.print("Masukkan nama file: ");
					String inFile = input.nextLine();
					mat.BacaFileMatriks(inFile);
				}

				if (mat.IsSquare()){
					Matriks matAdj = mat.MatriksAdjoin();
					System.out.println("Berikut adalah matriks adjoinnya: ");
					matAdj.OutputMatriks();
				}
				else{
					System.out.println("Matriks ini bukan matriks persegi, sehingga tidak punya matriks kofaktor");	
				}
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