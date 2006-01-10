package org.antlr.works.project;

import edu.usfca.xj.appkit.utils.XJFileChooser;
import org.antlr.works.components.project.CContainerProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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

public class ProjectToolbar {

    public Box toolbar;

    public JButton addFile;
    public JButton removeFile;

    public JButton build;

    public CContainerProject project;

    public ProjectToolbar(CContainerProject project) {
        this.project = project;

        createInterface();
        addActions();
    }

    public JComponent getToolbar() {
        return toolbar;
    }

    public void createInterface() {
        toolbar = Box.createHorizontalBox();
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        toolbar.add(Box.createHorizontalStrut(5));
        toolbar.add(addFile = (JButton)createNewButton("+", "Add File", new Dimension(40, 32)));
        toolbar.add(removeFile = (JButton)createNewButton("-", "Remove File", new Dimension(40, 32)));
        toolbar.add(Box.createHorizontalStrut(15));
        toolbar.add(build = (JButton)createNewButton("Build", "Build Project", new Dimension(80, 32)));
    }

    public void addActions() {
        addFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XJFileChooser chooser = XJFileChooser.shared();
                if(chooser.displayOpenDialog(project.getJavaContainer(), (List)null, (List)null, true)) {
                    project.addFilePaths(chooser.getSelectedFilePaths());
                }
            }
        });

        removeFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                project.removeSelectedFile();
            }
        });

        build.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                project.build();
            }
        });

    }

    public AbstractButton createNewButton(String title, String tooltip, Dimension d) {
        AbstractButton button;
        button = new JButton(title);
        button.setToolTipText(tooltip);
        button.setMinimumSize(d);
        button.setMaximumSize(d);
        button.setPreferredSize(d);
        button.setFocusable(false);
        return button;
    }

    public AbstractButton createNewButton(ImageIcon icon, String tooltip, boolean toggle) {
        AbstractButton button;
        if(toggle)
            button = new JToggleButton(icon);
        else
            button = new JButton(icon);
        button.setToolTipText(tooltip);
        Dimension d = new Dimension(32, 32);
        button.setMinimumSize(d);
        button.setMaximumSize(d);
        button.setPreferredSize(d);
        button.setFocusable(false);
        return button;
    }
}
