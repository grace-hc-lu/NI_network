package network_code_v5.beans;


import java.io.*;
import java.util.*;

public class ConfigBeans
{
	
	private	String		workingDirectory	=	"";
	private	String		geneInfo					=	"";
	private	String		geneExpr					=	"";
	private	String		fpkmFile					=	"";
	
	private String		putativeRegulatoryRegionFolder		=	"";
	private String		putativeRegulatoryRegions					=	"";
	
	private	String		putativeRegulatoryRegionIndexFolder		=	"";
	private	String		galGal5ChrFastaFolder									=	"";	
	private	String[]	putativeRegulatoryRegionIndices				=	null;
	private	String[]	otherRegulatoryRegions								=	null;
	private	String[]	putativeRegulatoryRegionHour					=	null;
	private	String		putativeRegulatoryRegionOutputFolder	=	"";
	
	private	String[]	networkTimePoints	=	null;
	private	String		fimoOutputFolder	=	"";
	private	boolean		printExpr					=	false;
	
	private	String[]	geneExpressionTimePoints	=	null;
	
	private	String		networkFolder			=	"";
	private	String		ucscFolder				=	"";
	
	private	String		upRegColor				=	"";
	private	String		downRegColor			=	"";

	private	String		activationColor		=	"";
	private	String		repressionColor		=	"";
	private	String		poisedColor				=	"";
	private	String		noChangeColor			=	"";

	private	String		selectedGene								=	"";
	private	String[]	selectedRegulatoryIndices	=	null;

	
	public String getWorkingDirectory()
	{
		return workingDirectory;
	}
	
	public void setWorkingDirectory(String workingDirectory)
	{
		this.workingDirectory = workingDirectory;
	}
	
	public String getGeneInfo()
	{
		return geneInfo;
	}
	
	public void setGeneInfo(String geneInfo)
	{
		this.geneInfo = geneInfo;
	}
	
	public String getGeneExpr()
	{
		return geneExpr;
	}
	
	public void setGeneExpr(String geneExpr)
	{
		this.geneExpr = geneExpr;
	}

	public String getFpkmFile()
	{
		return fpkmFile;
	}
	
	public void setFpkmFile(String fpkmFile)
	{
		this.fpkmFile = fpkmFile;
	}
	
	public String getPutativeRegulatoryRegionFolder()
	{
		return putativeRegulatoryRegionFolder;
	}
	
	public void setPutativeRegulatoryRegionFolder(String putativeRegulatoryRegionFolder)
	{
		this.putativeRegulatoryRegionFolder = putativeRegulatoryRegionFolder;
	}

	public String getPutativeRegulatoryRegions()
	{
		return putativeRegulatoryRegions;
	}
	
	public void setPutativeRegulatoryRegions(String putativeRegulatoryRegions)
	{
		this.putativeRegulatoryRegions = putativeRegulatoryRegions;
	}

	public String getPutativeRegulatoryRegionIndexFolder()
	{
		return putativeRegulatoryRegionIndexFolder;
	}
	
	public void setPutativeRegulatoryRegionIndexFolder(String putativeRegulatoryRegionIndexFolder)
	{
		this.putativeRegulatoryRegionIndexFolder = putativeRegulatoryRegionIndexFolder;
	}

	public String getGalGal5ChrFastaFolder()
	{
		return galGal5ChrFastaFolder;
	}
	
	public void setGalGal5ChrFastaFolder(String galGal5ChrFastaFolder)
	{
		this.galGal5ChrFastaFolder = galGal5ChrFastaFolder;
	}
	
	public String[] getPutativeRegulatoryRegionIndices()
	{
		return putativeRegulatoryRegionIndices;
	}
	
	public void setPutativeRegulatoryRegionIndices(String[] putativeRegulatoryRegionIndices)
	{
		this.putativeRegulatoryRegionIndices = putativeRegulatoryRegionIndices;
	}
	
	public String[] getOtherRegulatoryRegions()
	{
		return otherRegulatoryRegions;
	}
	
	public void setOtherRegulatoryRegions(String[] otherRegulatoryRegions)
	{
		this.otherRegulatoryRegions = otherRegulatoryRegions;
	}
	
	public String[] getPutativeRegulatoryRegionHour()
	{
		return putativeRegulatoryRegionHour;
	}
	
