package network_code_v5.proc;

import network_code_v5.func.*;
import network_code_v5.beans.*;

import java.io.*;
import java.util.*;

public class GenerateBioTapestryNetworkProc
{

	public GenerateBioTapestryNetworkProc(){}

	/*
	 *
	 */
	public void integrateInteractionFiles(ConfigBeans configBean)
	{	
		NetworkInteractions	handler	=	new NetworkInteractions();
		
		handler.integrateInteractionFiles(configBean.getNetworkFolder(), configBean.getPutativeRegulatoryRegionIndices());
	}

	/*
	 * 
	 */
	public void printBiotapstryTimeExpressionFile(ConfigBeans configBean)
	{	
		NetworkInteractions	handler	=	new NetworkInteractions();
		
		handler.printBiotapstryTimeExpressionFile(configBean.getWorkingDirectory(), configBean.getGeneExpr(), configBean.getNetworkFolder(), "NI_network_TimeExpression.xml");
	}

	/* 
	 *subtract a network of a gene by regulatory sites 
	 *
	 */
	public void subtractNetworkOfGene(ConfigBeans configBean)
	{
		NetworkInteractions	handler	=	new NetworkInteractions();
		
		handler.subtractNetworkOfGene(configBean.getNetworkFolder(), configBean.getSelectedGene(), configBean.getSelectedRegulatoryIndices());
	}

	/* 
	 * replace names for overlapped regulatory sites 
	 * remove duplicated regulatory interactions
	 *
	 */ 
	public void removeDuplicatedInputOfGeneNetwork(ConfigBeans configBean)
	{
		NetworkInteractions	handler	=	new NetworkInteractions();

		handler.removeDuplicatedInputOfGeneNetwork(configBean.getNetworkFolder(), configBean.getSelectedGene());
	}
}