package org.antlr.works.editor;

import edu.usfca.xj.foundation.notification.XJNotificationCenter;
import edu.usfca.xj.foundation.notification.XJNotificationObserver;
import org.antlr.works.components.grammar.CEditorGrammar;
import org.antlr.works.debugger.Debugger;
import org.antlr.works.utils.IconManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*

[The "BSD licence"]
Copyright (c) 2005 Jean Bovet
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

public class EditorToolbar implements XJNotificationObserver {

    public Box toolbar;

    public JButton backward;
    public JButton forward;

    public JToggleButton sort;
    public JButton sd;
    public JButton coloring;
    public JButton analysis;

    public JButton ideas;
    public JButton underlying;
    public JButton tips;

    public JButton find;

    public CEditorGrammar editor;

    public EditorToolbar(CEditorGrammar editor) {
        this.editor = editor;

        createInterface();
        addActions();

        XJNotificationCenter.defaultCenter().addObserver(this, Debugger.NOTIF_DEBUG_STARTED);
        XJNotificationCenter.defaultCenter().addObserver(this, Debugger.NOTIF_DEBUG_STOPPED);
    }

    public void close() {
        XJNotificationCenter.defaultCenter().removeObserver(this);
    }

    public JComponent getToolbar() {
        return toolbar;
    }

    public void notificationFire(Object source, String name) {
        if(name.equals(Debugger.NOTIF_DEBUG_STARTED)) {
            find.setEnabled(false);
        } else if(name.equals(Debugger.NOTIF_DEBUG_STOPPED)) {
            find.setEnabled(true);
        }
    }

    /*public void applyPrefs() {
        sd.setSelected(true);
        coloring.setSelected(true);
        analysis.setSelected(true);
        ideas.setSelected(true);
        underlying.setSelected(true);
        tips.setSelected(true);
    } */

    public void createInterface() {
        toolbar = Box.createHorizontalBox();
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        toolbar.add(Box.createHorizontalStrut(5));
        toolbar.add(backward = (JButton)createNewButton(IconManager.shared().getIconBackward(), "Back", false));
        toolbar.add(forward = (JButton)createNewButton(IconManager.shared().getIconForward(), "Forward", false));
        toolbar.add(Box.createHorizontalStrut(15));
        toolbar.add(sort = (JToggleButton)createNewButton(IconManager.shared().getIconSort(), "Toggle Sort rules", true));
        toolbar.add(sd = (JButton)createNewButton(IconManager.shared().getIconSyntaxDiagram(), "Toggle Syntax diagram", false));
        toolbar.add(coloring = (JButton)createNewButton(IconManager.shared().getIconColoring(), "Toggle Syntax coloring", false));
        toolbar.add(ideas = (JButton)createNewButton(IconManager.shared().getIconIdea(), "Toggle Syntax ideas", false));
//        toolbar.add(analysis = (JButton)createNewButton(IconManager.shared().getIconAnalysis(), "Syntax analysis", false));
        toolbar.add(Box.createHorizontalStrut(15));
//        toolbar.add(underlying = (JButton)createNewButton(IconManager.shared().getIconUnderlying(), "Syntax underlying", false));
//        toolbar.add(tips = (JButton)createNewButton(IconManager.shared().getIconTips(), "Syntax tips", false));
        toolbar.add(Box.createHorizontalStrut(15));
        toolbar.add(find = (JButton)createNewButton(IconManager.shared().getIconFind(), "Find text", false));
    }

    public void addActions() {
        backward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.menuGoTo.goToBackward();
            }
        });

        forward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.menuGoTo.goToForward();
            }
        });

        sort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.toggleRulesSorting();
            }
        });

        sd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.toggleSyntaxDiagram();
            }
        });

        coloring.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.toggleSyntaxColoring();
            }
        });

/*        analysis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.toggleAnalysis();
            }
        });*/

        ideas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.toggleIdeas();
            }
        });

/*        underlying.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.toggleUnderlying();
            }
        });

        tips.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.toggleTips();
            }
        });*/

        find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.menuFind.find();
            }
        });
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
