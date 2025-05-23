._CengPaper.java                                                                                    000644  000765  000024  00000000333 14733011665 014267  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/CengPaper.java                                                                            000644  000765  000024  00000000411 14733011665 016020  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1735136181.963782817
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       CengPaper.java                                                                                      000644  000765  000024  00000001300 14733011665 014045  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         
public class CengPaper {
	private Integer paperId;
	private String paperName;
	private String author;
	private String journal;
	
	public CengPaper(Integer paperId, String paperName, String author, String journal) {
		this.paperId = paperId;
		this.paperName = paperName;
		this.author = author;
		this.journal = journal;
	}
	
	// Getters
	public Integer paperId()
	{
		return paperId;
	}
	public String paperName()
	{
		return paperName;
	}
	public String author()
	{
		return author;
	}
	public String journal() {return journal;}
	
	// GUI method - do not modify
	public String fullName()
	{
		return "" + paperId() + "|" + journal() + "|" + paperName() + "|" + author();
	}
	
	// DO NOT ADD SETTERS
}
                                                                                                                                                                                                                                                                                                                                ._CengScholar.java                                                                                  000644  000765  000024  00000000333 14734072574 014622  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/CengScholar.java                                                                          000644  000765  000024  00000000410 14734072574 016352  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         29 mtime=1735423356.89558002
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                        CengScholar.java                                                                                    000644  000765  000024  00000005402 14734072574 014407  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.EventQueue;

public class CengScholar {
	
	private static Integer order;
	private static String fileName;
	private static Integer guiOptions;
	private static Boolean guiEnabled;
	private static ScholarTree scholarTree;
	private static CengGUI window;
	private static Boolean wrapNodes = true;
	private static Boolean packFrame = false;
	
	public static void main(String[] args) throws Exception {
		if(args.length <2) {
			throw new Exception("Usage : java CengScholar -order- -guiOptions- (-guiFileName-) ");
		}		
		
		order = Integer.parseInt(args[0]);
		guiOptions = Integer.parseInt(args[1]);
		
		if(args.length == 2 && guiOptions!=0) {
			throw new Exception("In order to use GUI, provide an input file");			
		}
		
		if(guiOptions>0 && guiOptions<4) {
			guiEnabled=true;
			fileName = args[2];
		}
		else if (guiOptions==0) guiEnabled=false;
		else {
			throw new Exception("Invalid GUI Options Value");			
		}		
		
		scholarTree = new ScholarTree(order);
				
		Integer orderN = 2 * order + 1; // N-based order, for GUI purposes only.

		CengGUI.orderN = orderN;
		
		if(guiEnabled) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						window = new CengGUI(guiOptions);
						window.show();
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		ScholarParser.startParsingCommandLine();
	}
	
	public static void addPaper (CengPaper paper) {
		scholarTree.addPaper(paper);
		
		if(guiEnabled) {
			if(window == null) {
				System.out.println("Err: Window is not initialized yet.");
				return;
			}
			window.modelNeedsUpdate(guiOptions, scholarTree.primaryRoot, scholarTree.secondaryRoot);
		}
	}

	public static void searchPaper(Integer key) {
		scholarTree.searchPaper(key);
	}

	public static void searchJournal(String key) {
		scholarTree.searchJournal(key);
	}
	
	public static void printPrimary() {
		scholarTree.printPrimaryScholar();
		
		if(guiEnabled) {
			window.modelNeedsUpdate(guiOptions, scholarTree.primaryRoot, scholarTree.secondaryRoot);
		}
	}
	
	public static void printSecondary() {
		scholarTree.printSecondaryScholar();
		
		if(guiEnabled) {
			window.modelNeedsUpdate(guiOptions, scholarTree.primaryRoot, scholarTree.secondaryRoot);
		}
	}
	
	public static void setPrimaryRoot(ScholarNode newRoot) {
		scholarTree.primaryRoot=newRoot;
	}
	
	public static void setSecondaryRoot(ScholarNode newRoot) {
		scholarTree.secondaryRoot=newRoot;
	}
	
	public static String getFilenameToParse()
	{
		return CengScholar.fileName;
	}
	
	public static Boolean shouldWrap()
	{
		return CengScholar.wrapNodes;
	}
	
