public class Main{
	public static void main(String[] args){
		System.out.println("Hello World!");
		Matriks mat = new Matriks();
		mat.BacaMatriks();
		mat.OutputMatriks();
		Matriks mat2 = mat;
		// Matriks matInverse = mat.MatriksAdjoin();
		// matInverse.OutputMatriks();

		mat.Gauss();
		mat.OutputMatriks();

		mat2.GaussJordan();
		mat2.OutputMatriks();
	}	
}