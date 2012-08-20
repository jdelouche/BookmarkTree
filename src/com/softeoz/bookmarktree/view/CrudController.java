package com.softeoz.bookmarktree.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.softeoz.bookmarktree.domain.UrlDocument;

/**
 * @author jdelouche
 * 
 */
@ManagedBean(name = "crudDoc")
@SessionScoped
public class CrudController {

	private TreeController treeControl;

	private String name;

	private String url;

	private TreeNode clipboard;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDoc(UrlDocument doc) {

		name = doc.getName();
		url = doc.getUrl();

	}

	public void setTreeControl(TreeController treeControl) {
		this.treeControl = treeControl;
	}

	public void createNode() {

		TreeNode selectedNode = treeControl.getSelectedNode();
		UrlDocument doc = new UrlDocument(name, url, selectedNode);

		new DefaultTreeNode(doc, selectedNode);

		treeControl.setNodePath(doc.getFullPath());

	}

	public void copyNode() {

		TreeNode selectedNode = treeControl.getSelectedNode();

		clipboard = new DefaultTreeNode(new UrlDocument("Base", "about:blank",
				(TreeNode) null), (TreeNode) null);

		duplicateTree(selectedNode, clipboard);

	}

	private void duplicateTree(TreeNode source, TreeNode target) {

		TreeNode node = duplicateNode(source, target);

		for (TreeNode item : source.getChildren()) {
			duplicateTree(item, node);
		}
	}

	private TreeNode duplicateNode(TreeNode source, TreeNode target) {
		UrlDocument doc = (UrlDocument) source.getData();
		TreeNode node = new DefaultTreeNode(new UrlDocument(doc.getName(),
				doc.getUrl(), target), target);
		return node;
	}

	public void pasteNode() {

		TreeNode selectedNode = treeControl.getSelectedNode();
		TreeNode node = clipboard.getChildren().get(0);
		boolean duplicated = false;
		for (TreeNode item : selectedNode.getChildren()) {
			UrlDocument doc = (UrlDocument) item.getData();
			UrlDocument docClipboard = (UrlDocument) node.getData();
			if (doc.getName().matches(docClipboard.getName())) {
				duplicated = true;
				break;
			}
		}
		if (!duplicated) {
			node.setParent(selectedNode);
			UrlDocument urlDocument = (UrlDocument) node.getData();
			urlDocument.setParentNode(selectedNode);
			urlDocument.pathUpdate();
			updateChildren(node);
			treeControl.setNodePath(urlDocument.getFullPath());
		}

	}

	public void deleteNode() {

		TreeNode selectedNode = treeControl.getSelectedNode();

		if (selectedNode.getParent() != treeControl.getRoot()) {

			selectedNode.getChildren().clear();

			DefaultTreeNode parent = (DefaultTreeNode) selectedNode.getParent();

			parent.getChildren().remove(selectedNode);

			UrlDocument doc = (UrlDocument) parent.getData();

			treeControl.setNodePath(doc.getFullPath());

		}
	}

	public void updateNode() {

		DefaultTreeNode selectedNode = (DefaultTreeNode) treeControl
				.getSelectedNode();

		TreeNode parent = selectedNode.getParent();
		UrlDocument doc = new UrlDocument(name, url, parent);

		selectedNode.setData(doc);

		treeControl.setNodePath(doc.getFullPath());

		updateChildren(selectedNode);

	}

	private void updateChildren(TreeNode node) {
		for (TreeNode item : node.getChildren()) {
			((UrlDocument) item.getData()).pathUpdate();
			updateChildren(item);
		}
	}

	@Override
	public String toString() {
		return name + ": " + url;
	}

}
