import java.text.DecimalFormat;
import java.util.Scanner;
import java.lang.Math; 

public class Interpolasi extends Spl{
    static Scanner input = new Scanner(System.in);
    public double[][] arrPers;
    public int  NbPers;
    public double X;
   
    public void InInterpolasi() {        
        System.out.println("Interpolasi");
        System.out.print("Masukkan banyaknya titik :");
        this.NbPers= input.nextInt();
        System.out.print("x , y");
        
        for (int i = 0; i<= NbPers; i++){
            for (int j = 1; i<= 2; j++){
                this.arrPers[i][j]= input.nextDouble();
            }
        }
        System.out.print("Masukkan nilai x yang akan ditaksir nilai fungsinya :");
        this.X=input.nextInt();
    }

    public void KonversiKeMatriks(double[][] arr){
        double temp;
        temp=1;
        MakeMatriks(NbPers,NbPers+1);
        for (int i = 0; i<=NbPers; i++){
			for (int j= 1; j<=2; i++){
                if(j==1){
                    for (int k = 1; k<=NbPers; k++){
                        temp = Math.pow((arrPers[i][j]),k);
                        SetElmt(i,k,temp);
                    }
                }else{
                    SetElmt(i,NbPers,(arrPers[i][j]));
                }
			}
		}
    }

    public void HasilInterpolasi(){
        double result, temp;
        Spl spl = new Spl();
        result=0;
        for(int i=1; i<=NbPers+1; i++){
            temp = spl.Solusi[i];
            if(i>1){
                for(int j=1; j<=i-1; i++){
                    temp *= X;  
                }
            }
            result+=temp;
        }
        System.out.printf("P(%.2f) = %.2f\n",X,result);
    }


    public void cetakPersPolinomial(){
        double temp;
        Spl spl = new Spl();
        System.out.println("P(X) = ");
        for(int i=1; i<=NbPers+1; i++){
            temp = spl.Solusi[i];
            if(i==1){
                System.out.print(temp);
            }else if (spl.Solusi[i]>0){
                System.out.printf(" + %.2f^%d",temp,i);                            
            }else if (spl.Solusi[i]<0){
                System.out.printf(" - %.2f^%d",(temp*(-1)),i);                            
            } //ketika spl.solusi[i] = 0 ,tidak mencetak apapun
        }
    }


    public void RunInterpolasi(){
        Spl spl = new Spl();
        InInterpolasi();
        KonversiKeMatriks(arrPers);
        cetakPersPolinomial();
        HasilInterpolasi();    
    }
}