import java.util.Scanner;
import java.io.*;

public class Spl extends Matriks{
    /*Array 2 dimensi pada Matriks digunakan untuk menyimpan nilai koefisien*/
    
    public Scanner input = new Scanner(System.in);  // untuk melakukan input
    public double[] Solusi; // untuk menyimpan solusi SPL yang unik
    public String[] Solusi1; // untuk menyimpan solusi SPL yang hasilnya solusi banyak (solusi parametrik) atau solusi SPL dalam tipe string
    public int NPers,NPeubah; // untuk menyimpan banyaknya persamaan dan peubah suatu SPL
    public int JenisSolusi; // untuk menyimpan status solusi : 1 apabila ada solusi (solusi unik), 2 apabila solusi banyak (parametrik), atau
                            // -1 apabila tidak ada solusi

    /***********   Konstruktor    ***********/
    public void MakeSPL(int NPers,int NPeubah){
    /*  Mengeset atribut NPers dan NPeubah pada SPL
    */
        this.NPers = NPers;
        this.NPeubah = NPeubah;
    }

    /***********   Input    ***********/
    public void BacaSPL(){
    /*  Membaca SPL dari input user
        I.S. : Objek SPL terdefinisi
        F.S. : Atribut primer SPL terinisialisasi
    */
        System.out.println("Membaca SPL");
        System.out.print("Masukkan banyak persamaan: ");
        this.NPers = input.nextInt();
        System.out.print("Masukkan banyak peubah: ");
        this.NPeubah = input.nextInt();

        this.MakeMatriks(NPers, (NPeubah+1));
        
        System.out.println("Masukkan koefisien aij: ");
        this.BacaElemenMatriks(NPers,NPeubah);
        System.out.println("Masukkan hasil bi: ");
        for (int i=1;i<=this.NPers;i++){
            this.SetElmt(i,this.NKolEff,input.nextDouble());
        }
    }

    public void BacaFileSPL(String namaFile) throws FileNotFoundException{
    /*  Membaca SPL yang berbentuk augmented array pada file namaFile
        I.S. : Objek SPL terdefinisi
        F.S. : Atribut primer SPL terinisialisasi
    */
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
        MakeSPL(row, (col-1));
	}

