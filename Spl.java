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
        else if (this.JenisSolusi == 2){
            System.out.println("Solusi Parametrik");
            for (int i = 1; i<this.Solusi1.length;++i){
                System.out.printf("x%d = %s\n",i,this.Solusi1[i]);
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
        else if (MetodePenyelesaian==3){     //Dengan Matriks Balikan
            if (this.NBrsEff==(this.NKolEff-1)){
                this.NKolEff--;
                double det = this.Determinan();
                this.NKolEff++;
                if (det!=0){
                    double[] hasilSPL = new double[(this.NBrsEff+1)];
                    for (int i=1;i<=this.NBrsEff;i++){
                        hasilSPL[i] = this.Elmt(i, this.NKolEff);
                    }
                    this.NKolEff--;
                    Matriks temp = new Matriks();
                    temp.MakeMatriks(this.NBrsEff, this.NKolEff);
                    temp = this.MatriksInverse();

                    this.Solusi = temp.KaliMatriks1D(hasilSPL);
                    
                    this.NKolEff++;
                    this.JenisSolusi =1;
                }
                else{
                    this.JenisSolusi=-1;
                }
            }
            else{
                this.JenisSolusi=-1;
            }
        }
        else if (MetodePenyelesaian==4){     //Dengan Cramer
            if (this.NPers==this.NPeubah){
                Matriks Mat = new Matriks();
                Mat.MakeMatriks(this.GetLastIdxBrs(), this.GetLastIdxKol());
                CopyTab(this.M, Mat.M, this.GetLastIdxBrs(), this.GetLastIdxKol());
                Mat.NKolEff--;
                double det = Mat.Determinan();
                Mat.NKolEff++;
                System.out.println(det);
                if (det==0){
                    this.JenisSolusi = -1;
                }
                else{
                    this.Solusi = Mat.Cramer();
                    this.JenisSolusi = 1;
                }
            }
            else{
                this.JenisSolusi = -1;
            }
        }

        //Jika solusi merupakan parametrik/ solusi banyak
        if (this.JenisSolusi == 2){
            this.Parametrik();
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
        else if (this.JenisSolusi==2){
            this.Parametrik();
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
    mengembalikan -1 apabila tidak ada solusi
    (setelah dilakukan gauss atau gauss jordan*/    
    {
        int i=this.GetLastIdxBrs(),cnt=0; //cnt->banyak baris yang peubah nol semua
        boolean AdaSolusi = true;

        while ((this.IsPeubahNol(i)) && (i>1) && AdaSolusi){
            if (this.Elmt(i, this.GetLastIdxKol())!=0){
                AdaSolusi = false;
            }
            else{
                i--;
                cnt++;
            }
        }

        if (!AdaSolusi || (this.IsPeubahNol(i) && (Elmt(i, this.GetLastIdxKol())!=0)) ){    // Tidak ada solusi ketika Peubah 0 semua, tp ada nilai
            return -1;
        }
        else if ((this.NPers-cnt)>=this.NPeubah) {     // i(brs yg ada 1) == Peubah (kol) -> solusi unik 
            return 1;
        }
        else{
            return 2;
        }
    }

    int IsOnlyOne(int i){
        int j=1,pos=0;
        while ((this.Elmt(i, j)==0) && (j<this.NPeubah)){
            j++;
        }
        if (this.Elmt(i,j)!=0){
            pos = j++;
            boolean AdaLain = false;
            while ((!AdaLain) && (j<=this.NPeubah)){
                if (this.Elmt(i, j) != 0){
                    AdaLain = true;
                    pos=0;
                }
                else{
                    j++;
                }
            }
        }
        return pos;
    }

    public void Parametrik()
    /*  Menentukan fungsi parametrik*/
    {
        boolean[] SudahBrs = new boolean[this.NBrsEff+1];
        boolean[] SudahPeubah = new boolean[this.NPeubah+1];
        boolean[] AdaPeubah = new boolean[this.NPeubah+1];
        this.Solusi1 = new String[(this.NPeubah+1)];

        for (int i=1; i<=this.NBrsEff; i++){
            for (int j=1; j<=this.NPeubah; j++){
                if (this.Elmt(i, j)!=0){
                    AdaPeubah[j] = true;
                }
            }

            int pos = IsOnlyOne(i);
            if (pos!=0){
                SudahBrs[i] = true;
                SudahPeubah[pos]=true;
                this.Solusi1[pos] = Double.toString(this.Elmt(i, this.NKolEff));
            }
        }
        
        int cnt=1; //untuk indeks variabel peubah
        for (int i=1; i<=this.NPeubah; i++){
             if (!AdaPeubah[i]){
                SudahPeubah[i] = true;
                this.Solusi1[i] = "r"+Integer.toString(cnt++);
             }
        }

        for (int i=1; i<=this.NPeubah; i++){
            if (!SudahPeubah[i]){
                this.Solusi1[i] = "r"+Integer.toString(cnt++);
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