	public void setPutativeRegulatoryRegionHour(String[] putativeRegulatoryRegionHour)
	{
		this.putativeRegulatoryRegionHour = putativeRegulatoryRegionHour;
	}
	
	public String getPutativeRegulatoryRegionOutputFolder()
	{
		return putativeRegulatoryRegionOutputFolder;
	}
	
	public void setPutativeRegulatoryRegionOutputFolder(String putativeRegulatoryRegionOutputFolder)
	{
		this.putativeRegulatoryRegionOutputFolder = putativeRegulatoryRegionOutputFolder;
	}
	
	public String[] getNetworkTimePoints()
	{
		return networkTimePoints;
	}
	
	public void setNetworkTimePoints(String[] networkTimePoints)
	{
		this.networkTimePoints = networkTimePoints;
	}

	public String getFimoOutputFolder()
	{
		return fimoOutputFolder;
	}
	
	public void setFimoOutputFolder(String fimoOutputFolder)
	{
		this.fimoOutputFolder = fimoOutputFolder;
	}

	public boolean getPrintExpr()
	{
		return printExpr;
	}
	
	public void setPrintExpr(boolean printExpr)
	{
		this.printExpr = printExpr;
	}

	public String[] getGeneExpressionTimePoints()
	{
		return geneExpressionTimePoints;
	}
	
	public void setGeneExpressionTimePoints(String[] geneExpressionTimePoints)
	{
		this.geneExpressionTimePoints = geneExpressionTimePoints;
	}

	public String getNetworkFolder()
	{
		return networkFolder;
	}
	
	public void setNetworkFolder(String networkFolder)
	{
		this.networkFolder = networkFolder;
	}

	public String getUcscFolder()
	{
		return ucscFolder;
	}
	
	public void setUcscFolder(String ucscFolder)
	{
		this.ucscFolder = ucscFolder;
	}
	
	public String getUpRegColor()
	{
		return upRegColor;
	}
	
	public void setUpRegColor(String upRegColor)
	{
		this.upRegColor = upRegColor;
	}
	
	public String getDownRegColor()
	{
		return downRegColor;
	}
	
	public void setDownRegColor(String downRegColor)
	{
		this.downRegColor = downRegColor;
	}
	
	public String getActivationColor()
	{
		return activationColor;
	}
	
	public void setActivationColor(String activationColor)
	{
		this.activationColor = activationColor;
	}
	
	public String getRepressionColor()
	{
		return repressionColor;
	}
	
	public void setRepressionColor(String repressionColor)
	{
		this.repressionColor = repressionColor;
	}
	
	public String getPoisedColor()
	{
		return poisedColor;
	}
	
	public void setPoisedColor(String poisedColor)
	{
		this.poisedColor = poisedColor;
	}
		
	public String getNoChangeColor()
	{
		return noChangeColor;
	}
	
	public void setNoChangeColor(String noChangeColor)
	{
		this.noChangeColor = noChangeColor;
	}

	public String getSelectedGene()
	{
		return selectedGene;
	}
	
	public void setSelectedGene(String selectedGene)
	{
		this.selectedGene = selectedGene;
	}
	
	public String[] getSelectedRegulatoryIndices()
	{
		return selectedRegulatoryIndices;
	}
	
	public void setSelectedRegulatoryIndices(String[] selectedRegulatoryIndices)
	{
		this.selectedRegulatoryIndices = selectedRegulatoryIndices;
	}


