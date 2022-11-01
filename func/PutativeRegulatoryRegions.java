package network_code_v5.func;


import java.io.*;
import java.util.*;


public class PutativeRegulatoryRegions
{

	public PutativeRegulatoryRegions(){}
	

	/*
	 *
	 */
	public void genPutativeRegulatoryRegionsFasta(String path, String bedFile, String geneInfoPath, String geneInfo, String geneExpr, String outputPath, String outputFile, String galGal5ChrFastaFolder, boolean printF)
	{
		String s = "";
		
		try
		{
			// for reading file
			FileInputStream	fis	=	new FileInputStream(new StringBuffer(path).append(bedFile).toString());  
			DataInputStream	dis	=	new DataInputStream(fis); 
			BufferedReader	br 	=	new BufferedReader(new InputStreamReader(dis));
			
			FileInputStream	fisgene	=	new FileInputStream(new StringBuffer(geneInfoPath).append(geneInfo).toString());  
			DataInputStream	disgene	=	new DataInputStream(fisgene); 
			BufferedReader	brgene	=	new BufferedReader(new InputStreamReader(disgene));

			FileInputStream	fisexpr	=	new FileInputStream(new StringBuffer(geneInfoPath).append(geneExpr).toString());  
			DataInputStream	disexpr	=	new DataInputStream(fisexpr); 
			BufferedReader	brexpr	=	new BufferedReader(new InputStreamReader(disexpr));


			String[]	tmpS	=	null;
			String[]	tmpS2	=	null;
			String[]	tmpS3	=	null;

			int		i	=	0;

			Map<String,Map<String,String>>	chrHash		=	new HashMap<String,Map<String,String>>();
			Map<String,String>							ctcfHash	=	new HashMap<String,String>();

			Map<String,Map<String,String>>	printChrHash	=	new HashMap<String,Map<String,String>>();
			Map<String,String>							printCtcfHash	=	new HashMap<String,String>();

			Map<String,String>							exprHash	=	new HashMap<String,String>();


			// select only differentially expressed genes
			while((s = brexpr.readLine()) != null)
			{
				tmpS = s.split("\t");
				// ensembl ID galGal5	name_in_NI_GRN	name_in_scRNAseq	Ensembl Name/ID galGal6	first_expressed_time_point	Exprs_0H	Exprs_1H	Exprs_3H	Exprs_5H	Exprs_7H	Exprs_9H	Exprs_12H	chr galGal5	start galGal5	end galGal5	gene_size	scRNAseq_evidence_gal5
				// 0									1								2									3												4
				
				
				if(tmpS.length>4 && !s.startsWith("ensembl"))
				{
						if(!tmpS[4].equals("Excluded in GRN"))
						{
							exprHash.put(tmpS[0], tmpS[1]);
						}
				}
				
			}			
			
			brexpr.close();
			disexpr.close();
			fisexpr.close();
					
					
			// extract ctcf boundaries for each gene from gene info file
			while((s = brgene.readLine()) != null)
			{
				tmpS = s.split("\t");
				// no.	ensembl ID	NI_shown_name	Gene	synonym	chr	ctcf insulate region start	ctcf insulate region end	ctcf insulate region size	ID	DNA binding	known motif	coregulation	DREiVe output	strand
				// 0			1						2							3			4				5		6														7

				if(!s.startsWith("no") && tmpS.length>6 && 
					tmpS[5].length()>0 && tmpS[6].length()>0 && tmpS[7].length()>0 && 
					tmpS[5].startsWith("chr") &&
					exprHash.get(tmpS[1])!=null)
				{

					if(chrHash.get(tmpS[5])!=null)
					{
						ctcfHash = (Map<String,String>)chrHash.get(tmpS[5]);
					}
					else
					{
						ctcfHash = new HashMap<String,String>();
					}

					// record all bases that are within ctcf defined boudaries
					for(i=Integer.parseInt(tmpS[6]); i<=Integer.parseInt(tmpS[7]); i++)
					{
						ctcfHash.put(String.valueOf(i), new StringBuffer(String.valueOf(i)).append(",").append(tmpS[3]).toString());
					}

					chrHash.put(tmpS[5], ctcfHash);
				}
			}

			brgene.close();
			disgene.close();
			fisgene.close();


			// extract chipseq peaks that are within ctcf boundaries
			while((s = br.readLine()) != null)
			{
				if(!s.startsWith("track"))
				{
					tmpS = s.split("	");
					// chr1    178700  179684  9h-IN-H3K27ac_peak_34   82      .       2.84398 9.93591 8.28431
					// 0			 1				2		   3			   							 4		   5	   	 6		   7		   8
					
					if(tmpS.length>0 && chrHash.get(tmpS[0])!=null)
					{
						ctcfHash = (Map<String,String>)chrHash.get(tmpS[0]);
						
						if(ctcfHash.get(tmpS[1])!=null || ctcfHash.get(tmpS[2])!=null)
						{
							
							if(ctcfHash.get(tmpS[1])!=null)
							{//System.out.println(ctcfHash.get(tmpS[1]));
								tmpS2 = ((String)ctcfHash.get(tmpS[1])).split(",");
							}
							else if(ctcfHash.get(tmpS[2])!=null)
							{//System.out.println(ctcfHash.get(tmpS[2]));
								tmpS2 = ((String)ctcfHash.get(tmpS[2])).split(",");
							}

							if(printChrHash.get(tmpS[0])!=null)
							{
								printCtcfHash = printChrHash.get(tmpS[0]);
							}
							else
							{
								printCtcfHash = new HashMap<String,String>();
							}
							
							if(tmpS[3].equals("1"))
							{
								tmpS3 = tmpS[4].split("_");
							}
							else
							{
								tmpS3 = tmpS[3].split("_");
							}
							
							//// add gene name in chipseq peak regions
							printCtcfHash.put(tmpS[1], 
																new StringBuffer(tmpS[2]).append(",").append(tmpS2[1]).toString());
							//                                   end posistion               NI_shown_name            
							
							printChrHash.put(tmpS[0], printCtcfHash);
						}
					}
				}
			}

			br.close();
			dis.close();
			fis.close();

			ctcfHash = null;
			chrHash = null;

			this.printFasta(galGal5ChrFastaFolder, printChrHash, outputPath, outputFile, printF);
			
			printChrHash = null;
			
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
	

	/**
		*	output bed and fasta files for NI gene neighbouring regions
		* only generate fasta file when printF==true
		*/
	public void printFasta(String galGal5ChrFastaFolder, Map<String,Map<String,String>> printChrHash, String outputPath, String outputFile, boolean printF)
	{
		String s = "";
		
		try
		{
			// for reading file
			FileInputStream	fis	=	null;  
			DataInputStream	dis	=	null; 
			BufferedReader	br 	=	null;
			
			FileWriter   	fwbed =	new FileWriter(new StringBuffer(outputPath).append(outputFile).append(".bed").toString());
			PrintWriter  	pwbed =	new PrintWriter(fwbed);

			FileWriter		fw 		= null;
			PrintWriter		pw 		=	null;
				
			if(printF)
			{
				fw 		= new FileWriter(new StringBuffer(outputPath).append(outputFile).append(".fasta").toString());
				pw 		= new PrintWriter(fw);
			}

			File		f	=	null;
			
			StringBuffer	seq	=	null;

			int		i	=	0;
			int		regionStart	=	0;
			int		regionEnd	=	0;
			int		regionMid	=	0;

			Map<String,String>	printCtcfHash	= null;

			String[]	tmpS	=	null;

			Set set = printChrHash.entrySet();
			Iterator iterator = set.iterator();
			Map.Entry mentry = null;

			Set setPrint = null;
			Iterator iteratorPrint = null;
			Map.Entry mentryPrint = null;
			String	key	=	"";

			// print fasta by chromosomes
			while(iterator.hasNext()) 
			{
				mentry = (Map.Entry)iterator.next();
				key = (String)mentry.getKey();	

				f = new File(new StringBuffer(galGal5ChrFastaFolder).append(key).append(".fa").toString());
				
				if(f.exists() && !f.isDirectory())
				{
					fis = new FileInputStream(new StringBuffer(galGal5ChrFastaFolder).append(key).append(".fa").toString());  
					dis = new DataInputStream(fis); 
					br = new BufferedReader(new InputStreamReader(dis));

					// retrive sequence of a chromosome
					seq = new StringBuffer("");

					while((s = br.readLine()) != null)
					{
						if(!s.startsWith(">"))
						{
							seq = seq.append(s);
						}
					}

					br.close();
					dis.close();
					fis.close();


					printCtcfHash = printChrHash.get(key);
					setPrint = printCtcfHash.entrySet();
					iteratorPrint = setPrint.iterator();

					while(iteratorPrint.hasNext()) 
					{
						mentryPrint = (Map.Entry)iteratorPrint.next();

						regionStart = Integer.parseInt((String)mentryPrint.getKey());

						tmpS = ((String)mentryPrint.getValue()).split(",");
						regionEnd = Integer.parseInt(tmpS[0]);
					
						regionMid = (regionEnd-regionStart)/2;

						// when the region is smaller than 110 bp, expand the region size
						if((regionEnd-regionStart)<110)
						{
							if(regionMid<=56)
							{
								regionStart = 1;
							}
							else
							{
								regionStart = regionMid - 56;
							}

							regionEnd = regionMid + 56;

							if(regionEnd > seq.toString().length())
							{
								regionEnd = seq.toString().length();
							}
						}
						
						if(printF)
						{
							pw.println(new StringBuffer(">").append(key).append("_").append(regionStart).append("_").append(regionEnd).append("_").append(tmpS[1]).toString());
							pw.println(seq.toString().substring(regionStart-1, regionEnd));
						}
						
						pwbed.println(new StringBuffer(key).append("	").append(regionStart).append("	").append(regionEnd).append("	").append(tmpS[1]).toString());

					}
					
					iteratorPrint = null;
					setPrint = null;
					printCtcfHash = null;
					
				}
			}
	
			if(printF)
			{
				pw.close();
				fw.close();
			}
			
			pwbed.close();
			fwbed.close();
			
			set = null;
			iterator = null;
			mentry = null;
			printChrHash = null;
			
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