package com.spreadtracker.ui.settings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Represents a singular node in a settings hierarchy.
 * Depending upon the implementation, this node could represent a setting,
 * a container of settings, a container of containers of settings, etc.
 * Each node has a parent and a set of children nodes.
 * A node without a parent represents a root level node, whose parent variable is null.
 * A node without any children represents a terminal vertex of the tree, whose children variable is an empty list of nodes.
 */
public abstract class SettingsNode implements ISavable {
    private boolean mParentSet = false;
    private SettingsNode mParent;
    private ArrayList<SettingsNode> mChildren = new ArrayList<>();
    private Context mCtx;
    private View mRoot;

    private boolean mIsDirty;

    public final SettingsNode getParent() { return mParent; }
    public final ArrayList<SettingsNode> getChildren () { return mChildren; }

    protected final Context getContext () {
        if (mCtx != null) return mCtx;
        SettingsNode node = mParent;
        while (node != null) {
            mCtx = node.getContext();
            if (mCtx != null) break;
            node = node.getParent();
        }
        return mCtx;
    }

    /**
     * Gets a root node in the hierarchy.
     * @return The root node to which all other nodes are children.
     */
    protected final SettingsNode getRootNode () {
        SettingsNode node = this;
        while (node.getParent() != null) {
            node = node.getParent();
        }
        return node;
    }

    public final View getRootView () {
        return mRoot;
    }

    protected final void setRootView (View v) {
        mRoot = v;
    }

    public final void setParent (SettingsNode parent) {
        if (mParentSet) return;
        mParentSet = true;
        mParent = parent;
    }

    public SettingsNode(SettingsNode... children) {
        if (children == null) return;
        for (SettingsNode child : children) addChild(child);
    }

    public SettingsNode (@NonNull Context ctx, SettingsNode... children) {
        this(children);
        mCtx = ctx;
    }

    public boolean addChild (@NonNull SettingsNode node) {
        if (mChildren.contains(node)) return false;
        mChildren.add(node);
        node.setParent(this);
        return true;
    }

    public boolean removeChild (@NonNull SettingsNode node) {
        return mChildren.remove(node);
    }

    @Override
    public boolean canSave() {
        for (SettingsNode child : mChildren)
            if (!child.canSave()) return false;
        return true;
    }

    @Override
    public void saveState() {
        for (SettingsNode child : mChildren)
            child.saveState();
    }

    @Override
    public void restoreState() {
        for (SettingsNode child : mChildren)
            child.restoreState();
    }

    public final boolean isDirty () {return mIsDirty;}

    @Override
    public void notifyDirty(boolean dirty) {
        mIsDirty = dirty;
        boolean childrenDirty = false;
        for (SettingsNode child : mChildren) {
            if (child.isDirty()) {
                childrenDirty = true;
                break;
            }
        }
        notifyChildrenDirty(childrenDirty);
        if (mParent != null) mParent.notifyDirty(dirty);
    }

    /**
     * Called when a parent's children are collectively dirty or undirty.
     * @param childrenDirty True if any of the children are dirty, and false otherwise.
     */
    public void notifyChildrenDirty(boolean childrenDirty) {}

    @Nullable
    protected ViewGroup getParentView () {
        if (mParent == null) return null;
        View pView = mParent.getRootView();
        if (pView == null) return null;
        if (pView instanceof ViewGroup) return (ViewGroup) pView;
        return null;
    }
}
