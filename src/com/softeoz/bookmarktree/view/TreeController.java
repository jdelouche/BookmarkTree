package com.softeoz.bookmarktree.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.softeoz.bookmarktree.domain.UrlDocument;

@ManagedBean(name = "treeController")
@SessionScoped
public class TreeController {

	private TreeNode root;

	private DefaultTreeNode selectedNode;

	private String nodePath;

	@ManagedProperty(value = "#{displayDoc}")
	private DisplayController displayDoc;

	@ManagedProperty(value = "#{crudDoc}")
	private CrudController crudDoc;

	public TreeController() {

		root = new DefaultTreeNode(new UrlDocument("Base", "about:blank",
				(TreeNode) null), (TreeNode) null);

		DefaultTreeNode top = new DefaultTreeNode(new UrlDocument("Root",
				"about:blank", root), root);

		selectedNode = top;
		nodePath = "/Root";

		DefaultTreeNode folder1 = new DefaultTreeNode(new UrlDocument(
				"Newspapers", "about:blank", top), top);

		new DefaultTreeNode(new UrlDocument("libe", "http://www.liberation.fr",
				folder1), folder1);

		new DefaultTreeNode(new UrlDocument("lemonde", "http://www.lemonde.fr",
				folder1), folder1);

		DefaultTreeNode folder2 = new DefaultTreeNode(new UrlDocument(
				"Web Design", "about:blank", top), top);

		new DefaultTreeNode(new UrlDocument("w3schools",
				"http://www.w3schools.com/", folder2), folder2);

		new DefaultTreeNode(new UrlDocument("primefaces",
				"http://www.primefaces.org/showcase/ui/home.jsf", folder2),
				folder2);

		new DefaultTreeNode(new UrlDocument("prettyfaces",
				"http://ocpsoft.org/docs/prettyfaces/3.3.2/en-US/html_single/",
				folder2), folder2);

	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode.setSelected(false);
		this.selectedNode = (DefaultTreeNode) selectedNode;
		this.selectedNode.setSelected(true);

	}

	public String displayNode() {

		String[] path = getNodePath().split("/");
		expandTree(root, path);

		return null;

	}

	private void expandTree(TreeNode start, String[] path) {

		for (String item : path) {
			for (TreeNode node : start.getChildren()) {
				UrlDocument doc = (UrlDocument) node.getData();
				if (doc.getName().matches(item)) {
					node.getParent().setExpanded(true);
					start = node;
					break;
				}
			}
		}
		setSelectedNode(start);
		UrlDocument doc = (UrlDocument) start.getData();
		displayDoc.setDoc(doc);
		crudDoc.setTreeControl(this);
		crudDoc.setDoc(doc);

	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode node) {
		root = node;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public DisplayController getDisplayDoc() {
		return displayDoc;
	}

	public void setDisplayDoc(DisplayController displayDoc) {
		this.displayDoc = displayDoc;
	}

	public CrudController getCrudDoc() {
		return crudDoc;
	}

	public void setCrudDoc(CrudController crudDoc) {
		this.crudDoc = crudDoc;
	}

}
