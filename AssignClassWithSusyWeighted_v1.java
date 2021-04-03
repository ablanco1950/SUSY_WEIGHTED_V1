//
// Author Alfonso Blanco Garc�a, Abril 2021
//
// Program that from the training file "C: \\ SusyWeighted78PerCentHits.txt", Susy weighted, generated by the
// PrepareSusyWeighted_NaiveBayes_v1_0 program, allows to establish weighted frequencies for each field of traing file,
// which allows to calculate the probability that the selected records of a file of test belong to a class 0 or 1
// 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Math;

public class AssignClassWithSusyWeighted_v1 {
	public static void main(String args[]) {
		//
	    //  Are received as parameters: the name of the training file: SusyWeighted78PerCentHits.txt, the name of the test file:
		// Susy.csv,and the margin  of records of training and test file  
		//
		String fichero = args[0];
		Double TrainingStart = Double.parseDouble(args[2]);
		Double TrainingEnd = Double.parseDouble(args[3]);
		Double TestStart = Double.parseDouble(args[4]);
		Double TestEnd = Double.parseDouble(args[5]);
		long Inicio = System.nanoTime();
		Double Conta = 0.0;
		Double Cont0 = 0.0;
		Double Cont1 = 0.0;
		Double ContLe = 0.0;
		Double ContTipo0 = 0.0;
		Double ContTipo1 = 0.0;
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

		for (int i = 0; i < NumCampos; i++) {
			for (int j = 0; j < TopeMemoria; j++) {
				Tabvotos0[i][j] = 0.0;
				Tabvotos1[i][j] = 0.0;
			}
		}
		System.out.println("Start AssignClassWithSusyWeighted_v1.java");
		Double Maximo = 0.0;

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

					Double FactorPri = 0.0;
					Double FactorPri2 = 0.0;
                    //
					// The input file SusyWeighted78PerCentHits.txt is SUSY.csv with an additional field added at position 19 
					// to classify Susy's records
					//
					Integer TipoPri = Integer.parseInt(lineadelTrain[19]);

					// Priorities are assigned based on the type of record in field 19 of SusyWeighted78PerCentHits.txt
					//
					switch (TipoPri) {
					case 0:
						FactorPri = 1.0;
						ContTipo0++;
						break;
					//
					//	Record-level priority is maintained. The priority will be established for each field
					//	of each record according to the type of record
					//
					case 1:
						FactorPri = 1.0;
						ContTipo1++;
						break;

					default:
						System.out.println("Rare case a priority that is not 0 or 1 is sneaked");

					}
					for (int z = 1; z < 9; z++) {
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

						Double MaxIndice = TopeMemoria - 2.0;
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
							System.out.println(" index overflowed =" + indice + " in the field=" + z);
							indice = TopeMemoria;
						}
                        //
						// Priorities are assigned based on the type of record and field
						//
						FactorPri2 = 0.0;

						if (TipoPri == 1) {
							if (z == 1) {
								FactorPri2 = 0.0;
							}
							if (z == 2) {
								FactorPri2 = 0.2;
							}
							if (z == 3) {
								FactorPri2 = -1.3;
							}
							if (z == 4) {
								FactorPri2 = 2.2;
							}
							if (z == 5) {
								FactorPri2 = 0.2;
							}
							if (z == 6) {
								FactorPri2 = 0.0;
							}
							if (z == 7) {
								FactorPri2 = 0.2;
							}
							if (z == 8) {
								FactorPri2 = 0.1;
							}
						}

						

						Double valor = 0.0;
						if (Double.parseDouble(lineadelTrain[0]) == 0.0) {

							valor = Tabvotos0[z][TopeMemoria - 1];

							valor++;
							Tabvotos0[z][TopeMemoria - 1] = valor;

							valor = Tabvotos0[z][indice];

							valor = valor + FactorPri + FactorPri2;
							Tabvotos0[z][indice] = valor;

						} else {
							valor = Tabvotos1[z][TopeMemoria - 1];

							valor++;
							Tabvotos1[z][TopeMemoria - 1] = valor;

							valor = Tabvotos1[z][indice];

							valor = valor + FactorPri + FactorPri2;
							Tabvotos1[z][indice] = valor;

						} // end if
					} // end for z

