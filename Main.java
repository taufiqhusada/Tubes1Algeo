public class Main{
	public static void main(String[] args){
		System.out.println("Hello World!");
		Matriks mat = new Matriks();
		mat.BacaMatriks();
		mat.OutputMatriks();
		Matriks mat2 = mat;
		Matriks mat3 = mat;
		// Matriks matInverse = mat.MatriksAdjoin();
		// matInverse.OutputMatriks();

		mat.Gauss();
		mat.OutputMatriks();

		mat2.GaussJordan();
		mat2.OutputMatriks();

		double []hasil = mat3.Cramer();
		for (int i = 1; i<=mat3.GetLastIdxKol()-1; ++i){
			System.out.println(hasil[i]);
		}
	}	

}