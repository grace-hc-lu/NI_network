package network_code_v5;

import network_code_v5.proc.*;
import network_code_v5.beans.*;

import java.io.*;
import java.util.*;
import java.lang.*;


/**
	* to compile the code: under regulatory_network folder run javac @networkCodeSource.txt
	* then run java network_code_v5.NIDataHandler
	*
	*
	****************** for generating NI network **************
  	* UPDATES from v3:
  	*
  	* -use only regions with index 1,2,3 (active), 4 (repressed) and 7 (poised>active)
  	* 
  	* UPDATES from v4:
  	* 
  	* -generate 2 networks
  	*  -use index 1,2,3 and 7 to generate the main network
  	*  -use index 4,5,6 to generate the 2nd network
  	* 
  	***********************************************************
  	*
  	* steps in details:
  	*
	* 	1. obtain constitutive CTCF boundaries for NI genes
	*
	*
	*	2. take ChIPseq peak calling results from MACS2 and CTCF bounderies (in broad peak format)
	*	   - produce bed file which contains putative regulatory regions and sequence (fasta format) for each NI genes.
	*
	*     - inputfiles: i.	gene info file (180 genes) that have differential expressions in RNAseq and NanoString data
	*									 ii.	regulatory sites indices 1, 2, 3, 4 and 7
	*     - output files:	 i.	coordinates of gene regulatory regions within ctcf boundary (*.bed)
	*									 		ii.	fasta sequences of gene regulatory regions (*.fasta)
	*
	*    **use output fasta sequence file from step 2 to run FIMO for motif screening
	*
	*
	*	3. take FIMO TF binding prediction output and gene expression profile as input 
	*    - FIMO TF binding output is filter with score >10.0
	*    - differentially expressed genes from RNAseq UP(FC>1.5) and Down(FC<0.5) and NanoString UP(FC>1.2) and Down(FC<0.8)
	*    - calculate the coordinates in gal5 of binding sites
	*
	*    - inputfiles: FIMO outputs for indices 1,2,3,4 and 7
	*    - output regulation relationships between TFs and genes in each time point
	*    output files:	i.	regulation relation files in csv format
	*									 ii.	TF binding coordinates in bed file format
	*									iii.	TF binding on regulatory region info file in csv format
	*
	*
	*	4. integrate output files from step 3, generate main network files for BioTapestry
	*		 - inputfiles:  i.	regulatory sites indices 1,2,3 and 7 for contracting the main network
	*                	 ii.	regulatory sites indices 1,2,3,4,5,6 and 7 for TF binding sites on UCSC
	*
	*
	*	5. generate a network with a selected gene's enhancers of all chromatin state, including active and repress
	*	   and subtract up-stream genes of the selected gene
	*	   - input files: indices 1 to 7
	*
	*    - use bedtools to merge regulatory regions in the output file *_reg_coordinates.bed
	*      #-------------------
				 GeneName=BRD8
				 cd network
	       sort -k1,1 -k2,2n "$GeneName"_reg_coordinates.bed > bed.sort.tmp
	       bedtools merge -i bed.sort.tmp -d 500 -c 1,4 -o count,collapse >"$GeneName"_reg_coordinates.txt
	       rm bed.sort.tmp
	       cd ..
	*	   #-------------------
	*
	*    - replace the names of regulatory regions that overlaps
	*
	*	
	*
	*
	*
	*
	****************** code structure *************************
	*
	* NIDataHandler --|-- ConfigBeans
	*                 |-- PutativeRegulatoryRegionsProc -- PutativeRegulatoryRegions
	*                 |-- NetworkInteractionsProc -- NetworkInteractions
	*                 |-- GenerateBioTapestryNetworkProc -- NetworkInteractions
	*                 |-- UCSCbrowserDataProc -- UCSCbrowserData
	*                 
	*
	*
	**/


public class NIDataHandler
{
	
	public static void main(String args[])
	{
		ConfigBeans												configBean	=	new ConfigBeans("config.txt");
		PutativeRegulatoryRegionsProc			regRegions	=	new PutativeRegulatoryRegionsProc();
		NetworkInteractionsProc						network			=	new NetworkInteractionsProc();
		GenerateBioTapestryNetworkProc		bioT				=	new GenerateBioTapestryNetworkProc();
		UCSCbrowserDataProc								ucsc				=	new	UCSCbrowserDataProc();

		// 1.

		// 2.
		//regRegions.genPutativeRegulatoryRegion(configBean);
		
		// 3.
		//network.genNetworkFromFimoOutput(configBean);

		// 4. 
		//bioT.integrateInteractionFiles(configBean);
		//bioT.printBiotapstryTimeExpressionFile(configBean);
		//ucsc.printUCSCGeneExpressionBedFiles(configBean);
		//ucsc.genPutativeRegulatoryRegion(configBean);
		ucsc.printUCSCTFBindingBedFiles(configBean);

		// 5. generate a network with a selected gene
		//bioT.subtractNetworkOfGene(configBean);
		//bioT.removeDuplicatedInputOfGeneNetwork(configBean);

		// 6.

	}

}
