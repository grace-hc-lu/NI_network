package network_code_v5.func;


import java.io.*;
import java.util.*;


public class UCSCbrowserData
{

	public UCSCbrowserData(){}

	/*
	 * TF binding tracks
	 * integrate files generated from NetworkInteractions.genNetworkFromFimoOutput()
	 *
	 */
	public void printUCSCTFBindingBedFiles(String networkFolder, String ucscFolder)
	{
		String s = "";
		
		try
		{
			// for reading file
			FileInputStream	fis	=	null;  
			DataInputStream	dis	=	null; 
			BufferedReader	br 	=	null;
			
			
			FileWriter   	fw5h    =	new FileWriter(new StringBuffer(ucscFolder).append("TF_binding_5h.bed").toString());
			PrintWriter  	pw5h    =	new PrintWriter(fw5h);
			
			FileWriter   	fw9h    =	new FileWriter(new StringBuffer(ucscFolder).append("TF_binding_9h.bed").toString());
			PrintWriter  	pw9h    =	new PrintWriter(fw9h);
			
			FileWriter   	fw12h   =	new FileWriter(new StringBuffer(ucscFolder).append("TF_binding_12h.bed").toString());
			PrintWriter  	pw12h   =	new PrintWriter(fw12h);
			
			File		folder			= new File(networkFolder);
			File[]	listOfFiles	= folder.listFiles();

			String[]	tmpS	=	null;
			int				i			=	0;

			String		fileName			=	"";
			
			// print bed file header
			pw5h.println("track name=\"5H_TF_binding\" description=\"node graft 5H TF binding sites\" visibility=4 itemRgb=\"On\"");
			pw9h.println("track name=\"9H_TF_binding\" description=\"node graft 9H TF binding sites\" visibility=4 itemRgb=\"On\"");
			pw12h.println("track name=\"12H_TF_binding\" description=\"node graft 12H TF binding sites\" visibility=4 itemRgb=\"On\"");
		
			
			for (i = 0; i < listOfFiles.length; i++) 
			{
				if(listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith("_TF_binding.bed")) 
				{
					fileName = listOfFiles[i].getName();
				
					fis = new FileInputStream(new StringBuffer(networkFolder).append(fileName).toString());  
					dis = new DataInputStream(fis); 
					br = new BufferedReader(new InputStreamReader(dis));

					while((s = br.readLine()) != null)
					{
						if(fileName.contains("_5h_"))
						{
							pw5h.println(s);
						}
						else if(fileName.contains("_9h_"))
						{
							pw9h.println(s);
						}
						else if(fileName.contains("_12h_"))
						{
							pw12h.println(s);
						}

					}	
					
					br.close();
					dis.close();
					fis.close();
					
				}
			} // end of i loop
			
			pw5h.close();
			fw5h.close();
			
			pw9h.close();
			fw9h.close();
			
			pw12h.close();
			fw12h.close();
			
		
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
	
	
	// ChIPseq data tracks
	public void genPutativeRegulatoryRegionBedFiles(String putativeRegulatoryRegionOutputFolder, String outputFolder, String activationColor, String repressionColor, String poisedColor, String noChangeColor)
	{
		String s = "";
		
		try
		{
			// for reading file
			FileInputStream	fis	=	null;  
			DataInputStream	dis	=	null; 
			BufferedReader	br 	=	null;
			
			
			FileWriter   	fw5h    =	new FileWriter(new StringBuffer(outputFolder).append("regulatory_sites_5h.bed").toString());
			PrintWriter  	pw5h    =	new PrintWriter(fw5h);
			
			FileWriter   	fw9h    =	new FileWriter(new StringBuffer(outputFolder).append("regulatory_sites_9h.bed").toString());
			PrintWriter  	pw9h    =	new PrintWriter(fw9h);
			
			FileWriter   	fw12h   =	new FileWriter(new StringBuffer(outputFolder).append("regulatory_sites_12h.bed").toString());
			PrintWriter  	pw12h   =	new PrintWriter(fw12h);
			
			
			File		folder			= new File(putativeRegulatoryRegionOutputFolder);
			File[]	listOfFiles	= folder.listFiles();

			String[]	tmpS	=	null;
			int				i			=	0;

			String		fileName			=	"";
			String		regCol				=	"";
			String		regName				=	"";
			
			// bed file header
			pw5h.println("track name=\"5H_regulatory_sites\" description=\"node graft 5H cis-regulatory sites\" visibility=4 itemRgb=\"On\"");
			pw9h.println("track name=\"9H_regulatory_sites\" description=\"node graft 9H cis-regulatory sites\" visibility=4 itemRgb=\"On\"");
			pw12h.println("track name=\"12H_regulatory_sites\" description=\"node graft 12H cis-regulatory sites\" visibility=4 itemRgb=\"On\"");
		

			for (i = 0; i < listOfFiles.length; i++) 
			{
				if(listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".bed")) 
				{
					fileName = listOfFiles[i].getName();

					if(fileName.contains("active") && !fileName.contains("poised"))
					{
						regCol = activationColor; //dark blue
						regName = "Active";
					}
					else if(fileName.contains("index_4"))
					{
						regCol = repressionColor; // sky blue
						regName = "Repressed_index_4";
					}
					else if(fileName.contains("index_5") || fileName.contains("index_6"))
					{
						regCol = repressionColor; // sky blue
						regName = "Repressed";
					}
					else if(fileName.contains("index_7"))
					{
						regCol = poisedColor; // green
						regName = "Poised->Active";
					}
					else if(fileName.contains("index_8") || fileName.contains("index_9") || fileName.contains("index_10") || fileName.contains("index_11") || fileName.contains("index_12"))
					{
						regCol = poisedColor; // green
						regName = "Poised";
					}
					else if(fileName.contains("index_13_") || fileName.contains("index_14_") || fileName.contains("index_16_"))
					{
						regCol = noChangeColor; // orange
						regName = "No_change";
					}
					else
					{
						regCol = "";
						regName = "";
					}
					
									
					if(fileName.length()>0 && regCol.length()>0)
					{

						fis = new FileInputStream(new StringBuffer(putativeRegulatoryRegionOutputFolder).append(fileName).toString());  
						dis = new DataInputStream(fis); 
						br = new BufferedReader(new InputStreamReader(dis));

						while((s = br.readLine()) != null)
						{		
							tmpS = s.split("\t");
							
							if(fileName.contains("_5h_"))
							{
								pw5h.println(new StringBuffer(tmpS[0]).append("\t").append(tmpS[1]).append("\t").append(tmpS[2]).append("\t")
														.append(regName).append("\t0\t.\t").append(tmpS[1]).append("\t").append(tmpS[2]).append("\t").append(regCol));
							}
							else if(fileName.contains("_9h_"))
							{
								pw9h.println(new StringBuffer(tmpS[0]).append("\t").append(tmpS[1]).append("\t").append(tmpS[2]).append("\t")
														.append(regName).append("\t0\t.\t").append(tmpS[1]).append("\t").append(tmpS[2]).append("\t").append(regCol));
							}
							else if(fileName.contains("_12h_"))
							{
								pw12h.println(new StringBuffer(tmpS[0]).append("\t").append(tmpS[1]).append("\t").append(tmpS[2]).append("\t")
														.append(regName).append("\t0\t.\t").append(tmpS[1]).append("\t").append(tmpS[2]).append("\t").append(regCol));
							}
						}
						
						br.close();
						dis.close();
						fis.close();
					} // end of if 
				}
			} // end of i loop
			
			pw5h.close();
			fw5h.close();
			
			pw9h.close();
			fw9h.close();
			
			pw12h.close();
			fw12h.close();
			

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
	
	
	// RNAseq data tracks
	public void printGeneExpressionsForUCSC(String path, String geneExpr, String upRegColor, String downRegColor, String outputFolder)
	{
		String s = "";
		Map<String,String>	exprHash	=	new HashMap<String,String>();
		
		try
		{
			// for reading file
			FileInputStream	fisfpkm	=	new FileInputStream(new StringBuffer(path).append(geneExpr).toString());
			DataInputStream	disfpkm	=	new DataInputStream(fisfpkm); 
			BufferedReader	brfpkm	=	new BufferedReader(new InputStreamReader(disfpkm));

			FileWriter   	fw5h    =	new FileWriter(new StringBuffer(outputFolder).append("gene_expressions_5h.bed").toString());
			PrintWriter  	pw5h    =	new PrintWriter(fw5h);
			
			FileWriter   	fw9h    =	new FileWriter(new StringBuffer(outputFolder).append("gene_expressions_9h.bed").toString());
			PrintWriter  	pw9h    =	new PrintWriter(fw9h);
			
			FileWriter   	fw12h   =	new FileWriter(new StringBuffer(outputFolder).append("gene_expressions_12h.bed").toString());
			PrintWriter  	pw12h   =	new PrintWriter(fw12h);
			
			String[]	tmpS	=	null;


			pw5h.println("track name=\"5H_gene_expression\" description=\"node graft 5H gene expression\" visibility=4 itemRgb=\"On\"");
			pw9h.println("track name=\"9H_gene_expression\" description=\"node graft 9H gene expression\" visibility=4 itemRgb=\"On\"");
			pw12h.println("track name=\"12H_gene_expression\" description=\"node graft 12H gene expression\" visibility=4 itemRgb=\"On\"");
		

			while((s = brfpkm.readLine()) != null)
			{
				if(!s.startsWith("tracking_id"))
				{
					tmpS = s.split(",");
					// tracking_id,gene_id,gene_short_name,chr,start,end,PS_cEpiblast,aAO,NP_Epiblast,nonNeural,Uninduced_0hrs,Uninduced_5hrs,Induced_5hrs,Uninduced_9hrs,Induced_9hrs,Uninduced_12hrs,Induced_12hrs,FC_PS,FC_0hrs,FC_5hrs,FC_9hrs,FC_12hrs,FC_NP
					// 0           1       2               3   4     5   6            7   8           9         10             11             12           13             14           15              16            17    18      19      20      21       22

					if(!tmpS[3].startsWith("A") && !tmpS[3].startsWith("K") && !tmpS[3].startsWith("M"))
					{
						// 5hr
						if(!tmpS[19].equals("Inf") && Double.parseDouble(tmpS[19])>=1.5 && Double.parseDouble(tmpS[12])>=10.0) // up
						{
							pw5h.println(new StringBuffer("chr").append(tmpS[3]).append("	").append(tmpS[4]).append("	").append(tmpS[5]).append("	")
														.append(tmpS[2]).append("	0	.	").append(tmpS[4]).append("	").append(tmpS[5]).append("	").append(upRegColor));
						}
						else if(!tmpS[19].equals("Inf") && Double.parseDouble(tmpS[19])<=0.5 && Double.parseDouble(tmpS[11])>=10.0) // down
						{
							pw5h.println(new StringBuffer("chr").append(tmpS[3]).append("	").append(tmpS[4]).append("	").append(tmpS[5]).append("	")
														.append(tmpS[2]).append("	0	.	").append(tmpS[4]).append("	").append(tmpS[5]).append("	").append(downRegColor));
						}
						
						// 9hr
						if(!tmpS[20].equals("Inf") && Double.parseDouble(tmpS[20])>=1.5 && Double.parseDouble(tmpS[14])>=10.0) 
						{
							pw9h.println(new StringBuffer("chr").append(tmpS[3]).append("	").append(tmpS[4]).append("	").append(tmpS[5]).append("	")
														.append(tmpS[2]).append("	0	.	").append(tmpS[4]).append("	").append(tmpS[5]).append("	").append(upRegColor));
						}
						else if(!tmpS[20].equals("Inf") && Double.parseDouble(tmpS[20])<=0.5 && Double.parseDouble(tmpS[13])>=10.0) 
						{
							pw9h.println(new StringBuffer("chr").append(tmpS[3]).append("	").append(tmpS[4]).append("	").append(tmpS[5]).append("	")
														.append(tmpS[2]).append("	0	.	").append(tmpS[4]).append("	").append(tmpS[5]).append("	").append(downRegColor));
						}
								
						// 12hr
						if(!tmpS[21].equals("Inf") && Double.parseDouble(tmpS[21])>=1.5 && Double.parseDouble(tmpS[16])>=10.0) 
						{
							pw12h.println(new StringBuffer("chr").append(tmpS[3]).append("	").append(tmpS[4]).append("	").append(tmpS[5]).append("	")
														.append(tmpS[2]).append("	0	.	").append(tmpS[4]).append("	").append(tmpS[5]).append("	").append(upRegColor));
						}
						else if(!tmpS[21].equals("Inf") && Double.parseDouble(tmpS[21])<=0.5 && Double.parseDouble(tmpS[15])>=10.0) 
						{
							pw12h.println(new StringBuffer("chr").append(tmpS[3]).append("	").append(tmpS[4]).append("	").append(tmpS[5]).append("	")
														.append(tmpS[2]).append("	0	.	").append(tmpS[4]).append("	").append(tmpS[5]).append("	").append(downRegColor));
						}
					
					}
				
				}
			}

			pw5h.close();
			fw5h.close();

			pw9h.close();
			fw9h.close();

			pw12h.close();
			fw12h.close();

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

	}



	
}