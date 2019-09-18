import java.util.Scanner;

public class Spl extends Matriks{
    /*Array 2 dimensi pada Matriks digunakan untuk menyimpan nilai koefisien*/
    
    public Scanner input = new Scanner(System.in);
    


    /***********   Input    ***********/
    public void BacaSPL(){
        int NPers,NPeubah;
        
        System.out.println("Membaca SPL");
        System.out.print("Masukkan banyak persamaan: ");
        NPers = input.nextInt();
        System.out.print("Masukkan banyak peubah: ");
        NPeubah = input.nextInt();

        this.MakeMatriks(NPers, (NPeubah+1));
        this.BacaElemenMatriks(NPers,(NPeubah+1));
    }

    /***********   Output    ***********/
    public void OutputSPL(){
        System.out.println("Ini jawaban SPL");
        this.OutputMatriks();
    }

    /***********   Penyelesaian SPL    ***********/
    public void PenyelesaianSPL(int MetodePenyelesaian)
    /*Menyelesaikan SPL dengan metode penyelesaian yang diinginkan
    */
    {
        if (MetodePenyelesaian==1){     //Dengan Gauss
            this.Gauss();
        }
        else if (MetodePenyelesaian==2){     //Dengan Gauss - Jordan
            this.GaussJordan();
        }
        else if (MetodePenyelesaian==3){     //Dengan Matriks Balikan
            this.MatriksInverse();
        }
        /*else if (MetodePenyelesaian==4){     //Dengan Cramer
            this.Cramer();;
        }*/
    }

    public boolean IsBrsNol(int i)
    /*Mengirimkan true apabila peubah baris i bernilai 0 semua*/
    {
        int j=1;
        while ((j<(this.GetLastIdxKol()-1)) && (Elmt(i, j)==0)){
            j++;
        }
        return Elmt(i, j) == 0;
    }

    public int PemilahSolusi()
    /*Mengembalikan 1 apabila ada solusi (solusi unik), mengembalikan 2 apabila solusi banyak,
    mengembalikan -1 apabila tidak ada solusi*/    
    {
        int i=this.GetLastIdxBrs();
        
        if (IsBrsNol(i)){
            if (this.Elmt(i, this.GetLastIdxKol())!=0){
                return -1;
            }
            else{
                return 2;
            }
        }

        return 1;
    }
}