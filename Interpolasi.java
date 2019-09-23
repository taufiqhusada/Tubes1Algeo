import java.util.Scanner;

public class Interpolasi{
    static Scanner input = new Scanner(System.in);
    public double[][] arrPers;
    public double[] resInterpolasi
    public void InInterpolasi() {
        int  NbPers;
        
        System.out.println("Interpolasi");
        System.out.print("Masukkan banyaknya titik :");
        this.NbPers= input.nextInt();
        
        for (int i = 0; i<= NbPers; i++){
            for (int j = 1; i<= 2; j){
                this.arrPers[i][j]= input.nextDouble();
            }
        }
    }

    public void KonversiKeMatriks(Double[][] arr){
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
        double result;
        result=0;
        
        for(int i=1; i<=NbPers+1; i++){
            for(int j=1; j<=i-1; i++)
            result += resInterpolasi[i]*;  
        }
    }

    public void RunInterpolasi(){
        InInterpolasi();
        KonversiKeMatriks(arrPers);
        Parametrik()        
        }
    }
}