	public static Boolean shouldPack()
	{
		return CengScholar.packFrame;
	}
}

	

                                                                                                                                                                                                                                                              ._GUIInternalPrimaryNode.java                                                                       000644  000765  000024  00000000333 14732045721 016715  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/GUIInternalPrimaryNode.java                                                               000644  000765  000024  00000000411 14732045721 020446  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734888401.902451221
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       GUIInternalPrimaryNode.java                                                                         000644  000765  000024  00000002243 14732045721 016502  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUIInternalPrimaryNode extends GUITreeNode {
	public GUIInternalPrimaryNode(ScholarNode node) {
		super(node);
		
		this.setBackground(Color.lightGray);

		ScholarNodePrimaryIndex castNode = (ScholarNodePrimaryIndex)node;
		
		for(int i = 0; i < castNode.paperIdCount(); i++) {
			if(i < this.labels.size()) {
	 			Integer paperId = castNode.paperIdAtIndex(i);
	 			
				String keyString = "" + paperId;
				
				JLabel correspondingLabel = this.labels.get(i);
				
				correspondingLabel.setText(keyString);
				
				correspondingLabel.repaint();
			}
			else {
				System.out.println("paperId count is greater than label count.");
			}
		}
		
		ArrayList<ScholarNode> allChildren = castNode.getAllChildren();
		
		if(allChildren.size() > this.paddings.size()) {
			System.out.println("Children count is greater than padding count.");
		}
		
		for(int i = 0; i < this.paddings.size(); i++) {
			if(i < allChildren.size()) {
				ScholarNode child = allChildren.get(i);
				
				this.paddings.get(i).setBackground(Color.BLUE);				
			}
		}
		
		this.repaint();
	}
}
                                                                                                                                                                                                                                                                                                                                                             ._GUIInternalSecondaryNode.java                                                                     000644  000765  000024  00000000333 14732045523 017221  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/GUIInternalSecondaryNode.java                                                             000644  000765  000024  00000000411 14732045523 020752  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734888275.655684401
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       GUIInternalSecondaryNode.java                                                                       000644  000765  000024  00000002171 14732045523 017006  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUIInternalSecondaryNode extends GUITreeNode {
	public GUIInternalSecondaryNode(ScholarNode node) {
		super(node);
		
		this.setBackground(Color.white);

		ScholarNodeSecondaryIndex castNode = (ScholarNodeSecondaryIndex)node;
		
		for(int i = 0; i < castNode.journalCount(); i++) {
			if(i < this.labels.size()) {
	 			String journal = castNode.journalAtIndex(i);
				
				JLabel correspondingLabel = this.labels.get(i);
				
				correspondingLabel.setText(journal);
				
				correspondingLabel.repaint();
			}
			else {
				System.out.println("Journal count is greater than label count.");
			}
		}
		
		ArrayList<ScholarNode> allChildren = castNode.getAllChildren();
		
		if(allChildren.size() > this.paddings.size()) {
			System.out.println("Children count is greater than padding count.");
		}
		
		for(int i = 0; i < this.paddings.size(); i++) {
			if(i < allChildren.size()) {
				ScholarNode child = allChildren.get(i);
				
				this.paddings.get(i).setBackground(Color.BLUE);				
			}
		}
		
		this.repaint();
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                       ._GUILevel.java                                                                                     000644  000765  000024  00000000333 14731114126 014031  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/GUILevel.java                                                                             000644  000765  000024  00000000411 14731114126 015562  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734645846.376450573
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       GUILevel.java                                                                                       000644  000765  000024  00000003457 14731114126 013626  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GUILevel extends JPanel {
	private ArrayList<GUITreeNode> allNodes;
	
	public GUILevel() {
		this.allNodes = new ArrayList<GUITreeNode>();
		
		if(CengScholar.shouldWrap()) {
			this.setLayout(new WrapLayout(FlowLayout.CENTER, 10, 10));
		}
		else {
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		}
		
		this.setOpaque(false);
	}
	
	public void addNode(ScholarNode node) {
		GUITreeNode newPanel = null;
		
		if(node.getType() == ScholarNodeType.Internal) {
			newPanel = new GUIInternalPrimaryNode(node);
		}
		else {
			newPanel = new GUIPrimaryLeafNode(node);
		}
		
		this.add(newPanel);
		this.allNodes.add(newPanel);
	}
	
	public void addNode2(ScholarNode node) {
		GUITreeNode newPanel = null;

		if(node.getType() == ScholarNodeType.Internal) {
			newPanel = new GUIInternalSecondaryNode(node);
		}
		else {
			newPanel = new GUISecondaryLeafNode(node);
		}
		
		this.add(newPanel);
		this.allNodes.add(newPanel);
	}

	public void paintSearchedNodes(ArrayList<ScholarNode> visitedNodes) {
		// Normally every level should have only one selected node while searching.
		
		for(ScholarNode node : visitedNodes) {
			GUITreeNode guiNode = panelForNode(node);
			
			if(guiNode != null) {
				TitledBorder visitedBorder = BorderFactory.createTitledBorder("Visited");
				visitedBorder.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, node.getColor()));
				
				guiNode.setBorder(visitedBorder);
			}	
		}
	}
	
	private GUITreeNode panelForNode(ScholarNode nodeToSearch) {
		for(GUITreeNode guiNode : this.allNodes) {
			if(guiNode.node.equals(nodeToSearch)) {
				return guiNode;
			}
		}
		
		return null;
	}
}
                                                                                                                                                                                                                 ._GUIPrimaryLeafNode.java                                                                           000644  000765  000024  00000000333 14732045721 016010  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/GUIPrimaryLeafNode.java                                                                   000644  000765  000024  00000000411 14732045721 017541  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734888401.908069967
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       GUIPrimaryLeafNode.java                                                                             000644  000765  000024  00000001315 14732045721 015574  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUIPrimaryLeafNode extends GUITreeNode {
	public GUIPrimaryLeafNode(ScholarNode node) {
		super(node);

		this.setBackground(Color.green);

		ScholarNodePrimaryLeaf castNode = (ScholarNodePrimaryLeaf)node;
		
		for(int i = 0; i < castNode.paperCount(); i++) {
			if(i >= this.labels.size()) {
				System.out.println("Paper count is greater than label count.");
				
				return;
			}
			
 			Integer paperId = castNode.paperIdAtIndex(i);
			 			
			String keyString = "" + paperId;
			
			JLabel correspondingLabel = this.labels.get(i);
			
			correspondingLabel.setText(keyString);
			correspondingLabel.repaint();
		}
	}
}
                                                                                                                                                                                                                                                                                                                   ._GUISecondaryLeafNode.java                                                                         000644  000765  000024  00000000333 14732045523 016314  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/GUISecondaryLeafNode.java                                                                 000644  000765  000024  00000000411 14732045523 020045  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734888275.650829822
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       GUISecondaryLeafNode.java                                                                           000644  000765  000024  00000001250 14732045523 016076  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUISecondaryLeafNode extends GUITreeNode {
	public GUISecondaryLeafNode(ScholarNode node) {
		super(node);

		this.setBackground(Color.pink);

		ScholarNodeSecondaryLeaf castNode = (ScholarNodeSecondaryLeaf)node;
		
		for(int i = 0; i < castNode.journalCount(); i++) {
			if(i >= this.labels.size()) {
				System.out.println("Journal count is greater than label count.");
				return;
			}
			
 			String journal = castNode.journalAtIndex(i);
			
			JLabel correspondingLabel = this.labels.get(i);
			
			correspondingLabel.setText(journal);
			correspondingLabel.repaint();
		}
	}
	
	
}
                                                                                                                                                                                                                                                                                                                                                        ._GUITreeNode.java                                                                                  000644  000765  000024  00000000333 14731114063 014467  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/GUITreeNode.java                                                                          000644  000765  000024  00000000411 14731114063 016220  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734645811.121377223
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       GUITreeNode.java                                                                                    000644  000765  000024  00000003021 14731114063 014247  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class GUITreeNode extends JPanel {
	public ScholarNode node; // To check and paint while searching
	
	protected ArrayList<JLabel> labels;
	protected ArrayList<JPanel> paddings;
	
	private static final int maxYValue = 40;
	
	public GUITreeNode(ScholarNode node) {
		this.node = node;
		this.labels = new ArrayList<JLabel>();
		this.paddings = new ArrayList<JPanel>();
		
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, node.getColor()));

		for(int i = 0; i < CengGUI.orderN - 1; i++) {
			
			JLabel keyLabel = new JLabel("");
			keyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			labels.add(keyLabel);
			
			this.add(this.getNewPadding());
			this.add(Box.createHorizontalStrut(5));
			this.add(keyLabel);
			this.add(Box.createHorizontalStrut(5));
		}
		
		this.add(this.getNewPadding());
						
		this.revalidate();
		this.repaint();
	}

	private JPanel getNewPadding() {
		JPanel padding = new JPanel();
		padding.setAlignmentX(Component.CENTER_ALIGNMENT);
		padding.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		padding.setMaximumSize(new Dimension(5, 1000));
		padding.setPreferredSize(new Dimension(5, maxYValue));
		
		this.paddings.add(padding);
		
		return padding;
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ._ScholarNode.java                                                                                  000644  000765  000024  00000000333 14731360524 014623  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/ScholarNode.java                                                                          000644  000765  000024  00000000411 14731360524 016354  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734730068.288400599
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       ScholarNode.java                                                                                    000644  000765  000024  00000001445 14731360524 014413  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.Color;
public class ScholarNode {
	static protected Integer order;
	private ScholarNode parent;
	protected ScholarNodeType type; // Type needs to be set for proper GUI. Check ScholarNodeType.java.

	// GUI Accessors - do not modify
	public Integer level;
	public Color color;
	
	public ScholarNode(ScholarNode parent) {
		this.parent = parent;
				
		if (parent != null)
		this.level = parent.level + 1;
		else
			this.level = 0;
	}
	
	// Getters-Setters - Do not modify
	public ScholarNode getParent()
	{
		return parent;
	}
	
	public void setParent(ScholarNode parent)
	{
		this.parent = parent;
	}
	
	public ScholarNodeType getType()
	{
		return type;
	}
	
	// GUI Methods - Do not modify
	public Color getColor()
	{
		return this.color;
	}

}
                                                                                                                                                                                                                           ._ScholarNodePrimaryIndex.java                                                                      000644  000765  000024  00000000423 14733214224 017154  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   �                                           ATTR         �   3                  �     com.apple.lastuseddate#PS       �     com.apple.provenance    �     com.apple.quarantine ｪxpg    �NE     ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                              PaxHeader/ScholarNodePrimaryIndex.java                                                              000644  000765  000024  00000000611 14733214224 020707  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1735202964.065742071
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
69 LIBARCHIVE.xattr.com.apple.lastuseddate#PS=qnhwZwAAAADuTkUNAAAAAA
59 SCHILY.xattr.com.apple.lastuseddate#PS=ｪxpg    �NE    
                                                                                                                       ScholarNodePrimaryIndex.java                                                                        000644  000765  000024  00000002061 14733214224 016737  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.util.ArrayList;

public class ScholarNodePrimaryIndex extends ScholarNode {
	private ArrayList<Integer> paperIds;
	private ArrayList<ScholarNode> children;
	
	public ScholarNodePrimaryIndex(ScholarNode parent) {
		super(parent);
		paperIds = new ArrayList<Integer>();
		children = new ArrayList<ScholarNode>();
		this.type = ScholarNodeType.Internal;
	}
	
	public ScholarNodePrimaryIndex(ScholarNode parent, ArrayList<Integer> paperIds, ArrayList<ScholarNode> children) {
		super(parent);
		this.paperIds = paperIds;
		this.children = children;
		this.type = ScholarNodeType.Internal;
	}
	
	// GUI Methods - Do not modify
	public ArrayList<ScholarNode> getAllChildren()
	{
		return this.children;
	}
	
	public ScholarNode getChildrenAt(Integer index) {return this.children.get(index); }
	
	public Integer paperIdCount()
	{
		return this.paperIds.size();
	}

	public Integer paperIdAtIndex(Integer index) {
		if(index >= this.paperIdCount() || index < 0) {
			return -1;
		}
		else {
			return this.paperIds.get(index);
		}
	}
	
	// Extra functions if needed

}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ._ScholarNodePrimaryLeaf.java                                                                       000644  000765  000024  00000000333 14733011477 016761  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/ScholarNodePrimaryLeaf.java                                                               000644  000765  000024  00000000410 14733011477 020511  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         29 mtime=1735136063.37708232
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                        ScholarNodePrimaryLeaf.java                                                                         000644  000765  000024  00000002361 14733011477 016547  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.util.ArrayList;

public class ScholarNodePrimaryLeaf extends ScholarNode {
	private ArrayList<CengPaper> papers;  // keep the papers in the leaf node
	
	public ScholarNodePrimaryLeaf(ScholarNode parent) {
		super(parent);
		papers = new ArrayList<CengPaper>();
		this.type = ScholarNodeType.Leaf;
	}
	
	public ScholarNodePrimaryLeaf(ScholarNode parent, ArrayList<CengPaper> papers ) {
		super(parent);
		this.papers = papers;
		this.type = ScholarNodeType.Leaf;
	}
	
	public void addPaper(int index, CengPaper paper) {
		papers.add(index, paper);
	}
	
	
	// GUI Methods - Do not modify
	public int paperCount()
	{
		return papers.size();
	}

	public Integer paperIdAtIndex(Integer index) {
		if(index >= this.paperCount()) {
			return -1;
		}
		else {
			CengPaper paper = this.papers.get(index);
			return paper.paperId();
		}
	}
	
	public String paperJournalAtIndex(Integer index) {
		if(index >= this.paperCount()) {
			return null;
		}
		else {
			CengPaper paper = this.papers.get(index);
			
			return paper.journal();
		}
	}
	
	public CengPaper paperAtIndex(Integer index) {
		if(index >= this.paperCount()) {
			return null;
		}
		else {
			return this.papers.get(index);
		}
	}
	
	public ArrayList<CengPaper> getPapers(){
		return papers;
	}
}
                                                                                                                                                                                                                                                                               ._ScholarNodeSecondaryIndex.java                                                                    000644  000765  000024  00000000423 14734073706 017471  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   �                                           ATTR         �   3                  �     com.apple.lastuseddate#PS       �     com.apple.provenance    �     com.apple.quarantine ｱxpg    T�	     ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                              PaxHeader/ScholarNodeSecondaryIndex.java                                                            000644  000765  000024  00000000611 14734073706 021224  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1735423942.667892538
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
69 LIBARCHIVE.xattr.com.apple.lastuseddate#PS=sXhwZwAAAABU+QkUAAAAAA
59 SCHILY.xattr.com.apple.lastuseddate#PS=ｱxpg    T�	    
                                                                                                                       ScholarNodeSecondaryIndex.java                                                                      000644  000765  000024  00000002111 14734073706 017250  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.util.ArrayList;

public class ScholarNodeSecondaryIndex extends ScholarNode {
	private ArrayList<String> journals;
	private ArrayList<ScholarNode> children;

	public ScholarNodeSecondaryIndex(ScholarNode parent) {
		super(parent);
		journals = new ArrayList<String>();
		children = new ArrayList<ScholarNode>();
		this.type = ScholarNodeType.Internal;
	}
	
	public ScholarNodeSecondaryIndex(ScholarNode parent, ArrayList<String> journals, ArrayList<ScholarNode> children) {
		super(parent);
		this.journals = journals;
		this.children = children;
		this.type = ScholarNodeType.Internal;
	}
	
	// GUI Methods - Do not modify
	public ArrayList<ScholarNode> getAllChildren()
	{
		return this.children;
	}
	
	public ScholarNode getChildrenAt(Integer index) {

		return this.children.get(index);
	}

	public Integer journalCount()
	{
		return this.journals.size();
	}
	
	public String journalAtIndex(Integer index) {
		if(index >= this.journalCount() || index < 0) {
			return "Not Valid Index!!!";
		}
		else {
			return this.journals.get(index);
		}
	}
	
	// Extra functions if needed

}
                                                                                                                                                                                                                                                                                                                                                                                                                                                       ._ScholarNodeSecondaryLeaf.java                                                                     000644  000765  000024  00000000333 14733011475 017263  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/ScholarNodeSecondaryLeaf.java                                                             000644  000765  000024  00000000411 14733011475 021014  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1735136061.351517122
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       ScholarNodeSecondaryLeaf.java                                                                       000644  000765  000024  00000004153 14733011475 017052  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.util.ArrayList;

public class ScholarNodeSecondaryLeaf extends ScholarNode {
    private ArrayList<String> journals;  // keeps journals in the leaf node
    private ArrayList<ArrayList<Integer>> paperIdBucket;  // keeps papers of the journals in the leaf node

    public ScholarNodeSecondaryLeaf(ScholarNode parent) {
        super(parent);
        journals = new ArrayList<String>();
        paperIdBucket = new ArrayList<ArrayList<Integer>>();
        this.type = ScholarNodeType.Leaf;
    }

    public ScholarNodeSecondaryLeaf(ScholarNode parent, ArrayList<String> journals, 
                                    ArrayList<ArrayList<Integer>> paperIdBucket) {
        super(parent);
        this.journals = journals;
        this.paperIdBucket = paperIdBucket;
        this.type = ScholarNodeType.Leaf;
    }

    public void addPaper(int index, CengPaper paper) {
        if(paperIdBucket.size() <= index) {
            paperIdBucket.add(new ArrayList<>());
            journals.add(paper.journal());
        }
        else if(!paper.journal().equalsIgnoreCase(this.journalAtIndex(index))){
            paperIdBucket.add(index, new ArrayList<>());
            journals.add(index, paper.journal());
        }
        paperIdBucket.get(index).add(paper.paperId());
    }


    // GUI Methods - Do not modify
    public int journalCount() {
        return journals.size();
    }

    public String journalAtIndex(Integer index) {
        if(index >= this.journalCount()) {
            return null;
        }
        else {
            return this.journals.get(index);
        }
    }

    public ArrayList<Integer> papersAtIndex(Integer index) {
        if(index >= this.journalCount()) {
            return null;
        }
        else if (paperIdBucket.get(index).size() == 0) {
            return null;
        }
        else {
            return this.paperIdBucket.get(index);
        }
    }

    public ArrayList<ArrayList<Integer>> getPaperIdBucket(){
        return paperIdBucket;
    }

    public ArrayList<String> getJournals(){
        return journals;
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                     ._ScholarNodeType.java                                                                              000644  000765  000024  00000000333 14731114063 015460  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/ScholarNodeType.java                                                                      000644  000765  000024  00000000411 14731114063 017211  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734645811.120491081
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       ScholarNodeType.java                                                                                000644  000765  000024  00000000062 14731114063 015242  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         public enum ScholarNodeType {
	Internal,
	Leaf,
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              ._ScholarParser.java                                                                                000644  000765  000024  00000000333 14734072514 015174  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ｩ      ﾛ                                      ATTR       ﾛ   ｸ   #                  ｸ     com.apple.provenance    ﾃ     com.apple.quarantine  ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                      PaxHeader/ScholarParser.java                                                                        000644  000765  000024  00000000411 14734072514 016725  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1735423308.221069887
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
                                                                                                                                                                                                                                                       ScholarParser.java                                                                                  000644  000765  000024  00000005146 14734072514 014766  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ScholarParser {
	// Parsing the input file in order to use GUI tables.
	public static ArrayList<CengPaper> parsePapersFromFile(String filename) {
		ArrayList<CengPaper> paperList = new ArrayList<CengPaper>();
		Scanner s = null;;
		try {
			s = new Scanner(new File(filename), "UTF-8");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		while ( s.hasNextLine()) {
			String myLine = s.nextLine();
			//System.out.println(myLine);

			String[] array = myLine.split("[|]");
			Integer key = Integer.parseInt(array[1]);
			String journal = array[2];
			String name = array[3];
			String author = array[4];

			paperList.add(new CengPaper(key,name,author,journal));
		}
		s.close();
		return paperList;
	}
	
	public static void startParsingCommandLine() throws IOException {
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean running = true;
		String inpLine = null;
		while(running){
			inpLine = reader.readLine();
			String[] array = inpLine.split("[|]");
			String command = array[0];
			if(command.equalsIgnoreCase("add")){
				Integer key = Integer.parseInt(array[1]);
				String journal = array[2];
				String name = array[3];
				String author = array[4];
				
				CengScholar.addPaper(new CengPaper(key,name,author,journal));
			}
			else if(command.equalsIgnoreCase("quit")){
				running = false;
			}
			else if(command.equalsIgnoreCase("search1")){
				int key = Integer.parseInt(array[1]);
				CengScholar.searchPaper(key);
			}
			else if(command.equalsIgnoreCase("search2")){
				String key = array[1];
				CengScholar.searchJournal(key);
			}
			else if(command.equalsIgnoreCase("print1")){
				CengScholar.printPrimary();
			}
			else if(command.equalsIgnoreCase("print2")){
				CengScholar.printSecondary();
			}
		}
		
		// 
		// There are 5 commands:
		// 1) quit : End the application. Print nothing, call nothing, just terminate.
		// 2) add : Parse and create the paper, calls CengScholar.addPaper(newPaper)
		// 3) search1 : Parse the key in primary tree, calls CengScholar.searchPaper(key)
		// 4) search2 : Parse the key in secondary tree, calls CengScholar.searchJournal(key)
		// 5) print1 : Print the whole primary tree, calls CengScholar.printPrimary()
		// 6) print2 : Print the whole secondary tree, calls CengScholar.printSecondary()

		// Commands (quit, add, search1, search2, print1, print2) are case-insensitive.
	}
}                                                                                                                                                                                                                                                                                                                                                                                                                          ._ScholarTree.java                                                                                  000644  000765  000024  00000000423 14734072757 014650  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   �                                           ATTR         �   3                  �     com.apple.lastuseddate#PS       �     com.apple.provenance    �     com.apple.quarantine ｸxpg    2s,     ｼ4{tかﾁq/0081;676427b6;Chrome;                                                                                                                                                                                                                                              PaxHeader/ScholarTree.java                                                                          000644  000765  000024  00000000611 14734072757 016403  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1735423471.692701233
70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
69 LIBARCHIVE.xattr.com.apple.lastuseddate#PS=uHhwZwAAAAAaMnMsAAAAAA
59 SCHILY.xattr.com.apple.lastuseddate#PS=ｸxpg    2s,    
                                                                                                                       ScholarTree.java                                                                                    000644  000765  000024  00000002513 14734072757 014435  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.util.ArrayList;
import java.util.Objects;

public class ScholarTree {
	
	public ScholarNode primaryRoot;		//root of the primary B+ tree
	public ScholarNode secondaryRoot;	//root of the secondary B+ tree
	public ScholarTree(Integer order) {
		ScholarNode.order = order;
		primaryRoot = new ScholarNodePrimaryLeaf(null);
		primaryRoot.level = 0;
		secondaryRoot = new ScholarNodeSecondaryLeaf(null);
		secondaryRoot.level = 0;
	}

	public void addPaper(CengPaper paper) {
		// TODO: Implement this method
		// add methods to fill both primary and secondary tree
		return;
	}

	public CengPaper searchPaper(Integer paperId) {
		// TODO: Implement this method
		// find the paper with the searched paperId in primary B+ tree
		// return value will not be tested, just print according to the specifications
		return null;
	}

	public void searchJournal(String journal) {
		// TODO: Implement this method
		// find the journal with the searched journal in secondary B+ tree
		return;
	}

	public void printPrimaryScholar() {
		// TODO: Implement this method
		// print the primary B+ tree in Depth-first order
		return;
	}

	public void printSecondaryScholar() {
		// TODO: Implement this method
		// print the secondary B+ tree in Depth-first order
		return;
	}
	
	// Extra functions if needed
}


                                                                                                                                                                                     ._WrapLayout.java                                                                                   000644  000765  000024  00000000260 14350613732 014527  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2   ~      ｰ                                      ATTR       ｰ   �                     �     com.apple.quarantine q/0081;676427b6;Chrome;                                                                                                                                                                                                                                                                                                                                                 PaxHeader/WrapLayout.java                                                                           000644  000765  000024  00000000201 14350613732 016256  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         70 LIBARCHIVE.xattr.com.apple.quarantine=MDA4MTs2NzY0MjdiNjtDaHJvbWU7
59 SCHILY.xattr.com.apple.quarantine=0081;676427b6;Chrome;
                                                                                                                                                                                                                                                                                                                                                                                               WrapLayout.java                                                                                     000644  000765  000024  00000012073 14350613732 014317  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

// "borrowed" from https://tips4java.wordpress.com/2008/11/06/wrap-layout/

/**
 *  FlowLayout subclass that fully supports wrapping of components.
 */
public class WrapLayout extends FlowLayout {
	private Dimension preferredLayoutSize;

	/**
	* Constructs a new <code>WrapLayout</code> with a left
	* alignment and a default 5-unit horizontal and vertical gap.
	*/
	public WrapLayout()
	{
		super();
	}

	/**
	* Constructs a new <code>FlowLayout</code> with the specified
	* alignment and a default 5-unit horizontal and vertical gap.
	* The value of the alignment argument must be one of
	* <code>WrapLayout</code>, <code>WrapLayout</code>,
	* or <code>WrapLayout</code>.
	* @param align the alignment value
	*/
	public WrapLayout(int align)
	{
		super(align);
	}

	/**
	* Creates a new flow layout manager with the indicated alignment
	* and the indicated horizontal and vertical gaps.
	* <p>
	* The value of the alignment argument must be one of
	* <code>WrapLayout</code>, <code>WrapLayout</code>,
	* or <code>WrapLayout</code>.
	* @param align the alignment value
	* @param hgap the horizontal gap between components
	* @param vgap the vertical gap between components
	*/
	public WrapLayout(int align, int hgap, int vgap)
	{
		super(align, hgap, vgap);
	}

	/**
	* Returns the preferred dimensions for this layout given the
	* <i>visible</i> components in the specified target container.
	* @param target the component which needs to be laid out
	* @return the preferred dimensions to lay out the
	* subcomponents of the specified container
	*/
	@Override
	public Dimension preferredLayoutSize(Container target)
	{
		return layoutSize(target, true);
	}

	/**
	* Returns the minimum dimensions needed to layout the <i>visible</i>
	* components contained in the specified target container.
	* @param target the component which needs to be laid out
	* @return the minimum dimensions to lay out the
	* subcomponents of the specified container
	*/
	@Override
	public Dimension minimumLayoutSize(Container target) {
		Dimension minimum = layoutSize(target, false);
		minimum.width -= (getHgap() + 1);
		return minimum;
	}

	/**
	* Returns the minimum or preferred dimension needed to layout the target
	* container.
	*
	* @param target target to get layout size for
	* @param preferred should preferred size be calculated
	* @return the dimension to layout the target container
	*/
	private Dimension layoutSize(Container target, boolean preferred) {
		synchronized (target.getTreeLock()) {
			//  Each row must fit with the width allocated to the containter.
			//  When the container width = 0, the preferred width of the container
			//  has not yet been calculated so lets ask for the maximum.

			int targetWidth = target.getSize().width;
			Container container = target;

			while (container.getSize().width == 0 && container.getParent() != null) {
				container = container.getParent();
			}

			targetWidth = container.getSize().width;

			if (targetWidth == 0)
				targetWidth = Integer.MAX_VALUE;

			int hgap = getHgap();
			int vgap = getVgap();
			Insets insets = target.getInsets();
			int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
			int maxWidth = targetWidth - horizontalInsetsAndGap;

			//  Fit components into the allowed width

			Dimension dim = new Dimension(0, 0);
			int rowWidth = 0;
			int rowHeight = 0;

			int nmembers = target.getComponentCount();

			for (int i = 0; i < nmembers; i++) {
				Component m = target.getComponent(i);

				if (m.isVisible()) {
					Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

					//  Can't add the component to current row. Start a new row.

					if (rowWidth + d.width > maxWidth) {
						addRow(dim, rowWidth, rowHeight);
						rowWidth = 0;
						rowHeight = 0;
					}

					//  Add a horizontal gap for all components after the first

					if (rowWidth != 0) {
						rowWidth += hgap;
					}

					rowWidth += d.width;
					rowHeight = Math.max(rowHeight, d.height);
				}
			}

			addRow(dim, rowWidth, rowHeight);

			dim.width += horizontalInsetsAndGap;
			dim.height += insets.top + insets.bottom + vgap * 2;

			//	When using a scroll pane or the DecoratedLookAndFeel we need to
			//  make sure the preferred size is less than the size of the
			//  target containter so shrinking the container size works
			//  correctly. Removing the horizontal gap is an easy way to do this.

			Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);

			if (scrollPane != null && target.isValid()) {
				dim.width -= (hgap + 1);
			}

			return dim;
		}
	}

	/*
	 *  A new row has been completed. Use the dimensions of this row
	 *  to update the preferred size for the container.
	 *
	 *  @param dim update the width and height when appropriate
	 *  @param rowWidth the width of the row to add
	 *  @param rowHeight the height of the row to add
	 */
	private void addRow(Dimension dim, int rowWidth, int rowHeight) {
		dim.width = Math.max(dim.width, rowWidth);

		if (dim.height > 0) {
			dim.height += getVgap();
		}

		dim.height += rowHeight;
	}
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ._sampleInput1.txt                                                                                  000644  000765  000024  00000000503 14732046174 014704  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                             Mac OS X            	   2       C                                      ATTR      C   �   G                  �     com.apple.lastuseddate#PS         ,  .com.apple.metadata:kMDItemTextContentLanguage      8     com.apple.provenance 舳pg    笞f    bplist00Ren                             ｼ4{tかﾁ                                                                                                                                                                                             PaxHeader/sampleInput1.txt                                                                          000644  000765  000024  00000000763 14732046174 016450  x                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         30 mtime=1734888572.099741146
57 LIBARCHIVE.xattr.com.apple.provenance=AQIAvDQUe3SCqcE
49 SCHILY.xattr.com.apple.provenance= ｼ4{tかﾁ
127 LIBARCHIVE.xattr.com.apple.metadata:kMDItemTextContentLanguage=YnBsaXN0MDBSZW4IAAAAAAAAAQEAAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAs
108 SCHILY.xattr.com.apple.metadata:kMDItemTextContentLanguage=bplist00Ren                            
69 LIBARCHIVE.xattr.com.apple.lastuseddate#PS=5HdwZwAAAADimmYRAAAAAA
59 SCHILY.xattr.com.apple.lastuseddate#PS=舳pg    笞f    
             sampleInput1.txt                                                                                    000644  000765  000024  00000004674 14732046174 014504  0                                                                                                    ustar 00mrklh                           staff                           000000  000000                                                                                                                                                                         add|1|AI Journal|Neural Networks|Alice Johnson
add|2|CS Review|Sorting Algorithms|Bob Smith
add|3|BioMed|Genome Sequencing|Carol Lee
add|4|Math Annals|Number Theory|David Brown
add|5|Physics Today|Quantum Mechanics|Emily Davis
add|6|Economics Quarterly|Market Analysis|Frank Wilson
add|7|AI Journal|Deep Learning|Grace Martin
add|8|CS Review|Hashing Techniques|Henry Clark
add|9|BioMed|Protein Folding|Ivy Baker
add|10|Math Annals|Topology|Jack Turner
add|11|Physics Today|Thermodynamics|Karen Evans
add|12|Economics Quarterly|Risk Management|Leo Young
add|13|Journal of Chemistry|Molecular Bonds|Mia Collins
add|14|AstroPhysics Letters|Galaxy Formation|Nathan Moore
add|15|CS Advances|Graph Algorithms|Olivia Scott
add|16|AI Journal|NLP Techniques|Paul Reed
add|17|BioMed|Cellular Pathways|Quinn Hughes
add|18|Math Annals|Abstract Algebra|Rachel Price
add|19|Physics Today|Optics|Sam White
add|20|Economics Quarterly|Supply Chain|Tina Murphy
add|21|Journal of Chemistry|Catalysis Methods|Uma Perry
add|22|AstroPhysics Letters|Exoplanet Studies|Victor Brooks
add|23|CS Advances|Distributed Systems|Wendy Adams
add|24|AI Journal|GAN Applications|Xander Lopez
add|25|Physics Today|Relativity|Yara Bell
add|26|Economics Quarterly|Economic Forecasts|Zane Hall
add|27|Medical Innovations|Stem Cell Research|Alex Green
add|28|Journal of Biology|Marine Biology|Bella Carter
add|29|BioMed|Cancer Studies|Cody Wright
add|30|Math Annals|Combinatorics|Dana Mitchell
add|31|Physics Today|Fluid Dynamics|Ethan King
add|32|CS Advances|Data Compression|Fiona Baker
add|33|Medical Innovations|Genetic Engineering|Gina Torres
add|34|Journal of Biology|Botany Insights|Hank Lopez
add|35|Math Annals|Linear Algebra|Isla Grant
add|36|AI Journal|AI Ethics|Jake Adams
add|37|Physics Today|Plasma Physics|Kara Steele
add|38|AstroPhysics Letters|Black Hole Physics|Liam Rogers
add|39|Journal of Biology|Cell Biology|Mona Carter
add|40|Medical Innovations|Drug Development|Noah Simmons
add|41|CS Review|Big Data|Olive James
add|42|CS Advances|Cybersecurity|Piper Nelson
add|43|BioMed|Virology|Quinn Harper
add|44|Journal of Chemistry|Chemical Reactions|Reed Cook
add|45|AstroPhysics Letters|Star Formation|Sofia Taylor
add|46|Physics Today|Cosmology|Theo Foster
add|47|Medical Innovations|Clinical Trials|Una Moore
add|48|Journal of Biology|Ecology Studies|Victor Grant
add|49|Math Annals|Matrix Theory|Willa James
add|50|Economics Quarterly|Global Trade|Xavier Ward
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    