import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.lang.Math; 

public class Matriks{
	public int NBrsEff;				// attribut untuk menyimpan jumlah baris efektif	
	public int NKolEff;				// attribut untuk menyimpan jumlah kolom efektif
	public double [][] M;			// attribut untuk menyimpan elemen dari matriks ke dalam array 2D

	public Scanner input = new Scanner(System.in);

	public void MakeMatriks(int NB, int NK){
		// Method untuk konstuktor matriks dengan jumlah baris dan kolom yang diberikan
		this.M = new double [NB+1][NK+1];
		this.NBrsEff = NB;
		this.NKolEff = NK;
	}

	public void MakeMatriks(int NB, int NK, double[][] tab){
		// Method untuk konstuktor matriks dengan jumlah baris, kolom yang diberikan, dan array elemen yang ditentukan
		this.M = tab;
		this.NBrsEff = NB;
		this.NKolEff = NK;
	}

	public double Elmt(int i, int j){
		// selektor elemen dari matriks untuk index i,j
		return M[i][j];
	}

	public void SetElmt(int i, int j, double val){
		// setter elemen di matriks, untuk index i,j, dengan value yang ditentukan
		M[i][j] = val;
	}

	public int GetLastIdxBrs(){
		// getter untuk untuk mendapatkan last index baris matriks
		return NBrsEff;
	}

	public int GetLastIdxKol(){
		// getter untuk untuk mendapatkan last index kolom matriks
		return NKolEff;
	}

	public boolean IsSquare(){
		// Method untuk mengecek apakah suatu matriks, merupakan matriks persegi atau tidak
		return (NBrsEff==NKolEff);
	}

	public void BacaMatriks(){
		// Method untuk melakukan membaca matriks dari input terminal
		System.out.print("Masukkan jumlah baris: ");
		int NB = input.nextInt();
		System.out.print("Masukkan jumlah kolom: ");
		int NK = input.nextInt();
		MakeMatriks(NB,NK);
		System.out.println("Masukkan elemen matriksnya");
		BacaElemenMatriks(NB,NK);
	}

	public void BacaElemenMatriks(int NB, int NK){
		// Method untuk membaca elemen matriks
		for (int i = 1; i<=NB; ++i){
			for (int j= 1; j<=NK; ++j){
				SetElmt(i,j,input.nextDouble());
			}
		}
	}

