package com.spreadtracker.ui.settings;

/**
 * Represents an object whose state can be saved and restored
 * with an inflatable layout to represent the object.
 */
public interface ISavable extends ILayoutFactory {
    /**
     * If true, this savable object can be saved.
     * Return false if this object's data is in an invalid state,
     * or saving would be impossible.
     * Used to check if this object can be saved before a call to {@link ISavable#saveState()}
     * @return True if this object can be saved. False if this object cannot currently be saved.
     */
    boolean canSave();

    /**
     * Saves this objects state so that it can be restored at a later time.
     */
    void saveState();

    /**
     * Restores this object's state after being saved by an earlier call to {@link ISavable#saveState()},
     * or restores to some default value if no earlier state could be found.
     */
    void restoreState();

    /**
     * Call to notify to consumers of this object that the data in this object has or hasn't been modified.
     * @param dirty If true, the state of this object has been modified and can be saved by a call to {@link ISavable#saveState()}.
     *              If false, signifies that this object's state has not been modified.
     */
    void setDirty(boolean dirty);
}