					// --------------------------------------
				} // end if conta
			} // end while
			fr.close();
			System.out.println("Readed records " + fichero + ": " + ContLe);
		} // fin try
		catch (Exception e) {
			System.out.println("Exception reading file " + fichero + ": " + e);
		}

		// **************************************************************************************

		Double TotAciertos = 0.0;
		Double TotFallos = 0.0;
		Double TotValoresACero = 0.0;
		Conta = 0.0;
		ContLe = 0.0;
		String ficheroTest = args[1];
		try {
			FileReader fr = new FileReader(ficheroTest);
			BufferedReader br = new BufferedReader(fr);
			FileWriter Salida = null;
			PrintWriter pw = null;
			Salida = new FileWriter("C:\\FileTextWithClassAsigned.txt");
			pw = new PrintWriter(Salida);
			String linea;

			while (((linea = br.readLine()) != null)) {
				Conta++;
				
				if ((Conta >= TestStart) && (Conta <= TestEnd)) {
					ContLe++;
					String lineadelTest[] = linea.split(",");

					Double TotValor0 = 0.0;
					Double TotValor1 = 0.0;
					Double TotValores = 0.0;
					Double Producto_P_indice_clase0 = 1.0;
					Double Producto_P_indice_clase1 = 1.0;
					Double P_indice_clase0 = 0.0;
					Double P_indice_clase1 = 0.0;

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
							System.out.println(" Index overflowed==" + indice + " in the field=" + z);
							indice = TopeMemoria;
						}

						Double valor = 0.0;
						//
						// frequencies are recovered
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
							// The probability of each index of each field, given a class condition, is multiplied by the probability of each of the indexes
							// of previous fields in the record
							//
							
							Producto_P_indice_clase0 = Producto_P_indice_clase0 * P_indice_clase0;
							Producto_P_indice_clase1 = Producto_P_indice_clase1 * P_indice_clase1;

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
					
					// The concordances and discrepancies with the input test file are counted, which is used
					// to establish the accuracy of the procedure in case SUSY.csv is used, or a margin of SUSY.csv
					// records, as a test file.
					// In the case of using a test file that does not have assigned classes,
					// this accounting will not make sense
					
					if (TotValores != 0.0) {
						TotValoresACero++;
					} else {
						if (TotValor1 > TotValor0) {
							if (Double.parseDouble(lineadelTest[0]) == 0.0) {

								TotFallos++;
							} else {
								TotAciertos++;
							}
						} else {
							if (Double.parseDouble(lineadelTest[0]) == 0.0) {
								TotAciertos++;
							} else {

								TotFallos++;
							}
						}
					} // end  of else if (TotValores == 0.0)
					
					//
					// In the output file C: \\ FileTextWithClassAsigned.txt, the records of the test file
					// are recorded with the assigned classes
					//
					
					if (TotValor1 > TotValor0)
						lineadelTest[0] = "1.000000000000000000e+00";
					else
						lineadelTest[0] = "0.000000000000000000e+00";
					linea = String.join(",", lineadelTest);
					pw.println(linea);
				} // end of if conta 
			} // end while

			System.out.println(" Total hits with the test file =  " + TotAciertos);
			System.out.println(" Total failures = " + TotFallos);

			System.out.println(" Assigned without foundation = = " + TotValoresACero);
			// System.out.println(" Records counted type 0 = " + ContTipo0);
			// System.out.println(" Records counted type 1 = = " + ContTipo1);

			fr.close();
			pw.close();
			Salida.close();
			System.out.println("Records read test file" + ficheroTest + ": " + ContLe);
			Double FinalParcial = (System.nanoTime() - Inicio) / 1000000000.0;
			System.out.println("Total run time= " + FinalParcial);
		} catch (Exception e) {
			System.out.println("Exception reading file " + fichero + ": " + e);
		}
		// ________________________________________________________________________________________
	}// end of  main
}
