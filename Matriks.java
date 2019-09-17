import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Math; 

// determinan done
// kofaktor done
// adj done
// gauss
// gaussjordan
// cramer
// invers


public class Matriks{
	int NBrsEff;
	int NKolEff;
	public double [][] M;

	public Scanner input = new Scanner(System.in);

	public void MakeMatriks(int NB, int NK){
		M = new double [NB+1][NK+1];
		NBrsEff = NB;
		NKolEff = NK;
	}

	public double Elmt(int i, int j){
		return M[i][j];
	}

	public void SetElmt(int i, int j, double val){
		M[i][j] = val;
	}

	public int GetLastIdxBrs(){
		return NBrsEff;
	}

	public int GetLastIdxKol(){
		return NKolEff;
	}

	public boolean IsSquare(){
		return (NBrsEff==NKolEff);
	}

	public void BacaMatriks(){
		System.out.print("Masukkan jumlah baris: ");
		int NB = input.nextInt();
		System.out.print("Masukkan jumlah kolom: ");
		int NK = input.nextInt();
		MakeMatriks(NB,NK);
		System.out.println("Masukkan elemen matriksnya");
		for (int i = 1; i<=NB; ++i){
			for (int j= 1; j<=NK; ++j){
				SetElmt(i,j,input.nextDouble());
			}
		}
	}

	public void OutputMatriks(){
		//System.out.println("Matriksnya sebagai berikut");
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j=1; j<=GetLastIdxKol(); ++j){
				System.out.printf("%.2f ",Elmt(i,j));
			}
			System.out.println();
		}
	}

	public void TukarBaris(int row1, int row2){
		double temp;
		for (int j=1; j<=GetLastIdxKol(); ++j){
			temp = Elmt(row1,j);
			SetElmt(row1,j,Elmt(row2,j));
			SetElmt(row2,j,temp);
		}
	}

	public void AddBaris(int rowTarget, int rowAsal, double pengali){
		for (int j = 1; j<=GetLastIdxKol(); ++j){
			M[rowTarget][j] += pengali*M[rowAsal][j]; 
		}
	}

	public void Tranpose(){
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

	public Matriks MatriksAdjoin(){
		Matriks newMat = this.MatriksCofactor();
		newMat.Tranpose();
		return newMat;
	}

	public Matriks MatriksInverse(){
		Matriks newMat = this.MatriksAdjoin();
		double det = this.Determinan();
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			for (int j = 1; j<=GetLastIdxKol(); ++j){
				newMat.M[i][j] /=  det;
			}
		}
		return newMat;
	}

	public void GaussJordan(){
		double temp;
		for (int j = 1; j<=GetLastIdxKol()-1; ++j){
			for (int i =1; i<=GetLastIdxBrs(); ++i){
				if (i!=j){
					temp = Elmt(i,j)/Elmt(j,j);
					for (int k = 1; k<=GetLastIdxKol(); ++k ){
						SetElmt(i,k,Elmt(i,k)-temp*Elmt(j,k));
					}
				}
			}
		}
		for (int i = 1; i<=GetLastIdxBrs(); ++i){
			SetElmt(i,i,1);
			SetElmt(i,GetLastIdxKol(), Elmt(i,GetLastIdxKol())/Elmt(i,i));
		}
	}

	
}