	public void OutputMatriks(){
		// method untuk mengoutput matriks di terminal 
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j=1; j<=GetLastIdxKol(); ++j){
				System.out.printf("%.2f ",Elmt(i,j));
			}
			System.out.println();
		}
	}

	public void CopyTab(double[][] tabAwal, double[][] tabTarget, int sizeRow, int sizeCol){
		// method untuk mengcopy isi array tabAwal ke array tabTarget
		for (int i = 1; i<=sizeRow; ++i){
			for (int j = 1; j<=sizeCol; ++j){
				tabTarget[i][j] = tabAwal[i][j];
			}
		}
	}

	public void TukarBaris(int row1, int row2){
		// prosedur untuk swap semua elemen di row1 dan row2
		double temp;
		for (int j=1; j<=GetLastIdxKol(); ++j){
			temp = Elmt(row1,j);
			SetElmt(row1,j,Elmt(row2,j));
			SetElmt(row2,j,temp);
		}
	}

	public void AddBaris(int rowTarget, int rowAsal, double pengali){
		// prosedur untuk menambahkan semua elemen baris rowAsal ke rowTarget
		// elemen di rowTarget = elemen di rowAwal x pengali
		for (int j = 1; j<=GetLastIdxKol(); ++j){
			M[rowTarget][j] += pengali*M[rowAsal][j]; 
		}
	}

	public double[]	KaliMatriks1D(double[] M2)
	/*Menghasilkan matriks 1 dimensi untuk menghasilkan solusi spl
	Ukuran M1(this): M*N
	Ukuran M2: N*1 */
	{
		double[] res = new double[(this.NBrsEff+1)];

		for (int i=1; i<=this.NBrsEff; ++i){
			res[i] = 0;
			for (int j=1;j<=this.NKolEff; ++j){
				res[i] += (this.Elmt(i, j)*M2[j]);
			}
		}
		return res;
	}

	public void Tranpose(){
		// Prosedur untuk mentranpose Matrix
		double[][] temp = new double[NKolEff+1][NBrsEff+1];
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j = 1; j<=GetLastIdxKol(); ++j){
				temp[j][i]= Elmt(i,j);
			}
		}
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j = 1; j<=GetLastIdxKol(); ++j){
				SetElmt(i,j,temp[i][j]);
			}
		}
	}

	public void PerkecilMatriks(double [][] Makhir, double [][] Mawal, int rowIlang, int colIlang){
		// Prosedur untuk membuat matriks baru dengan menghilangkan baris dan kolom tertentu dari Matriks awal
		int rowNow =1, colNow = 1;
		for (int i = 1; i<=this.GetLastIdxBrs(); i++){
			if (i!=rowIlang){
				colNow = 1;
				for (int j = 1; j<=this.GetLastIdxKol(); ++j){
					if (j!=colIlang){
						Makhir[rowNow][colNow] = Mawal[i][j];
						colNow++;
					}
				}
				rowNow++;
			}
		}

	}


	public Matriks MatriksCofactor(){
		// Fungsi untuk menghasilkan matriks yang elemen-elemennya adalah kofaktor dari matriksnya
		Matriks newMat = new Matriks();
		newMat.MakeMatriks(NBrsEff, NKolEff);
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j = 1; j<=GetLastIdxKol(); ++j){
				Matriks tempMat = new Matriks();
				tempMat.MakeMatriks(NBrsEff-1, NKolEff-1);	
				this.PerkecilMatriks(tempMat.M,this.M,i,j);
				newMat.SetElmt(i,j,Math.pow(-1,i+j)*tempMat.Determinan());
			}
		}
		return newMat;
	}

	public double Determinan(){
		// Fungsi untuk menentukan determinan dari matriks dengan metode kofaktor
		if (this.NBrsEff==1 && this.NKolEff==1) return Elmt(1,1);
		else{
			double res=0.0;
			for (int j = 1 ; j<=this.GetLastIdxKol(); ++j){
				Matriks tempMat = new Matriks();
				tempMat.MakeMatriks(this.NBrsEff-1,this.NKolEff-1);
				this.PerkecilMatriks(tempMat.M,this.M,1,j);
				res+=Math.pow(-1,1+j)*tempMat.Determinan()*this.Elmt(1,j);
			}
			return res;
		}
	}

	public double DeterminanOBE(){
		// Fungsi untuk mencari determinan matrix dengan metode gauss
		Matriks newMat = new Matriks();
		newMat.MakeMatriks(this.GetLastIdxBrs(), this.GetLastIdxKol(), this.M);
		double res = 1;
		int rowNow = 1;
		for (int j = 1; j<=GetLastIdxKol(); ++j){
			if (rowNow>=GetLastIdxBrs()+1) break;
			int posNonZero=0;
			for (int i = rowNow; i<=GetLastIdxBrs(); ++i){
				if (newMat.Elmt(i,j)!=0){
					posNonZero = i;
					break;
				}
			}
			if (posNonZero!=0){
				newMat.TukarBaris(rowNow,posNonZero);	
				
				for (int k = rowNow+1; k<=GetLastIdxBrs(); ++k){
					newMat.AddBaris(k,rowNow,-Elmt(k,j)/Elmt(rowNow,j));
				}
				rowNow++;
			}
		}
		this.OutputMatriks();
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			double temp = Elmt(i,i);
			if (temp==0){
				return 0;
			}
			res*=temp;
		}
		return res;
	}

	public Matriks MatriksAdjoin(){
		//Fungsi untuk mereturn adjoin matriks
		Matriks newMat = this.MatriksCofactor();	// Membuat matriks kofaktor
		newMat.Tranpose();							// Mentranpose matriks kofaktor
		return newMat;
	}

	public Matriks MatriksInverseOBE(){
		// Fungsi untuk menghasilkan matrix inverse dengan metode operasi baris elementer dan Gauss
		Matriks newMat = new Matriks();
		newMat.MakeMatriks(NBrsEff,NKolEff*2);
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j = 1; j<=GetLastIdxKol(); ++j){
				newMat.SetElmt(i,j,Elmt(i,j));
			}
		}
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j = this.GetLastIdxKol()+1; j<=newMat.GetLastIdxKol(); ++j){
				newMat.SetElmt(i,j,0);
			}
			newMat.SetElmt(i,i+newMat.GetLastIdxBrs(),1);
		}

		newMat.GaussJordan();
		newMat.OutputMatriks();
		Matriks retMatriks = new Matriks();
		retMatriks.MakeMatriks(NBrsEff,NKolEff);
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j =1 ; j<=GetLastIdxKol(); ++j){
				retMatriks.SetElmt(i,j,newMat.Elmt(i,j+NKolEff));
			}
		}
		return retMatriks;
	}


	public Matriks MatriksInverse(){
		// Fungsi untuk mereturn inverse matriks dengan metode (1/det(A))*adj(A)
		// I.S : Matriks persegi yang invertible
		Matriks newMat = this.MatriksAdjoin();
		double det = this.Determinan();
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j = 1; j<=GetLastIdxKol(); ++j){
				newMat.M[i][j] /=  det;
			}
		}
		return newMat;
	}

	public double GetFirstNonZero(int row){
		// Fungsi untuk mencari ELEMEN pertama di baris 'row' yang tidak bernilai 0
		for (int j = 1; j<=GetLastIdxKol(); ++j){
			if (Elmt(row,j)!=0) return Elmt(row,j);
		}
		return 0;
	}

	public int GetPosFirstOne(int row){
		// Fungsi untuk mencari POSISI pertama angka 1 di baris 'row'
		for (int j = 1; j<=GetLastIdxKol(); ++j){
			if (Elmt(row,j)==1) return j;
		}
		return 0;
	}

	public void Gauss(){
		// prosedur untuk menjadikan matrix echelon form dengan eliminasi Gauss
		int rowNow = 1;
		for (int j = 1; j<=GetLastIdxKol()-1; ++j){
			if (rowNow>=GetLastIdxBrs()+1) break;
			int posNonZero=0;
			for (int i = rowNow; i<=GetLastIdxBrs(); ++i){
				if (Elmt(i,j)!=0){
					posNonZero = i;
					break;
				}
			}
			if (posNonZero!=0){
				TukarBaris(rowNow,posNonZero);	
				
				for (int k = rowNow+1; k<=GetLastIdxBrs(); ++k){
					AddBaris(k,rowNow,-Elmt(k,j)/Elmt(rowNow,j));
				}
				rowNow++;
			}
		}
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			double temp = GetFirstNonZero(i);
			if (temp!=0){
				for (int j = i; j<=GetLastIdxKol(); ++j ){
					SetElmt(i,j, Elmt(i,j)/temp);
				}	
			}	
		}
	}

	public void GaussJordan(){
		// prosedur untuk menjadikan matriks reduced echelon form dengan metode gauss jordan
		this.Gauss();
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			int pos = GetPosFirstOne(i);
			if (pos!=0){
				for (int k=1; k<=GetLastIdxBrs(); ++k){
					if (k!=i){
						double fiElmt = Elmt(k,pos);
						AddBaris(k,i,-fiElmt);
					}
				}
			}
		}

	}

	public void SwapCol(int col1, int col2){
		// prosedur untuk menukar semua elemen di kolom col1 dan col2
		double temp;
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			temp = Elmt(i,col1);
			SetElmt(i,col1,Elmt(i,col2));
			SetElmt(i,col2,temp);
		}
	}

	public double[] Cramer(){
		// prosedur yang menghasilkan solusi persamaan dengan metode kramer
		// FS: array 1d yang berisi solusi x1,x2,...,xn
		double[] hasil = new double[NKolEff];
		int posSolusi = 1;
		this.NKolEff--;
		double det = this.Determinan();
		this.NKolEff++;
		for (int j = 1; j<=GetLastIdxKol()-1; ++j){
			
			this.SwapCol(GetLastIdxKol(),j);
			this.NKolEff--;
			double detNow = this.Determinan();
			this.NKolEff++;
			this.SwapCol(GetLastIdxKol(),j);
			hasil[posSolusi] = detNow/det;
			posSolusi++;
		}
		return hasil;
	}

	public void BacaFileMatriks(String namaFile) throws FileNotFoundException{
		// prosedur untuk melakukan pembacaan matriks dari file yang ditentukan
		Scanner input = new Scanner(new FileReader(namaFile));
		double [][] inputArray = new double[100][100];
		int i = 1, row=0, col = 0;
		while(input.hasNextLine()){
			String[] line = input.nextLine().trim().split(" ");

			for (int j = 0; j<line.length; j++){
				inputArray[i][j+1] = Double.parseDouble(line[j]);
				if (i==1)col++;
			}
			i++;
			row++;
		}
		input.close();
		MakeMatriks(row,col,inputArray);
	}

	public void TulisFileMatriks(String namaFile) throws IOException{
		// prosedur untuk menulis matriks ke dalam file yang ditentukan
		File fout = new File(namaFile);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		int i,j;
		DecimalFormat df = new DecimalFormat("##.00");
   		
		for (i = 1; i<=GetLastIdxBrs(); ++i){
			for (j = 1; j<=GetLastIdxKol(); ++j){
				String dFormated = df.format(Elmt(i,j));
				bw.write(dFormated);
				bw.write(" ");
			}
			bw.newLine();
		}
		bw.close();
	}
}