package src;

import java.util.Scanner;
import java.io.*;
import java.lang.Math; 

public class Interpolasi extends Spl{
    // Array pada class Matriks digunakan untuk menyimpan nilai perpangkatan absis
    // Array double Solusi pada class SPL digunakan untuk menyimpan hasil nilai koefisien
    
    static Scanner input = new Scanner(System.in);
    public double[][] arrPers;  // untuk menyimpan koordinat titik
    public int NbPers; // untuk menyimpan banyak titik yang diinput
    public double X;    // untuk meyimpan X yang akan dicari nilai Y nya
    public String Pers; // untuk menyimpan persamaan polinomial
   
    /***********   Konstruktor    ***********/
    public void MakeInterpolasi(int NbPers,int X){
    /*  Mengeset atribut NbPers pada Interpolasi
    */
        this.NbPers = NbPers;
        this.X = X;
        this.arrPers = new double[this.NbPers+1][3];
    }

    /***********   Input    ***********/
    public void BacaInterpolasi() 
    /* Membaca Interpolasi dari user melalui terminal */
    {        
        System.out.println("Interpolasi");
        System.out.print("Masukkan banyaknya titik : ");
        this.NbPers= input.nextInt();
        
        this.arrPers = new double[this.NbPers+1][3];
        System.out.println("Masukkan titik (x , y) :");
        for (int i = 1; i<= this.NbPers; i++){
            for (int j = 1; j<= 2; j++){
                this.arrPers[i][j]= input.nextDouble();
            }
        }
    }

    public void BacaFileInterpolasi(String namaFile) throws FileNotFoundException{
        /*  Membaca interpolasi yang berbentuk tupple of point pada file 'namaFile'
            I.S. : Objek interpolasi terdefinisi
            F.S. : Atribut primer interpolasi terinisialisasi
        */
            Scanner input = new Scanner(new FileReader(namaFile));
            double [][] inputArray = new double[100][3];
            int i = 1, row=0;
            while(input.hasNextLine()){
                String[] line = input.nextLine().trim().split(" ");
    
                for (int j = 0; j<line.length; j++){
                    inputArray[i][j+1] = Double.parseDouble(line[j]);
                }
                i++;
                row++;
            }
            input.close();
            this.MakeInterpolasi(row, 5);
            this.CopyTab(inputArray, this.arrPers, row, 2);
        }
    
    /***********   Output    ***********/
    public void TulisFileInterpolasi(String namaFile) throws IOException {
        /* Menyimpan solusi spl ke file 'namaFile'.
        */
        File fout = new File(namaFile);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        bw.write("Persamaan polinomial interpolasinya adalah:");
        bw.newLine();
        bw.write(this.Pers);
        bw.newLine();
        bw.newLine();
        
        bw.write("Nilai fungsi dari X yang ditaksir adalah:");
        bw.newLine();
        bw.write(this.HasilInterpolasi());
        bw.newLine();
        bw.close();
    }
    
    public double FungsiInterpolasi(double X){
    /* Mengembalikan nilai F(X) */
        double result, temp;
        result=0;
        for(int i=1; i<=NbPers; i++){
            temp = this.Solusi[i];
            if(i>1){
                temp *= Math.pow(X, (i-1));
            }
            result+=temp;
        }
        return result;
    }

    public String HasilInterpolasi(){
    /* Mengembalikan hasil persamaan dari nilai X */    
        return ("P(" + Double.toString(X) + ") = "+ Double.toString(FungsiInterpolasi(X)) );
    }


    public void BikinPersPolinomial(){
    /* Mengeset atribut Pers menjadi persamaan polinomial interpolasi */
        double temp;
        boolean awal = false;
        this.Pers = "P(X) = ";
        for(int i=1; i<=NbPers; i++){
            temp = this.Solusi[i];
            if((i==1) && (temp!=0)){
                this.Pers += Double.toString(temp);
                awal=true;
            }
            else if (this.Solusi[i]>0){
                if (awal){
                    this.Pers += " + ";
                }
                else{
                    awal = true;
                }
                if (temp==1) this.Pers += "X^"+Integer.toString(i-1);
                else this.Pers += Double.toString(temp)+"X^"+Integer.toString(i-1);
            }
            else if (this.Solusi[i]<0){
                if (awal){
                    this.Pers += " - ";
                }
                else{
                    this.Pers += "-";
                    awal = true;
                }
                if (temp==-1) this.Pers += "X^"+Integer.toString(i-1);
                else this.Pers += Double.toString(temp*(-1)) + "X^"+ Integer.toString(i-1);
            } //ketika spl.solusi[i] = 0 ,tidak mencetak apapun
        }
    }

    public void KonversiKeMatriks(){
    /* Membikin matriks dari perpangkatan nilai x dari titik-titik yang telah dimasukkan */
        double temp;

        this.MakeMatriks(NbPers,(NbPers+1));
        this.MakeSPL(NbPers, NbPers);
        for (int i = 1; i<=NbPers; i++){
			for (int j= 1; j<=2; j++){
                if(j==1){
                    for (int k = 0; k<NbPers; k++){
                        temp = Math.pow((arrPers[i][j]),k);
                        this.SetElmt(i,(k+1),temp);
                    }
                }else{ // j=2
                    this.SetElmt(i,(NbPers+1),(arrPers[i][j]));
                }
			}
		}
    }

    public void PenyelesaianInterpolasi(){
    /* Menyelesaikan persoalan interpolasi */
        this.KonversiKeMatriks();
        if (this.IsAdaSolusi()){
            this.Solusi = this.SolusiSPLGaussJordan();
        }
    }
}