    /***********   Output    ***********/
    public void OutputSPL()
    /*  Mencetak solusi SPL ke layar
        I.S. : Telah dilakukan operasi penyelesaian SPL
        F.S. : Apabila SPL memiliki solusi, program akan mencetak solusi tersebut ke layar
    */
    {
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

    public void TulisFileSPL(String namaFile) throws IOException{
    /*  Menyimpan solusi spl ke file 'namaFile'.
    */
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
		if (this.JenisSolusi!=-1){
            for (i = 1; i<=this.NPeubah; ++i){
                String s = "x" + Integer.toString(i) + " = "; 
                if (this.JenisSolusi==1) s+=Double.toString(this.Solusi[i]);
                else if (this.JenisSolusi==2) s+=this.Solusi1[i];
                bw.write(s);
                bw.newLine();
            }
        }
		bw.close();
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
            if (this.NBrsEff==(this.NKolEff-1)){    // cek apakah square
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

    public void SPLGauss(int x)
    /*  Menentukan solusi SPL dengan eliminasi Gauss atau Gauss-Jordan 
        I.S. : SPL sembarang telah dilakukan eliminasi Gauss atau Gauss-Jordan, x terdefinisi bernilai 1 atau 2
        F.S. : Atribut JenisSolusi terinisialisasi dengan menggunakan metode fungsi PemilahSolusi.
               Apabila JenisSolusi bernilai 1, dilakukan inisialisasi array of doble Solusi sesuai dengan nilai x
               yang menyatakan:
               1. Apabila x bernilai 1, dilakukan eliminasi peubah terlebih dahulu untuk mendapatkan solusi unik.
               2. Apabila x bernilai 2, dilakukan inisialisasi langsung dengan metode SolusiSPLGaussJordan untuk mendapatkan solusi unik.
    */
    {
        this.JenisSolusi = this.PemilahSolusi();
        if (this.JenisSolusi==1){
            this.Solusi = new double[(this.NPeubah+1)];
            if (x==1){
                for (int i=this.NPeubah;i>=1;--i){
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
    /*Mengirimkan true apabila koefisien peubah baris i bernilai 0 semua*/
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
    (setelah dilakukan eliminasi Gauss atau Gauss-Jordan*/    
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

    public void Parametrik()
    /*  Menentukan solusi SPL yang memiliki banyak solusi (solusi parametrik)
        I.S. : Telah dilakukan operasi Gauss atau Gauss-Jordan
        F.S. : Atribut Solusi1 dari SPL yang bertipe string berisi dengan solusi SPL tersebut.
               Atribut Solusi dari SPL yang bertipe double juga mungkin beberapa terisi untuk peubah yang memiliki nilai unik
    */
    {
        this.Solusi1 = new String[this.NPeubah+1];
        this.Solusi = new double[this.NPeubah+1];
        int[] SudahPeubah = new int[this.NPeubah+1];
        /* mengkategorikan peubah ke i sebagai:
        0-> murni variabel parametrik
        1-> eksak
        2-> campuran*/

        Matriks mat = new Matriks();
        mat.MakeMatriks(this.NBrsEff, this.NKolEff);
        mat.CopyTab(this.M, mat.M, this.NBrsEff, this.NKolEff);
        
        int[] IdxParametrik = new int[this.NPeubah+1];
        int cnt=1;
        for (int i=mat.GetLastIdxBrs(); i>=1; i--){ //iterasi dari baris terakhir
            int j=1;
            while ((mat.Elmt(i, j)==0) && (j<this.NPeubah)){ //mencari elemen bukan 0 pertama (first one)
                j++;
            }

            if (this.Elmt(i, j)!=0){ //proses kalau bukan 0 (first one)
                boolean AdaParametrik = false;
                this.Solusi1[j] = "";

                for (int k=j+1; k<=this.NPeubah; k++){ //mencari elemen bukan 0 stlhnya
                    if (mat.Elmt(i, k)!=0){
                        if (SudahPeubah[k]==0){ // berarti peubah ke-k merupakan parametrik
                            if ((mat.Elmt(i,k)<0) && AdaParametrik){
                                this.Solusi1[j] += " + ";
                            }
                            else if (mat.Elmt(i,k)>0){
                                if (AdaParametrik){
                                    this.Solusi1[j] += " - ";
                                }
                                else{
                                    this.Solusi1[j] += "-";
                                }
                            }
                            if (Math.abs(mat.Elmt(i, k))!=1.0){
                                this.Solusi1[j] += Double.toString(Math.abs(mat.Elmt(i, k)));
                            }
                            if (IdxParametrik[k]!=0){
                                this.Solusi1[j] += "r" + IdxParametrik[k];
                            }
                            else{
                                this.Solusi1[j] += "r" + cnt;
                                IdxParametrik[k] = cnt;
                                cnt++;
                            }
                            AdaParametrik = true;
                        }
                        // peubah merupakan eksak
                        else if (SudahPeubah[k]==1){
                            double res=mat.Elmt(i, mat.GetLastIdxKol())-(mat.Elmt(i, k)*this.Solusi[k]);
                            mat.SetElmt(i, mat.GetLastIdxKol(), res); // mengurangi kolom hasil
                            mat.SetElmt(i, k, 0);   //set elemen menjadi 0
                        }

                        else if (SudahPeubah[k]==2){ // bisa substitusi
                            for (int l=k+1; l<=mat.GetLastIdxKol(); l++){
                                double res= mat.Elmt(i,l) - (mat.Elmt(i,k)*mat.Elmt((int) this.Solusi[k],l));
                                mat.SetElmt(i, l, res);
                            }
                            mat.SetElmt(i,k,0);
                        }
                    }
                }

                if (!AdaParametrik){
                    this.Solusi[j] = mat.Elmt(i,j);
                    SudahPeubah[j] = 1; 
                }
                else{
                    SudahPeubah[j] = 2;
                    this.Solusi[j] = i; //menyimpan baris ke berapa ia ditentukan
                }

                if (mat.Elmt(i, mat.GetLastIdxKol())>0){
                    if (AdaParametrik){
                        this.Solusi1[j] += " + ";
                    }
                }
                else if (mat.Elmt(i, mat.GetLastIdxKol())<0){
                    if (AdaParametrik){
                        this.Solusi1[j] += " - ";
                    }
                    else{
                        this.Solusi1[j] += "-";
                    }
                }
                if (mat.Elmt(i,mat.GetLastIdxKol()) != 0){ 
                    this.Solusi1[j] += Double.toString( Math.abs(mat.Elmt(i,mat.GetLastIdxKol())) );
                }
            }
        }

        /* untuk peubah yang murni parametrik */
        for (int i=this.NPeubah; i>=1; i--){
            if (SudahPeubah[i]==0){
                if (IdxParametrik[i]!=0){
                    this.Solusi1[i] = "r"+Integer.toString(IdxParametrik[i]);
                }
                else{
                    this.Solusi1[i] = "r"+Integer.toString(cnt++);
                }
            }
        }
    }

    public double[] SolusiSPLGaussJordan()
    /* Mengembalikan array of double yang berisikan solusi SPL setelah menggunakan metode Gauss-Jordan */
    {
        double[] res = new double[(this.NPeubah+1)];
        for (int i=1;i<=this.NPeubah;++i){
            res[i] = this.Elmt(i, this.GetLastIdxKol());
        }
        return res;
    }

    public boolean IsAdaSolusi()
    /* Mengembalikan true jika SPL tersebut ada memiliki solusi unik setelah dilakukan metode Gauss-Jordan*/
    {
        this.GaussJordan();
        return (this.PemilahSolusi()==1);
    }
}