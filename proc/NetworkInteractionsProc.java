package network_code_v5.proc;

import network_code_v5.func.*;
import network_code_v5.beans.*;

import java.io.*;
import java.util.*;

public class NetworkInteractionsProc
{

	public NetworkInteractionsProc(){}


	public void genNetworkFromFimoOutput(ConfigBeans configBean)
	{	
		NetworkInteractions	handler			=	new NetworkInteractions();
		
		String	fimoFile		=	"";
		String	outputFile	=	"";
		
		int	i	=	0;
		int	j	=	0;
		
		for(i=0; i<configBean.getNetworkTimePoints().length; i++)
		{

			for(j=0; j<configBean.getSelectedRegulatoryIndices().length; j++)
			{
			
				// time point 0, 1, 3 use ChIPseq 5h data
				if(configBean.getNetworkTimePoints()[i].equals("0h") || 
				 	 configBean.getNetworkTimePoints()[i].equals("1h") || 
				 	 configBean.getNetworkTimePoints()[i].equals("3h"))
				{
					fimoFile = new StringBuffer("fimo_out_").append(configBean.getSelectedRegulatoryIndices()[j]).append("_5h/fimo.tsv").toString(); // for 0,1,3h
				}
				else if(configBean.getNetworkTimePoints()[i].equals("7h")) // time point 7 use ChIPseq 9h data
				{
					fimoFile = new StringBuffer("fimo_out_").append(configBean.getSelectedRegulatoryIndices()[j]).append("_9h/fimo.tsv").toString();	 // for 7
				}
				else
				{
					fimoFile = new StringBuffer("fimo_out_").append(configBean.getSelectedRegulatoryIndices()[j]).append("_").append(configBean.getNetworkTimePoints()[i]).append("/fimo.tsv").toString();	 // for 5,9,12
				}

				outputFile = new StringBuffer("NI_").append(configBean.getNetworkTimePoints()[i]).append("_network_").append(configBean.getSelectedRegulatoryIndices()[j]).toString();

				handler.genNetworkFromFimoOutput(configBean.getWorkingDirectory(), configBean.getGeneExpr(), 
																					configBean.getFimoOutputFolder(),
																					fimoFile, 
																					configBean.getSelectedRegulatoryIndices()[j], 
																					configBean.getNetworkTimePoints()[i], 
																					configBean.getNetworkFolder(), 
																					outputFile, 
																					configBean.getUpRegColor(), 
																					configBean.getDownRegColor(),
																					configBean.getPrintExpr());

			} // end of for loop j
		} // end of for loop i
	} // end of public void genNetworkFromFimoOutput(ConfigBeans configBean)
	
}