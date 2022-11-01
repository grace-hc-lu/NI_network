package network_code_v5.func;


import java.io.*;
import java.util.*;

public class NetworkInteractions
{

	public NetworkInteractions(){}
	
	
	public void removeDuplicatedInputOfGeneNetwork(String networkFolder, String selectedGene)
	{
		String s = "";
		
		try
		{
			FileInputStream	fis		=	new FileInputStream(networkFolder + selectedGene + "_reg_coordinates.txt");  
			DataInputStream	dis		=	new DataInputStream(fis); 
			BufferedReader	br 		=	new BufferedReader(new InputStreamReader(dis));
			
			FileInputStream	fisnw	=	new FileInputStream(networkFolder + selectedGene + "_NI_network.csv");  
			DataInputStream	disnw	=	new DataInputStream(fisnw); 
			BufferedReader	brnw 	=	new BufferedReader(new InputStreamReader(disnw));
			
			FileWriter   	fw      =	new FileWriter(networkFolder + selectedGene + "_NI_network_de_dup.csv");
			PrintWriter  	pw      =	new PrintWriter(fw);
			
			String[]	tmpS		=	null;
			String[]	tmpS2		=	null;
			String[]	frst		=	null;
			String[]	replcd	=	null;
			
			StringBuffer	str	=	null;
			
			int	i	=	0;
			
			Map<String,String>	regHash	=	new HashMap<String,String>();
			Map<String,String>	dupHash	=	new HashMap<String,String>();
			
			
			
			while((s = br.readLine()) != null)
			{
				tmpS = s.split("\t");
				// chr2	3903207	3903526	2	SETD2_reg_actv_27,NI_5h_network_active,SETD2_reg_actv_9,NI_9h_network_active
				// 0		1				2				3	4
				
				if(Integer.parseInt(tmpS[3])>1 && tmpS[4].length()>1)
				{
					tmpS2 = tmpS[4].split(",");
					frst = tmpS2[1].split("_");
				
					for(i=2; i<tmpS2.length; i=i+2)
					{
						replcd = tmpS2[i+1].split("_");
							
						if(frst[2].equals(replcd[2]))
						{
							regHash.put(tmpS2[i], tmpS2[0]);
						}
					}
				}
			}
			
			br.close();
			dis.close();
			fis.close();
			
			
			while((s = brnw.readLine()) != null)
			{
				if(!s.startsWith("general"))
				{
					pw.println(s);
				}
				else
				{
					tmpS = s.split(",");
					// general,9 Hours,gene,ZNF821,gene,SETD2_reg_actv_1,positive,A,A
					// 0       1       2    3      4    5                6
					
					if(regHash.get(tmpS[5])!=null)
					{
						for(i=0; i<tmpS.length; i++)
						{
							if(i==0)
								str = new StringBuffer(tmpS[0]);
							else if(i==5)
								str.append(",").append(regHash.get(tmpS[5]));
							else
								str.append(",").append(tmpS[i]);
						}
						
						if(dupHash.get(str.toString())==null)
						{
							dupHash.put(str.toString(), str.toString());
							
							pw.println(str.toString());
						}
					}
					else if(dupHash.get(s)==null)
					{
						dupHash.put(s,s);
						
						pw.println(s);
					}
				}
			}
			
			brnw.close();
			disnw.close();
			fisnw.close();

			pw.close();
			fw.close();

			
		}catch(FileNotFoundException exp)
		{
			System.out.println("FileNotFoundException : "+exp);
			System.out.println(s);
		}catch(IOException exp)
		{
			System.out.println("IOException : "+exp);
			System.out.println(s);
		}
			
	}
	
	
	public void subtractNetworkOfGene(String networkFolder, String selectedGene, String[] selectedRegulatoryIndices)
	{
		String s = "";
		
		try
		{
			// for reading file
			FileInputStream	fis	=	null;  
			DataInputStream	dis	=	null; 
			BufferedReader	br 	=	null;
			
			FileWriter	fw   	=	new FileWriter(networkFolder + selectedGene + "_NI_network.csv");
			PrintWriter	pw   	=	new PrintWriter(fw);
			
			FileWriter	fwreg	=	new FileWriter(networkFolder + selectedGene + "_reg_coordinates.bed");
			PrintWriter	pwreg	=	new PrintWriter(fwreg);
			
			
			File		folder			= new File(networkFolder);
			File[]	listOfFiles	= folder.listFiles();

			int	i				=	0;
			int	j				=	0;
			int	enhCont	=	1;

			String[]	tmpS					=	null;
			String[]	tmpS2					=	null;
			String		dupCheckStr		=	"";
			String		fileName			=	"";
			String		enhName				=	"";
			boolean		includeIndex	=	false;
			
			Map<String,String>	dupHash	=	new HashMap<String,String>();
			Map<String,String>	enhHash	=	new HashMap<String,String>();
			

			// print BioTapestry csv file header
			fis = new FileInputStream(new StringBuffer(networkFolder).append("NI_network_file_header.csv").toString());  
			dis = new DataInputStream(fis); 
			br = new BufferedReader(new InputStreamReader(dis));
			
			while((s = br.readLine()) != null)
			{
				if(!s.contains("0 Hours") && !s.contains("1 Hours") && !s.contains("3 Hours") && !s.contains("7 Hours"))
				{
					pw.println(s);
				}
			}
			
			br.close();
			dis.close();
			fis.close();
				
			// read files
			for (i=0; i<listOfFiles.length; i++) 
			{
				fileName = listOfFiles[i].getName();
		
				if(listOfFiles[i].isFile() && fileName.endsWith("_by_regulatory_site.csv") &&
					(fileName.contains("_5h_") || fileName.contains("_9h_") || fileName.contains("_12h_"))) 
				{
					includeIndex = false;
					
					for(j=0; j<selectedRegulatoryIndices.length; j++)
					{
						if(fileName.contains(selectedRegulatoryIndices[j]))
						{
							includeIndex = true;
						}
					}
			
					if(includeIndex)
					{
						fis = new FileInputStream(new StringBuffer(networkFolder).append(fileName).toString());  
						dis = new DataInputStream(fis); 
						br = new BufferedReader(new InputStreamReader(dis));

						while((s = br.readLine()) != null)
						{	
							tmpS = s.split(",");
							// general,0 Hours,gene,TFAP2A,gene,CSRP2,positive,H00,H00
							// 0       1       2    3      4    5     6        7   8

							if(tmpS[5].contains(selectedGene))
							{
								if(enhHash.get(tmpS[5])==null)
								{
									if(fileName.contains("active"))
									{
										enhName = selectedGene+"_reg_actv_"+enhCont;
									}
									else if(fileName.contains("_index_4_") || fileName.contains("_index_5_") || fileName.contains("_index_6_"))
									{
										enhName = selectedGene+"_reg_repr_"+enhCont;
									}
									else
									{
										System.out.println(fileName+"\tunrecognised reg state");
									}
									
									tmpS2 = tmpS[5].split("_");
									pwreg.println(new StringBuffer(tmpS2[0]).append("\t").append(tmpS2[1]).append("\t").append(tmpS2[2])
															.append("\t").append(enhName).append(",").append(fileName.substring(0, fileName.indexOf("_by_regulatory_site.csv"))));
									
									enhHash.put(tmpS[5], enhName);
									enhCont++;
									
								}
								else
								{
									enhName = enhHash.get(tmpS[5]);
								}
							
								// general network model
								dupCheckStr = new StringBuffer(tmpS[3]).append(tmpS[5]).append(tmpS[6]).toString();
						
								if(dupHash.get(dupCheckStr)==null)
								{
									for(j=0; j<tmpS.length; j++)
									{
										if(j==0)
											pw.print("general");
										else if(j==1)
											pw.print(",NI Model");
										else if(j==5)
											pw.print(","+enhName);
										else
											pw.print(","+tmpS[j]);
									}
							
									pw.println("");
							
									dupHash.put(dupCheckStr, dupCheckStr);
								}
						
								// time point network
								dupCheckStr = new StringBuffer(tmpS[1]).append(tmpS[3]).append(tmpS[5]).append(tmpS[6]).toString();
						
								if(dupHash.get(dupCheckStr)==null)
								{
									for(j=0; j<tmpS.length; j++)
									{
										if(j==0)
											pw.print(tmpS[j]);
										else if(j==5)
											pw.print(","+enhName);
										else
											pw.print(","+tmpS[j]);
									}
							
									pw.println("");
									
									dupHash.put(dupCheckStr, dupCheckStr);
								}
							}
						}	// end of while loop

						br.close();
						dis.close();
						fis.close();
					}
				}
			} // end of i loop

			
			pw.close();
			fw.close();

			pwreg.close();
			fwreg.close();


		}catch(FileNotFoundException exp)
		{
			System.out.println("FileNotFoundException : "+exp);
			System.out.println(s);
		}catch(IOException exp)
		{
			System.out.println("IOException : "+exp);
			System.out.println(s);
		}
			
	}
	
	
	
