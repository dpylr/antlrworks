package org.antlr.works.project;

import edu.usfca.xj.foundation.XJUtils;
import org.antlr.works.components.ComponentContainer;
import org.antlr.works.components.ComponentEditor;
import org.antlr.works.components.grammar.CEditorGrammar;
import org.antlr.works.components.project.CContainerProject;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
/*

[The "BSD licence"]
Copyright (c) 2005-2006 Jean Bovet
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

public class ProjectFileItem {

    public static final String FILE_GRAMMAR_EXTENSION = ".g";
    public static final String FILE_JAVA_EXTENSION = ".java";
    public static final String FILE_STG_EXTENSION = ".stg";
    public static final String FILE_ST_EXTENSION = ".st";
    public static final String FILE_TEXT_EXTENSION = ".txt";

    public static final String FILE_TYPE_GRAMMAR = "FILE_TYPE_GRAMMAR";
    public static final String FILE_TYPE_JAVA = "FILE_TYPE_JAVA";
    public static final String FILE_TYPE_STG = "FILE_TYPE_STG";
    public static final String FILE_TYPE_ST = "FILE_TYPE_ST";
    public static final String FILE_TYPE_TEXT = "FILE_TYPE_TEXT";
    public static final String FILE_TYPE_UNKNOWN = "FILE_TYPE_UNKNOWN";

    protected CContainerProject project;
    protected ComponentContainer container;

    protected String fileName;
    protected String fileType;
    protected boolean opened;
    protected boolean editorGrammarBottomComponentVisible;

    public ProjectFileItem(CContainerProject project) {
        this.project = project;
    }

    public ProjectFileItem(CContainerProject project, String name) {
        setFileName(name);
        this.project = project;
    }

    public static String getFileType(String filePath) {
        if(filePath.endsWith(FILE_GRAMMAR_EXTENSION))
            return FILE_TYPE_GRAMMAR;
        if(filePath.endsWith(FILE_STG_EXTENSION))
            return FILE_TYPE_STG;
        if(filePath.endsWith(FILE_ST_EXTENSION))
            return FILE_TYPE_ST;
        if(filePath.endsWith(FILE_JAVA_EXTENSION))
            return FILE_TYPE_JAVA;
        if(filePath.endsWith(FILE_TEXT_EXTENSION))
            return FILE_TYPE_TEXT;

        return FILE_TYPE_UNKNOWN;
    }

    public static String getFileTypeName(String type) {
        if(type.equals(FILE_TYPE_GRAMMAR))
            return "Grammar";
        if(type.equals(FILE_TYPE_STG))
            return "ST Group";
        if(type.equals(FILE_TYPE_ST))
            return "ST";
        if(type.equals(FILE_TYPE_JAVA))
            return "Java";
        if(type.equals(FILE_TYPE_TEXT))
            return "Text";

        return "Unkown";
    }

    public void setOpened(boolean flag) {
        opened = flag;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isDirty() {
        if(container != null)
            return container.getDocument().isDirty();
        else
            return false;
    }

    public boolean save() {
        if(container != null && container.getDocument().isDirty())
            return container.getDocument().performSave(false);
        else
            return false;
    }

    public void close() {
        if(container != null)
            container.close();
    }

    public void setComponentContainer(ComponentContainer container) {
        this.container = container;
    }
    
    public ComponentContainer getComponentContainer() {
        return container;
    }

    // @todo replace that by EditorGrammarPersistentStateData...
    public void setEditorGrammarBottomVisible(boolean visible) {
        editorGrammarBottomComponentVisible = visible;
    }

    public boolean isEditorGrammarBottomVisible_() {
        return editorGrammarBottomComponentVisible;
    }

    public boolean isEditorGrammarBottomVisible() {
        if(container == null)
            return false;

        ComponentEditor editor = container.getEditor();
        if(editor instanceof CEditorGrammar) {
            CEditorGrammar eg = (CEditorGrammar)editor;
            return eg.isBottomComponentVisible();
        } else
            return false;
    }

    public JPanel getEditorPanel() {
        if(container == null)
            return null;
        else
            return container.getEditor().getPanel();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        this.fileType = getFileType(fileName);
    }

    public String getFilePath() {
        return XJUtils.concatPath(project.getSourcePath(), fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }
    
    public void windowActivated() {
        if(container != null)
            container.getEditor().componentActivated();
    }

    public boolean handleExternalModification() {
        if(container == null)
            return false;

        if(container.getDocument().isModifiedOnDisk()) {
            container.getEditor().componentDocumentContentChanged();
            container.getDocument().synchronizeLastModifiedDate();
            return true;
        } else
            return false;
    }

    protected static final String KEY_FILE_NAME = "KEY_FILE_NAME";
    protected static final String KEY_FILE_OPENED = "KEY_FILE_OPENED";
    protected static final String KEY_EDITOR_GRAMMAR_BOTTOM_VISIBLE = "KEY_EDITOR_GRAMMAR_BOTTOM_VISIBLE";

    public void setPersistentData(Map data) {
        setFileName((String)data.get(KEY_FILE_NAME));
        setOpened(((Boolean)data.get(KEY_FILE_OPENED)).booleanValue());
        setEditorGrammarBottomVisible(((Boolean)data.get(KEY_EDITOR_GRAMMAR_BOTTOM_VISIBLE)).booleanValue());
    }

    public Map getPersistentData() {
        Map data = new HashMap();
        data.put(KEY_FILE_NAME, fileName);
        data.put(KEY_FILE_OPENED, Boolean.valueOf(opened));
        data.put(KEY_EDITOR_GRAMMAR_BOTTOM_VISIBLE, Boolean.valueOf(isEditorGrammarBottomVisible()));
        return data;
    }

    /** Called by the XJTree to display the cell content. Use only the last path component
     * (that is the name of file) only.
     */

    public String toString() {
        return getFileName();
    }

}
