public class Main{
	public static void main(String[] args){
		System.out.println("Hello World!");
		Matriks mat = new Matriks();
		mat.BacaMatriks();
		mat.OutputMatriks();
		// Matriks matInverse = mat.MatriksAdjoin();
		// matInverse.OutputMatriks();

		mat.GaussJordan();
		mat.OutputMatriks();
	}	
}