	/**
	  *
	*/
	public ConfigBeans(String configFile)
	{
		try
		{				
			
			InputStream				is	=	this.getClass().getResourceAsStream(configFile);
			InputStreamReader	isr	=	new InputStreamReader(is);
			BufferedReader		br	=	new BufferedReader(isr);	
			
			String		s			=	"";
			String[]	tmpS	=	null;
			
									
			while((s = br.readLine()) != null)
			{
				if(s.contains("=") && !s.startsWith("#"))
				{
					tmpS = s.split("=");
			
					if(tmpS[1].contains("/"))
					{
						tmpS[1] = tmpS[1].replace("/", File.separator);
					}
					else if(tmpS[1].contains("\\"))
					{
						tmpS[1] = tmpS[1].replace("\\", File.separator);
					}
				
				
					if(tmpS[0].equals("workingDirectory"))
					{
						this.setWorkingDirectory(tmpS[1]);
					}
					else if(tmpS[0].equals("geneInfo"))
					{
						this.setGeneInfo(tmpS[1]);
					}
					else if(tmpS[0].equals("geneExpr"))
					{
						this.setGeneExpr(tmpS[1]);
					}
					else if(tmpS[0].equals("fpkmFile"))
					{
						this.setFpkmFile(tmpS[1]);
					}
					else if(tmpS[0].equals("putativeRegulatoryRegionFolder"))
					{
						this.setPutativeRegulatoryRegionFolder(tmpS[1]);
					}
					else if(tmpS[0].equals("putativeRegulatoryRegions"))
					{
						this.setPutativeRegulatoryRegions(tmpS[1]);
					}
					else if(tmpS[0].equals("putativeRegulatoryRegionIndexFolder"))
					{
						this.setPutativeRegulatoryRegionIndexFolder(new StringBuffer(this.getWorkingDirectory()).append(tmpS[1]).toString());
					}
					else if(tmpS[0].equals("galGal5ChrFastaFolder"))
					{
						this.setGalGal5ChrFastaFolder(new StringBuffer(this.getWorkingDirectory()).append(tmpS[1]).toString());
					}
					else if(tmpS[0].equals("putativeRegulatoryRegionIndices"))
					{
						this.setPutativeRegulatoryRegionIndices(tmpS[1].split(","));
					}
					else if(tmpS[0].equals("otherRegulatoryRegions"))
					{
						this.setOtherRegulatoryRegions(tmpS[1].split(","));
					}
					else if(tmpS[0].equals("putativeRegulatoryRegionHour"))
					{
						this.setPutativeRegulatoryRegionHour(tmpS[1].split(","));
					}
					else if(tmpS[0].equals("putativeRegulatoryRegionOutputFolder"))
					{
						this.setPutativeRegulatoryRegionOutputFolder(new StringBuffer(this.getWorkingDirectory()).append(tmpS[1]).toString());
					}
					else if(tmpS[0].equals("networkTimePoints"))
					{
						this.setNetworkTimePoints(tmpS[1].split(","));
					}
					else if(tmpS[0].equals("fimoOutputFolder"))
					{
						this.setFimoOutputFolder(new StringBuffer(this.getWorkingDirectory()).append(tmpS[1]).toString());
					}
					else if(tmpS[0].equals("printExpr"))
					{
						this.setPrintExpr(tmpS[1].equalsIgnoreCase("true"));
					}
					else if(tmpS[0].equals("geneExpressionTimePoints"))
					{
						this.setGeneExpressionTimePoints(tmpS[1].split(","));
					}
					else if(tmpS[0].equals("networkFolder"))
					{
						this.setNetworkFolder(new StringBuffer(this.getWorkingDirectory()).append(tmpS[1]).toString());
					}
					else if(tmpS[0].equals("ucscFolder"))
					{
						this.setUcscFolder(new StringBuffer(this.getWorkingDirectory()).append(tmpS[1]).toString());
					}
					else if(tmpS[0].equals("upRegColor"))
					{
						this.setUpRegColor(tmpS[1]);
					}
					else if(tmpS[0].equals("downRegColor"))
					{
						this.setDownRegColor(tmpS[1]);
					}
					else if(tmpS[0].equals("activationColor"))
					{
						this.setActivationColor(tmpS[1]);
					}
					else if(tmpS[0].equals("repressionColor"))
					{
						this.setRepressionColor(tmpS[1]);
					}
					else if(tmpS[0].equals("poisedColor"))
					{
						this.setPoisedColor(tmpS[1]);
					}
					else if(tmpS[0].equals("noChangeColor"))
					{
						this.setNoChangeColor(tmpS[1]);
					}
					else if(tmpS[0].equals("selectedGene"))
					{
						this.setSelectedGene(tmpS[1]);
					}
					else if(tmpS[0].equals("selectedRegulatoryIndices"))
					{
						this.setSelectedRegulatoryIndices(tmpS[1].split(","));
					}
				}
			}
			
			br.close();
			isr.close();
			is.close();
		
		}catch(FileNotFoundException exp)
		{
			System.out.println("FileNotFoundException : "+exp);			
		}catch(IOException exp)
		{
			System.out.println("IOException : "+exp);
		}	
	}


}