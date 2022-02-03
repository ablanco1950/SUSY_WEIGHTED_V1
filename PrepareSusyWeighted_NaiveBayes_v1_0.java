//
// Author Alfonso Blanco García, Abril 2021
//
// Program that makes an evaluation of the records of Susy.csv producing a Susy with  weighted records
// which name is "C:\\SusyWeighted78PerCentHits.txt"
// 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
public class PrepareSusyWeighted_NaiveBayes_v1_0 {

	public static void main(String[] args) {
	//  Are received as parameters: the name of the training file: Susy.csv, the name of the test file, also Susy.csv,
			// and the range of records of Sus.csv that will constitute the training file, it will be considered as test file 
			// the  same register margin
			
			String fichero = args[0];
			Double TrainingStart = Double.parseDouble(args[2]);
			Double TrainingEnd = Double.parseDouble(args[3]);
			String ficheroSalida = args[4];
			long Inicio = System.nanoTime();
			Double Conta = 0.0;
			Double ContLe = 0.0;
			Double Cont0 = 0.0;
			Double Cont1 = 0.0;
			Double[] Max = new Double[9];
			Double[] Min = new Double[9];

			Max[1] = 20.553449630737305;
			Max[2] = 2.10160493850708;
			Max[3] = 1.7348390817642212;
			Max[4] = 33.035621643066406;
			Max[5] = 2.059720754623413;
			Max[6] = 1.734686255455017;
			Max[7] = 21.068876266479492;
			Max[8] = 1.7406890392303467;
			Min[1] = 0.25488153100013733;
			Min[2] = -2.1029269695281982;
			Min[3] = -1.7347885370254517;
			Min[4] = 0.4285859763622284;
			Min[5] = -2.0593061447143555;
			Min[6] = -1.7342021465301514;
			Min[7] = 2.598710998427123E-4;
			Min[8] = -1.7271170616149902;

			int NumCampos = 9;
			int TopeMemoria = 20004;
			Double[][] Tabvotos0 = new Double[NumCampos][TopeMemoria];
			Double[][] Tabvotos1 = new Double[NumCampos][TopeMemoria];

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < TopeMemoria; j++) {
					Tabvotos0[i][j] = 0.0;
					Tabvotos1[i][j] = 0.0;
				}
			}
			Double Maximo = 0.0;
			System.out.println("Start PrepareSusyWeighted_v1_0.java");
			try {
				FileReader fr = new FileReader(fichero);
				BufferedReader br = new BufferedReader(fr);

				String linea;
				// 
				// Reading the training file
				//
				while ((linea = br.readLine()) != null) {
					Conta++;
					if ((Conta >= TrainingStart) && (Conta <= TrainingEnd)) {
						ContLe++;
						
						String lineadelTrain[] = linea.split(",");

						if (Double.parseDouble(lineadelTrain[0]) == 0.0)
							Cont0++;
						else
							Cont1++;
						for (int z = 1; z < 9; z++) {
							Double FactorPri = 1.0;
	                        //
							// Assign memory limits that will determine the width of the samples for each field
							// The memory limits for each field have been adjusted by successive approximations 
							//(trial and error) to the number of hits.
							//
							switch (z) {
							case 1:
								TopeMemoria = 802;
								break;
							case 2:
								TopeMemoria = 20002;
								break;
							case 3:
								TopeMemoria = 2002;
								break;
							case 4:
								TopeMemoria = 702;
								break;
							case 5:
								TopeMemoria = 1002;
								break;
							case 6:
								TopeMemoria = 3502;
								break;
							case 7:
								TopeMemoria = 602;
								break;
							case 8:
								TopeMemoria = 802;
								break;
							default:
								System.out.println(" Error out of case in the field=" + z);
							}

						
							int indice = 0;

							Double ValorTrain = Double.parseDouble(lineadelTrain[z]);
	                        
							//
							// The memory index in which the frequencies of each field will be stored is calculated based 
							// on the memory limit set to each field and the maximum and minimum values of each field
							//
							ValorTrain = ValorTrain - Min[z];
							Maximo = Max[z] - Min[z];
							indice = (int) (((TopeMemoria - 2.0) * ValorTrain) / Maximo);
						
							if ((indice > (TopeMemoria - 2)) || (indice < 0)) {
								System.out.println(" index overflowed=" + indice + " in the field=" + z);
								indice = TopeMemoria;
							}

							Double valor = 0.0;
							if (Double.parseDouble(lineadelTrain[0]) == 0.0) {

								valor = Tabvotos0[z][TopeMemoria - 1];

								valor++;
								Tabvotos0[z][TopeMemoria - 1] = valor;

								valor = Tabvotos0[z][indice];

								valor = valor + FactorPri;
								Tabvotos0[z][indice] = valor;

							} else {
								valor = Tabvotos1[z][TopeMemoria - 1];

								valor++;
								Tabvotos1[z][TopeMemoria - 1] = valor;

								valor = Tabvotos1[z][indice];

								valor = valor + FactorPri;
								Tabvotos1[z][indice] = valor;

							} // end if
						} // end for z
					} // end of if conta
					// --------------------------------------
				} // end while
				fr.close();
				System.out.println("Readed records from training" + fichero + ": " + ContLe);
				System.out.println("Readed records classs 0 from training" + fichero + ": " + Cont0);
				System.out.println("Readed records classs 1 from training" + fichero + ": " + Cont1);
			} // fin try
			catch (Exception e) {
				System.out.println("Exception reading file " + fichero + ": " + e);
			}

			// **************************************************************************************

			Double TotAciertos = 0.0;
			Double TotFallos = 0.0;
			Double TotValoresACero = 0.0;
			Double Producto_P_indice_clase0 = 1.0;
			Double Producto_P_indice_clase1 = 1.0;
			Double P_indice_clase0 = 0.0;
			Double P_indice_clase1 = 0.0;
			Conta = 0.0;
			ContLe = 0.0;
			String ficheroTest = args[1];
			try {
				FileReader fr = new FileReader(ficheroTest);
				BufferedReader br = new BufferedReader(fr);
				FileWriter Salida = null;
				PrintWriter pw = null;
				Salida = new FileWriter(ficheroSalida);
				pw = new PrintWriter(Salida);
				String linea;
				
				while ((linea = br.readLine()) != null) {

					Conta++;
					if ((Conta >= TrainingStart) && (Conta <= TrainingEnd)) {
						ContLe++;
						String lineadelTest[] = linea.split(",");
						Double TotValor0 = 0.0;
						Double TotValor1 = 0.0;
						Double TotValores = 0.0;
						Producto_P_indice_clase0 = 1.0;
						Producto_P_indice_clase1 = 1.0;

						for (int z = 1; z < 9; z++) {
							switch (z) {
							case 1:
								TopeMemoria = 802;
								break;
							case 2:
								TopeMemoria = 20002;
								break;
							case 3:
								TopeMemoria = 2002;
								break;
							case 4:
								TopeMemoria = 702;
								break;
							case 5:
								TopeMemoria = 1002;
								break;
							case 6:
								TopeMemoria = 3502;
								break;
							case 7:
								TopeMemoria = 602;
								break;
							case 8:
								TopeMemoria = 802;
								break;
							default:
								System.out.println(" Error out of case in the field=" + z);
							}

							int indice = 0;
							Double ValorTrain = Double.parseDouble(lineadelTest[z]);
							ValorTrain = ValorTrain - Min[z];
							Maximo = Max[z] - Min[z];
							indice = (int) (((TopeMemoria - 2) * ValorTrain) / Maximo);

							if ((indice > (TopeMemoria - 2)) || (indice < 0)) {
								System.out.println(" Index overflowed=" + indice + " in the field=" + z);
								indice = TopeMemoria;
							}

							Double valor = 0.0;
							//
							// Frequencies are recovered
							//
							Double valor0 = Tabvotos0[z][indice];

							Double valor1 = Tabvotos1[z][indice];

							Double FactorPri = 0.0;
							
							if (indice < (TopeMemoria - 1)) {

								// 
								// The probabilities of each class are calculated by the Naive Bayes method,
								// given the independence of each of the 8 significant fields of SUSY, and in view of the results
							    // obtained by other means. 
								// 
								valor0 = valor0 + FactorPri;
								valor1 = valor1 + FactorPri;
								//
								// The probability of each index given a class is obtained
								//
								P_indice_clase0 = valor0 / Cont0;
								P_indice_clase1 = valor1 / Cont1;
								//
								// the probability of each index of each field, given a class condition, is multiplied by the probability of each of the indexes
								// of previous fields in the record
								//
								Producto_P_indice_clase0 = Producto_P_indice_clase0 * P_indice_clase0;
								Producto_P_indice_clase1 = Producto_P_indice_clase1 * P_indice_clase1;
								TotValores = TotValores + valor1 - valor0;
							}
						} // end for int z=1
						
						//
						// The probability of the class is calculated, which then  is multiplied by the result of the
						// products of the probabilities of the indices of each field, 
						// assuming belonging to one of the two classes
						//
						
						Producto_P_indice_clase0 = Producto_P_indice_clase0 * Cont0 / (Cont0 + Cont1);
						Producto_P_indice_clase1 = Producto_P_indice_clase1 * Cont1 / (Cont0 + Cont1);
						if ((Producto_P_indice_clase0 + Producto_P_indice_clase1) != 0.0) {
							TotValor0 = Producto_P_indice_clase0 / (Producto_P_indice_clase0 + Producto_P_indice_clase1);
							TotValor1 = Producto_P_indice_clase1 / (Producto_P_indice_clase0 + Producto_P_indice_clase1);
						} else
							TotValores = TotValores + 1;

						if (TotValores == 0.0) {
							pw.println(linea + ",1");
							TotValoresACero++;
						} else {
							if (TotValor1 > TotValor0) {
								if (Double.parseDouble(lineadelTest[0]) == 0.0) {

									pw.println(linea + ",1");
									TotFallos++;
								} else {

									pw.println(linea + ",0");
									TotAciertos++;
								}
							} else {
								if (Double.parseDouble(lineadelTest[0]) == 0.0) {

									pw.println(linea + ",0");
									TotAciertos++;
								} else {

									pw.println(linea + ",1");
									TotFallos++;
								}
							}
						} // fin de else if (TotValores == 0.0)
					} // fin de if conta
				} // fin while

				System.out.println(" Total hits = " + TotAciertos);
				System.out.println(" Total failures = = " + TotFallos);

				System.out.println(" Assigned without foundation = " + TotValoresACero);

				fr.close();
				pw.close();
				Salida.close();
				System.out.println("Records read test file " + ficheroTest + ": " + ContLe);
				Double FinalParcial = (System.nanoTime() - Inicio) / 1000000000.0;
				System.out.println("Total run time= " + FinalParcial);
			} catch (Exception e) {
				System.out.println("Exception reading file " + fichero + ": " + e);
			}
	}// fin del main

}