	public void printBiotapstryTimeExpressionFile(String path, String geneExpr, String outputPath, String outputFile)
	{
		String s = "";
		
		try
		{
			FileInputStream	fis	=	new FileInputStream(path + geneExpr);  
			DataInputStream	dis	=	new DataInputStream(fis); 
			BufferedReader	br 	=	new BufferedReader(new InputStreamReader(dis));
			
			FileWriter   	fw      =	new FileWriter(outputPath + outputFile);
			PrintWriter  	pw      =	new PrintWriter(fw);
			
			String[]	tmpS	=	null;
			

			pw.println("<TimeCourseData>");

			while((s = br.readLine()) != null)
			{
				tmpS = s.split("\t");
				// ensembl ID galGal5	name_in_NI_GRN	name_in_scRNAseq	Ensembl Name/ID galGal6	first_expressed_time_point	Exprs_0H	Exprs_1H	Exprs_3H	Exprs_5H	Exprs_7H	Exprs_9H	Exprs_12H	chr galGal5	start galGal5	end galGal5	gene_size
				// 0									1								2									3												4														5					6					7					8					9					10				11				12					13						14					15
				
				if(!s.startsWith("ensembl") && !tmpS[4].equals("Excluded in GRN"))
				{
					
					pw.println(new StringBuffer("<timeCourse gene=\"").append(tmpS[1]).append("\" baseConfidence=\"normal\" timeCourse=\"no\" note=\"\">").toString());

					pw.print("<data region=\"A\" time=\"0\" expr=\"");
					if(tmpS[5].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"1\" expr=\"");
					if(tmpS[6].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"2\" expr=\"");
					if(tmpS[6].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"3\" expr=\"");
					if(tmpS[7].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"4\" expr=\"");
					if(tmpS[7].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"5\" expr=\"");
					if(tmpS[8].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"6\" expr=\"");
					if(tmpS[8].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"7\" expr=\"");
					if(tmpS[9].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"8\" expr=\"");
					if(tmpS[9].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"9\" expr=\"");
					if(tmpS[10].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"10\" expr=\"");
					if(tmpS[10].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"11\" expr=\"");
					if(tmpS[10].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.print("<data region=\"A\" time=\"12\" expr=\"");
					if(tmpS[11].equals("UP")) pw.print("yes"); else pw.print("no");
					pw.println("\"/>");

					pw.println("</timeCourse>");
				}
			}

			pw.println("</TimeCourseData>");

			br.close();
			dis.close();
			fis.close();

			pw.close();
			fw.close();

		}catch(FileNotFoundException exp)
		{
			System.out.println("FileNotFoundException : "+exp);
			System.out.println(s);
		}catch(IOException exp)
		{
			System.out.println("IOException : "+exp);
			System.out.println(s);
		}
			
	}
	

	public void integrateInteractionFiles(String networkFolder, String[] putativeRegulatoryRegionIndices)
	{
		String s = "";
		
		try
		{
			// for reading file
			FileInputStream	fis	=	null;  
			DataInputStream	dis	=	null; 
			BufferedReader	br 	=	null;
			
			FileWriter   fw   =	new FileWriter(networkFolder + "NI_network.csv");
			PrintWriter  pw   =	new PrintWriter(fw);
			
			
			File		folder			= new File(networkFolder);
			File[]	listOfFiles	= folder.listFiles();

			int	i	=	0;
			int	j	=	0;

			String[]	tmpS					=	null;
			String		dupCheckStr		=	"";
			String		fileName			=	"";
			boolean		includeIndex	=	false;
			
			Map<String,String>	dupHash	=	new HashMap<String,String>();
			
			
			// print BioTapestry csv file header
			fis = new FileInputStream(new StringBuffer(networkFolder).append("NI_network_file_header.csv").toString());  
			dis = new DataInputStream(fis); 
			br = new BufferedReader(new InputStreamReader(dis));
			
			while((s = br.readLine()) != null)
			{
				pw.println(s);
			}
				
			br.close();
			dis.close();
			fis.close();
					
			
			for (i=0; i<listOfFiles.length; i++) 
			{
		
				if(listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith("_TF_UP.csv")) 
				{
					includeIndex = false;
					
					for(j=0; j<putativeRegulatoryRegionIndices.length; j++)
					{
						if(fileName.contains(putativeRegulatoryRegionIndices[i]))
						{
							includeIndex = true;
						}
					}
			
					if(includeIndex)
					{
			
						fis = new FileInputStream(new StringBuffer(networkFolder).append(listOfFiles[i].getName()).toString());  
						dis = new DataInputStream(fis); 
						br = new BufferedReader(new InputStreamReader(dis));

						while((s = br.readLine()) != null)
						{	
							tmpS = s.split(",");
							// general,0 Hours,gene,TFAP2A,gene,CSRP2,positive,H00,H00
							// 0       1       2    3      4    5     6        7   8

							// general network model
							dupCheckStr = new StringBuffer(tmpS[3]).append(tmpS[5]).append(tmpS[6]).toString();
						
							if(dupHash.get(dupCheckStr)==null)
							{
								for(j=0; j<tmpS.length; j++)
								{
									if(j==0)
										pw.print("general");
									else if(j==1)
										pw.print(",NI Model");
									else
										pw.print(","+tmpS[j]);
								}
								
								pw.println("");
						
								dupHash.put(dupCheckStr, dupCheckStr);
							}
						
							// time point network
							dupCheckStr = new StringBuffer(tmpS[1]).append(tmpS[3]).append(tmpS[5]).append(tmpS[6]).toString();
						
							if(dupHash.get(dupCheckStr)==null)
							{
								pw.println(s);
						
								dupHash.put(dupCheckStr, dupCheckStr);
							}
						}

						br.close();
						dis.close();
						fis.close();
					}
				}
			} // end of i loop

			pw.close();
			fw.close();


		}catch(FileNotFoundException exp)
		{
			System.out.println("FileNotFoundException : "+exp);
			System.out.println(s);
		}catch(IOException exp)
		{
			System.out.println("IOException : "+exp);
			System.out.println(s);
		}
			
	}
	
	
	public void genNetworkFromFimoOutput(String path, String geneExpr, String fimoFolder, String fimoFile, String reg, String timepoint, String outputFolder, String outputFile, String upRegColor, String downRegColor, boolean printExpr)
	{
		String s = "";
		
		try
		{
			// for reading file
			FileInputStream	fis	=	new FileInputStream(fimoFolder + fimoFile);  
			DataInputStream	dis	=	new DataInputStream(fis); 
			BufferedReader	br 	=	new BufferedReader(new InputStreamReader(dis));
			
			// 
			FileWriter   	fw      =	new FileWriter(new StringBuffer(outputFolder).append(outputFile).append("_by_regulatory_site.csv").toString());
			PrintWriter  	pw      =	new PrintWriter(fw);
			
			// only regulations that TFs are expressed
			FileWriter   	fwtfup	=	new FileWriter(new StringBuffer(outputFolder).append(outputFile).append("_TF_UP.csv").toString());
			PrintWriter  	pwtfup	=	new PrintWriter(fwtfup);

			// TF binding sites bed file
			FileWriter   	fwreg		=	new FileWriter(new StringBuffer(outputFolder).append(outputFile).append("_TF_binding.bed").toString());
			PrintWriter  	pwreg		=	new PrintWriter(fwreg);

			// TF binding on regulatory region info
			FileWriter   	fwinfo	=	new FileWriter(new StringBuffer(outputFolder).append(outputFile).append("_TF_binding.csv").toString());
			PrintWriter  	pwinfo	=	new PrintWriter(fwinfo);


			String[]	tmpS	=	null;
			String[]	tmpS2	=	null;
			String[]	regulators	=	null;
			String[]	tfExprStr1	=	null;
			String[]	tfExprStr2	=	null;
			String[]	geneExprStr	=	null;

			String		theHour		=	this.getHourLabel(timepoint);
			String		regState	=	this.getRegState(reg);
			
			String		bioTapestryRegulationStr	=	"";
			String		bioTapestryRegulationStr2	=	"";
			String		ucscRegulationStr	=	"";
			
			int				start				=	0;
			
			Map<String,String>	exprHash	=	this.readGeneExpressions(path, geneExpr, timepoint);
			Map<String,String>	dupHash		=	new HashMap<String,String>();


			// read fimo output
			while((s = br.readLine()) != null) // read fimo output tsv file
			{
				tmpS = s.split("\t");
				// MA1149.1	RARA::RXRG	chr4_83688137_83688969_WHSC1	736	753	+	20.6727	8.45e-09	0.0497	ggggtcATCTCAGGGTCA
				// 0				1						2															3		4		5	6

				// obtain regulatory site coordinate
				if(tmpS.length>2) tmpS2 = tmpS[2].split("_"); else tmpS2 = null;


				if(!s.startsWith("motif_id") && tmpS2 !=null && 
					exprHash.get(tmpS2[3])!=null && // if the regulated gene has expression data
					Double.parseDouble(tmpS[6])>10.0) // fimo score higher than 10
				{
					// regulatory coordinate start
					start = Integer.parseInt(tmpS2[1]);

					geneExprStr = exprHash.get(tmpS2[3]).split(";");
					

					// if it's a regulator complex
					if((tmpS[1].length()>0 && tmpS[1].contains("::")) || (tmpS[1].length()==0 && tmpS[0].contains("_"))) 
					{
					
						if(tmpS[1].length()>0 && tmpS[1].contains("::"))
						{
							regulators = tmpS[0].split("::");
						}
						else if(tmpS[1].length()==0 && tmpS[0].contains("_"))
						{
							regulators = tmpS[0].split("_");
						}
						
							// if both the regulators are in the NI gene list
							if(exprHash.get(regulators[0])!=null && exprHash.get(regulators[1])!=null) 
							{
							
								tfExprStr1 = exprHash.get(regulators[0]).split(";");
								tfExprStr2 = exprHash.get(regulators[1]).split(";");
				
								// if both TFs are up or down
								if((tfExprStr1[0].equals("UP") && tfExprStr2[0].equals("UP")) || (tfExprStr1[0].equals("Down") && tfExprStr2[0].equals("Down")))
								{
								
									// regulator 1
									bioTapestryRegulationStr = this.getBioTapestryRegulationStr(theHour, regulators[0], tmpS2[3], tfExprStr1[1], geneExprStr[1], tfExprStr1[0], geneExprStr[0], regState, "1");
									
									if(tfExprStr1[0].equals("UP") && bioTapestryRegulationStr.length()>0 && dupHash.get(bioTapestryRegulationStr)==null)
									{
										pwtfup.println(bioTapestryRegulationStr);
										
										dupHash.put(bioTapestryRegulationStr, regState);
									}
						
									bioTapestryRegulationStr2 = this.getBioTapestryRegulationStr(theHour, regulators[0], tmpS[2], tfExprStr1[1], geneExprStr[1], tfExprStr1[0], geneExprStr[0], regState, "2");
						
									if(tfExprStr1[0].equals("UP") && bioTapestryRegulationStr2.length()>0 && dupHash.get(bioTapestryRegulationStr2)==null)
									{
										pw.println(bioTapestryRegulationStr2);
										
										dupHash.put(bioTapestryRegulationStr2, regState);
									}
									
									// regulator 2
									bioTapestryRegulationStr = this.getBioTapestryRegulationStr(theHour, regulators[1], tmpS2[3], tfExprStr2[1], geneExprStr[1], tfExprStr2[0], geneExprStr[0], regState, "1");
									
									if(tfExprStr2[0].equals("UP") && bioTapestryRegulationStr.length()>0 && dupHash.get(bioTapestryRegulationStr)==null)
									{
										pwtfup.println(bioTapestryRegulationStr);
										
										dupHash.put(bioTapestryRegulationStr, regState);
									}
									
									bioTapestryRegulationStr2 = this.getBioTapestryRegulationStr(theHour, regulators[1], tmpS[2], tfExprStr2[1], geneExprStr[1], tfExprStr2[0], geneExprStr[0], regState, "2");
						
									if(tfExprStr2[0].equals("UP") && bioTapestryRegulationStr2.length()>0 && dupHash.get(bioTapestryRegulationStr2)==null)
									{
										pw.println(bioTapestryRegulationStr2);
										
										dupHash.put(bioTapestryRegulationStr2, regState);
									}
									
									ucscRegulationStr = this.getUCSCRegulationStr(tmpS2[0], start, tmpS[3], tmpS[4], regulators[0]+"::"+regulators[1], tfExprStr1[0], upRegColor, downRegColor);
						
									if(ucscRegulationStr.length()>0 && dupHash.get(ucscRegulationStr)==null)
									{
										// print bed file for UCSC
										pwreg.println(ucscRegulationStr);
							
										dupHash.put(ucscRegulationStr, tmpS[1]);
										
										pwinfo.println(this.getRegulationInfoStr(tmpS2[3], regState, tmpS2[0], start, tmpS2[2], tmpS[3], tmpS[4], tmpS[1]));
									}	
									
								}
					
							}
						
					}	
					else if((tmpS[1].length()>0 && exprHash.get(tmpS[1])!=null)) // a single TF 
					{
						tfExprStr1 = exprHash.get(tmpS[1]).split(";");
						
						bioTapestryRegulationStr = this.getBioTapestryRegulationStr(theHour, tmpS[1], tmpS2[3], tfExprStr1[1], geneExprStr[1], tfExprStr1[0], geneExprStr[0], regState, "1");
						
						if(tfExprStr1[0].equals("UP") && bioTapestryRegulationStr.length()>0 && dupHash.get(bioTapestryRegulationStr)==null)
						{
							pwtfup.println(bioTapestryRegulationStr);
							
							dupHash.put(bioTapestryRegulationStr, regState);
						}
						
						bioTapestryRegulationStr2 = this.getBioTapestryRegulationStr(theHour, tmpS[1], tmpS[2], tfExprStr1[1], geneExprStr[1], tfExprStr1[0], geneExprStr[0], regState, "2");

						if(tfExprStr1[0].equals("UP") && bioTapestryRegulationStr2.length()>0 && dupHash.get(bioTapestryRegulationStr2)==null)
						{
							pw.println(bioTapestryRegulationStr2);
							
							dupHash.put(bioTapestryRegulationStr2, regState);
						}
						
						ucscRegulationStr = this.getUCSCRegulationStr(tmpS2[0], start, tmpS[3], tmpS[4], tmpS[1], tfExprStr1[0], upRegColor, downRegColor);
						
						if(ucscRegulationStr.length()>0 && dupHash.get(ucscRegulationStr)==null)
						{
							// print bed file for UCSC
							pwreg.println(ucscRegulationStr);
							
							dupHash.put(ucscRegulationStr, tmpS[1]);
							
							pwinfo.println(this.getRegulationInfoStr(tmpS2[3], regState, tmpS2[0], start, tmpS2[2], tmpS[3], tmpS[4], tmpS[1]));
						}				
										
					}
				}
			}

			br.close();
			dis.close();
			fis.close();

			pw.close();
			fw.close();

			pwtfup.close();
			fwtfup.close();
			
			pwreg.close();
			fwreg.close();
			
			pwinfo.close();
			fwinfo.close();

		}catch(FileNotFoundException exp)
		{
			System.out.println("FileNotFoundException : "+exp);
			System.out.println(s);
		}catch(IOException exp)
		{
			System.out.println("IOException : "+exp);
			System.out.println(s);
		}
			
	}
	
	
	/*
	 * return BioTapestry input csv format string
	 *
	 * regRegion: active or repress
	 * bioDataType: 1->main network; 2-> 2nd network, which includes active and repress regulatory regions
	 *
	 */
	public String getBioTapestryRegulationStr(String theHour, String tfName, String geneName, String tfGp, String geneGp, String tfExpression, String geneExpression, String regRegion, String bioDataType)
	{
		if(regRegion.equals("active"))
		{
			if(tfExpression.equals("UP") && geneExpression.equals("UP"))
			{
				return new StringBuffer("general,").append(theHour)
										.append(",gene,").append(tfName).append(",gene,").append(geneName)
										.append(",positive,")
										.append(tfGp).append(",").append(geneGp).toString();
			}
			else if(tfExpression.equals("UP") && geneExpression.equals("Down"))
			{
				return new StringBuffer("general,").append(theHour)
										.append(",gene,").append(tfName).append(",gene,").append(geneName)
										.append(",negative,")
										.append(tfGp).append(",").append(geneGp).toString();
			}
			else if(tfExpression.equals("Down") && geneExpression.equals("UP"))
			{
				return new StringBuffer("general,").append(theHour)
										.append(",gene,").append(tfName).append(",gene,").append(geneName)
										.append(",negative,")
										.append(tfGp).append(",").append(geneGp).toString();
			}
			else if(tfExpression.equals("Down") && geneExpression.equals("Down"))
			{
				return new StringBuffer("general,").append(theHour).
										append(",gene,").append(tfName).append(",gene,").append(geneName)
										.append(",positive,")
										.append(tfGp).append(",").append(geneGp).toString();
			}
			else
			{
				return "";
			}
		}
		else if(regRegion.equals("repress"))
		{
			if(tfExpression.equals("UP") && geneExpression.equals("UP"))
			{
				return new StringBuffer("general,").append(theHour)
										.append(",gene,").append(tfName).append(",gene,").append(geneName)
										.append(",negative,")
										.append(tfGp).append(",").append(geneGp).toString();
			}
			else if((tfExpression.equals("UP") && geneExpression.equals("Down")) || 
							(bioDataType.equals("2") && tfExpression.equals("UP") && geneExpression.equals("-")))
			{
				return new StringBuffer("general,").append(theHour)
										.append(",gene,").append(tfName).append(",gene,").append(geneName)
										.append(",positive,")
										.append(tfGp).append(",").append(geneGp).toString();
			}
			else if(tfExpression.equals("Down"))
			{
				return "";
			}
			else
			{
				return "";
			}
		}
		else
		{
			return "";
		}
	}
	
	
	public String getUCSCRegulationStr(String chr, int regRegionStart, String bindingSiteStart, String bindingSiteEnd, String tfName, String tfExpression, String upRegColor, String downRegColor)
	{
		if(tfExpression.equals("UP"))
		{
			return new StringBuffer(chr).append("\t")
										.append((regRegionStart+Integer.parseInt(bindingSiteStart)-1)).append("\t").append((regRegionStart+Integer.parseInt(bindingSiteEnd)-1)).append("\t")
										.append(tfName).append("\t0\t+\t")
										.append((regRegionStart+Integer.parseInt(bindingSiteStart)-1)).append("\t").append((regRegionStart+Integer.parseInt(bindingSiteEnd)-1)).append("\t")
										.append(upRegColor).toString();
		}
		else if(tfExpression.equals("Down"))
		{
			return new StringBuffer(chr).append("\t")
										.append((regRegionStart+Integer.parseInt(bindingSiteStart)-1)).append("\t").append((regRegionStart+Integer.parseInt(bindingSiteEnd)-1)).append("\t")
										.append(tfName).append("\t0\t+\t")
										.append((regRegionStart+Integer.parseInt(bindingSiteStart)-1)).append("\t").append((regRegionStart+Integer.parseInt(bindingSiteEnd)-1)).append("\t")
										.append(downRegColor).toString();
		}
		else
			return "";
	}
	
	public String getRegulationInfoStr(String gene, String regState, String chr, int regRegionStart, String regRegionEnd, String bindingSiteStart, String bindingSiteEnd, String tfName)
	{
		return new StringBuffer(gene).append(",").append(regState).append(",")
										 				.append(chr).append(",").append(regRegionStart).append(",").append(regRegionEnd).append(",")
										 				.append(tfName).append(",")
										 				.append(chr).append(",").append((regRegionStart+Integer.parseInt(bindingSiteStart)-1)).append(",").append((regRegionStart+Integer.parseInt(bindingSiteEnd)-1)).toString();
	}
	
	public String getHourLabel(String hour)
	{
		if(hour.equals("0h"))
			return "0 Hours";
		else if(hour.equals("1h"))
			return "1 Hours";
		else if(hour.equals("3h"))
			return "3 Hours";
		else if(hour.equals("5h"))
			return "5 Hours";
		else if(hour.equals("7h"))
			return "7 Hours";
		else if(hour.equals("9h"))
			return "9 Hours";
		else if(hour.equals("12h"))
			return "12 Hours";
		else
			return "";
	}


	public String getRegState(String reg)
	{
		if(reg.equals("active"))
		{
			return "active";
		}
		else if(reg.equals("repress") || reg.equals("index_4") || reg.equals("index_5") || reg.equals("index_6"))
		{
			return "repress";
		}
		else if(reg.equals("index_7_poised_active"))
		{
			return "active";
		}
		else if(reg.equals("index_8_active_poised") || 
						reg.equals("index_9_poised_repressed") || 
						reg.equals("index_10_repressed_poised") || 
						reg.equals("index_11_poised") || reg.equals("index_12_poised"))
		{
			return "poised";
		}
		else
		{ 
			return "";
		}
	}
	
	
	public Map<String,String> getGeneNameToID(String path, String geneInfo)
	{
		String s = "";
		Map<String,String>	geneNameHash	=	new HashMap<String,String>();
		
		try
		{
			// for reading file
			FileInputStream	fisgene	=	new FileInputStream(new StringBuffer(path).append(geneInfo).toString());  
			DataInputStream	disgene	=	new DataInputStream(fisgene); 
			BufferedReader	brgene	=	new BufferedReader(new InputStreamReader(disgene));

			String[]	tmpS	=	null;

			while((s = brgene.readLine()) != null)
			{
				tmpS = s.split("	");
				// no.	ensembl ID	NI_shown_name	Gene	synonym	chr	ctcf insulate region start	ctcf insulate region end	ctcf insulate region size	ID	DNA binding	known motif	coregulation	DREiVe output	strand
				// 0		1						2							3			4				5		6														7

				if(!s.startsWith("no"))
				{
					geneNameHash.put(tmpS[2], tmpS[1]); // gene name to ensembl id
				}
			}

			brgene.close();
			disgene.close();
			fisgene.close();


		}catch(FileNotFoundException exp)
		{
			System.out.println("FileNotFoundException : "+exp);
			System.out.println(s);
		}catch(IOException exp)
		{
			System.out.println("IOException : "+exp);
			System.out.println(s);
		}

		return geneNameHash;
	}


	public Map<String,String> readGeneExpressions(String path, String geneExpr, String timepoint)
	{
		String s = "";
		Map<String,String>	exprHash	=	new HashMap<String,String>();
		
		try
		{
			// for reading file
			FileInputStream	fisfpkm	=	new FileInputStream(new StringBuffer(path).append(geneExpr).toString());
			DataInputStream	disfpkm	=	new DataInputStream(fisfpkm); 
			BufferedReader	brfpkm	=	new BufferedReader(new InputStreamReader(disfpkm));

			String[]	tmpS	=	null;

			String		firstExprsHour = "";
			
			while((s = brfpkm.readLine()) != null)
			{
				tmpS = s.split("\t");
				// ensembl ID galGal5	name_in_NI_GRN	name_in_scRNAseq	Ensembl Name/ID galGal6	first_expressed_time_point	Exprs_0H	Exprs_1H	Exprs_3H	Exprs_5H	Exprs_7H	Exprs_9H	Exprs_12H	chr galGal5	start galGal5	end galGal5	gene_size
				// 0									1								2									3												4														5					6					7					8					9					10				11				12					13						14					15
				
				if(!s.startsWith("Genes") && !s.startsWith("ensembl") && !tmpS[4].equals("Excluded in GRN"))
				{
					//firstExprsHour = tmpS[13];
					firstExprsHour = "A"; // space name in BioTapestry
					
					if(timepoint.equals("0h"))
						exprHash.put(tmpS[1], new StringBuffer(tmpS[5]).append(";").append(firstExprsHour).toString()); // 0hour
					else if(timepoint.equals("1h"))
						exprHash.put(tmpS[1], new StringBuffer(tmpS[6]).append(";").append(firstExprsHour).toString()); // 1hour
					else if(timepoint.equals("3h"))
						exprHash.put(tmpS[1], new StringBuffer(tmpS[7]).append(";").append(firstExprsHour).toString()); // 3hour
					else if(timepoint.equals("5h"))
						exprHash.put(tmpS[1], new StringBuffer(tmpS[8]).append(";").append(firstExprsHour).toString()); // 5hour
					else if(timepoint.equals("7h"))
						exprHash.put(tmpS[1], new StringBuffer(tmpS[9]).append(";").append(firstExprsHour).toString()); // 7hour
					else if(timepoint.equals("9h"))
						exprHash.put(tmpS[1], new StringBuffer(tmpS[10]).append(";").append(firstExprsHour).toString()); // 9hour
					else if(timepoint.equals("12h"))
						exprHash.put(tmpS[1], new StringBuffer(tmpS[11]).append(";").append(firstExprsHour).toString()); // 12hour
					else
						System.out.println("unknown time point");
				}
			}

			brfpkm.close();
			disfpkm.close();
			fisfpkm.close();

		}catch(FileNotFoundException exp)
		{
			System.out.println("FileNotFoundException : "+exp);
			System.out.println(s);
		}catch(IOException exp)
		{
			System.out.println("IOException : "+exp);
			System.out.println(s);
		}

		return exprHash;
	}



}