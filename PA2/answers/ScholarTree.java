import java.util.ArrayList;
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
		primaryRoot = add_to_primary_tree(primaryRoot, paper);
		secondaryRoot = add_to_secondary_tree(secondaryRoot, paper);
	}

	private ScholarNode add_to_primary_tree(ScholarNode root, CengPaper paper){
		if(root == null){
			ScholarNodePrimaryLeaf new_root = new ScholarNodePrimaryLeaf(null);
			new_root.getPapers().add(paper);
			return new_root;
		}

		ScholarNode current_node = root;
		while(current_node.type == ScholarNodeType.Internal){
			ScholarNodePrimaryIndex internal_node = (ScholarNodePrimaryIndex) current_node;
			boolean found = false;
			for(int i=0; i<internal_node.paperIdCount(); i++){
				if(paper.paperId()<internal_node.paperIdAtIndex(i)){
					current_node = internal_node.getChildrenAt(i);
					found = true;
					break;
				}
			}
			if(!found){
				current_node = internal_node.getChildrenAt(internal_node.paperIdCount());
			}
		}

		ScholarNodePrimaryLeaf leaf_node = (ScholarNodePrimaryLeaf) current_node;
		ArrayList<CengPaper> papers = leaf_node.getPapers();
		papers.add(paper);
		papers.sort((p1, p2) -> p1.paperId().compareTo(p2.paperId()));

		if(papers.size() > 2*ScholarNode.order){
			return split_primary_leaf(leaf_node, root);
		}
		return root;
	}

	private ScholarNode split_primary_leaf(ScholarNodePrimaryLeaf leaf_node, ScholarNode root){
		ArrayList<CengPaper> papers = leaf_node.getPapers();
		int mid_index = papers.size()/2;
		ArrayList<CengPaper> right_papers = new ArrayList<>(papers.subList(mid_index, papers.size()));
		papers.subList(mid_index, papers.size()).clear();
		ScholarNodePrimaryLeaf new_leaf_node = new ScholarNodePrimaryLeaf(leaf_node.getParent(), right_papers);

		if(leaf_node.getParent() == null){
			ScholarNodePrimaryIndex new_root = new ScholarNodePrimaryIndex(null);
			new_root.get_paper_ids().add(right_papers.get(0).paperId());
			new_root.get_children().add(leaf_node);
			new_root.get_children().add(new_leaf_node);
			leaf_node.setParent(new_root);
			new_leaf_node.setParent(new_root);
			return new_root;
		}

		ScholarNodePrimaryIndex parent = (ScholarNodePrimaryIndex) leaf_node.getParent();
		int insert_index = parent.get_paper_ids().size();

		for(int i=0; i<parent.get_paper_ids().size(); i++){
			if(right_papers.get(0).paperId()<parent.paperIdAtIndex(i)){
				insert_index = i;
				break;
			}
		}

		parent.get_paper_ids().add(insert_index, right_papers.get(0).paperId());
		parent.get_children().add(insert_index+1, new_leaf_node);

		if(parent.get_paper_ids().size() > 2*ScholarNode.order){
			return split_primary_internal(parent, root);
		}
		return root;
	}

	private ScholarNode split_primary_internal(ScholarNodePrimaryIndex internal_node, ScholarNode root){
		ArrayList<Integer> paper_ids = internal_node.get_paper_ids();
		ArrayList<ScholarNode> children = internal_node.get_children();
		int mid_index = paper_ids.size()/2;
		Integer mid_paper_id = paper_ids.get(mid_index);
		ArrayList<Integer> right_paper_ids = new ArrayList<>(paper_ids.subList(mid_index+1, paper_ids.size()));
		ArrayList<ScholarNode> right_children = new ArrayList<>(children.subList(mid_index+1, children.size()));
		ScholarNodePrimaryIndex new_internal_node = new ScholarNodePrimaryIndex(internal_node.getParent(), right_paper_ids, right_children);

		for(ScholarNode child : right_children){
			child.setParent(new_internal_node);
		}

		paper_ids.subList(mid_index, paper_ids.size()).clear();
		children.subList(mid_index+1, children.size()).clear();

		if(internal_node.getParent() == null){
			ScholarNodePrimaryIndex new_root = new ScholarNodePrimaryIndex(null);
			new_root.get_paper_ids().add(mid_paper_id);
			new_root.get_children().add(internal_node);
			new_root.get_children().add(new_internal_node);
			internal_node.setParent(new_root);
			new_internal_node.setParent(new_root);
			return new_root;
		}

		ScholarNodePrimaryIndex parent = (ScholarNodePrimaryIndex) internal_node.getParent();
		int insert_index = parent.get_paper_ids().size();

		for(int i=0; i<parent.get_paper_ids().size(); i++){
			if(mid_paper_id<parent.paperIdAtIndex(i)){
				insert_index = i;
				break;
			}
		}

		parent.get_paper_ids().add(insert_index, mid_paper_id);
		parent.get_children().add(insert_index+1, new_internal_node);
		new_internal_node.setParent(parent);

		if(parent.get_paper_ids().size() > 2*ScholarNode.order){
			return split_primary_internal(parent, root);
		}
		return root;
	}

	private ScholarNode add_to_secondary_tree(ScholarNode root, CengPaper paper){
		if(root == null){
			ScholarNodeSecondaryLeaf new_root = new ScholarNodeSecondaryLeaf(null);
			new_root.getJournals().add(paper.journal());
			ArrayList<Integer> paper_ids = new ArrayList<>();
			paper_ids.add(paper.paperId());
			new_root.getPaperIdBucket().add(paper_ids);
			return new_root;
		}

		ScholarNode current_node = root;
		while(current_node.type == ScholarNodeType.Internal){
			ScholarNodeSecondaryIndex internal_node = (ScholarNodeSecondaryIndex) current_node;
			boolean found = false;
			for(int i=0; i<internal_node.journalCount(); i++){
				if(paper.journal().compareToIgnoreCase(internal_node.journalAtIndex(i))<0){
					current_node = internal_node.getChildrenAt(i);
					found = true;
					break;
				}
			}
			if(!found){
				current_node = internal_node.getChildrenAt(internal_node.journalCount());
			}
		}

		ScholarNodeSecondaryLeaf leaf_node = (ScholarNodeSecondaryLeaf) current_node;
		ArrayList<String> journals = leaf_node.getJournals();
		ArrayList<ArrayList<Integer>> paper_id_bucket = leaf_node.getPaperIdBucket();

		int index = journals.indexOf(paper.journal());
		if(index == -1){
			journals.add(paper.journal());
			paper_id_bucket.add(new ArrayList<>());
			boolean swapped;
			do{
				swapped=false;
				for(int i=0; i<journals.size()-1; i++){
					if(journals.get(i).compareToIgnoreCase(journals.get(i+1))>0){
						String temp_journal = journals.get(i);
						ArrayList<Integer> temp_bucket = paper_id_bucket.get(i);
						journals.set(i, journals.get(i+1));
						paper_id_bucket.set(i, paper_id_bucket.get(i+1));
						journals.set(i+1, temp_journal);
						paper_id_bucket.set(i+1, temp_bucket);
						swapped = true;
					}
				}
			}
			while(swapped);
			index = journals.indexOf(paper.journal());
		}

		paper_id_bucket.get(index).add(paper.paperId());
		if(journals.size() > 2*ScholarNode.order){
			return split_secondary_leaf(leaf_node, root);
		}
		return root;
	}

	private ScholarNode split_secondary_leaf(ScholarNodeSecondaryLeaf leaf_node, ScholarNode root){
		ArrayList<String> journals = leaf_node.getJournals();
		ArrayList<ArrayList<Integer>> paper_id_bucket = leaf_node.getPaperIdBucket();
		int mid_index = journals.size()/2;
		ArrayList<String> right_journals = new ArrayList<>(journals.subList(mid_index, journals.size()));
		ArrayList<ArrayList<Integer>> right_paper_id_bucket = new ArrayList<>(paper_id_bucket.subList(mid_index, paper_id_bucket.size()));
		journals.subList(mid_index, journals.size()).clear();
		paper_id_bucket.subList(mid_index, paper_id_bucket.size()).clear();
		ScholarNodeSecondaryLeaf new_leaf_node = new ScholarNodeSecondaryLeaf(leaf_node.getParent(), right_journals, right_paper_id_bucket);

		if(leaf_node.getParent() == null){
			ScholarNodeSecondaryIndex new_root = new ScholarNodeSecondaryIndex(null);
			new_root.getJournals().add(right_journals.get(0));
			new_root.get_children().add(leaf_node);
			new_root.get_children().add(new_leaf_node);
			leaf_node.setParent(new_root);
			new_leaf_node.setParent(new_root);
			return new_root;
		}

		ScholarNodeSecondaryIndex parent = (ScholarNodeSecondaryIndex) leaf_node.getParent();
		int insert_index = parent.getJournals().size();

		for(int i=0; i<parent.getJournals().size(); i++){
			if(right_journals.get(0).compareToIgnoreCase(parent.journalAtIndex(i))<0){
				insert_index = i;
				break;
			}
		}

		parent.getJournals().add(insert_index, right_journals.get(0));
		parent.get_children().add(insert_index+1, new_leaf_node);
		new_leaf_node.setParent(parent);

		if(parent.getJournals().size() > 2*ScholarNode.order){
			return split_secondary_internal(parent, root);
		}
		return root;
	}

	private ScholarNode split_secondary_internal(ScholarNodeSecondaryIndex internal_node, ScholarNode root){
		ArrayList<String> journals = internal_node.getJournals();
		ArrayList<ScholarNode> children = internal_node.get_children();
		int mid_index = journals.size()/2;
		String mid_journal = journals.get(mid_index);
		ArrayList<String> right_journals = new ArrayList<>(journals.subList(mid_index+1, journals.size()));
		ArrayList<ScholarNode> right_children = new ArrayList<>(children.subList(mid_index+1, children.size()));
		ScholarNodeSecondaryIndex new_internal_node = new ScholarNodeSecondaryIndex(internal_node.getParent(), right_journals, right_children);

		for(ScholarNode child : right_children){
			child.setParent(new_internal_node);
		}

		journals.subList(mid_index, journals.size()).clear();
		children.subList(mid_index+1, children.size()).clear();

		if(internal_node.getParent() == null){
			ScholarNodeSecondaryIndex new_root = new ScholarNodeSecondaryIndex(null);
			new_root.getJournals().add(mid_journal);
			new_root.get_children().add(internal_node);
			new_root.get_children().add(new_internal_node);
			internal_node.setParent(new_root);
			new_internal_node.setParent(new_root);
			return new_root;
		}

		ScholarNodeSecondaryIndex parent = (ScholarNodeSecondaryIndex) internal_node.getParent();
		int insert_index = parent.getJournals().size();

		for(int i=0; i<parent.getJournals().size(); i++){
			if(mid_journal.compareToIgnoreCase(parent.journalAtIndex(i))<0){
				insert_index = i;
				break;
			}
		}

		parent.getJournals().add(insert_index, mid_journal);
		parent.get_children().add(insert_index+1, new_internal_node);
		new_internal_node.setParent(parent);

		if(parent.getJournals().size() > 2*ScholarNode.order){
			return split_secondary_internal(parent, root);
		}
		return root;
	}

	public CengPaper searchPaper(Integer paperId) {
		// TODO: Implement this method
		// find the paper with the searched paperId in primary B+ tree
		// return value will not be tested, just print according to the specifications
		return search_paper_in_primary_tree(primaryRoot, paperId, 0);
	}

	private CengPaper search_paper_in_primary_tree(ScholarNode current_node, Integer paperId, int level){
		if (current_node == null){
			System.out.println("Could not find " + paperId);
			return null;
		}

		String indent = "\t".repeat(level);

		if(current_node instanceof ScholarNodePrimaryIndex){
			ScholarNodePrimaryIndex internal_node = (ScholarNodePrimaryIndex) current_node;

			System.out.println(indent + "<index>");
			for(Integer key : internal_node.get_paper_ids()){
				System.out.println(indent + key);
			}
			System.out.println(indent + "</index>");

			for(int i=0; i<internal_node.paperIdCount(); i++){
				if(paperId<internal_node.paperIdAtIndex(i)){
					return search_paper_in_primary_tree(internal_node.getChildrenAt(i), paperId, level+1);
				}
			}
			return search_paper_in_primary_tree(internal_node.getChildrenAt(internal_node.paperIdCount()), paperId, level+1);
		}

		else if(current_node instanceof ScholarNodePrimaryLeaf){
			ScholarNodePrimaryLeaf leaf_node = (ScholarNodePrimaryLeaf) current_node;

			for(CengPaper paper : leaf_node.getPapers()){
				if(paper.paperId().equals(paperId)){
					System.out.println(indent + "<data>");
					System.out.println(indent + "<record>" + paperId + "|" + paper.journal() + "|" + paper.paperName() + "|" + paper.author() + "</record>");
					System.out.println(indent + "</data>");
					return paper;
				}
			}
			System.out.println("Could not find " + paperId);
			return null;
		}
		return null;
	}

	private CengPaper search_paper_in_primary_tree_silent(ScholarNode current_node, Integer paperId){
		if(current_node == null){
			return null;
		}

		if(current_node instanceof ScholarNodePrimaryIndex){
			ScholarNodePrimaryIndex internal_node = (ScholarNodePrimaryIndex) current_node;

			for(int i=0; i<internal_node.paperIdCount(); i++){
				if(paperId<internal_node.paperIdAtIndex(i)){
					return search_paper_in_primary_tree_silent(internal_node.getChildrenAt(i), paperId);
				}
			}
			return search_paper_in_primary_tree_silent(internal_node.getChildrenAt(internal_node.paperIdCount()), paperId);
		}

		else if (current_node instanceof ScholarNodePrimaryLeaf){
			ScholarNodePrimaryLeaf leaf_node = (ScholarNodePrimaryLeaf) current_node;

			for(CengPaper paper : leaf_node.getPapers()){
				if(paper.paperId().equals(paperId)){
					return paper;
				}
			}
		}
		return null;
	}

	public void searchJournal(String journal) {
		// TODO: Implement this method
		// find the journal with the searched journal in secondary B+ tree
		search_journal_in_secondary_tree(secondaryRoot, journal, 0);
	}

	private void search_journal_in_secondary_tree(ScholarNode current_node, String journal, int level){
		if (current_node == null){
			System.out.println("Could not find " + journal);
			return;
		}

		String indent = "\t".repeat(level);

		if(current_node instanceof ScholarNodeSecondaryIndex){
			ScholarNodeSecondaryIndex internal_node = (ScholarNodeSecondaryIndex) current_node;

			System.out.println(indent + "<index>");
			for(String key : internal_node.getJournals()){
				System.out.println(indent + key);
			}
			System.out.println(indent + "</index>");

			for(int i=0; i<internal_node.journalCount(); i++){
				if(journal.compareToIgnoreCase(internal_node.journalAtIndex(i))<0){
					search_journal_in_secondary_tree(internal_node.getChildrenAt(i), journal, level+1);
					return;
				}
			}
			search_journal_in_secondary_tree(internal_node.getChildrenAt(internal_node.journalCount()), journal, level+1);
		}

		else if (current_node instanceof ScholarNodeSecondaryLeaf){
			ScholarNodeSecondaryLeaf leaf_node = (ScholarNodeSecondaryLeaf) current_node;

			ArrayList<String> journals = leaf_node.getJournals();
			ArrayList<ArrayList<Integer>> paper_id_bucket = leaf_node.getPaperIdBucket();
			boolean found = false;

			for(int i=0; i<journals.size(); i++){
				if(Objects.equals(journals.get(i), journal)){
					if (!found){
						System.out.println(indent + "<data>");
						found = true;
					}
					System.out.println(indent + journal);
					for(Integer paperId : paper_id_bucket.get(i)){
						CengPaper paper = search_paper_in_primary_tree_silent(primaryRoot, paperId);
						if(paper != null){
							System.out.println(indent + "\t<record>" + paper.paperId() + "|" + paper.journal() + "|" + paper.paperName() + "|" + paper.author() + "</record>");
						}
					}
				}
			}
			if(found){
				System.out.println(indent + "</data>");
			}
			else{
				System.out.println("Could not find " + journal);
			}
			return;
		}
		return;
	}

	public void printPrimaryScholar() {
		// TODO: Implement this method
		// print the primary B+ tree in Depth-first order
		print_primary_tree(primaryRoot, 0);
	}

	private void print_primary_tree(ScholarNode current_node, int level){
		if(current_node == null){
			return;
		}

		String indent = "\t".repeat(level);

		if(current_node instanceof ScholarNodePrimaryIndex){
			ScholarNodePrimaryIndex internal_node = (ScholarNodePrimaryIndex) current_node;

			System.out.println(indent + "<index>");
			for(Integer key : internal_node.get_paper_ids()){
				System.out.println(indent + key);
			}
			System.out.println(indent + "</index>");

			for(int i=0; i<=internal_node.paperIdCount(); i++){
				print_primary_tree(internal_node.getChildrenAt(i), level+1);
			}
		}

		else if(current_node instanceof ScholarNodePrimaryLeaf){
			ScholarNodePrimaryLeaf leaf_node = (ScholarNodePrimaryLeaf) current_node;

			System.out.println(indent + "<data>");
			for(CengPaper paper : leaf_node.getPapers()){
				System.out.println(indent + "<record>" + paper.paperId() + "|" + paper.journal() + "|" + paper.paperName() + "|" + paper.author() + "</record>");
			}
			System.out.println(indent + "</data>");
		}
	}

	public void printSecondaryScholar() {
		// TODO: Implement this method
		// print the secondary B+ tree in Depth-first order
		print_secondary_tree(secondaryRoot, 0);
	}

	private void print_secondary_tree(ScholarNode current_node, int level){
		if(current_node == null){
			return;
		}

		String indent = "\t".repeat(level);

		if(current_node instanceof ScholarNodeSecondaryIndex){
			ScholarNodeSecondaryIndex internal_node = (ScholarNodeSecondaryIndex) current_node;

			System.out.println(indent + "<index>");
			for(String journal : internal_node.getJournals()){
				System.out.println(indent + journal);
			}
			System.out.println(indent + "</index>");

			for(int i=0; i<=internal_node.journalCount(); i++){
				print_secondary_tree(internal_node.getChildrenAt(i), level+1);
			}
		}

		else if(current_node instanceof ScholarNodeSecondaryLeaf){
			ScholarNodeSecondaryLeaf leaf_node = (ScholarNodeSecondaryLeaf) current_node;

			System.out.println(indent + "<data>");
			ArrayList<String> journals = leaf_node.getJournals();
			ArrayList<ArrayList<Integer>> paper_buckets = leaf_node.getPaperIdBucket();

			for(int i=0; i<journals.size(); i++){
				String journal_name = journals.get(i);
				ArrayList<Integer> paper_ids = paper_buckets.get(i);
				System.out.println(indent + journal_name);
				for(Integer paperId : paper_ids){
					System.out.println(indent + "\t<record>" + paperId + "</record>");
				}
			}
			System.out.println(indent + "</data>");
		}
	}
	// Extra functions if needed
}