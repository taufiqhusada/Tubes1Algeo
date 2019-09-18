import java.util.Scanner;

public class Spl extends Matriks{
    /*Array 2 dimensi pada Matriks digunakan untuk menyimpan nilai koefisien*/
    
    public Scanner input = new Scanner(System.in);
    public double[] Solusi;
    public String[] Solusi1;
    public int NPers,NPeubah;
    public int JenisSolusi;

    /***********   Input    ***********/
    public void BacaSPL(){
        System.out.println("Membaca SPL");
        System.out.print("Masukkan banyak persamaan: ");
        this.NPers = input.nextInt();
        System.out.print("Masukkan banyak peubah: ");
        this.NPeubah = input.nextInt();

        this.MakeMatriks(NPers, (NPeubah+1));
        this.BacaElemenMatriks(NPers,(NPeubah+1));
    }

    /***********   Output    ***********/
    public void OutputSPL(){
        System.out.println("Ini jawaban SPL");
        if (this.JenisSolusi==1){
            System.out.println("Solusi Unik");
            for (int i = 1; i<this.Solusi.length;++i){
                System.out.printf("x%d = %.2f\n",i,this.Solusi[i]);
            }
        }
        else if (this.JenisSolusi==-1){
            System.out.println("Tidak ada solusi yang memenuhi");
        }
    }

    /***********   Penyelesaian SPL    ***********/
    public void PenyelesaianSPL(int MetodePenyelesaian)
    /*Menyelesaikan SPL dengan metode penyelesaian yang diinginkan
    */
    {
        if (MetodePenyelesaian==1){     //Dengan Gauss
            this.Gauss();
            SPLGauss(1);
        }
        else if (MetodePenyelesaian==2){     //Dengan Gauss - Jordan
            this.GaussJordan();
            SPLGauss(2);
        }
        /*else if (MetodePenyelesaian==3){     //Dengan Matriks Balikan
            this.MatriksInverse();
        }*/
        else if (MetodePenyelesaian==4){     //Dengan Cramer
            if (this.NPers==this.NPeubah){
                double det = this.Determinan();
                if (det==0){
                    this.JenisSolusi = -1;
                }
                else{
                    this.Solusi = this.Cramer();
                    for (int i = 1; i<=this.Solusi.length;++i){
                        this.Solusi[i]/=det;
                    }
                    this.JenisSolusi = 1;
                }
            }
            else{
                this.JenisSolusi = -1;
            }
        }
    }

    public void SPLGauss(int x){
        this.JenisSolusi = this.PemilahSolusi();
        if (this.JenisSolusi==1){
            this.Solusi = new double[(this.NPeubah+1)];
            if (x==1){
                for (int i=this.GetLastIdxBrs();i>=1;--i){
                    this.Solusi[i] = Elmt(i,this.GetLastIdxKol());
                    for (int j = this.NPeubah; j>i; --j){
                        if (Elmt(i,j)!=0){
                            this.Solusi[i] -= (Elmt(i, j)*this.Solusi[j]);
                        }
                    }
                }
            }
            else if (x == 2){
                this.Solusi = this.SolusiSPLGaussJordan();
            }
        }
    }

    public boolean IsPeubahNol(int i)
    /*Mengirimkan true apabila peubah baris i bernilai 0 semua*/
    {
        int j=1;
        while ((j<this.NPeubah) && (Elmt(i, j)==0)){
            j++;
        }
        return Elmt(i, j) == 0;
    }

    public int PemilahSolusi()
    /*Mengembalikan 1 apabila ada solusi (solusi unik), mengembalikan 2 apabila solusi banyak (parametrik),
    mengembalikan -1 apabila tidak ada solusi*/    
    {
        int i=this.GetLastIdxBrs();
        boolean AdaSolusi = true;

        if (this.NPers!=this.NPeubah){
            return 2;
        }
        else{   //Peubah == Persamaan (square)
            while ((this.IsPeubahNol(i)) && (i>this.GetLastIdxBrs()) && AdaSolusi){
                if (this.Elmt(i, this.GetLastIdxKol())!=0){
                    AdaSolusi = false;
                }
                else{
                    i--;
                }
            }
            if (!AdaSolusi || (this.IsPeubahNol(i) && (Elmt(i, this.GetLastIdxKol())!=0)) ){    // Tidak ada solusi ketika Peubah 0 semua, tp ada nilai
                return -1;
            }
            else if (!this.IsPeubahNol(i)) {     // i(brs yg ada 1) == Peubah (kol) -> solusi unik 
                return 1;
            }
            else{
                return 2;
            }
        }
    }

    public double[] SolusiSPLGaussJordan()
    /* Mengambil elemen setelah menggunakan gaussjordan */
    {
        double[] res = new double[(this.NPeubah+1)];
        for (int i=1;i<=this.GetLastIdxBrs();++i){
            res[i] = this.Elmt(i, this.GetLastIdxKol());
        }
        return res;
    }

    public boolean IsAdaSolusi(){
        this.GaussJordan();
        return (this.PemilahSolusi()==1);
    }
}