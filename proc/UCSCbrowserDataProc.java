package network_code_v5.proc;

import network_code_v5.func.*;
import network_code_v5.beans.*;

import java.io.*;
import java.util.*;

public class UCSCbrowserDataProc
{

	public UCSCbrowserDataProc(){}

	//
	public void printUCSCGeneExpressionBedFiles(ConfigBeans configBean)
	{
		UCSCbrowserData handler = new UCSCbrowserData();
		
		handler.printGeneExpressionsForUCSC(configBean.getWorkingDirectory(), configBean.getFpkmFile(), configBean.getUpRegColor(), configBean.getDownRegColor(), configBean.getUcscFolder());
	}
	
	// 
	public void genPutativeRegulatoryRegion(ConfigBeans configBean)
	{	
		UCSCbrowserData	handler	=	new UCSCbrowserData();
		
		handler.genPutativeRegulatoryRegionBedFiles(configBean.getPutativeRegulatoryRegionFolder(), 
																								configBean.getUcscFolder(), 
																								configBean.getActivationColor(), 
																								configBean.getRepressionColor(),
																								configBean.getPoisedColor(),
																								configBean.getNoChangeColor());
	}
	
	// integrate files generated from NetworkInteractions.genNetworkFromFimoOutput()
	public void printUCSCTFBindingBedFiles(ConfigBeans configBean)
	{
		UCSCbrowserData handler = new UCSCbrowserData();
		
		handler.printUCSCTFBindingBedFiles(configBean.getNetworkFolder(), configBean.getUcscFolder());
	}
	
	
}