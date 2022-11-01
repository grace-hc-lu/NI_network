package network_code_v5.proc;

import network_code_v5.func.*;
import network_code_v5.beans.*;

import java.io.*;
import java.util.*;


public class PutativeRegulatoryRegionsProc
{

	public PutativeRegulatoryRegionsProc(){}

	
	public void genPutativeRegulatoryRegion(ConfigBeans configBean)
	{	
		PutativeRegulatoryRegions	handler			=	new PutativeRegulatoryRegions();
		
		String	bedFile			=	"";
		String	outputFile	=	"";	
	
		int	i	=	0;
		int	j	=	0;
		
		
		for(i=0; i<configBean.getPutativeRegulatoryRegionHour().length; i++)
		{
		
			// for active regulatory regions, including active, index_7_poised_active
			for(j=0; j<configBean.getPutativeRegulatoryRegionIndices().length; j++)
			{
				bedFile = new StringBuffer(configBean.getPutativeRegulatoryRegionIndices()[j]).append("_")
												.append(configBean.getPutativeRegulatoryRegionHour()[i]).append(".bed").toString();
													 
				outputFile = new StringBuffer(configBean.getPutativeRegulatoryRegionIndices()[j]).append("_")
												.append(configBean.getPutativeRegulatoryRegionHour()[i]).append("_NI_network").toString();	

				//System.out.println(bedFile);
		
				handler.genPutativeRegulatoryRegionsFasta(configBean.getPutativeRegulatoryRegionIndexFolder(), bedFile, 
					configBean.getWorkingDirectory(), configBean.getGeneInfo(), configBean.getGeneExpr(),
					configBean.getPutativeRegulatoryRegionOutputFolder(), outputFile,
					configBean.getGalGal5ChrFastaFolder(), true);
			
			}
			
			// other regions
			for(j=0; j<configBean.getOtherRegulatoryRegions().length; j++)
			{
				bedFile = new StringBuffer(configBean.getOtherRegulatoryRegions()[j]).append("_")
												.append(configBean.getPutativeRegulatoryRegionHour()[i]).append(".bed").toString();
													 
				outputFile = new StringBuffer(configBean.getOtherRegulatoryRegions()[j]).append("_")
												.append(configBean.getPutativeRegulatoryRegionHour()[i]).append("_NI_network").toString();	

				//System.out.println(bedFile);
		
				handler.genPutativeRegulatoryRegionsFasta(configBean.getPutativeRegulatoryRegionIndexFolder(), bedFile, 
					configBean.getWorkingDirectory(), configBean.getGeneInfo(), configBean.getGeneExpr(),
					configBean.getPutativeRegulatoryRegionOutputFolder(), outputFile,
					configBean.getGalGal5ChrFastaFolder(), true);
			
			}
			
		} // end of loop i
		
		
	} // end of public void genPutativeRegulatoryRegion(ConfigBeans configBean)
	
	
}