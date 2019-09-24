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
        System.out.print("Masukkan nilai x yang akan ditaksir nilai fungsinya : ");
        this.X=input.nextDouble();
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
    /*public void TulisFileInterpolasi(String namaFile) throws IOException {
        Menyimpan solusi spl ke file 'namaFile'.
    
        File fout = new File(namaFile);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        int i;
        
        if (this.JenisSolusi==-1){
            bw.write("Tidak ada solusi yang memenuhi");
        }
        else if (this.JenisSolusi==1){
            bw.write("Solusi unik");
        }
        else{
            bw.write("Solusi banyak");
        }
        bw.newLine();
        
        bw.close();
    }*/
    
    public void HasilInterpolasi(){
        double result, temp;
        result=0;
        for(int i=1; i<=NbPers; i++){
            temp = this.Solusi[i];
            if(i>1){
                temp *= Math.pow(X, (i-1));
            }
            result+=temp;
        }
        System.out.printf("P(%.2f) = %.2f\n",X,result);
    }


    public void cetakPersPolinomial(){
        double temp;
        boolean awal = false;
        System.out.print("P(X) = ");
        for(int i=1; i<=NbPers; i++){
            temp = this.Solusi[i];
            if((i==1) && (temp!=0)){
                System.out.print(temp);
                awal=true;
            }
            else if (this.Solusi[i]>0){
                if (awal){
                    System.out.printf(" + ");
                }
                else{
                    awal = true;
                }
                if (temp==1) System.out.printf("X^%d",(i-1));
                else System.out.printf("%.2fX^%d",temp,(i-1));
            }
            else if (this.Solusi[i]<0){
                if (awal){
                    System.out.printf(" - ");
                }
                else{
                    System.out.printf("-");
                    awal = true;
                }
                if (temp==-1) System.out.printf("X^%d",(i-1));
                else System.out.printf("%.2fX^%d",(temp*(-1)),(i-1));
            } //ketika spl.solusi[i] = 0 ,tidak mencetak apapun
        }
        System.out.println();
    }

    public void KonversiKeMatriks(){
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
        this.KonversiKeMatriks();
        if (this.IsAdaSolusi()){
            this.Solusi = this.SolusiSPLGaussJordan();
        }
    }
}