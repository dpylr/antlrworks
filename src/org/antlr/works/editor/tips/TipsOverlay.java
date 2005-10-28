package org.antlr.works.editor.tips;

import org.antlr.works.editor.EditorWindow;
import org.antlr.works.editor.swing.OverlayObject;
import org.antlr.works.editor.tooltip.ToolTipList;
import org.antlr.works.editor.tooltip.ToolTipListDelegate;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;
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

public class TipsOverlay extends OverlayObject implements ToolTipListDelegate {

    protected EditorWindow editor;
    protected ToolTipList toolTip;
    protected Point location;

    public TipsOverlay(EditorWindow editor, JFrame parentFrame, JComponent parentComponent) {
        super(parentFrame, parentComponent);
        this.editor = editor;
    }

    public void setTips(List tips) {
        toolTip.clear();
        for(Iterator iter = tips.iterator(); iter.hasNext();) {
            toolTip.addLine((String)iter.next());
        }
        toolTip.selectFirstLine();
    }

    public void setLocation(Point location) {
        this.location = location;
        resize();
    }

    public JComponent overlayCreateInterface() {
        JPanel panel = new JPanel(new BorderLayout());

        toolTip = new ToolTipList(this);
        panel.add(toolTip, BorderLayout.CENTER);

        return panel;
    }

    public void resize() {
        toolTip.resize();
        if(location != null)
            content.setBounds(location.x,  location.y, toolTip.getWidth(), toolTip.getHeight());
    }

    public boolean overlayWillDisplay() {
        return true;
    }

    public void toolTipListHide() {
        hide();
    }
}
